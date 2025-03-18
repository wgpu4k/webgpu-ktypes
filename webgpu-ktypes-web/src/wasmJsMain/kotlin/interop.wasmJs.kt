package io.ygdrasil.webgpu

import kotlinx.coroutines.await
import kotlin.js.Promise

actual typealias JsNumber = kotlin.js.JsNumber

actual typealias JsString = kotlin.js.JsString

actual typealias JsObject = kotlin.js.JsAny

actual fun <T : JsObject> createJsObject(): T = js("({ })")

inline actual fun <A, B : JsObject> Collection<A>.mapJsArray(converter: (A) -> B): JsObject {
    val output = JsArray<B>()
    forEachIndexed { index, value ->
        output[index] = converter(value)
    }
    return output
}
@Suppress("NOTHING_TO_INLINE")
actual inline suspend fun <T : JsObject> JsObject.wait(): T {
    return unsafeCast<Promise<T>>().await()
}
@Suppress("NOTHING_TO_INLINE")
actual inline fun <T : JsObject> JsObject.castAs(): T = unsafeCast()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asDouble(): Double = toDouble()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Double.asJsNumber(): JsNumber = this.toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Int.asJsNumber(): JsNumber = this.toJsNumber()
