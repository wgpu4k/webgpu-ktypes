@file:OptIn(ExperimentalForeignApi::class)

package io.ygdrasil.webgpu

import kotlinx.cinterop.COpaque
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.set

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
        buffer.useOpaquePinned { buffer ->
            val ptr = buffer.reinterpret<ByteVar>()
            ptr[offset] = value
        }
    }

    override fun setShort(offset: Int, value: Short) {
        buffer.useOpaquePinned { buffer ->

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

private fun Any.getSizeInBytes(): Long = when (this) {
    is ByteArray -> size.toLong()
    is ShortArray -> (size * Short.SIZE_BYTES).toLong()
    is IntArray -> (size * Int.SIZE_BYTES).toLong()
    is LongArray -> (size * Long.SIZE_BYTES).toLong()
    is FloatArray -> (size * Float.SIZE_BYTES).toLong()
    is DoubleArray -> (size * Double.SIZE_BYTES).toLong()
    is UByteArray -> size.toLong()
    is UShortArray -> (size * Short.SIZE_BYTES).toLong()
    is UIntArray -> (size * Int.SIZE_BYTES).toLong()
    is ULongArray -> (size * Long.SIZE_BYTES).toLong()
    else -> error("Unsupported buffer type: ${this::class}")
}


private inline fun <R> Any.useOpaquePinned(block: (COpaquePointer) -> R): R = when (this) {
    is ByteArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is ShortArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is IntArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is LongArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is FloatArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is DoubleArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is UByteArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is UShortArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is UIntArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    is ULongArray -> this.usePinned { block(it.addressOf(0).reinterpret<COpaque>()) }
    else -> error("Unsupported buffer type: ${this::class}")
}