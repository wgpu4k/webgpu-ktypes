package io.ygdrasil.wgsl.ast

import io.ygdrasil.wgsl.ir.Span

/**
 * A type declaration in WGSL.
 * 
 * This represents a type in the WGSL type system, which can be:
 * - Scalar types (bool, i32, u32, f32, etc.)
 * - Vector types (vec2<f32>, vec3<i32>, etc.)
 * - Matrix types (mat2x2<f32>, mat3x4<f32>, etc.)
 * - Array types (array<i32, 4>, array<f32>, etc.)
 * - Struct types
 * - Pointer types
 * - Reference types
 * - Named types (type aliases)
 * - Template types
 */
sealed class TypeDecl {
    /** The span of this type declaration. */
    abstract val span: Span
}

/**
 * A scalar type.
 */
data class ScalarType(
    /** The scalar kind. */
    val kind: ScalarKind,
    override val span: Span,
) : TypeDecl()

/**
 * Scalar kinds in WGSL.
 */
enum class ScalarKind {
    BOOL,
    I8, U8,
    I16, U16,
    I32, U32,
    I64, U64,
    F16, F32, F64,
}

/**
 * Check if this scalar kind is an integer type.
 */
val ScalarKind.isInteger: Boolean
    get() = when (this) {
        ScalarKind.I8, ScalarKind.U8,
        ScalarKind.I16, ScalarKind.U16,
        ScalarKind.I32, ScalarKind.U32,
        ScalarKind.I64, ScalarKind.U64 -> true
        else -> false
    }

/**
 * Check if this scalar kind is a float type.
 */
val ScalarKind.isFloat: Boolean
    get() = when (this) {
        ScalarKind.F16, ScalarKind.F32, ScalarKind.F64 -> true
        else -> false
    }

/**
 * A vector type.
 */
data class VectorType(
    /** The size of the vector (2, 3, or 4). */
    val size: Int,
    /** The type of elements in the vector. */
    val elementType: TypeDecl,
    override val span: Span,
) : TypeDecl()

/**
 * A matrix type.
 */
data class MatrixType(
    /** The number of columns. */
    val columns: Int,
    /** The number of rows. */
    val rows: Int,
    /** The type of elements in the matrix. */
    val elementType: TypeDecl,
    override val span: Span,
) : TypeDecl()

/**
 * An array type.
 */
data class ArrayType(
    /** The element type of the array. */
    val elementType: TypeDecl,
    /** The length of the array (null for runtime-sized arrays). */
    val length: Expression?,
    /** The stride (if specified). */
    val stride: Int?,
    override val span: Span,
) : TypeDecl()

/**
 * A struct type reference.
 */
data class StructType(
    /** The name of the struct. */
    val name: String,
    override val span: Span,
) : TypeDecl()

/**
 * A named type (type alias reference).
 */
data class NamedType(
    /** The name of the type. */
    val name: String,
    override val span: Span,
) : TypeDecl()

/**
 * A pointer type.
 */
data class PointerType(
    /** The storage class. */
    val storageClass: StorageClass,
    /** The type being pointed to. */
    val elementType: TypeDecl,
    /** The access mode (optional). */
    val accessMode: String? = null,
    override val span: Span,
) : TypeDecl()

/**
 * Storage classes for pointers.
 */
enum class StorageClass {
    FUNCTION,
    PRIVATE,
    WORKGROUP,
    UNIFORM,
    STORAGE,
    HANDLE,
    PUSH_CONSTANT,
}

/**
 * A reference type.
 */
data class ReferenceType(
    /** The storage class. */
    val storageClass: StorageClass,
    /** The type being referenced. */
    val elementType: TypeDecl,
    override val span: Span,
) : TypeDecl()

/**
 * A template type.
 */
data class TemplateType(
    /** The name of the template parameter. */
    val name: String,
    /** The template arguments (if any). */
    val args: List<TypeDecl>,
    override val span: Span,
) : TypeDecl()

/**
 * An atomic type like `atomic<i32>`.
 */
data class AtomicType(
    /** The element type. */
    val elementType: TypeDecl,
    override val span: Span,
) : TypeDecl()

/**
 * A sampler type.
 */
data class SamplerType(
    /** Whether this is a comparison sampler. */
    val isComparison: Boolean,
    override val span: Span,
) : TypeDecl()

/**
 * A texture type like `texture_2d<f32>`.
 */
data class TextureType(
    /** The texture kind. */
    val kind: TextureKind,
    /** The element type (if applicable). */
    val elementType: TypeDecl? = null,
    /** The access mode (for storage textures). */
    val accessMode: String? = null,
    override val span: Span,
) : TypeDecl()

/**
 * A type that is actually a constant expression, used in template arguments.
 */
data class ConstantType(
    val expression: Expression,
    override val span: Span,
) : TypeDecl()

/**
 * A ray_query type.
 */
data class RayQueryType(
    override val span: Span,
) : TypeDecl()

/**
 * Abstract integer type.
 * 
 * Represents an abstract integer value without specifying the underlying
 * representation (could be i32, u32, etc.).
 */
data class AbstractIntType(
    override val span: Span,
) : TypeDecl()

/**
 * Abstract float type.
 * 
 * Represents an abstract floating-point value without specifying the
 * underlying representation (could be f32, f64, etc.).
 */
data class AbstractFloatType(
    override val span: Span,
) : TypeDecl()

/**
 * An enum type reference.
 */
data class EnumType(
    /** The name of the enum. */
    val name: String,
    override val span: Span,
) : TypeDecl()

/**
 * Texture kinds in WGSL.
 */
enum class TextureKind {
    TEXTURE_1D,
    TEXTURE_1D_ARRAY,
    TEXTURE_2D,
    TEXTURE_2D_ARRAY,
    TEXTURE_3D,
    TEXTURE_CUBE,
    TEXTURE_CUBE_ARRAY,
    TEXTURE_MULTISAMPLED_2D,
    TEXTURE_DEPTH_2D,
    TEXTURE_DEPTH_2D_ARRAY,
    TEXTURE_DEPTH_CUBE,
    TEXTURE_DEPTH_CUBE_ARRAY,
    TEXTURE_DEPTH_MULTISAMPLED_2D,
    TEXTURE_EXTERNAL,
    TEXTURE_STORAGE_1D,
    TEXTURE_STORAGE_2D,
    TEXTURE_STORAGE_2D_ARRAY,
    TEXTURE_STORAGE_3D,
}
