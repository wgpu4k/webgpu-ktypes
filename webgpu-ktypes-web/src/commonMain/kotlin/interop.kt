package io.ygdrasil.webgpu

expect fun <T: JsObject> createJsObject(): T
expect inline fun <A, B : JsObject> Collection<A>.mapJsArray(crossinline converter: (A) -> B): JsObject
expect inline suspend fun <T: JsObject> JsObject.wait(): T
expect inline fun <T : JsObject> JsObject.castAs(): T
expect inline fun JsNumber.asDouble(): Double
expect inline fun Double.asJsNumber(): JsNumber
expect inline fun Int.asJsNumber(): JsNumber

expect class JsNumber : JsObject
expect class JsString
expect interface JsObject

