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
         * Allocates a new ArrayBuffer with the specified size in bytes.
         * The buffer is zero-initialized and memory is managed automatically.
         *
         * @param sizeInBytes the size of the buffer in bytes
         * @return a new ArrayBuffer with the specified size
         */
        actual fun allocate(sizeInBytes: ULong): ArrayBuffer {
            val buffer = ByteBuffer.allocateDirect(sizeInBytes.toInt())
            return AndroidArrayBuffer(buffer)
        }

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

    override val size: ULong
        get() = buffer.capacity().toULong()

    // Read methods - convert entire buffer to typed arrays

    override fun toByteArray(): ByteArray {
        val array = ByteArray(buffer.capacity())
        buffer.duplicate().get(array)
        return array
    }

    override fun toShortArray(): ShortArray {
        val array = ShortArray(buffer.capacity() / Short.SIZE_BYTES)
        buffer.duplicate().asShortBuffer().get(array)
        return array
    }

    override fun toIntArray(): IntArray {
        val array = IntArray(buffer.capacity() / Int.SIZE_BYTES)
        buffer.duplicate().asIntBuffer().get(array)
        return array
    }

    override fun toFloatArray(): FloatArray {
        val array = FloatArray(buffer.capacity() / Float.SIZE_BYTES)
        buffer.duplicate().asFloatBuffer().get(array)
        return array
    }

    override fun toDoubleArray(): DoubleArray {
        val array = DoubleArray(buffer.capacity() / Double.SIZE_BYTES)
        buffer.duplicate().asDoubleBuffer().get(array)
        return array
    }

    override fun toUByteArray(): UByteArray = toByteArray().asUByteArray()

    override fun toUShortArray(): UShortArray = toShortArray().asUShortArray()

    override fun toUIntArray(): UIntArray = toIntArray().asUIntArray()

    // Indexed read methods

    override fun getByte(offset: Int): Byte = buffer.get(offset)

    override fun getShort(offset: Int): Short = buffer.getShort(offset)

    override fun getInt(offset: Int): Int = buffer.getInt(offset)

    override fun getFloat(offset: Int): Float = buffer.getFloat(offset)

    override fun getDouble(offset: Int): Double = buffer.getDouble(offset)

    override fun getUByte(offset: Int): UByte = getByte(offset).toUByte()

    override fun getUShort(offset: Int): UShort = getShort(offset).toUShort()

    override fun getUInt(offset: Int): UInt = getInt(offset).toUInt()

    // Indexed write methods

    override fun setByte(offset: Int, value: Byte) {
        buffer.put(offset, value)
    }

    override fun setShort(offset: Int, value: Short) {
        buffer.putShort(offset, value)
    }

    override fun setInt(offset: Int, value: Int) {
        buffer.putInt(offset, value)
    }

    override fun setFloat(offset: Int, value: Float) {
        buffer.putFloat(offset, value)
    }

    override fun setDouble(offset: Int, value: Double) {
        buffer.putDouble(offset, value)
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

