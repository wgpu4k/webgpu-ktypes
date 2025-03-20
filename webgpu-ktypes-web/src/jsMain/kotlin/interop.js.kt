package io.ygdrasil.webgpu

import kotlinx.coroutines.await
import kotlin.js.Promise


/**
 * This is a just placeholder for the compiler
 */
actual class JsNumber: Number(), JsObject {
    override fun toByte(): Byte = error("Do not use this implementation")
    override fun toDouble(): Double = error("Do not use this implementation")
    override fun toFloat(): Float = error("Do not use this implementation")
    override fun toInt(): Int  = error("Do not use this implementation")
    override fun toLong(): Long  = error("Do not use this implementation")
    override fun toShort(): Short = error("Do not use this implementation")
}

actual typealias JsString = String

actual external interface JsObject

actual fun <T : JsObject> createJsObject(): T = js("({ })")


@Suppress("NOTHING_TO_INLINE")
actual inline suspend fun <T> JsObject.wait(): T {
    return unsafeCast<Promise<JsObject>>().await().unsafeCast<T>()
}

@Suppress("NOTHING_TO_INLINE")
actual inline fun <T : JsObject> JsObject.castAs(): T = unsafeCast<T>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsString.castAs(): JsObject = unsafeCast<JsObject>()
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
actual inline fun JsNumber.asBoolean(): Boolean = unsafeCast<Boolean>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Double.asJsNumber(): JsNumber = unsafeCast<JsNumber>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Int.asJsNumber(): JsNumber = unsafeCast<JsNumber>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Short.asJsNumber(): JsNumber = unsafeCast<JsNumber>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Boolean.asJsNumber(): JsNumber = js("Number(this ? 1 : 0)").unsafeCast<JsNumber>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun String.asJsString(): JsString = unsafeCast<JsString>()

actual fun <K: JsObject, V: JsObject> jsMap(): JsMap<K, V> = js("new Map()").unsafeCast<JsMap<K, V>>()

actual fun <K: JsObject, V: JsObject> Map<K, V>.toJsMap(): JsMap<K, V> {
    val jsMap = jsMap<K, V>()
    forEach { (key, value) ->
        val map = jsMap
        val k = key
        val v = value
        js("map.set(k, v)")
    }
    return jsMap
}
