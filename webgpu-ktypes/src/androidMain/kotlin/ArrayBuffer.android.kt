@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import java.nio.ByteBuffer

/**
 * Represents a platform-specific abstraction for handling raw binary data buffers.
 *
 * `ArrayBuffer` is a sealed interface that provides a common type for working with
 * binary data across multiple platforms. It is typically associated with use cases
 * such as data transfer, WebGPU operations, or interfacing with native libraries.
 *
 * This interface is intended to be implemented by platform-specific classes
 * or value types that wrap the underlying buffer implementation, such as direct
 * `ByteBuffer` on Android.
 *
 * Example usage:
 * ```kotlin
 * val buffer = ArrayBuffer.from(byteArrayOf(1, 2, 3, 4))
 * val intBuffer = ArrayBuffer.from(intArrayOf(100, 200, 300))
 * ```
 */
actual sealed interface ArrayBuffer {
    actual companion object {

        /**
         * Creates an ArrayBuffer from a ByteBuffer.
         * @param buffer the byte buffer to wrap (must be a direct buffer)
         * @return an ArrayBuffer backed by the byte buffer
         */
        fun from(buffer: ByteBuffer): ArrayBuffer = AndroidArrayBuffer(buffer)

        /**
         * Creates an ArrayBuffer from a ByteArray.
         * @param array the byte array to convert
         * @return an ArrayBuffer containing the data from the byte array
         */
        actual fun from(array: ByteArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size)
            buffer.put(array)
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a ShortArray.
         * @param array the short array to convert
         * @return an ArrayBuffer containing the data from the short array
         */
        actual fun from(array: ShortArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Short.SIZE_BYTES)
            buffer.asShortBuffer().put(array)
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from an IntArray.
         * @param array the int array to convert
         * @return an ArrayBuffer containing the data from the int array
         */
        actual fun from(array: IntArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Int.SIZE_BYTES)
            buffer.asIntBuffer().put(array)
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a LongArray.
         * @param array the long array to convert
         * @return an ArrayBuffer containing the data from the long array
         */
        actual fun from(array: LongArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Long.SIZE_BYTES)
            buffer.asLongBuffer().put(array)
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a FloatArray.
         * @param array the float array to convert
         * @return an ArrayBuffer containing the data from the float array
         */
        actual fun from(array: FloatArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Float.SIZE_BYTES)
            buffer.asFloatBuffer().put(array)
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a DoubleArray.
         * @param array the double array to convert
         * @return an ArrayBuffer containing the data from the double array
         */
        actual fun from(array: DoubleArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Double.SIZE_BYTES)
            buffer.asDoubleBuffer().put(array)
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a UByteArray.
         * @param array the unsigned byte array to convert
         * @return an ArrayBuffer containing the data from the unsigned byte array
         */
        actual fun from(array: UByteArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size)
            buffer.put(array.asByteArray())
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a UShortArray.
         * @param array the unsigned short array to convert
         * @return an ArrayBuffer containing the data from the unsigned short array
         */
        actual fun from(array: UShortArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Short.SIZE_BYTES)
            buffer.asShortBuffer().put(array.asShortArray())
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a UIntArray.
         * @param array the unsigned int array to convert
         * @return an ArrayBuffer containing the data from the unsigned int array
         */
        actual fun from(array: UIntArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Int.SIZE_BYTES)
            buffer.asIntBuffer().put(array.asIntArray())
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }

        /**
         * Creates an ArrayBuffer from a ULongArray.
         * @param array the unsigned long array to convert
         * @return an ArrayBuffer containing the data from the unsigned long array
         */
        actual fun from(array: ULongArray): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(array.size * Long.SIZE_BYTES)
            buffer.asLongBuffer().put(array.asLongArray())
            buffer.rewind()
            return AndroidArrayBuffer(buffer)
        }
    }
}

/**
 * An Android-specific implementation of the `ArrayBuffer` interface.
 *
 * `AndroidArrayBuffer` provides a lightweight wrapper around a direct `ByteBuffer`,
 * allowing Android platforms to manage, access, and manipulate raw binary data in a way
 * that conforms to the `ArrayBuffer` abstraction.
 *
 * This class leverages the `@JvmInline` annotation, making it a value class. This ensures
 * minimal runtime overhead and allows the `ByteBuffer` instance to be used with improved
 * performance due to inlining and reduced object allocations.
 *
 * @param buffer The underlying direct `ByteBuffer` instance that serves as the basis for this array buffer.
 * @throws IllegalStateException if the provided ByteBuffer is not a direct buffer
 */
@JvmInline
value class AndroidArrayBuffer internal constructor(val buffer: ByteBuffer): ArrayBuffer {
    init {
        if (buffer.isDirect.not()) error("ByteBuffer must be direct")
    }
}

