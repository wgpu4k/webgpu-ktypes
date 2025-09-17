@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

actual fun <A, B : JsAny> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B> {
    val output = JsArray<B>()
    forEachIndexed { index, value ->
        output[index] = converter(value)
    }
    return output.unsafeCast()
}

actual fun <A: JsAny> jsArray(vararg values: A): JsArray<A> = js("Array.from(values)")
