@file:OptIn(ExperimentalForeignApi::class)

package io.ygdrasil.webgpu

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.DoubleVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.NativePtr
import kotlinx.cinterop.ShortVar
import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.UShortVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.get
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.usePinned

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
    override val size: ULong
        get() = buffer.getSizeInBytes()

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


    // Indexed read methods

    override fun getByte(offset: Int): Byte {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<ByteVar>()
            return ptr[0]
        }
    }

    override fun getShort(offset: Int): Short {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<ShortVar>()
            return ptr[0]
        }
    }

    override fun getInt(offset: Int): Int {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<IntVar>()
            return ptr[0]
        }
    }


    override fun getFloat(offset: Int): Float {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<FloatVar>()
            return ptr[0]
        }
    }

    override fun getDouble(offset: Int): Double {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<DoubleVar>()
            return ptr[0]
        }
    }

    override fun getUByte(offset: Int): UByte {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<UByteVar>()
            return ptr[0]
        }
    }

    override fun getUShort(offset: Int): UShort {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<UShortVar>()
            return ptr[0]
        }
    }

    override fun getUInt(offset: Int): UInt {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<UIntVar>()
            return ptr[0]
        }
    }


    // Indexed write methods

    override fun setByte(offset: Int, value: Byte) {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<ByteVar>()
            ptr[0] = value
        }
    }

    override fun setShort(offset: Int, value: Short) {
        useOpaquePinned(offset) { buffer ->
            val ptr = (buffer).reinterpret<ShortVar>()
            ptr[0] = value
        }
    }

    override fun setInt(offset: Int, value: Int) {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<IntVar>()
            ptr[0] = value
        }
    }


    override fun setFloat(offset: Int, value: Float) {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<FloatVar>()
            ptr[0] = value
        }
    }

    override fun setDouble(offset: Int, value: Double) {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<DoubleVar>()
            ptr[0] = value
        }
    }

    override fun setUByte(offset: Int, value: UByte) {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<UByteVar>()
            ptr[0] = value
        }
    }

    override fun setUShort(offset: Int, value: UShort) {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<UShortVar>()
            ptr[0] = value
        }
    }

    override fun setUInt(offset: Int, value: UInt) {
        useOpaquePinned(offset) { buffer ->
            val ptr = buffer.reinterpret<UIntVar>()
            ptr[0] = value
        }
    }

    inline fun <R> useOpaquePinned(offset: Int, block: (COpaquePointer) -> R): R = buffer.useOpaquePinned(offset, block)
}

private fun Any.getSizeInBytes(): ULong = when (this) {
    is ByteArray -> size
    is ShortArray -> size * Short.SIZE_BYTES
    is IntArray -> size * Int.SIZE_BYTES
    is FloatArray -> size * Float.SIZE_BYTES
    is DoubleArray -> size * Double.SIZE_BYTES
    is UByteArray -> size
    is UShortArray -> size * Short.SIZE_BYTES
    is UIntArray -> size * Int.SIZE_BYTES
    else -> error("Unsupported buffer type: ${this::class}")
}.toULong()

@PublishedApi
internal inline fun <R> Any.useOpaquePinned(offset: Int, block: (COpaquePointer) -> R): R = when (this) {
    is ByteArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    is ShortArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    is IntArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    is FloatArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    is DoubleArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    is UByteArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    is UShortArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    is UIntArray -> this.usePinned { block(it.addressOf(0).rawValue.withOffset(offset)) }
    else -> error("Unsupported buffer type: ${this::class}")
}

@PublishedApi
internal fun NativePtr.withOffset(offset: Int) = interpretCPointer<CPointed>(this + offset.toLong())!!