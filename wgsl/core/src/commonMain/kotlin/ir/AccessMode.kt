package io.ygdrasil.wgsl.ir

import kotlinx.serialization.Serializable

/**
 * Access mode for storage buffers.
 */
@Serializable
enum class AccessMode {
    Read,
    Write,
    ReadWrite
}
