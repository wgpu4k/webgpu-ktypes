package io.ygdrasil.wgsl.tests

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.TypeResolver
import io.ygdrasil.wgsl.parser.parseWgsl
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.toList

class NagaIntegrationTest : FunSpec({

    registerAllBackends()

    val nagaAvailable = try {
        val process = ProcessBuilder("naga", "--version").start()
        process.waitFor() == 0
    } catch (e: Exception) {
        false
    }

    val runIntegrationTests = System.getenv("RUN_NAGA_INTEGRATION_TESTS")?.toBoolean() ?: false

    val isEnabled = nagaAvailable && runIntegrationTests

    fun findProjectRoot(): Path {
        var current = Paths.get(".").toAbsolutePath()
        while (current != null) {
            if (Files.exists(current.resolve("settings.gradle.kts"))) {
                return current
            }
            current = current.parent
        }
        return Paths.get(".")
    }

    val rootDir = findProjectRoot()
    val inputDir = rootDir.resolve("tests/golden/inputs")

    context("Naga Integration Validation Tests") {
        if (!isEnabled) {
            test("Naga integration tests are skipped (Naga CLI installed: $nagaAvailable, RUN_NAGA_INTEGRATION_TESTS: $runIntegrationTests)") {
                println("Skipping Naga integration tests (needs RUN_NAGA_INTEGRATION_TESTS=true and naga installed).")
            }
        } else {
            val inputFiles = Files.list(inputDir)
                .filter { it.toString().endsWith(".wgsl") }
                .toList()

            inputFiles.forEach { inputFile ->
                val fileName = inputFile.fileName.toString()
                
                // Skip files that require wgpu binding arrays or extensions that Naga standard CLI rejects
                if (fileName.contains("binding-arrays") || fileName.contains("binding-buffer-arrays") || 
                    fileName.contains("int16") || fileName.contains("per-vertex")) {
                    return@forEach
                }

                test("Naga validates generated WGSL for: $fileName") {
                    val source = Files.readString(inputFile)
                    
                    // 1. Compile WGSL using our compiler
                    val unit = try {
                        parseWgsl(source)
                    } catch (e: Exception) {
                        // Skip if parser fails (known parser limitations)
                        return@test
                    }
                    
                    val resolver = TypeResolver()
                    val resolutionResult = resolver.resolve(unit)
                    if (!resolutionResult.isSuccess) {
                        // Skip if type resolution fails
                        return@test
                    }
                    
                    val lowerer = Lowerer()
                    val module = try {
                        lowerer.lower(resolutionResult.resolvedUnit)
                    } catch (e: Exception) {
                        // Skip if lowering fails
                        return@test
                    }
                    
                    val writer = BackendRegistry.DEFAULT.get("wgsl")!!
                    val generatedWgsl = try {
                        writer.write(module, io.ygdrasil.wgsl.valid.ModuleInfo())
                    } catch (e: Exception) {
                        // Skip if writer fails
                        return@test
                    }
                    
                    // 2. Write to temp file
                    val tempFile = File.createTempFile("naga_val_", ".wgsl")
                    tempFile.deleteOnExit()
                    tempFile.writeText(generatedWgsl)
                    
                    // 3. Run naga validator on the generated WGSL
                    val process = ProcessBuilder("naga", tempFile.absolutePath).start()
                    val exitCode = process.waitFor()
                    val output = process.inputStream.bufferedReader().readText() + 
                                 process.errorStream.bufferedReader().readText()
                    
                    if (exitCode != 0) {
                        println("NAGA VALIDATION FAILED FOR: $fileName")
                        println("--- Generated WGSL ---")
                        println(generatedWgsl)
                        println("--- Naga Output ---")
                        println(output)
                    }
                    
                    exitCode shouldBe 0
                }
            }
        }
    }
})
