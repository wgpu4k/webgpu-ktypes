package io.ygdrasil.wgsl.tests

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.back.BackendOptions
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.TypeResolver
import io.ygdrasil.wgsl.parser.parseWgsl
import io.ygdrasil.wgsl.tests.validator.BackendType
import io.ygdrasil.wgsl.tests.validator.ValidatorFactory
import io.ygdrasil.wgsl.tests.roundtrip.WgslNormalizer
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

private val logger = KotlinLogging.logger {}

/**
 * Custom exception for golden test failures with clean error messages.
 * Stack trace is logged separately to keep stdout clean.
 */
class GoldenTestException(
    val fileName: String,
    val backend: String,
    val phase: String,
    message: String,
    cause: Throwable? = null
) : RuntimeException("[$backend] $phase failed for $fileName: $message", cause)

/**
 * Centralized error handler that logs the full exception but throws a clean error.
 */
private fun handleGoldenError(fileName: String, backend: String, phase: String, e: Throwable): Nothing {
    val simpleMessage = "[$backend] $phase failed for $fileName: ${e.message}"
    logger.error(e) { simpleMessage }
    throw GoldenTestException(fileName, backend, phase, e.message ?: "Unknown error", e)
}

abstract class GoldenTestBase(val backendName: String) : FunSpec({

    registerAllBackends()
    val goldenUpdate = System.getenv("GOLDEN_UPDATE")?.toBoolean() ?: false
    val goldenFilter = System.getenv("GOLDEN_FILTER")?.takeIf { it.isNotEmpty() }
    val rootDir = findProjectRoot()
    val inputDir = rootDir.resolve("tests/golden/inputs")
    val outputBaseDir = rootDir.resolve("tests/golden/outputs")

    context("$backendName Golden Tests") {
        val inputFiles = Files.list(inputDir)
            .filter { it.toString().endsWith(".wgsl") }
            .filter { goldenFilter == null || it.fileName.toString().contains(goldenFilter) }
            .toList()

        inputFiles.forEach { inputFile ->
            val fileName = inputFile.fileName.toString()
            test("Golden test: $fileName") {
                logger.debug { "Testing $fileName" }
                val source = Files.readString(inputFile)
                
                // 1. Parse
                logger.debug { "Parsing..." }
                val unit = try {
                    parseWgsl(source)
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "parse", e)
                }
                
                // 2. Resolve types
                logger.debug { "Resolving types..." }
                val resolver = TypeResolver()
                val resolutionResult = try {
                    resolver.resolve(unit)
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "type-resolution", e)
                }
                if (!resolutionResult.isSuccess) {
                    throw GoldenTestException(
                        fileName, backendName, "type-resolution",
                        "Unresolved references: ${resolutionResult.unresolvedReferences}"
                    )
                }
                
                // 3. Lower to IR
                logger.debug { "Lowering to IR..." }
                val lowerer = Lowerer()
                val module = try {
                    lowerer.lower(resolutionResult.resolvedUnit)
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "lowering", e)
                }
                
                // 4. Generate backend code
                logger.debug { "Generating backend code for $backendName..." }
                val writer = BackendRegistry.DEFAULT.get(backendName)
                    ?: throw GoldenTestException(fileName, backendName, "backend-lookup", "Backend $backendName not found")
                val output = try {
                    writer.write(module, io.ygdrasil.wgsl.valid.ModuleInfo())
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "code-generation", e)
                }
                
                // 5. Compare or Update
                val outputFile = outputBaseDir.resolve(backendName).resolve(fileName.replace(".wgsl", getExtension(backendName)))
                Files.createDirectories(outputFile.parent)
                
                if (goldenUpdate || !Files.exists(outputFile)) {
                    Files.writeString(outputFile, output)
                } else {
                    val expected = Files.readString(outputFile)
                    if (backendName.lowercase() == "wgsl") {
                        val normalizedActual = WgslNormalizer.normalize(output)
                        val normalizedExpected = WgslNormalizer.normalize(expected)
                        try {
                            normalizedActual shouldBe normalizedExpected
                        } catch (e: AssertionError) {
                            handleGoldenError(fileName, backendName, "comparison", e)
                        }
                    } else {
                        try {
                            output shouldBe expected
                        } catch (e: AssertionError) {
                            handleGoldenError(fileName, backendName, "comparison", e)
                        }
                    }
                }

                // 6. Native Validation (if available)
                val type = when (backendName.lowercase()) {
                    "msl" -> BackendType.MSL
                    "glsl" -> BackendType.GLSL
                    "hlsl" -> BackendType.HLSL
                    "spirv" -> BackendType.SPIRV
                    else -> null
                }

                if (type != null && ValidatorFactory.isAvailable(type)) {
                    logger.debug { "Native validation for $backendName..." }
                    val stage = module.entryPoints.firstOrNull()?.stage?.let {
                        when (it) {
                            io.ygdrasil.wgsl.ir.ShaderStage.Vertex -> io.ygdrasil.wgsl.tests.validator.ShaderStage.VERTEX
                            io.ygdrasil.wgsl.ir.ShaderStage.Fragment -> io.ygdrasil.wgsl.tests.validator.ShaderStage.FRAGMENT
                            io.ygdrasil.wgsl.ir.ShaderStage.Compute -> io.ygdrasil.wgsl.tests.validator.ShaderStage.COMPUTE
                        }
                    }

                    val validator = ValidatorFactory.getValidator(type)!!
                    val validationResult = try {
                        validator.validate(output, stage = stage)
                    } catch (e: Exception) {
                        handleGoldenError(fileName, backendName, "validation", e)
                    }
                    if (validationResult.isFailure) {
                        logger.debug { "Native validation FAILED for $fileName ($backendName): ${validationResult.output}" }
                        throw GoldenTestException(
                            fileName, backendName, "native-validation",
                            "Validation failed: ${validationResult.output}"
                        )
                    } else {
                        logger.debug { "Native validation SUCCESS for $fileName ($backendName)" }
                    }
                }
            }
        }
    }
})

private fun findProjectRoot(): java.nio.file.Path {
    var current = Paths.get(".").toAbsolutePath()
    while (current != null) {
        if (Files.exists(current.resolve("settings.gradle.kts"))) {
            return current
        }
        current = current.parent
    }
    return Paths.get(".")
}

private fun getExtension(backend: String): String = when (backend.lowercase()) {
    "msl" -> ".metal"
    "hlsl" -> ".hlsl"
    "glsl" -> ".glsl"
    "wgsl" -> ".wgsl"
    "ir" -> ".json"
    else -> ".txt"
}
