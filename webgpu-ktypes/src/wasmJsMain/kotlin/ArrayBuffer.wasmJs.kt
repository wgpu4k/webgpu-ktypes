@file:OptIn(ExperimentalWasmJsInterop::class, ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import js.core.BigInt
import js.core.JsPrimitives.toJsByte
import js.core.JsPrimitives.toJsFloat
import js.core.JsPrimitives.toJsInt
import js.core.JsPrimitives.toJsShort
import js.core.JsPrimitives.toJsUByte
import js.typedarrays.*
import kotlin.js.set

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
internal actual inline fun LongArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<BigInt>()
    forEachIndexed { index, value ->
        array[index] = BigInt(value)
    }
    return ArrayBuffer.from(BigInt64Array<js.buffer.ArrayBuffer>(array).buffer)
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

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ULongArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<BigInt>()
    forEachIndexed { index, value ->
        array[index] = BigInt(value.toLong())
    }
    return ArrayBuffer.from(BigInt64Array<js.buffer.ArrayBuffer>(array).buffer)
}