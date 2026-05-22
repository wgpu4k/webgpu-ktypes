package io.ygdrasil.wgsl.ir

import io.ygdrasil.wgsl.arena.Handle
import kotlinx.serialization.Serializable

/**
 * A global variable in the module.
 */
@Serializable
data class GlobalVariable(
    /**
     * The name of the variable.
     */
    val name: String,

    /**
     * The storage class of the variable.
     */
    val storageClass: StorageClass,
    
    /**
     * The access mode of the variable.
     */
    val accessMode: AccessMode? = null,

    /**
     * The type of the variable.
     */
    val type: Handle<Type>,

    /**
     * The initial value expression (if any).
     */
    val init: Handle<Expression>? = null,

    /**
     * The binding for this variable (if it's a shader I/O variable).
     */
    val binding: Binding? = null,
) {
    override fun toString(): String = name
}

/**
 * Binding information for global variables.
 */
@Serializable
data class Binding(
    /**
     * The group index.
     */
    val group: Int,

    /**
     * The binding index within the group.
     */
    val index: Int,
)
