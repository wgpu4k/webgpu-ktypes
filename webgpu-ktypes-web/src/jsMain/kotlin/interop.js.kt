package io.ygdrasil.webgpu

import kotlinx.coroutines.await
import kotlin.js.Promise


@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asFloat(): Float = unsafeCast<Float>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asDouble(): Double = unsafeCast<Double>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asLong(): Long = unsafeCast<Long>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asInt(): Int = unsafeCast<Int>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asShort(): Short = unsafeCast<Short>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun String.asJsString(): JsString = unsafeCast<JsString>()

actual fun <K: JsAny, V: JsAny> jsMap(): JsMap<K, V> = js("new Map()").unsafeCast<JsMap<K, V>>()

actual fun <K: JsAny, V: JsAny> Map<K, V>.toJsMap(): JsMap<K, V> {
    val jsMap = jsMap<K, V>()
    forEach { (key, value) ->
        val map = jsMap
        val k = key
        val v = value
        js("map.set(k, v)")
    }
    return jsMap
}
