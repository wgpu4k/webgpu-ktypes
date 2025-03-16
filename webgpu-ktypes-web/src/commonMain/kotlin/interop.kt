package io.ygdrasil.webgpu

expect fun <T: JsObject> createJsObject(): T
expect internal fun <A, B : JsObject> Set<A>.mapJsArray(converter: (A) -> B): JsObject
expect suspend fun <T: JsObject> JsObject.wait(): T
expect fun <T : JsObject> JsObject.castAs(): T
expect fun JsNumber.asDouble(): Double
expect fun Double.asJsNumber(): JsNumber


expect class JsNumber
expect class JsString
expect interface JsObject
