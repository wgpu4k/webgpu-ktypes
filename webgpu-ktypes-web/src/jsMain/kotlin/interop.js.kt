package io.ygdrasil.webgpu

import kotlinx.coroutines.await
import kotlin.js.Promise


/**
 * This is a just placeholder for the compiler
 */
actual class JsNumber: Number() {
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
internal actual fun <A, B : JsObject> Set<A>.mapJsArray(converter: (A) -> B): JsObject {
    return asSequence()
        .map { converter(it) }
        .toList()
        .toTypedArray()
        .unsafeCast<JsObject>()
}

actual suspend fun <T : JsObject> JsObject.wait(): T {
    return unsafeCast<Promise<T>>().await()
}

actual fun <T : JsObject> JsObject.castAs(): T = unsafeCast<T>()

actual fun JsNumber.asDouble(): Double = toDouble()
actual fun Double.asJsNumber(): JsNumber = this.unsafeCast<JsNumber>()