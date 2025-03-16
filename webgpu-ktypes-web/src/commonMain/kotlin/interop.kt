package io.ygdrasil.webgpu

expect fun <T: JsObject> createJsObject(): T
expect internal fun <A, B : JsObject> Set<A>.mapJsArray(converter: (A) -> B): JsObject

expect class JsNumber
expect class JsString
expect interface JsObject