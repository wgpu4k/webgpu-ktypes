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
    /**
     * The size of the buffer in bytes.
     */
    actual val size: Long

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
     * Converts the buffer to a LongArray.
     * @return a LongArray containing the buffer's data (size must be multiple of 8)
     */
    actual fun toLongArray(): LongArray

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

    /**
     * Converts the buffer to a ULongArray.
     * @return a ULongArray containing the buffer's data (size must be multiple of 8)
     */
    actual fun toULongArray(): ULongArray

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
     * Reads a long at the specified offset.
     * @param offset the byte offset (must be aligned to 8 bytes)
     * @return the long value
     */
    actual fun getLong(offset: Int): Long

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

    /**
     * Reads an unsigned long at the specified offset.
     * @param offset the byte offset (must be aligned to 8 bytes)
     * @return the unsigned long value
     */
    actual fun getULong(offset: Int): ULong

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
     * Writes a long at the specified offset.
     * @param offset the byte offset (must be aligned to 8 bytes)
     * @param value the long value to write
     */
    actual fun setLong(offset: Int, value: Long)

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

    /**
     * Writes an unsigned long at the specified offset.
     * @param offset the byte offset (must be aligned to 8 bytes)
     * @param value the unsigned long value to write
     */
    actual fun setULong(offset: Int, value: ULong)

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
value class NativeArrayBuffer internal constructor(val buffer: Any): ArrayBuffer {
    override val size: Long
        get() = when (buffer) {
            is ByteArray -> buffer.size.toLong()
            is ShortArray -> (buffer.size * Short.SIZE_BYTES).toLong()
            is IntArray -> (buffer.size * Int.SIZE_BYTES).toLong()
            is LongArray -> (buffer.size * Long.SIZE_BYTES).toLong()
            is FloatArray -> (buffer.size * Float.SIZE_BYTES).toLong()
            is DoubleArray -> (buffer.size * Double.SIZE_BYTES).toLong()
            is UByteArray -> buffer.size.toLong()
            is UShortArray -> (buffer.size * Short.SIZE_BYTES).toLong()
            is UIntArray -> (buffer.size * Int.SIZE_BYTES).toLong()
            is ULongArray -> (buffer.size * Long.SIZE_BYTES).toLong()
            else -> error("Unsupported buffer type: ${buffer::class}")
        }

    // Read methods - convert entire buffer to typed arrays

    override fun toByteArray(): ByteArray = when (buffer) {
        is ByteArray -> buffer
        is UByteArray -> buffer.asByteArray()
        else -> error("Cannot convert ${buffer::class} to ByteArray")
    }

    override fun toShortArray(): ShortArray = when (buffer) {
        is ShortArray -> buffer
        is UShortArray -> buffer.asShortArray()
        else -> error("Cannot convert ${buffer::class} to ShortArray")
    }

    override fun toIntArray(): IntArray = when (buffer) {
        is IntArray -> buffer
        is UIntArray -> buffer.asIntArray()
        else -> error("Cannot convert ${buffer::class} to IntArray")
    }

    override fun toLongArray(): LongArray = when (buffer) {
        is LongArray -> buffer
        is ULongArray -> buffer.asLongArray()
        else -> error("Cannot convert ${buffer::class} to LongArray")
    }

    override fun toFloatArray(): FloatArray = when (buffer) {
        is FloatArray -> buffer
        else -> error("Cannot convert ${buffer::class} to FloatArray")
    }

    override fun toDoubleArray(): DoubleArray = when (buffer) {
        is DoubleArray -> buffer
        else -> error("Cannot convert ${buffer::class} to DoubleArray")
    }

    override fun toUByteArray(): UByteArray = when (buffer) {
        is UByteArray -> buffer
        is ByteArray -> buffer.asUByteArray()
        else -> error("Cannot convert ${buffer::class} to UByteArray")
    }

    override fun toUShortArray(): UShortArray = when (buffer) {
        is UShortArray -> buffer
        is ShortArray -> buffer.asUShortArray()
        else -> error("Cannot convert ${buffer::class} to UShortArray")
    }

    override fun toUIntArray(): UIntArray = when (buffer) {
        is UIntArray -> buffer
        is IntArray -> buffer.asUIntArray()
        else -> error("Cannot convert ${buffer::class} to UIntArray")
    }

    override fun toULongArray(): ULongArray = when (buffer) {
        is ULongArray -> buffer
        is LongArray -> buffer.asULongArray()
        else -> error("Cannot convert ${buffer::class} to ULongArray")
    }

    // Indexed read methods

    override fun getByte(offset: Int): Byte = when (buffer) {
        is ByteArray -> buffer[offset]
        is UByteArray -> buffer[offset].toByte()
        else -> error("Cannot read byte from ${buffer::class}")
    }

    override fun getShort(offset: Int): Short = when (buffer) {
        is ShortArray -> buffer[offset / Short.SIZE_BYTES]
        is UShortArray -> buffer[offset / Short.SIZE_BYTES].toShort()
        else -> error("Cannot read short from ${buffer::class}")
    }

    override fun getInt(offset: Int): Int = when (buffer) {
        is IntArray -> buffer[offset / Int.SIZE_BYTES]
        is UIntArray -> buffer[offset / Int.SIZE_BYTES].toInt()
        else -> error("Cannot read int from ${buffer::class}")
    }

    override fun getLong(offset: Int): Long = when (buffer) {
        is LongArray -> buffer[offset / Long.SIZE_BYTES]
        is ULongArray -> buffer[offset / Long.SIZE_BYTES].toLong()
        else -> error("Cannot read long from ${buffer::class}")
    }

    override fun getFloat(offset: Int): Float = when (buffer) {
        is FloatArray -> buffer[offset / Float.SIZE_BYTES]
        else -> error("Cannot read float from ${buffer::class}")
    }

    override fun getDouble(offset: Int): Double = when (buffer) {
        is DoubleArray -> buffer[offset / Double.SIZE_BYTES]
        else -> error("Cannot read double from ${buffer::class}")
    }

    override fun getUByte(offset: Int): UByte = when (buffer) {
        is UByteArray -> buffer[offset]
        is ByteArray -> buffer[offset].toUByte()
        else -> error("Cannot read unsigned byte from ${buffer::class}")
    }

    override fun getUShort(offset: Int): UShort = when (buffer) {
        is UShortArray -> buffer[offset / Short.SIZE_BYTES]
        is ShortArray -> buffer[offset / Short.SIZE_BYTES].toUShort()
        else -> error("Cannot read unsigned short from ${buffer::class}")
    }

    override fun getUInt(offset: Int): UInt = when (buffer) {
        is UIntArray -> buffer[offset / Int.SIZE_BYTES]
        is IntArray -> buffer[offset / Int.SIZE_BYTES].toUInt()
        else -> error("Cannot read unsigned int from ${buffer::class}")
    }

    override fun getULong(offset: Int): ULong = when (buffer) {
        is ULongArray -> buffer[offset / Long.SIZE_BYTES]
        is LongArray -> buffer[offset / Long.SIZE_BYTES].toULong()
        else -> error("Cannot read unsigned long from ${buffer::class}")
    }

    // Indexed write methods

    override fun setByte(offset: Int, value: Byte) {
        when (buffer) {
            is ByteArray -> buffer[offset] = value
            is UByteArray -> buffer[offset] = value.toUByte()
            else -> error("Cannot write byte to ${buffer::class}")
        }
    }

    override fun setShort(offset: Int, value: Short) {
        when (buffer) {
            is ShortArray -> buffer[offset / Short.SIZE_BYTES] = value
            is UShortArray -> buffer[offset / Short.SIZE_BYTES] = value.toUShort()
            else -> error("Cannot write short to ${buffer::class}")
        }
    }

    override fun setInt(offset: Int, value: Int) {
        when (buffer) {
            is IntArray -> buffer[offset / Int.SIZE_BYTES] = value
            is UIntArray -> buffer[offset / Int.SIZE_BYTES] = value.toUInt()
            else -> error("Cannot write int to ${buffer::class}")
        }
    }

    override fun setLong(offset: Int, value: Long) {
        when (buffer) {
            is LongArray -> buffer[offset / Long.SIZE_BYTES] = value
            is ULongArray -> buffer[offset / Long.SIZE_BYTES] = value.toULong()
            else -> error("Cannot write long to ${buffer::class}")
        }
    }

    override fun setFloat(offset: Int, value: Float) {
        when (buffer) {
            is FloatArray -> buffer[offset / Float.SIZE_BYTES] = value
            else -> error("Cannot write float to ${buffer::class}")
        }
    }

    override fun setDouble(offset: Int, value: Double) {
        when (buffer) {
            is DoubleArray -> buffer[offset / Double.SIZE_BYTES] = value
            else -> error("Cannot write double to ${buffer::class}")
        }
    }

    override fun setUByte(offset: Int, value: UByte) {
        when (buffer) {
            is UByteArray -> buffer[offset] = value
            is ByteArray -> buffer[offset] = value.toByte()
            else -> error("Cannot write unsigned byte to ${buffer::class}")
        }
    }

    override fun setUShort(offset: Int, value: UShort) {
        when (buffer) {
            is UShortArray -> buffer[offset / Short.SIZE_BYTES] = value
            is ShortArray -> buffer[offset / Short.SIZE_BYTES] = value.toShort()
            else -> error("Cannot write unsigned short to ${buffer::class}")
        }
    }

    override fun setUInt(offset: Int, value: UInt) {
        when (buffer) {
            is UIntArray -> buffer[offset / Int.SIZE_BYTES] = value
            is IntArray -> buffer[offset / Int.SIZE_BYTES] = value.toInt()
            else -> error("Cannot write unsigned int to ${buffer::class}")
        }
    }

    override fun setULong(offset: Int, value: ULong) {
        when (buffer) {
            is ULongArray -> buffer[offset / Long.SIZE_BYTES] = value
            is LongArray -> buffer[offset / Long.SIZE_BYTES] = value.toLong()
            else -> error("Cannot write unsigned long to ${buffer::class}")
        }
    }
}

