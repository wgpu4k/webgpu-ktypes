@file:Suppress("unused")
@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

import js.promise.Promise
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.JsNumber
import kotlin.js.JsString

expect inline fun JsNumber.asFloat(): Float
expect inline fun JsNumber.asDouble(): Double
expect inline fun JsNumber.asInt(): Int
@Suppress("NOTHING_TO_INLINE")
inline fun JsNumber.asUInt(): UInt = asInt().toUInt()
expect inline fun JsNumber.asLong(): Long
@Suppress("NOTHING_TO_INLINE")
inline fun JsNumber.asULong(): ULong = asLong().toULong()
expect inline fun JsNumber.asShort(): Short
@Suppress("NOTHING_TO_INLINE")
inline fun JsNumber.asUShort(): UShort = asShort().toUShort()

external interface EventTarget: JsAny
external interface DOMException: JsAny
external interface Event: JsAny
external interface EventInit: JsAny

external object navigator {
    val gpu: GPU?
}

external object window {
    var devicePixelRatio: JsNumber
}

external interface GPU: JsAny {
    fun getPreferredCanvasFormat(): String
    fun requestAdapter(): Promise<JsAny>
    fun requestAdapter(descriptor: WGPURequestAdapterOptions): Promise<JsAny>
    var wgslLanguageFeatures: JsAny /* WGSLLanguageFeatures */
}

