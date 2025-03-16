package io.ygdrasil.webgpu

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