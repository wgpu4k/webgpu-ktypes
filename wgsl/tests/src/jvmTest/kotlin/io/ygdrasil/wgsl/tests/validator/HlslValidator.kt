package io.ygdrasil.wgsl.tests.validator

import java.io.BufferedReader
import java.io.File
import java.nio.file.Files

/**
 * Wrapper for DXC and FXC validators
 */
class HlslValidator : BackendValidator {

    override val name: String = "DXC/FXC Validator"
    override val backendType: BackendType = BackendType.HLSL

    private var useDxc: Boolean = true
    private var executable: String = findExecutable()

    override fun validate(
        code: String,
        target: String?,
        stage: ShaderStage?
    ): ValidationResult {
        val tempFile = Files.createTempFile("shader", ".hlsl").toFile()
        try {
            tempFile.writeText(code)

            val command = if (useDxc) {
                buildDxcCommand(tempFile, target, stage)
            } else {
                buildFxcCommand(tempFile, target, stage)
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

    private fun buildDxcCommand(
        file: File,
        target: String?,
        stage: ShaderStage?
    ): List<String> {
        val shaderTarget = target ?: when (stage) {
            ShaderStage.VERTEX -> "vs_6_0"
            ShaderStage.FRAGMENT -> "ps_6_0"
            ShaderStage.COMPUTE -> "cs_6_0"
            null -> "ps_6_0" // Default to pixel shader
        }

        return listOf(
            executable,
            file.absolutePath,
            "-T", shaderTarget,
            "-Zs", // Validate only (no output)
            "-WX"  // Treat warnings as errors
        )
    }

    private fun buildFxcCommand(
        file: File,
        target: String?,
        stage: ShaderStage?
    ): List<String> {
        val shaderTarget = target ?: when (stage) {
            ShaderStage.VERTEX -> "vs_5_0"
            ShaderStage.FRAGMENT -> "ps_5_0"
            ShaderStage.COMPUTE -> "cs_5_0"
            null -> "ps_5_0" // Default to pixel shader 5.0
        }

        return listOf(
            executable,
            file.absolutePath,
            "/T", shaderTarget,
            "/Vn", "${file.nameWithoutExtension}_validation", // Validate only (no output file)
            "/WX"  // Treat warnings as errors
        )
    }

    private fun findExecutable(): String {
        // Try DXC first
        val dxcCandidates = listOf(
            "dxc",
            "dxc.exe",
            "C:/Program Files (x86)/Windows Kits/10/bin/10.0.22621.0/x64/dxc.exe",
            "C:/Program Files (x86)/Microsoft DirectX Shader Compiler/dxc.exe"
        )

        for (candidate in dxcCandidates) {
            if (isExecutable(candidate)) {
                useDxc = true
                return candidate
            }
        }

        // Try FXC
        val fxcCandidates = listOf(
            "fxc",
            "fxc.exe",
            "C:/Program Files (x86)/Windows Kits/10/bin/10.0.22621.0/x64/fxc.exe"
        )

        for (candidate in fxcCandidates) {
            if (isExecutable(candidate)) {
                useDxc = false
                return candidate
            }
        }

        error("Neither DXC nor FXC found. Please install the Windows SDK or DirectX Shader Compiler.")
    }

    private fun isExecutable(command: String): Boolean {
        return try {
            ProcessBuilder(command, if (useDxc) "--version" else "/?").start().waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        fun isAvailable(): Boolean = try {
            HlslValidator()
            true
        } catch (e: Exception) {
            false
        }
    }
}
