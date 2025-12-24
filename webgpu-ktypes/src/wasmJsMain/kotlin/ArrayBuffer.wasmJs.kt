@file:OptIn(ExperimentalWasmJsInterop::class, ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import js.core.JsPrimitives.toJsByte
import js.core.JsPrimitives.toJsFloat
import js.core.JsPrimitives.toJsInt
import js.core.JsPrimitives.toJsShort
import js.core.JsPrimitives.toJsUByte
import js.typedarrays.Float32Array
import js.typedarrays.Float64Array
import js.typedarrays.Int16Array
import js.typedarrays.Int32Array
import js.typedarrays.Int8Array
import js.typedarrays.Uint16Array
import js.typedarrays.Uint32Array
import js.typedarrays.Uint8Array

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ByteArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toJsByte()
    }
    return ArrayBuffer.from(Int8Array<js.buffer.ArrayBuffer>(array).buffer)
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ShortArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toJsShort()
    }
    return ArrayBuffer.from(Int16Array<js.buffer.ArrayBuffer>(array).buffer)
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun IntArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toJsNumber()
    }
    return ArrayBuffer.from(Int32Array<js.buffer.ArrayBuffer>(array).buffer)
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun FloatArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toJsFloat()
    }
    return ArrayBuffer.from(Float32Array<js.buffer.ArrayBuffer>(array).buffer)
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun DoubleArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toJsNumber()
    }
    return ArrayBuffer.from(Float64Array<js.buffer.ArrayBuffer>(array).buffer)
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun UByteArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toJsUByte()
    }
    return ArrayBuffer.from(Uint8Array<js.buffer.ArrayBuffer>(array).buffer)
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun UShortArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toShort().toJsShort()
    }
    return ArrayBuffer.from(Uint16Array<js.buffer.ArrayBuffer>(array).buffer)
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun UIntArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toInt().toJsInt()
    }
    return ArrayBuffer.from(Uint32Array<js.buffer.ArrayBuffer>(array).buffer)
}

// Read methods - convert ArrayBuffer to typed arrays
@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readByteArray(): ByteArray {
    val view = Int8Array<js.buffer.ArrayBuffer>(this)
    return ByteArray(view.length) { view[it].toInt().toByte() }
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readShortArray(): ShortArray {
    val view = Int16Array<js.buffer.ArrayBuffer>(this)
    return ShortArray(view.length) { view[it].toInt().toShort() }
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readIntArray(): IntArray {
    val view = Int32Array<js.buffer.ArrayBuffer>(this)
    return IntArray(view.length) { view[it].toInt() }
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readFloatArray(): FloatArray {
    val view = Float32Array<js.buffer.ArrayBuffer>(this)
    return FloatArray(view.length) { view[it].toDouble().toFloat() }
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readDoubleArray(): DoubleArray {
    val view = Float64Array<js.buffer.ArrayBuffer>(this)
    return DoubleArray(view.length) { view[it].toDouble() }
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readUByteArray(): UByteArray {
    val view = Uint8Array<js.buffer.ArrayBuffer>(this)
    return UByteArray(view.length) { view[it].toInt().toUByte() }
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readUShortArray(): UShortArray {
    val view = Uint16Array<js.buffer.ArrayBuffer>(this)
    return UShortArray(view.length) { view[it].toInt().toUShort() }
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readUIntArray(): UIntArray {
    val view = Uint32Array<js.buffer.ArrayBuffer>(this)
    return UIntArray(view.length) { view[it].toInt().toUInt() }
}

// Indexed read methods
@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readByte(offset: Int): Byte {
    val view = Int8Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toInt().toByte()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readShort(offset: Int): Short {
    val view = Int16Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toInt().toShort()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readInt(offset: Int): Int {
    val view = Int32Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toInt()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readFloat(offset: Int): Float {
    val view = Float32Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toDouble().toFloat()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readDouble(offset: Int): Double {
    val view = Float64Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toDouble()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readUByte(offset: Int): UByte {
    val view = Uint8Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toInt().toUByte()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readUShort(offset: Int): UShort {
    val view = Uint16Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toInt().toUShort()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.readUInt(offset: Int): UInt {
    val view = Uint32Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    return view[0].toInt().toUInt()
}

// Indexed write methods
@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeByte(offset: Int, value: Byte) {
    val view = Int8Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toJsByte()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeShort(offset: Int, value: Short) {
    val view = Int16Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toJsShort()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeInt(offset: Int, value: Int) {
    val view = Int32Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toJsInt()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeFloat(offset: Int, value: Float) {
    val view = Float32Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toJsFloat()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeDouble(offset: Int, value: Double) {
    val view = Float64Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toJsNumber()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeUByte(offset: Int, value: UByte) {
    val view = Uint8Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toJsUByte()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeUShort(offset: Int, value: UShort) {
    val view = Uint16Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toShort().toJsShort()
}

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun js.buffer.ArrayBuffer.writeUInt(offset: Int, value: UInt) {
    val view = Uint32Array<js.buffer.ArrayBuffer>(
        this, offset, 1
    )
    view[0] = value.toInt().toJsInt()
}