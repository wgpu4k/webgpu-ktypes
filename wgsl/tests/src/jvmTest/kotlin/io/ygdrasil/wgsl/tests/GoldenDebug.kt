package io.ygdrasil.wgsl.tests

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.TypeResolver
import io.ygdrasil.wgsl.parser.parseWgsl
import io.ygdrasil.wgsl.tests.validator.BackendType
import io.ygdrasil.wgsl.tests.validator.ValidatorFactory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

private val logger = KotlinLogging.logger {}

/**
 * Find the project root directory by looking for settings.gradle.kts
 */
private fun findProjectRoot(): Path {
    var current = Paths.get(".").toAbsolutePath()
    while (current != null) {
        if (Files.exists(current.resolve("settings.gradle.kts"))) {
            return current
        }
        current = current.parent
    }
    return Paths.get(".")
}

/**
 * Standalone golden test debugger for individual file testing.
 * Usage: ./gradlew :wgsl:tests:runGoldenDebug --args="<file.wgsl> [backend]"
 */
fun main(args: Array<String>) {
    val fileName = args.getOrNull(0) ?: run {
        println("Usage: GoldenDebug <file.wgsl> [backend]")
        println("Available backends: wgsl, msl, hlsl, glsl, spirv, ir")
        println("Example: GoldenDebug abstract-types-const.wgsl wgsl")
        return
    }
    
    val backend = args.getOrNull(1) ?: "wgsl"
    val debugMode = System.getenv("DEBUG")?.toBoolean() ?: false
    
    // Initialize backends
    registerAllBackends()
    
    val rootDir = findProjectRoot()
    val inputFile = rootDir.resolve("tests/golden/inputs/$fileName")
    
    if (!Files.exists(inputFile)) {
        println("ERROR: File not found: tests/golden/inputs/$fileName")
        return
    }
    
    println("Debugging: $fileName (backend: $backend)")
    println("=".repeat(60))
    
    val source = try {
        Files.readString(inputFile)
    } catch (e: Exception) {
        println("ERROR: Failed to read file: ${e.message}")
        return
    }
    
    try {
        runGoldenDebug(fileName, source, backend, debugMode)
        println("\n✓ All phases completed successfully!")
    } catch (e: GoldenTestException) {
        println("\n✗ FAILED: [${e.backend}] ${e.phase} failed for ${e.fileName}")
        println("   Message: ${e.message}")
        if (debugMode) {
            println("\nFull stack trace:")
            e.printStackTrace()
        }
    } catch (e: Exception) {
        println("\n✗ FAILED: Unexpected error")
        println("   Message: ${e.message}")
        if (debugMode) {
            println("\nFull stack trace:")
            e.printStackTrace()
        }
    }
}

/**
 * Execute all golden test phases for a single file with detailed logging.
 */
fun runGoldenDebug(fileName: String, source: String, backend: String, debugMode: Boolean) {
    // 1. Parse
    logger.info { "Phase 1: Parsing..." }
    val unit = parseWgsl(source)
    logger.info { "  ✓ Parse successful" }
    
    // 2. Resolve types
    logger.info { "Phase 2: Resolving types..." }
    val resolver = TypeResolver()
    val resolutionResult = resolver.resolve(unit)
    if (!resolutionResult.isSuccess) {
        throw GoldenTestException(fileName, backend, "type-resolution",
            "Unresolved references: ${resolutionResult.unresolvedReferences}")
    }
    logger.info { "  ✓ Type resolution successful" }
    
    // 3. Lower to IR
    logger.info { "Phase 3: Lowering to IR..." }
    val lowerer = Lowerer()
    val module = lowerer.lower(resolutionResult.resolvedUnit)
    logger.info { "  ✓ Lowering successful" }
    
    // 4. Generate backend code
    logger.info { "Phase 4: Generating $backend code..." }
    val writer = BackendRegistry.DEFAULT.get(backend)
        ?: throw GoldenTestException(fileName, backend, "backend-lookup", "Backend $backend not found")
    val output = writer.write(module, io.ygdrasil.wgsl.valid.ModuleInfo())
    logger.info { "  ✓ Code generation successful" }
    if (debugMode) {
        println("\n--- Generated $backend code ---")
        println(output)
        println("--- End of generated code ---\n")
    }
    
    // 5. Native Validation (if available)
    val type = when (backend.lowercase()) {
        "msl" -> BackendType.MSL
        "glsl" -> BackendType.GLSL
        "hlsl" -> BackendType.HLSL
        "spirv" -> BackendType.SPIRV
        else -> null
    }
    
    if (type != null && ValidatorFactory.isAvailable(type)) {
        logger.info { "Phase 5: Native validation for $backend..." }
        val stage = module.entryPoints.firstOrNull()?.stage?.let {
            when (it) {
                io.ygdrasil.wgsl.ir.ShaderStage.Vertex -> io.ygdrasil.wgsl.tests.validator.ShaderStage.VERTEX
                io.ygdrasil.wgsl.ir.ShaderStage.Fragment -> io.ygdrasil.wgsl.tests.validator.ShaderStage.FRAGMENT
                io.ygdrasil.wgsl.ir.ShaderStage.Compute -> io.ygdrasil.wgsl.tests.validator.ShaderStage.COMPUTE
            }
        }
        
        val validator = ValidatorFactory.getValidator(type)!!
        val validationResult = validator.validate(output, stage = stage)
        if (validationResult.isFailure) {
            throw GoldenTestException(
                fileName, backend, "native-validation",
                "Validation failed: ${validationResult.output}"
            )
        }
        logger.info { "  ✓ Native validation successful" }
    } else if (type != null) {
        logger.info { "  ⊘ Native validation not available for $backend" }
    }
}
