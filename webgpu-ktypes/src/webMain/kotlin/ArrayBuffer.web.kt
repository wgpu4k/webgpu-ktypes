@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

/**
 * A platform-independent representation of a fixed-length raw binary data buffer.
 *
 * The `ArrayBuffer` interface provides the ability to efficiently handle and store
 * binary data in memory, often as the underlying storage for typed arrays. It is
 * immutable, meaning its size cannot be adjusted once created.
 *
 * This abstraction is commonly used for processing low-level binary data, enabling
 * tasks such as file handling, network communication, or interfacing with Web APIs
 * that require binary data storage.
 */
actual sealed interface ArrayBuffer {
    actual companion object {
        /**
         * Creates an ArrayBuffer from a JavaScript ArrayBuffer.
         * @param buffer the JavaScript array buffer to wrap
         * @return an ArrayBuffer backed by the JavaScript array buffer
         */
        fun from(buffer: js.buffer.ArrayBuffer)
            = WebArrayBuffer(buffer)

        /**
         * Creates an ArrayBuffer from a ByteArray.
         * @param array the byte array to convert
         * @return an ArrayBuffer containing the data from the byte array
         */
        actual fun from(array: ByteArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a ShortArray.
         * @param array the short array to convert
         * @return an ArrayBuffer containing the data from the short array
         */
        actual fun from(array: ShortArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from an IntArray.
         * @param array the int array to convert
         * @return an ArrayBuffer containing the data from the int array
         */
        actual fun from(array: IntArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a LongArray.
         * @param array the long array to convert
         * @return an ArrayBuffer containing the data from the long array
         */
        actual fun from(array: LongArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a FloatArray.
         * @param array the float array to convert
         * @return an ArrayBuffer containing the data from the float array
         */
        actual fun from(array: FloatArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a DoubleArray.
         * @param array the double array to convert
         * @return an ArrayBuffer containing the data from the double array
         */
        actual fun from(array: DoubleArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a UByteArray.
         * @param array the unsigned byte array to convert
         * @return an ArrayBuffer containing the data from the unsigned byte array
         */
        actual fun from(array: UByteArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a UShortArray.
         * @param array the unsigned short array to convert
         * @return an ArrayBuffer containing the data from the unsigned short array
         */
        actual fun from(array: UShortArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a UIntArray.
         * @param array the unsigned int array to convert
         * @return an ArrayBuffer containing the data from the unsigned int array
         */
        actual fun from(array: UIntArray): ArrayBuffer
            = array.toArrayBuffer()

        /**
         * Creates an ArrayBuffer from a ULongArray.
         * @param array the unsigned long array to convert
         * @return an ArrayBuffer containing the data from the unsigned long array
         */
        actual fun from(array: ULongArray): ArrayBuffer
            = array.toArrayBuffer()
    }
}

internal expect inline fun ByteArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun ShortArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun IntArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun LongArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun FloatArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun DoubleArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun UByteArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun UShortArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun UIntArray.toArrayBuffer(): ArrayBuffer
internal expect inline fun ULongArray.toArrayBuffer(): ArrayBuffer

/**
 * A Kotlin/JS value class that serves as a wrapper for the JavaScript `ArrayBuffer`.
 *
 * This class allows for interoperability between Kotlin and JavaScript by embedding
 * the `js.buffer.ArrayBuffer` instance, enabling the handling of binary data in
 * scenarios such as Web API interactions, file operations, or low-level binary data
 * processing.
 *
 * @property buffer The underlying `js.buffer.ArrayBuffer` instance being wrapped.
 */
value class WebArrayBuffer internal constructor(val buffer: js.buffer.ArrayBuffer): ArrayBuffer

