package io.ygdrasil.webgpu

external interface JsArray<T: JsObject> : JsObject {
    val length: Int
}

expect fun <T: JsObject> get(array: JsArray<T>, index: Int): T?

fun <A: JsObject, B> JsArray<A>.map(converter: (A) -> B): List<B> = sequence<B> {
    (0 until length).forEach { index ->
        yield(converter(get(this@map, index)!!))
    }
}.toList()


expect fun <A: JsObject> jsArray(vararg values: A): JsArray<A>
expect fun <A, B : JsObject> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B>

