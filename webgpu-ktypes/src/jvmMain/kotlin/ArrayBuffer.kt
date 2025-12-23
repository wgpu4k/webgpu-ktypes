package io.ygdrasil.webgpu

import java.lang.foreign.MemorySegment
import java.nio.ByteBuffer

/**
 * Represents a platform-specific abstraction for handling raw binary data buffers.
 *
 * `ArrayBuffer` is a sealed interface that provides a common type for working with
 * binary data across multiple platforms. It is typically associated with use cases
 * such as data transfer, WebGPU operations, or interfacing with native libraries.
 *
 * This interface is intended to be implemented by platform-specific classes
 * or value types that wrap the underlying buffer implementation, such as `ByteBuffer`
 * on JVM or `org.khronos.webgl.ArrayBuffer` on JavaScript or Wasm.
 */
actual sealed interface ArrayBuffer {
    actual companion object {
        /**
         * Creates an ArrayBuffer from a MemorySegment.
         * @param segment the memory segment to wrap
         * @return an ArrayBuffer backed by the memory segment
         */
        fun from(segment: MemorySegment): ArrayBuffer = JvmArrayBuffer(segment)

        /**
         * Creates an ArrayBuffer from a ByteBuffer.
         * @param buffer the byte buffer to convert
         * @return an ArrayBuffer backed by the byte buffer's memory segment
         */
        fun from(buffer: ByteBuffer): ArrayBuffer = JvmArrayBuffer(MemorySegment.ofBuffer(buffer))

        /**
         * Creates an ArrayBuffer from a ByteArray.
         * @param array the byte array to convert
         * @return an ArrayBuffer containing the data from the byte array
         */
        actual fun from(array: ByteArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array))
        }

        /**
         * Creates an ArrayBuffer from a ShortArray.
         * @param array the short array to convert
         * @return an ArrayBuffer containing the data from the short array
         */
        actual fun from(array: ShortArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array))
        }

        /**
         * Creates an ArrayBuffer from an IntArray.
         * @param array the int array to convert
         * @return an ArrayBuffer containing the data from the int array
         */
        actual fun from(array: IntArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array))
        }

        /**
         * Creates an ArrayBuffer from a LongArray.
         * @param array the long array to convert
         * @return an ArrayBuffer containing the data from the long array
         */
        actual fun from(array: LongArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array))
        }

        /**
         * Creates an ArrayBuffer from a FloatArray.
         * @param array the float array to convert
         * @return an ArrayBuffer containing the data from the float array
         */
        actual fun from(array: FloatArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array))
        }

        /**
         * Creates an ArrayBuffer from a DoubleArray.
         * @param array the double array to convert
         * @return an ArrayBuffer containing the data from the double array
         */
        actual fun from(array: DoubleArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array))
        }

        /**
         * Creates an ArrayBuffer from a UByteArray.
         * @param array the unsigned byte array to convert
         * @return an ArrayBuffer containing the data from the unsigned byte array
         */
        actual fun from(array: UByteArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array.asByteArray()))
        }

        /**
         * Creates an ArrayBuffer from a UShortArray.
         * @param array the unsigned short array to convert
         * @return an ArrayBuffer containing the data from the unsigned short array
         */
        actual fun from(array: UShortArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array.asShortArray()))
        }

        /**
         * Creates an ArrayBuffer from a UIntArray.
         * @param array the unsigned int array to convert
         * @return an ArrayBuffer containing the data from the unsigned int array
         */
        actual fun from(array: UIntArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array.asIntArray()))
        }

        /**
         * Creates an ArrayBuffer from a ULongArray.
         * @param array the unsigned long array to convert
         * @return an ArrayBuffer containing the data from the unsigned long array
         */
        actual fun from(array: ULongArray): ArrayBuffer {
            return JvmArrayBuffer(MemorySegment.ofArray(array.asLongArray()))
        }
    }
}


/**
 * A JVM-specific implementation of the `ArrayBuffer` interface.
 *
 * `JvmArrayBuffer` provides a lightweight wrapper around the `MemorySegment` class, allowing
 * JVM platforms to manage, access, and manipulate raw binary data in a way that conforms
 * to the `ArrayBuffer` abstraction.
 *
 * This class leverages the `@JvmInline` annotation, making it a value class. This ensures
 * minimal runtime overhead and allows the `MemorySegment` instance to be used with improved
 * performance due to inlining and reduced object allocations.
 *
 * @param buffer The underlying `MemorySegment` instance that serves as the basis for this array buffer.
 */
@JvmInline
value class JvmArrayBuffer internal constructor(val buffer: MemorySegment): ArrayBuffer
