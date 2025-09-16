package io.ygdrasil.webgpu

import kotlin.js.JsAny

external interface JsArray<T: JsAny> : JsAny {
    val length: Int
}

expect fun <T: JsAny> set(array: JsArray<T>, index: Int, value: T)
expect fun <T: JsAny> get(array: JsArray<T>, index: Int): T?

fun <A: JsAny, B> JsArray<A>.map(converter: (A) -> B): List<B> = sequence<B> {
    (0 until length).forEach { index ->
        yield(converter(get(this@map, index)!!))
    }
}.toList()


expect fun <A: JsAny> jsArray(vararg values: A): JsArray<A>
expect fun <A, B : JsAny> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B>

