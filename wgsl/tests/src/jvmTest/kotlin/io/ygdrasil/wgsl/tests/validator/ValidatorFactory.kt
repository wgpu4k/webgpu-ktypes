package io.ygdrasil.wgsl.tests.validator

/**
 * Validator factory
 */
object ValidatorFactory {

    private val validators: MutableMap<BackendType, BackendValidator> = mutableMapOf()

    init {
        // Register available validators
        if (GlslValidator.isAvailable()) {
            validators[BackendType.GLSL] = GlslValidator()
        }

        if (SpirvValidator.isAvailable()) {
            validators[BackendType.SPIRV] = SpirvValidator()
        }

        if (HlslValidator.isAvailable()) {
            validators[BackendType.HLSL] = HlslValidator()
        }

        // Metal is disabled because it requires Metal Toolchain which might not be installed
        /*
        if (MetalValidator.isAvailable()) {
            validators[BackendType.MSL] = MetalValidator()
        }
        */
    }

    /**
     * Get validator for backend type
     */
    fun getValidator(backendType: BackendType): BackendValidator? {
        return validators[backendType]
    }

    /**
     * Get all available validators
     */
    fun getAllValidators(): Map<BackendType, BackendValidator> {
        return validators.toMap()
    }

    /**
     * Check if validator is available for backend type
     */
    fun isAvailable(backendType: BackendType): Boolean {
        return validators.containsKey(backendType)
    }
}
