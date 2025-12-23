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
        fun from(buffer: js.buffer.ArrayBuffer)
            = WebArrayBuffer(buffer)

        actual fun from(array: ByteArray): ArrayBuffer
            = array.toArrayBuffer()

        actual fun from(array: ShortArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: IntArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: LongArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: FloatArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: DoubleArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: UByteArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: UShortArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: UIntArray): ArrayBuffer {
            TODO("Not yet implemented")
        }

        actual fun from(array: ULongArray): ArrayBuffer {
            TODO("Not yet implemented")
        }
    }
}

inline internal expect fun ByteArray.toArrayBuffer(): ArrayBuffer

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

