package io.ygdrasil.webgpu

import kotlinx.cinterop.*
import kotlin.experimental.ExperimentalNativeApi

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
 * Represents a native array buffer backed by an opaque C pointer, providing direct access
 * to unmanaged memory for efficient interop with native C libraries.
 *
 * This class wraps a raw C pointer (COpaquePointer) and manages the lifecycle of the allocated
 * memory. It implements the `ArrayBuffer` interface to provide a unified API for buffer operations
 * while allowing direct manipulation of native memory.
 *
 * The buffer automatically manages memory allocation and deallocation using Kotlin/Native's
 * Arena or manual memory management. This is particularly useful when interfacing with WebGPU
 * or other graphics APIs that expect native memory pointers.
 *
 * @param pointer The opaque C pointer to the native memory
 * @param size The size of the buffer in bytes
 * @param ownsMemory Whether this buffer owns the memory and should free it on cleanup
 */
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
class OpaquePointerArrayBuffer internal constructor(
    private val pointer: COpaquePointer,
    override val size: Long,
    private val ownsMemory: Boolean = true
) : ArrayBuffer {

    /**
     * Creates a new buffer by allocating native memory of the specified size.
     * @param sizeInBytes The size of the buffer in bytes
     */
    constructor(sizeInBytes: Long) : this(
        pointer = nativeHeap.allocArray<ByteVar>(sizeInBytes.toInt()).reinterpret(),
        size = sizeInBytes,
        ownsMemory = true
    )

    /**
     * Creates a new buffer from an existing pointer without taking ownership.
     * @param pointer The opaque C pointer
     * @param sizeInBytes The size of the buffer in bytes
     */
    constructor(pointer: COpaquePointer, sizeInBytes: Long) : this(
        pointer = pointer,
        size = sizeInBytes,
        ownsMemory = false
    )

    private val bytePtr: CPointer<ByteVar>
        get() = pointer.reinterpret()

    // Read methods - convert entire buffer to typed arrays

    override fun toByteArray(): ByteArray {
        val array = ByteArray(size.toInt())
        for (i in 0 until size.toInt()) {
            array[i] = bytePtr[i]
        }
        return array
    }

    override fun toShortArray(): ShortArray {
        require(size % Short.SIZE_BYTES == 0L) { "Buffer size must be multiple of ${Short.SIZE_BYTES}" }
        val array = ShortArray((size / Short.SIZE_BYTES).toInt())
        val shortPtr = pointer.reinterpret<ShortVar>()
        for (i in array.indices) {
            array[i] = shortPtr[i]
        }
        return array
    }

    override fun toIntArray(): IntArray {
        require(size % Int.SIZE_BYTES == 0L) { "Buffer size must be multiple of ${Int.SIZE_BYTES}" }
        val array = IntArray((size / Int.SIZE_BYTES).toInt())
        val intPtr = pointer.reinterpret<IntVar>()
        for (i in array.indices) {
            array[i] = intPtr[i]
        }
        return array
    }

    override fun toLongArray(): LongArray {
        require(size % Long.SIZE_BYTES == 0L) { "Buffer size must be multiple of ${Long.SIZE_BYTES}" }
        val array = LongArray((size / Long.SIZE_BYTES).toInt())
        val longPtr = pointer.reinterpret<LongVar>()
        for (i in array.indices) {
            array[i] = longPtr[i]
        }
        return array
    }

    override fun toFloatArray(): FloatArray {
        require(size % Float.SIZE_BYTES == 0L) { "Buffer size must be multiple of ${Float.SIZE_BYTES}" }
        val array = FloatArray((size / Float.SIZE_BYTES).toInt())
        val floatPtr = pointer.reinterpret<FloatVar>()
        for (i in array.indices) {
            array[i] = floatPtr[i]
        }
        return array
    }

    override fun toDoubleArray(): DoubleArray {
        require(size % Double.SIZE_BYTES == 0L) { "Buffer size must be multiple of ${Double.SIZE_BYTES}" }
        val array = DoubleArray((size / Double.SIZE_BYTES).toInt())
        val doublePtr = pointer.reinterpret<DoubleVar>()
        for (i in array.indices) {
            array[i] = doublePtr[i]
        }
        return array
    }

    override fun toUByteArray(): UByteArray {
        return toByteArray().asUByteArray()
    }

    override fun toUShortArray(): UShortArray {
        return toShortArray().asUShortArray()
    }

    override fun toUIntArray(): UIntArray {
        return toIntArray().asUIntArray()
    }

    override fun toULongArray(): ULongArray {
        return toLongArray().asULongArray()
    }

    // Indexed read methods

    override fun getByte(offset: Int): Byte {
        require(offset >= 0 && offset < size) { "Offset out of bounds: $offset" }
        return bytePtr[offset]
    }

    override fun getShort(offset: Int): Short {
        require(offset >= 0 && offset + Short.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        return pointer.reinterpret<ShortVar>()[offset / Short.SIZE_BYTES]
    }

    override fun getInt(offset: Int): Int {
        require(offset >= 0 && offset + Int.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        return pointer.reinterpret<IntVar>()[offset / Int.SIZE_BYTES]
    }

    override fun getLong(offset: Int): Long {
        require(offset >= 0 && offset + Long.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        return pointer.reinterpret<LongVar>()[offset / Long.SIZE_BYTES]
    }

    override fun getFloat(offset: Int): Float {
        require(offset >= 0 && offset + Float.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        return pointer.reinterpret<FloatVar>()[offset / Float.SIZE_BYTES]
    }

    override fun getDouble(offset: Int): Double {
        require(offset >= 0 && offset + Double.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        return pointer.reinterpret<DoubleVar>()[offset / Double.SIZE_BYTES]
    }

    override fun getUByte(offset: Int): UByte {
        return getByte(offset).toUByte()
    }

    override fun getUShort(offset: Int): UShort {
        return getShort(offset).toUShort()
    }

    override fun getUInt(offset: Int): UInt {
        return getInt(offset).toUInt()
    }

    override fun getULong(offset: Int): ULong {
        return getLong(offset).toULong()
    }

    // Indexed write methods

    override fun setByte(offset: Int, value: Byte) {
        require(offset >= 0 && offset < size) { "Offset out of bounds: $offset" }
        bytePtr[offset] = value
    }

    override fun setShort(offset: Int, value: Short) {
        require(offset >= 0 && offset + Short.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        pointer.reinterpret<ShortVar>()[offset / Short.SIZE_BYTES] = value
    }

    override fun setInt(offset: Int, value: Int) {
        require(offset >= 0 && offset + Int.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        pointer.reinterpret<IntVar>()[offset / Int.SIZE_BYTES] = value
    }

    override fun setLong(offset: Int, value: Long) {
        require(offset >= 0 && offset + Long.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        pointer.reinterpret<LongVar>()[offset / Long.SIZE_BYTES] = value
    }

    override fun setFloat(offset: Int, value: Float) {
        require(offset >= 0 && offset + Float.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        pointer.reinterpret<FloatVar>()[offset / Float.SIZE_BYTES] = value
    }

    override fun setDouble(offset: Int, value: Double) {
        require(offset >= 0 && offset + Double.SIZE_BYTES <= size) { "Offset out of bounds: $offset" }
        pointer.reinterpret<DoubleVar>()[offset / Double.SIZE_BYTES] = value
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

    override fun setULong(offset: Int, value: ULong) {
        setLong(offset, value.toLong())
    }

    /**
     * Returns the raw C pointer for interop with native APIs.
     * Use with caution as this provides direct access to unmanaged memory.
     */
    fun getRawPointer(): COpaquePointer = pointer

    /**
     * Copies data from a ByteArray into this buffer at the specified offset.
     * @param source The source byte array
     * @param offset The offset in this buffer where to start writing
     */
    fun copyFrom(source: ByteArray, offset: Int = 0) {
        require(offset >= 0 && offset + source.size <= size) { "Copy would exceed buffer bounds" }
        for (i in source.indices) {
            bytePtr[offset + i] = source[i]
        }
    }

    /**
     * Copies data from this buffer into a ByteArray at the specified offset.
     * @param destination The destination byte array
     * @param offset The offset in this buffer where to start reading
     * @param length The number of bytes to copy
     */
    fun copyTo(destination: ByteArray, offset: Int = 0, length: Int = destination.size) {
        require(offset >= 0 && offset + length <= size) { "Copy would exceed buffer bounds" }
        require(length <= destination.size) { "Destination array too small" }
        for (i in 0 until length) {
            destination[i] = bytePtr[offset + i]
        }
    }

    /**
     * Releases the native memory if this buffer owns it.
     * After calling this method, the buffer should not be used anymore.
     */
    fun free() {
        if (ownsMemory) {
            nativeHeap.free(bytePtr)
        }
    }

    companion object {
        /**
         * Creates an OpaquePointerArrayBuffer from a ByteArray by copying the data.
         * @param array The source byte array
         * @return A new buffer containing a copy of the array data
         */
        fun fromByteArray(array: ByteArray): OpaquePointerArrayBuffer {
            val buffer = OpaquePointerArrayBuffer(array.size.toLong())
            buffer.copyFrom(array)
            return buffer
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a ShortArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromShortArray(array: ShortArray): OpaquePointerArrayBuffer {
            val buffer = OpaquePointerArrayBuffer((array.size * Short.SIZE_BYTES).toLong())
            val ptr = buffer.pointer.reinterpret<ShortVar>()
            for (i in array.indices) {
                ptr[i] = array[i]
            }
            return buffer
        }

        /**
         * Creates an OpaquePointerArrayBuffer from an IntArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromIntArray(array: IntArray): OpaquePointerArrayBuffer {
            val buffer = OpaquePointerArrayBuffer((array.size * Int.SIZE_BYTES).toLong())
            val ptr = buffer.pointer.reinterpret<IntVar>()
            for (i in array.indices) {
                ptr[i] = array[i]
            }
            return buffer
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a LongArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromLongArray(array: LongArray): OpaquePointerArrayBuffer {
            val buffer = OpaquePointerArrayBuffer((array.size * Long.SIZE_BYTES).toLong())
            val ptr = buffer.pointer.reinterpret<LongVar>()
            for (i in array.indices) {
                ptr[i] = array[i]
            }
            return buffer
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a FloatArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromFloatArray(array: FloatArray): OpaquePointerArrayBuffer {
            val buffer = OpaquePointerArrayBuffer((array.size * Float.SIZE_BYTES).toLong())
            val ptr = buffer.pointer.reinterpret<FloatVar>()
            for (i in array.indices) {
                ptr[i] = array[i]
            }
            return buffer
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a DoubleArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromDoubleArray(array: DoubleArray): OpaquePointerArrayBuffer {
            val buffer = OpaquePointerArrayBuffer((array.size * Double.SIZE_BYTES).toLong())
            val ptr = buffer.pointer.reinterpret<DoubleVar>()
            for (i in array.indices) {
                ptr[i] = array[i]
            }
            return buffer
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a UByteArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromUByteArray(array: UByteArray): OpaquePointerArrayBuffer {
            return fromByteArray(array.asByteArray())
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a UShortArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromUShortArray(array: UShortArray): OpaquePointerArrayBuffer {
            return fromShortArray(array.asShortArray())
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a UIntArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromUIntArray(array: UIntArray): OpaquePointerArrayBuffer {
            return fromIntArray(array.asIntArray())
        }

        /**
         * Creates an OpaquePointerArrayBuffer from a ULongArray by copying the data.
         * @param array The source array
         * @return A new buffer containing a copy of the array data
         */
        fun fromULongArray(array: ULongArray): OpaquePointerArrayBuffer {
            return fromLongArray(array.asLongArray())
        }
    }
}

