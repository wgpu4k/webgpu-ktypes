package io.ygdrasil.wgsl.tests.validator

import java.io.BufferedReader
import java.io.File
import java.nio.file.Files

/**
 * Wrapper for glslangValidator
 */
class GlslValidator : BackendValidator {

    override val name: String = "glslangValidator"
    override val backendType: BackendType = BackendType.GLSL

    private var executable: String = findExecutable()

    override fun validate(
        code: String,
        target: String?,
        stage: ShaderStage?
    ): ValidationResult {
        val extension = when (stage) {
            ShaderStage.VERTEX -> ".vert"
            ShaderStage.FRAGMENT -> ".frag"
            ShaderStage.COMPUTE -> ".comp"
            null -> ".glsl"
        }
        val tempFile = Files.createTempFile("shader", extension).toFile()
        try {
            tempFile.writeText(code)

            val command = buildList {
                add(executable)

                // Version
                when (target?.uppercase()) {
                    "460", "V460" -> add("-V460")
                    "450", "V" -> add("-V")
                    "320", "E" -> add("-e")
                    "400", "G" -> add("-G")
                    else -> add("-V") // Default to Vulkan 450
                }

                // Stage
                val stageArg = when (stage) {
                    ShaderStage.VERTEX -> "vert"
                    ShaderStage.FRAGMENT -> "frag"
                    ShaderStage.COMPUTE -> "comp"
                    null -> null
                }
                if (stageArg != null) {
                    add("-S")
                    add(stageArg)
                }

                add(tempFile.absolutePath)
            }

            val process = ProcessBuilder(command)
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().use(BufferedReader::readText)
            val exitCode = process.waitFor()

            return ValidationResult(
                success = exitCode == 0,
                output = output,
                exitCode = exitCode,
                command = command.joinToString(" ")
            )

        } finally {
            tempFile.delete()
        }
    }

    private fun findExecutable(): String {
        val candidates = listOf(
            "glslangValidator",
            "glslangValidator.exe",
            "/usr/local/bin/glslangValidator",
            "/opt/homebrew/bin/glslangValidator",
            "/usr/bin/glslangValidator"
        )

        for (candidate in candidates) {
            if (isExecutable(candidate)) {
                return candidate
            }
        }

        error("glslangValidator not found. Please install it from https://github.com/KhronosGroup/glslang")
    }

    private fun isExecutable(command: String): Boolean {
        return try {
            ProcessBuilder(command, "--version").start().waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        fun isAvailable(): Boolean = try {
            GlslValidator()
            true
        } catch (e: Exception) {
            false
        }
    }
}
