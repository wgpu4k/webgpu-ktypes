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
value class WebArrayBuffer internal constructor(val buffer: js.buffer.ArrayBuffer): ArrayBuffer {
    override val size: Long
        get() = buffer.byteLength.toLong()

    // Read methods - convert entire buffer to typed arrays

    override fun toByteArray(): ByteArray {
        val view = js.typedarrays.Int8Array<js.buffer.ArrayBuffer>(buffer)
        return ByteArray(view.length) { view[it] }
    }

    override fun toShortArray(): ShortArray {
        val view = js.typedarrays.Int16Array<js.buffer.ArrayBuffer>(buffer)
        return ShortArray(view.length) { view[it] }
    }

    override fun toIntArray(): IntArray {
        val view = js.typedarrays.Int32Array<js.buffer.ArrayBuffer>(buffer)
        return IntArray(view.length) { view[it] }
    }

    override fun toLongArray(): LongArray {
        val view = js.typedarrays.BigInt64Array<js.buffer.ArrayBuffer>(buffer)
        return LongArray(view.length) { view[it].toLong() }
    }

    override fun toFloatArray(): FloatArray {
        val view = js.typedarrays.Float32Array<js.buffer.ArrayBuffer>(buffer)
        return FloatArray(view.length) { view[it] }
    }

    override fun toDoubleArray(): DoubleArray {
        val view = js.typedarrays.Float64Array<js.buffer.ArrayBuffer>(buffer)
        return DoubleArray(view.length) { view[it] }
    }

    override fun toUByteArray(): UByteArray {
        val view = js.typedarrays.Uint8Array<js.buffer.ArrayBuffer>(buffer)
        return UByteArray(view.length) { view[it].toUByte() }
    }

    override fun toUShortArray(): UShortArray {
        val view = js.typedarrays.Uint16Array<js.buffer.ArrayBuffer>(buffer)
        return UShortArray(view.length) { view[it].toUShort() }
    }

    override fun toUIntArray(): UIntArray {
        val view = js.typedarrays.Uint32Array<js.buffer.ArrayBuffer>(buffer)
        return UIntArray(view.length) { view[it].toUInt() }
    }

    override fun toULongArray(): ULongArray {
        val view = js.typedarrays.BigUint64Array<js.buffer.ArrayBuffer>(buffer)
        return ULongArray(view.length) { view[it].toULong() }
    }

    // Indexed read methods

    override fun getByte(offset: Int): Byte {
        val view = js.typedarrays.Int8Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset]
    }

    override fun getShort(offset: Int): Short {
        val view = js.typedarrays.Int16Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Short.SIZE_BYTES]
    }

    override fun getInt(offset: Int): Int {
        val view = js.typedarrays.Int32Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Int.SIZE_BYTES]
    }

    override fun getLong(offset: Int): Long {
        val view = js.typedarrays.BigInt64Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Long.SIZE_BYTES].toLong()
    }

    override fun getFloat(offset: Int): Float {
        val view = js.typedarrays.Float32Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Float.SIZE_BYTES]
    }

    override fun getDouble(offset: Int): Double {
        val view = js.typedarrays.Float64Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Double.SIZE_BYTES]
    }

    override fun getUByte(offset: Int): UByte {
        val view = js.typedarrays.Uint8Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset].toUByte()
    }

    override fun getUShort(offset: Int): UShort {
        val view = js.typedarrays.Uint16Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Short.SIZE_BYTES].toUShort()
    }

    override fun getUInt(offset: Int): UInt {
        val view = js.typedarrays.Uint32Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Int.SIZE_BYTES].toUInt()
    }

    override fun getULong(offset: Int): ULong {
        val view = js.typedarrays.BigUint64Array<js.buffer.ArrayBuffer>(buffer)
        return view[offset / Long.SIZE_BYTES].toULong()
    }

    // Indexed write methods

    override fun setByte(offset: Int, value: Byte) {
        val view = js.typedarrays.Int8Array<js.buffer.ArrayBuffer>(buffer)
        view[offset] = value
    }

    override fun setShort(offset: Int, value: Short) {
        val view = js.typedarrays.Int16Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Short.SIZE_BYTES] = value
    }

    override fun setInt(offset: Int, value: Int) {
        val view = js.typedarrays.Int32Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Int.SIZE_BYTES] = value
    }

    override fun setLong(offset: Int, value: Long) {
        val view = js.typedarrays.BigInt64Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Long.SIZE_BYTES] = js.core.BigInt(value)
    }

    override fun setFloat(offset: Int, value: Float) {
        val view = js.typedarrays.Float32Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Float.SIZE_BYTES] = value
    }

    override fun setDouble(offset: Int, value: Double) {
        val view = js.typedarrays.Float64Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Double.SIZE_BYTES] = value
    }

    override fun setUByte(offset: Int, value: UByte) {
        val view = js.typedarrays.Uint8Array<js.buffer.ArrayBuffer>(buffer)
        view[offset] = value.toInt()
    }

    override fun setUShort(offset: Int, value: UShort) {
        val view = js.typedarrays.Uint16Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Short.SIZE_BYTES] = value.toInt()
    }

    override fun setUInt(offset: Int, value: UInt) {
        val view = js.typedarrays.Uint32Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Int.SIZE_BYTES] = value.toInt()
    }

    override fun setULong(offset: Int, value: ULong) {
        val view = js.typedarrays.BigUint64Array<js.buffer.ArrayBuffer>(buffer)
        view[offset / Long.SIZE_BYTES] = js.core.BigInt(value.toLong())
    }
}

