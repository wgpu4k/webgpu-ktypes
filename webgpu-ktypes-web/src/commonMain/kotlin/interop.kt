@file:Suppress("unused")
package io.ygdrasil.webgpu

expect fun <T: JsObject> createJsObject(): T
expect inline suspend fun <T> JsObject.wait(): T
expect inline fun <T : JsObject> JsObject.castAs(): T
expect inline fun JsString.castAs(): JsObject
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
expect inline fun JsNumber.asBoolean(): Boolean
expect inline fun Double.asJsNumber(): JsNumber
expect inline fun Int.asJsNumber(): JsNumber
expect inline fun Short.asJsNumber(): JsNumber
expect inline fun Boolean.asJsNumber(): JsNumber
expect inline fun String.asJsString(): JsString
expect fun <K: JsObject, V: JsObject> jsMap(): JsMap<K, V>
expect fun <K: JsObject, V: JsObject> Map<K, V>.toJsMap(): JsMap<K, V>


expect class JsNumber : JsObject
expect class JsString
expect interface JsObject
external interface JsMap<a: JsObject, B: JsObject> : JsObject

external interface EventTarget: JsObject
external interface DOMException: JsObject
external interface Event: JsObject
external interface EventInit: JsObject

external object navigator {
    val gpu: GPU?
}

external object window {
    var devicePixelRatio: JsNumber
}

external interface GPU: JsObject {
    fun getPreferredCanvasFormat(): String
    fun requestAdapter(): JsObject
    fun requestAdapter(descriptor: WGPURequestAdapterOptions): JsObject
    var wgslLanguageFeatures: JsObject /* WGSLLanguageFeatures */
}

external interface HTMLCanvasElement: JsObject {
    fun getContext(name: String): JsObject

    var clientHeight: JsNumber
    var clientWidth: JsNumber
    var width: JsNumber
    var height: JsNumber
}
