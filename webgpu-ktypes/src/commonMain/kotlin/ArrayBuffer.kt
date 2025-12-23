@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu


/**
 * Represents a buffer of raw binary data, which can be used in various WebGPU operations.
 * Provides a mechanism to work directly with raw memory for performance-critical computations
 * or data manipulation when working with GPU resources.
 *
 * This class is an abstraction for handling binary data in a format compatible with WebGPU,
 * allowing interoperability with GPU resources like buffers and textures.
 */
expect sealed interface ArrayBuffer {
    companion object {
        fun from(array: ByteArray): ArrayBuffer
        fun from(array: ShortArray): ArrayBuffer
        fun from(array: IntArray): ArrayBuffer
        fun from(array: LongArray): ArrayBuffer
        fun from(array: FloatArray): ArrayBuffer
        fun from(array: DoubleArray): ArrayBuffer
        fun from(array: UByteArray): ArrayBuffer
        fun from(array: UShortArray): ArrayBuffer
        fun from(array: UIntArray): ArrayBuffer
        fun from(array: ULongArray): ArrayBuffer
    }
}