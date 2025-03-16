package io.ygdrasil.webgpu

import kotlinx.coroutines.await
import kotlin.js.Promise

actual typealias JsNumber = kotlin.js.JsNumber

actual typealias JsString = kotlin.js.JsString

actual typealias JsObject = kotlin.js.JsAny

actual fun <T : JsObject> createJsObject(): T = js("({ })")

internal actual fun <A, B : JsObject> Set<A>.mapJsArray(converter: (A) -> B): JsObject {
    val output: JsArray<B> = JsArray()
    forEachIndexed { index, value ->
        output[index] = converter(value)
    }
    return output
}

actual suspend fun <T : JsObject> JsObject.wait(): T {
    return unsafeCast<Promise<T>>().await()
}

actual fun <T : JsObject> JsObject.castAs(): T = unsafeCast()

actual fun JsNumber.asDouble(): Double = toDouble()
actual fun Double.asJsNumber(): JsNumber = this.toJsNumber()