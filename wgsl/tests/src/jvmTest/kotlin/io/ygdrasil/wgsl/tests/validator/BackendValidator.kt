package io.ygdrasil.wgsl.tests.validator

/**
 * Common interface for backend validators
 */
interface BackendValidator {

    val name: String
    val backendType: BackendType

    /**
     * Validate the generated code
     * @param code Source code to validate
     * @param target Target specification (version, shader model, etc.)
     * @param stage Shader stage (optional)
     * @return ValidationResult with success/failure info
     */
    fun validate(
        code: String,
        target: String? = null,
        stage: ShaderStage? = null
    ): ValidationResult
}

/**
 * Result of a validation attempt
 */
data class ValidationResult(
    val success: Boolean,
    val output: String,
    val exitCode: Int,
    val command: String
) {
    val isFailure: Boolean get() = !success

    override fun toString(): String = buildString {
        appendLine("Validation: ${if (success) "SUCCESS" else "FAILURE"}")
        appendLine("Command: $command")
        appendLine("Exit Code: $exitCode")
        if (output.isNotBlank()) {
            appendLine("Output:")
            appendLine(output)
        }
    }
}

/**
 * Shader stages
 */
enum class ShaderStage {
    VERTEX, FRAGMENT, COMPUTE
}

/**
 * Backend types
 */
enum class BackendType {
    IR, SPIRV, GLSL, MSL, HLSL, WGSL
}
