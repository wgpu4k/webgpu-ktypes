@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import org.khronos.webgl.Float32Array
import org.khronos.webgl.Float64Array
import org.khronos.webgl.Int16Array
import org.khronos.webgl.Int32Array
import org.khronos.webgl.Int8Array
import kotlin.js.JsArray
import kotlin.js.JsNumber
import kotlin.js.toJsNumber

actual fun ByteArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.toJsNumber())
    }
    return Int8Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun UShortArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.asJsNumber())
    }
    return Int16Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun ShortArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.toJsNumber())
    }
    return Int16Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun IntArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.toJsNumber())
    }
    return Int32Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun UIntArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.asJsNumber())
    }
    return Int32Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun LongArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.toJsNumber())
    }
    return Int32Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun ULongArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.asJsNumber())
    }
    return Int32Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun FloatArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.toJsNumber())
    }
    return Float32Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}

actual fun DoubleArray.asArrayBuffer(): ArrayBuffer {
    val array = jsArray<JsNumber>()
    forEachIndexed { index, value ->
        set(array, index, value.toJsNumber())
    }
    return Float64Array(array.unsafeCast<JsArray<JsNumber>>()).buffer
}
