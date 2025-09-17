@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu


actual fun <A, B : JsAny> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B> {
    return map { converter(it) }
        .toList()
        .toTypedArray()
        .unsafeCast<JsArray<B>>()
}

actual fun <A: JsAny> jsArray(vararg values: A): JsArray<A> {
    return js("Array.from(values)").unsafeCast<JsArray<A>>()
}
