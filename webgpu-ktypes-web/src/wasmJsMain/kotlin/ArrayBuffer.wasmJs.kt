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
    return Int8Array<ArrayBuffer>(array).buffer
}

actual fun UShortArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Int16Array<ArrayBuffer>(array).buffer
}

actual fun ShortArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Int16Array<ArrayBuffer>(array).buffer
}

actual fun IntArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Int32Array<ArrayBuffer>(array).buffer
}

actual fun UIntArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Int32Array<ArrayBuffer>(array).buffer
}

actual fun LongArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Int32Array<ArrayBuffer>(array).buffer
}

actual fun ULongArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Int32Array<ArrayBuffer>(array).buffer
}

actual fun FloatArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Float32Array<ArrayBuffer>(array).buffer
}

actual fun DoubleArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.asJsNumber()
    }
    return Float64Array<ArrayBuffer>(array).buffer
}
