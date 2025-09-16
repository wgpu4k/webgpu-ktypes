@file:Suppress("unused")
package io.ygdrasil.webgpu

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

expect inline fun String.asJsString(): JsString
expect fun <K: JsAny, V: JsAny> jsMap(): JsMap<K, V>
expect fun <K: JsAny, V: JsAny> Map<K, V>.toJsMap(): JsMap<K, V>


external interface JsMap<a: JsAny, B: JsAny> : JsAny

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
    fun requestAdapter(): JsAny
    fun requestAdapter(descriptor: WGPURequestAdapterOptions): JsAny
    var wgslLanguageFeatures: JsAny /* WGSLLanguageFeatures */
}

external interface HTMLCanvasElement: JsAny {
    fun getContext(name: String): JsAny

    var clientHeight: JsNumber
    var clientWidth: JsNumber
    var width: JsNumber
    var height: JsNumber
}
