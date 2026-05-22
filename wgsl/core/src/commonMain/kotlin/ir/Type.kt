package io.ygdrasil.wgsl.ir

import io.ygdrasil.wgsl.arena.Equatable
import io.ygdrasil.wgsl.arena.Handle
import kotlinx.serialization.Serializable

/**
 * A type in the Naga IR.
 * 
 * Types are stored in a UniqueArena to ensure that identical types share the same Handle.
 */
@Serializable
data class Type(
    /**
     * The inner type definition.
     */
    val inner: TypeInner,
) : Equatable {

    override fun isEquivalentTo(other: Any): Boolean {
        if (other !is Type) return false
        return this.inner == other.inner
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Type) return false
        return inner == other.inner
    }

    override fun hashCode(): Int = inner.hashCode()

    override fun toString(): String = inner.toString()
}

/**
 * The inner definition of a type.
 */
@Serializable
sealed class TypeInner : Equatable {

    // Scalar types
    @Serializable
    data class Scalar(val kind: ScalarKind, val width: Int) : TypeInner()

    // Vector types
    @Serializable
    data class Vector(val size: VectorSize, val scalar: Handle<Type>) : TypeInner()

    // Matrix types
    @Serializable
    data class Matrix(val columns: VectorSize, val rows: VectorSize, val scalar: Handle<Type>) : TypeInner()

    // Array types
    @Serializable
    data class Array(val element: Handle<Type>, val size: ArraySize) : TypeInner()

    // Struct types
    @Serializable
    data class Struct(val members: List<StructMember>) : TypeInner()

    // Pointer types
    @Serializable
    data class Pointer(val base: Handle<Type>, val addressSpace: AddressSpace, val accessMode: AccessMode? = null) : TypeInner()

    // Value pointer types (WGSL extension)
    @Serializable
    data class ValuePointer(val base: Handle<Type>) : TypeInner()

    // Opaque types (for external types like textures, samplers)
    @Serializable
    data class Opaque(val name: String) : TypeInner()

    // Error type (for type errors)
    @Serializable
    object Error : TypeInner()

    // Abstract types
    @Serializable
    data class Abstract(val scalar: ScalarKind) : TypeInner()

    override fun isEquivalentTo(other: Any): Boolean {
        if (this === other) return true
        if (other !is TypeInner) return false
        return when (this) {
            is Scalar -> other is Scalar && kind == other.kind && width == other.width
            is Vector -> other is Vector && size == other.size && scalar == other.scalar
            is Matrix -> other is Matrix && columns == other.columns && rows == other.rows && scalar == other.scalar
            is Array -> other is Array && element == other.element && size == other.size
            is Struct -> other is Struct && members == other.members
            is Pointer -> other is Pointer && base == other.base && addressSpace == other.addressSpace
            is ValuePointer -> other is ValuePointer && base == other.base
            is Opaque -> other is Opaque && name == other.name
            Error -> other is Error
            is Abstract -> other is Abstract && scalar == other.scalar
        }
    }
}

/**
 * A member of a struct type.
 */
@Serializable
data class StructMember(
    val name: String,
    val type: Handle<Type>,
    val binding: BindingAttribute? = null,
    val offset: Int = 0,
)

/**
 * The size of an array.
 */
@Serializable
sealed class ArraySize : Equatable {
    @Serializable
    class Constant(val value: Int) : ArraySize()
    @Serializable
    class Dynamic(val expression: Handle<Expression>) : ArraySize()

    override fun isEquivalentTo(other: Any): Boolean {
        if (this === other) return true
        if (other !is ArraySize) return false
        return when (this) {
            is Constant -> other is Constant && value == other.value
            is Dynamic -> other is Dynamic && expression == other.expression
        }
    }
}

/**
 * Extension to check if a type is a scalar.
 */
val Type.isScalar: Boolean
    get() = inner is TypeInner.Scalar

/**
 * Extension to check if a type is a vector.
 */
val Type.isVector: Boolean
    get() = inner is TypeInner.Vector

/**
 * Extension to check if a type is a matrix.
 */
val Type.isMatrix: Boolean
    get() = inner is TypeInner.Matrix

/**
 * Extension to check if a type is an array.
 */
val Type.isArray: Boolean
    get() = inner is TypeInner.Array

/**
 * Extension to check if a type is a struct.
 */
val Type.isStruct: Boolean
    get() = inner is TypeInner.Struct

/**
 * Extension to check if a type is a pointer.
 */
val Type.isPointer: Boolean
    get() = inner is TypeInner.Pointer || inner is TypeInner.ValuePointer

/**
 * Extension to check if a type is numeric (scalar or vector of numeric).
 * Note: This requires access to the Arena to dereference handles.
 * Temporary implementation only checks the immediate scalar type.
 */
val Type.isNumeric: Boolean
    get() = when (val i = inner) {
        is TypeInner.Scalar -> i.kind.isNumeric
        is TypeInner.Vector -> true // Vector types are numeric if their scalar is
        is TypeInner.Matrix -> true // Matrix types are numeric if their scalar is
        else -> false
    }
