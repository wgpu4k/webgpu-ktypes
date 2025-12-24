package io.ygdrasil.webgpu

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.DoubleVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.ShortVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.free
import kotlinx.cinterop.get
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlin.experimental.ExperimentalNativeApi

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

    }
}