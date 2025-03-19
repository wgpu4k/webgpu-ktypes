@file:Suppress("unused")
package io.ygdrasil.webgpu

import kotlinx.coroutines.await
import kotlin.js.Promise

actual typealias JsNumber = kotlin.js.JsNumber

actual typealias JsString = kotlin.js.JsString

actual typealias JsObject = kotlin.js.JsAny

actual fun <T : JsObject> createJsObject(): T = js("({ })")

actual fun <A, B : JsObject> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B> {
    val output = JsArray<B>()
    forEachIndexed { index, value ->
        output[index] = converter(value)
    }
    return output.unsafeCast()
}

actual fun <A: JsObject> jsArray(vararg values: A): JsArray<A> = js("Array.from(values)")

@Suppress("NOTHING_TO_INLINE")
actual inline suspend fun <T : JsObject> JsObject.wait(): T {
    return unsafeCast<Promise<T>>().await()
}
@Suppress("NOTHING_TO_INLINE")
actual inline fun <T : JsObject> JsObject.castAs(): T = unsafeCast()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsString.castAs(): JsObject = unsafeCast<JsObject>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asFloat(): Float = toFloat()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asDouble(): Double = toDouble()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asInt(): Int = toInt()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asLong(): Long = toLong()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asShort(): Short = externRefToKotlinShortAdapter(this)
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asBoolean(): Boolean = this.toInt() != 0
@Suppress("NOTHING_TO_INLINE")
actual inline fun Double.asJsNumber(): JsNumber = toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Int.asJsNumber(): JsNumber = toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Short.asJsNumber(): JsNumber = this.toInt().toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Boolean.asJsNumber(): JsNumber = if (this) 1.toJsNumber() else 0.toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun String.asJsString(): JsString = toJsString()

actual fun <K: JsObject, V: JsObject> jsMap(): JsMap<K, V> {
    return js("new Map()").unsafeCast<JsMap<K, V>>()
}

actual fun <K: JsObject, V: JsObject> Map<K, V>.toJsMap(): JsMap<K, V> {
    val jsMap = jsMap<K, V>()
    forEach { (key, value) ->
        set<K, V>(jsMap, key, value)
    }
    return jsMap
}

private fun <K: JsObject, V: JsObject> set(map: JsMap<K, V>, key: K, value: V): Nothing = js("map.set(key, value)")

public fun kotlin.js.JsNumber.toFloat(): Float =
    externRefToKotlinFloatAdapter(this)

internal fun externRefToKotlinFloatAdapter(x: JsAny): Float =
    externrefToFloat(x)

private fun externrefToFloat(ref: JsAny): Float =
    js("Number(ref)")

public fun kotlin.js.JsNumber.toLong(): Long =
    externRefToKotlinLongAdapter(this)

internal fun externRefToKotlinLongAdapter(x: JsAny): Long =
    externrefToLong(x)

private fun externrefToLong(ref: JsAny): Long =
    js("Number(ref)")

public fun kotlin.js.JsNumber.toShort(): Short =
    externRefToKotlinShortAdapter(this)

public fun externRefToKotlinShortAdapter(x: JsAny): Short =
    externrefToShort(x)

private fun externrefToShort(ref: JsAny): Short =
    js("Number(ref)")
