package io.ygdrasil.wgsl.tests.validator

import java.io.BufferedReader
import java.io.File

/**
 * SPIR-V validator wrapper
 */
class SpirvValidator : BackendValidator {
    override val name: String = "spirv-val"
    override val backendType: BackendType = BackendType.SPIRV

    private var executable: String = findExecutable()

    override fun validate(
        code: String,
        target: String?,
        stage: ShaderStage?
    ): ValidationResult {
        val tempFile = File.createTempFile("spirv", ".spv")
        try {
            tempFile.writeBytes(code.toByteArray())

            val command = listOf(executable, tempFile.absolutePath)

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
            "spirv-val",
            "spirv-val.exe",
            "/usr/local/bin/spirv-val",
            "/opt/homebrew/bin/spirv-val",
            "/usr/bin/spirv-val"
        )

        for (candidate in candidates) {
            if (isExecutable(candidate)) {
                return candidate
            }
        }

        error("spirv-val not found. Please install SPIRV-Tools from https://github.com/KhronosGroup/SPIRV-Tools")
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
            SpirvValidator()
            true
        } catch (e: Exception) {
            false
        }
    }
}
