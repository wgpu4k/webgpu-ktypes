package io.ygdrasil.webgpu

import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Represents a platform-specific abstraction over raw binary data stored in an ArrayBuffer.
 *
 * This interface provides a unified way to interact with binary data across platforms,
 * allowing efficient manipulation and processing of raw buffers. It is primarily used
 * in contexts where native interop or web functionalities like WebGPU and WebGL require
 * a representation of binary buffers.
 *
 * Implementations of this interface may vary depending on the target platform, such as
 * browser environments or native environments. They typically map to platform-native
 * concepts like `org.khronos.webgl.ArrayBuffer` in JavaScript or analogous native
 * structures in other environments.
 *
 * Intended for use with platform-specific operations where binary data transfer or
 * manipulation is necessary.
 */
actual sealed interface ArrayBuffer {
    actual companion object {
        /**
         * Creates an ArrayBuffer from a ByteArray.
         * @param array the byte array to convert
         * @return an ArrayBuffer containing the data from the byte array
         */
        actual fun from(array: ByteArray): ArrayBuffer
            = NativeArrayBuffer(array)

        /**
         * Creates an ArrayBuffer from a ShortArray.
         * @param array the short array to convert
         * @return an ArrayBuffer containing the data from the short array
         */
        actual fun from(array: ShortArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from an IntArray.
         * @param array the int array to convert
         * @return an ArrayBuffer containing the data from the int array
         */
        actual fun from(array: IntArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from a LongArray.
         * @param array the long array to convert
         * @return an ArrayBuffer containing the data from the long array
         */
        actual fun from(array: LongArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from a FloatArray.
         * @param array the float array to convert
         * @return an ArrayBuffer containing the data from the float array
         */
        actual fun from(array: FloatArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from a DoubleArray.
         * @param array the double array to convert
         * @return an ArrayBuffer containing the data from the double array
         */
        actual fun from(array: DoubleArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from a UByteArray.
         * @param array the unsigned byte array to convert
         * @return an ArrayBuffer containing the data from the unsigned byte array
         */
        actual fun from(array: UByteArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from a UShortArray.
         * @param array the unsigned short array to convert
         * @return an ArrayBuffer containing the data from the unsigned short array
         */
        actual fun from(array: UShortArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from a UIntArray.
         * @param array the unsigned int array to convert
         * @return an ArrayBuffer containing the data from the unsigned int array
         */
        actual fun from(array: UIntArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }

        /**
         * Creates an ArrayBuffer from a ULongArray.
         * @param array the unsigned long array to convert
         * @return an ArrayBuffer containing the data from the unsigned long array
         */
        actual fun from(array: ULongArray): ArrayBuffer {
            return NativeArrayBuffer(array)
        }
    }
}


/**
 * Represents a native array buffer backed by a primitive array, providing platform-specific
 * functionality for handling raw binary data efficiently.
 *
 * This value class wraps a primitive array (such as `ByteArray`, `IntArray`, `FloatArray`, etc.)
 * and implements the `ArrayBuffer` interface, serving as a lightweight representation of binary data.
 * It is primarily intended for use cases that require interoperation with native systems or other
 * low-level data processing tasks where buffers are commonly utilized.
 *
 * The use of `@OptIn(ExperimentalForeignApi::class)` indicates that this class makes
 * use of experimental API functionality, which may be subject to change in future versions.
 *
 * @param buffer The underlying primitive array stored as `Any` to support various array types
 */
@OptIn(ExperimentalForeignApi::class)
value class NativeArrayBuffer internal constructor(val buffer: Any): ArrayBuffer

