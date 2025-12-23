@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

import js.core.JsPrimitives.toJsByte
import js.typedarrays.Int8Array
import kotlin.js.set

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ByteArray.toArrayBuffer(): ArrayBuffer {
    val array = JsArray<JsNumber>()
    forEachIndexed { index, value ->
        array[index] = value.toJsByte()
    }
    return ArrayBuffer.from(Int8Array<js.buffer.ArrayBuffer>(array).buffer)
}