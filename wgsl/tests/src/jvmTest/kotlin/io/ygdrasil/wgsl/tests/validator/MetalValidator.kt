package io.ygdrasil.wgsl.tests.validator

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.BufferedReader
import java.io.File
import java.nio.file.Files

private val logger = KotlinLogging.logger {}

/**
 * Wrapper for Metal Shader Compiler (macOS only)
 */
class MetalValidator : BackendValidator {

    override val name: String = "Metal Compiler"
    override val backendType: BackendType = BackendType.MSL

    init {
        require(isMacOs()) { "Metal Compiler is only available on macOS" }
        require(isAvailable()) { "Metal compiler not found. Please install Xcode." }
    }

    override fun validate(
        code: String,
        target: String?,
        stage: ShaderStage?
    ): ValidationResult {
        val tempFile = Files.createTempFile("shader", ".msl").toFile()
        try {
            tempFile.writeText(code)

            val sdk = when (target?.uppercase()) {
                "IOS", "IPHONEOS" -> "iphoneos"
                "SIMULATOR", "IPHONESIMULATOR" -> "iphonesimulator"
                "CATALYST", "MACCATALYST" -> "mac_catalyst"
                else -> "macosx" // Default to macOS
            }

            val command = listOf(
                "xcrun", "-sdk", sdk, "metal",
                tempFile.absolutePath,
                "-o", "/dev/null",
                "--validate-only"
            )

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

    private fun isMacOs(): Boolean {
        return System.getProperty("os.name").contains("Mac", ignoreCase = true)
    }

    companion object {
        fun isAvailable(): Boolean = try {
            val process = ProcessBuilder("xcrun", "-sdk", "macosx", "metal", "--version")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                logger.debug { "MetalValidator.isAvailable failed with exit code $exitCode. Output:\n$output" }
            }
            exitCode == 0
        } catch (e: Exception) {
            logger.debug(e) { "MetalValidator.isAvailable failed with exception: ${e.message}" }
            false
        }
    }
}
