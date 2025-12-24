@file:OptIn(ExperimentalUnsignedTypes::class)

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
    /**
     * The size of the buffer in bytes.
     */
    actual val size: ULong

    // Read methods - convert entire buffer to typed arrays

    /**
     * Converts the buffer to a ByteArray.
     * @return a ByteArray containing the buffer's data
     */
    actual fun toByteArray(): ByteArray

    /**
     * Converts the buffer to a ShortArray.
     * @return a ShortArray containing the buffer's data (size must be multiple of 2)
     */
    actual fun toShortArray(): ShortArray

    /**
     * Converts the buffer to an IntArray.
     * @return an IntArray containing the buffer's data (size must be multiple of 4)
     */
    actual fun toIntArray(): IntArray


    /**
     * Converts the buffer to a FloatArray.
     * @return a FloatArray containing the buffer's data (size must be multiple of 4)
     */
    actual fun toFloatArray(): FloatArray

    /**
     * Converts the buffer to a DoubleArray.
     * @return a DoubleArray containing the buffer's data (size must be multiple of 8)
     */
    actual fun toDoubleArray(): DoubleArray

    /**
     * Converts the buffer to a UByteArray.
     * @return a UByteArray containing the buffer's data
     */
    actual fun toUByteArray(): UByteArray

    /**
     * Converts the buffer to a UShortArray.
     * @return a UShortArray containing the buffer's data (size must be multiple of 2)
     */
    actual fun toUShortArray(): UShortArray

    /**
     * Converts the buffer to a UIntArray.
     * @return a UIntArray containing the buffer's data (size must be multiple of 4)
     */
    actual fun toUIntArray(): UIntArray


    // Indexed read methods

    /**
     * Reads a byte at the specified offset.
     * @param offset the byte offset
     * @return the byte value
     */
    actual fun getByte(offset: Int): Byte

    /**
     * Reads a short at the specified offset.
     * @param offset the byte offset (must be aligned to 2 bytes)
     * @return the short value
     */
    actual fun getShort(offset: Int): Short

    /**
     * Reads an int at the specified offset.
     * @param offset the byte offset (must be aligned to 4 bytes)
     * @return the int value
     */
    actual fun getInt(offset: Int): Int


    /**
     * Reads a float at the specified offset.
     * @param offset the byte offset (must be aligned to 4 bytes)
     * @return the float value
     */
    actual fun getFloat(offset: Int): Float

    /**
     * Reads a double at the specified offset.
     * @param offset the byte offset (must be aligned to 8 bytes)
     * @return the double value
     */
    actual fun getDouble(offset: Int): Double

    /**
     * Reads an unsigned byte at the specified offset.
     * @param offset the byte offset
     * @return the unsigned byte value
     */
    actual fun getUByte(offset: Int): UByte

    /**
     * Reads an unsigned short at the specified offset.
     * @param offset the byte offset (must be aligned to 2 bytes)
     * @return the unsigned short value
     */
    actual fun getUShort(offset: Int): UShort

    /**
     * Reads an unsigned int at the specified offset.
     * @param offset the byte offset (must be aligned to 4 bytes)
     * @return the unsigned int value
     */
    actual fun getUInt(offset: Int): UInt


    // Indexed write methods

    /**
     * Writes a byte at the specified offset.
     * @param offset the byte offset
     * @param value the byte value to write
     */
    actual fun setByte(offset: Int, value: Byte)

    /**
     * Writes a short at the specified offset.
     * @param offset the byte offset (must be aligned to 2 bytes)
     * @param value the short value to write
     */
    actual fun setShort(offset: Int, value: Short)

    /**
     * Writes an int at the specified offset.
     * @param offset the byte offset (must be aligned to 4 bytes)
     * @param value the int value to write
     */
    actual fun setInt(offset: Int, value: Int)


    /**
     * Writes a float at the specified offset.
     * @param offset the byte offset (must be aligned to 4 bytes)
     * @param value the float value to write
     */
    actual fun setFloat(offset: Int, value: Float)

    /**
     * Writes a double at the specified offset.
     * @param offset the byte offset (must be aligned to 8 bytes)
     * @param value the double value to write
     */
    actual fun setDouble(offset: Int, value: Double)

    /**
     * Writes an unsigned byte at the specified offset.
     * @param offset the byte offset
     * @param value the unsigned byte value to write
     */
    actual fun setUByte(offset: Int, value: UByte)

    /**
     * Writes an unsigned short at the specified offset.
     * @param offset the byte offset (must be aligned to 2 bytes)
     * @param value the unsigned short value to write
     */
    actual fun setUShort(offset: Int, value: UShort)

    /**
     * Writes an unsigned int at the specified offset.
     * @param offset the byte offset (must be aligned to 4 bytes)
     * @param value the unsigned int value to write
     */
    actual fun setUInt(offset: Int, value: UInt)


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
value class JvmArrayBuffer internal constructor(val buffer: MemorySegment): ArrayBuffer {
    override val size: ULong
        get() = buffer.byteSize().toULong()

    // Read methods - convert entire buffer to typed arrays

    override fun toByteArray(): ByteArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_BYTE)

    override fun toShortArray(): ShortArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_SHORT)

    override fun toIntArray(): IntArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_INT)


    override fun toFloatArray(): FloatArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_FLOAT)

    override fun toDoubleArray(): DoubleArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_DOUBLE)

    override fun toUByteArray(): UByteArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_BYTE).asUByteArray()

    override fun toUShortArray(): UShortArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_SHORT).asUShortArray()

    override fun toUIntArray(): UIntArray = buffer.toArray(java.lang.foreign.ValueLayout.JAVA_INT).asUIntArray()


    // Indexed read methods

    override fun getByte(offset: Int): Byte = buffer.get(java.lang.foreign.ValueLayout.JAVA_BYTE, offset.toLong())

    override fun getShort(offset: Int): Short = buffer.get(java.lang.foreign.ValueLayout.JAVA_SHORT_UNALIGNED, offset.toLong())

    override fun getInt(offset: Int): Int = buffer.get(java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED, offset.toLong())


    override fun getFloat(offset: Int): Float = buffer.get(java.lang.foreign.ValueLayout.JAVA_FLOAT_UNALIGNED, offset.toLong())

    override fun getDouble(offset: Int): Double = buffer.get(java.lang.foreign.ValueLayout.JAVA_DOUBLE_UNALIGNED, offset.toLong())

    override fun getUByte(offset: Int): UByte = getByte(offset).toUByte()

    override fun getUShort(offset: Int): UShort = getShort(offset).toUShort()

    override fun getUInt(offset: Int): UInt = getInt(offset).toUInt()


    // Indexed write methods

    override fun setByte(offset: Int, value: Byte) {
        buffer.set(java.lang.foreign.ValueLayout.JAVA_BYTE, offset.toLong(), value)
    }

    override fun setShort(offset: Int, value: Short) {
        buffer.set(java.lang.foreign.ValueLayout.JAVA_SHORT_UNALIGNED, offset.toLong(), value)
    }

    override fun setInt(offset: Int, value: Int) {
        buffer.set(java.lang.foreign.ValueLayout.JAVA_INT_UNALIGNED, offset.toLong(), value)
    }


    override fun setFloat(offset: Int, value: Float) {
        buffer.set(java.lang.foreign.ValueLayout.JAVA_FLOAT_UNALIGNED, offset.toLong(), value)
    }

    override fun setDouble(offset: Int, value: Double) {
        buffer.set(java.lang.foreign.ValueLayout.JAVA_DOUBLE_UNALIGNED, offset.toLong(), value)
    }

    override fun setUByte(offset: Int, value: UByte) {
        setByte(offset, value.toByte())
    }

    override fun setUShort(offset: Int, value: UShort) {
        setShort(offset, value.toShort())
    }

    override fun setUInt(offset: Int, value: UInt) {
        setInt(offset, value.toInt())
    }

}
