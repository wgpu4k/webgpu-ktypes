@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

import js.typedarrays.Float32Array
import js.typedarrays.Float64Array
import js.typedarrays.Int16Array
import js.typedarrays.Int32Array
import js.typedarrays.Int8Array
import kotlin.js.JsNumber

actual fun ByteArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Int8Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun UShortArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Int16Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun ShortArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Int16Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun IntArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Int32Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun UIntArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Int32Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun LongArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Int32Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun ULongArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Int32Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun FloatArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Float32Array<js.buffer.ArrayBuffer>(array).buffer)
}

actual fun DoubleArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return ArrayBuffer.from(Float64Array<js.buffer.ArrayBuffer>(array).buffer)
}
