package io.ygdrasil.wgsl.ast

import io.ygdrasil.wgsl.ir.Span

/**
 * Base sealed class for all predeclared enumerant values.
 * 
 * Predeclared enumerants are built-in enum-like values defined by the WGSL
 * specification. They are used in various WGSL constructs like texture
 * sampling, interpolation, etc.
 * 
 * Unlike user-defined enums, these are not declared in WGSL code but are
 * available globally.
 */
sealed class PredeclaredEnumerant {
    /** The category/group this enumerant belongs to (e.g., "AddressMode"). */
    abstract val category: String
    
    /** The string value of this enumerant (e.g., "clamp_to_edge"). */
    abstract val value: String
    
    /** The span of this enumerant in source code (if applicable). */
    abstract val span: Span
}

// =============================================================================
// Texture Sampling Enumerants
// =============================================================================

/**
 * Address mode for texture sampling.
 * 
 * Controls how texture coordinates outside the [0, 1] range are handled.
 */
sealed class AddressMode(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "AddressMode"
}

/** clamp_to_edge: Clamps coordinates to the [0, 1] range. */
data class ClampToEdge(override val span: Span) : AddressMode(span) {
    override val value: String = "clamp_to_edge"
}

/** repeat: Repeats the texture. */
data class Repeat(override val span: Span) : AddressMode(span) {
    override val value: String = "repeat"
}

/** mirror_repeat: Mirrors the texture at integer boundaries. */
data class MirrorRepeat(override val span: Span) : AddressMode(span) {
    override val value: String = "mirror_repeat"
}

// =============================================================================
// Filter Mode Enumerants
// =============================================================================

/**
 * Filter mode for texture sampling.
 * 
 * Controls the filtering method used when sampling a texture.
 */
sealed class FilterMode(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "FilterMode"
}

/** nearest: Nearest neighbor filtering (no interpolation). */
data class Nearest(override val span: Span) : FilterMode(span) {
    override val value: String = "nearest"
}

/** linear: Linear filtering (bilinear interpolation). */
data class Linear(override val span: Span) : FilterMode(span) {
    override val value: String = "linear"
}

// =============================================================================
// Mipmap Filter Mode Enumerants
// =============================================================================

/**
 * Mipmap filter mode for texture sampling.
 * 
 * Controls the filtering method used between mipmap levels.
 */
sealed class MipmapFilterMode(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "MipmapFilterMode"
}

/** nearest: Nearest neighbor filtering between mip levels. */
data class MipmapNearest(override val span: Span) : MipmapFilterMode(span) {
    override val value: String = "nearest"
}

/** linear: Linear filtering between mip levels. */
data class MipmapLinear(override val span: Span) : MipmapFilterMode(span) {
    override val value: String = "linear"
}

// =============================================================================
// Interpolation Type Enumerants
// =============================================================================

/**
 * Interpolation type for fragment shader inputs.
 * 
 * Controls how values are interpolated across a triangle.
 */
sealed class InterpolationType(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "InterpolationType"
}

/** perspective: Perspective-correct interpolation. */
data class Perspective(override val span: Span) : InterpolationType(span) {
    override val value: String = "perspective"
}

/** linear: Linear interpolation (not perspective-correct). */
data class LinearInterpolation(override val span: Span) : InterpolationType(span) {
    override val value: String = "linear"
}

/** flat: No interpolation, uses the value from the vertex with the lowest index. */
data class Flat(override val span: Span) : InterpolationType(span) {
    override val value: String = "flat"
}

// =============================================================================
// Interpolation Sampling Enumerants
// =============================================================================

/**
 * Interpolation sampling for fragment shader inputs.
 * 
 * Controls at which location the interpolated value is sampled.
 */
sealed class InterpolationSampling(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "InterpolationSampling"
}

/** center: Sample at the center of the pixel. */
data class Center(override val span: Span) : InterpolationSampling(span) {
    override val value: String = "center"
}

/** centroid: Sample at the centroid of the pixel. */
data class Centroid(override val span: Span) : InterpolationSampling(span) {
    override val value: String = "centroid"
}

/** sample: Sample at the sample location. */
data class Sample(override val span: Span) : InterpolationSampling(span) {
    override val value: String = "sample"
}

// =============================================================================
// Builtin Value Enumerants
// =============================================================================

/**
 * Built-in values that can be used as struct members.
 * 
 * These are special values that represent shader inputs/outputs.
 */
sealed class BuiltinValueEnum(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "BuiltinValue"
}

// Position
data class Position(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "position"
}

// Vertex Index
data class VertexIndex(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "vertex_index"
}

// Instance Index
data class InstanceIndex(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "instance_index"
}

// Front Facing
data class FrontFacing(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "front_facing"
}

// Primitive Index
data class PrimitiveIndex(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "primitive_index"
}

// Sample Index
data class SampleIndex(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "sample_index"
}

// Sample Mask
data class SampleMask(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "sample_mask"
}

// Viewport Index
data class ViewportIndex(override val span: Span) : BuiltinValueEnum(span) {
    override val value: String = "viewport_index"
}

// =============================================================================
// Wrapping Mode Enumerants
// =============================================================================

sealed class WrappingMode(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "WrappingMode"
}

data class WrappingClampToEdge(override val span: Span) : WrappingMode(span) {
    override val value: String = "clamp_to_edge"
}

data class WrappingRepeat(override val span: Span) : WrappingMode(span) {
    override val value: String = "repeat"
}

data class WrappingMirrorRepeat(override val span: Span) : WrappingMode(span) {
    override val value: String = "mirror_repeat"
}

// =============================================================================
// Comparison Function Enumerants
// =============================================================================

sealed class ComparisonFunction(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "ComparisonFunction"
}

data class Never(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "never"
}

data class Less(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "less"
}

data class Equal(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "equal"
}

data class LessEqual(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "less_equal"
}

data class Greater(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "greater"
}

data class NotEqual(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "not_equal"
}

data class GreaterEqual(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "greater_equal"
}

data class Always(override val span: Span) : ComparisonFunction(span) {
    override val value: String = "always"
}

// =============================================================================
// Sampler Address Mode Enumerants
// =============================================================================

sealed class SamplerAddressMode(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "SamplerAddressMode"
}

data class SamplerClampToEdge(override val span: Span) : SamplerAddressMode(span) {
    override val value: String = "clamp_to_edge"
}

data class SamplerRepeat(override val span: Span) : SamplerAddressMode(span) {
    override val value: String = "repeat"
}

data class SamplerMirrorRepeat(override val span: Span) : SamplerAddressMode(span) {
    override val value: String = "mirror_repeat"
}

data class ClampToBorder(override val span: Span) : SamplerAddressMode(span) {
    override val value: String = "clamp_to_border"
}

// =============================================================================
// Sampler Filter Mode Enumerants
// =============================================================================

sealed class SamplerFilterMode(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "SamplerFilterMode"
}

data class SamplerNearest(override val span: Span) : SamplerFilterMode(span) {
    override val value: String = "nearest"
}

data class SamplerLinear(override val span: Span) : SamplerFilterMode(span) {
    override val value: String = "linear"
}

// =============================================================================
// Sampler Mipmap Filter Mode Enumerants
// =============================================================================

sealed class SamplerMipmapFilterMode(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "SamplerMipmapFilterMode"
}

data class SamplerMipmapNearest(override val span: Span) : SamplerMipmapFilterMode(span) {
    override val value: String = "nearest"
}

data class SamplerMipmapLinear(override val span: Span) : SamplerMipmapFilterMode(span) {
    override val value: String = "linear"
}

// =============================================================================
// Sampler Border Color Enumerants
// =============================================================================

sealed class SamplerBorderColor(override val span: Span) : PredeclaredEnumerant() {
    override val category: String = "SamplerBorderColor"
}

data class TransparentBlack(override val span: Span) : SamplerBorderColor(span) {
    override val value: String = "transparent_black"
}

data class OpaqueBlack(override val span: Span) : SamplerBorderColor(span) {
    override val value: String = "opaque_black"
}

data class OpaqueWhite(override val span: Span) : SamplerBorderColor(span) {
    override val value: String = "opaque_white"
}

// =============================================================================
// Helper functions
// =============================================================================

/**
 * Get a predeclared enumerant by its category and value.
 * 
 * @param category The category of the enumerant (e.g., "AddressMode")
 * @param value The value of the enumerant (e.g., "clamp_to_edge")
 * @param span The span of the enumerant in source code
 * @return The corresponding PredeclaredEnumerant, or null if not found
 */
fun getPredeclaredEnumerant(category: String, value: String, span: Span): PredeclaredEnumerant? {
    return when (category) {
        "AddressMode" -> when (value) {
            "clamp_to_edge" -> ClampToEdge(span)
            "repeat" -> Repeat(span)
            "mirror_repeat" -> MirrorRepeat(span)
            else -> null
        }
        "WrappingMode" -> when (value) {
            "clamp_to_edge" -> WrappingClampToEdge(span)
            "repeat" -> WrappingRepeat(span)
            "mirror_repeat" -> WrappingMirrorRepeat(span)
            else -> null
        }
        "FilterMode" -> when (value) {
            "nearest" -> Nearest(span)
            "linear" -> Linear(span)
            else -> null
        }
        "MipmapFilterMode" -> when (value) {
            "nearest" -> MipmapNearest(span)
            "linear" -> MipmapLinear(span)
            else -> null
        }
        "ComparisonFunction" -> when (value) {
            "never" -> Never(span)
            "less" -> Less(span)
            "equal" -> Equal(span)
            "less_equal" -> LessEqual(span)
            "greater" -> Greater(span)
            "not_equal" -> NotEqual(span)
            "greater_equal" -> GreaterEqual(span)
            "always" -> Always(span)
            else -> null
        }
        "SamplerAddressMode" -> when (value) {
            "clamp_to_edge" -> SamplerClampToEdge(span)
            "repeat" -> SamplerRepeat(span)
            "mirror_repeat" -> SamplerMirrorRepeat(span)
            "clamp_to_border" -> ClampToBorder(span)
            else -> null
        }
        "SamplerFilterMode" -> when (value) {
            "nearest" -> SamplerNearest(span)
            "linear" -> SamplerLinear(span)
            else -> null
        }
        "SamplerMipmapFilterMode" -> when (value) {
            "nearest" -> SamplerMipmapNearest(span)
            "linear" -> SamplerMipmapLinear(span)
            else -> null
        }
        "SamplerBorderColor" -> when (value) {
            "transparent_black" -> TransparentBlack(span)
            "opaque_black" -> OpaqueBlack(span)
            "opaque_white" -> OpaqueWhite(span)
            else -> null
        }
        "InterpolationType" -> when (value) {
            "perspective" -> Perspective(span)
            "linear" -> LinearInterpolation(span)
            "flat" -> Flat(span)
            else -> null
        }
        "InterpolationSampling" -> when (value) {
            "center" -> Center(span)
            "centroid" -> Centroid(span)
            "sample" -> Sample(span)
            else -> null
        }
        "BuiltinValue" -> when (value) {
            "position" -> Position(span)
            "vertex_index" -> VertexIndex(span)
            "instance_index" -> InstanceIndex(span)
            "front_facing" -> FrontFacing(span)
            "primitive_index" -> PrimitiveIndex(span)
            "sample_index" -> SampleIndex(span)
            "sample_mask" -> SampleMask(span)
            "viewport_index" -> ViewportIndex(span)
            else -> null
        }
        else -> null
    }
}

/**
 * Get all categories of predeclared enumerants.
 */
val allPredeclaredEnumerantCategories: List<String> = listOf(
    "AddressMode",
    "WrappingMode",
    "FilterMode",
    "MipmapFilterMode",
    "ComparisonFunction",
    "SamplerAddressMode",
    "SamplerFilterMode",
    "SamplerMipmapFilterMode",
    "SamplerBorderColor",
    "InterpolationType",
    "InterpolationSampling",
    "BuiltinValue"
)

/**
 * Get all values for a given category.
 */
fun getPredeclaredEnumerantValues(category: String): List<String> = when (category) {
    "AddressMode" -> listOf("clamp_to_edge", "repeat", "mirror_repeat")
    "WrappingMode" -> listOf("clamp_to_edge", "repeat", "mirror_repeat")
    "FilterMode" -> listOf("nearest", "linear")
    "MipmapFilterMode" -> listOf("nearest", "linear")
    "ComparisonFunction" -> listOf("never", "less", "equal", "less_equal", "greater", "not_equal", "greater_equal", "always")
    "SamplerAddressMode" -> listOf("clamp_to_edge", "repeat", "mirror_repeat", "clamp_to_border")
    "SamplerFilterMode" -> listOf("nearest", "linear")
    "SamplerMipmapFilterMode" -> listOf("nearest", "linear")
    "SamplerBorderColor" -> listOf("transparent_black", "opaque_black", "opaque_white")
    "InterpolationType" -> listOf("perspective", "linear", "flat")
    "InterpolationSampling" -> listOf("center", "centroid", "sample")
    "BuiltinValue" -> listOf(
        "position", "vertex_index", "instance_index", "front_facing",
        "primitive_index", "sample_index", "sample_mask", "viewport_index"
    )
    else -> emptyList()
}
