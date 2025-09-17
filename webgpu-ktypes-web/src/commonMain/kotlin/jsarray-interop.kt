@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.JsArray
import kotlin.js.js
import kotlin.js.length


fun <T: JsAny> set(array: JsArray<T>, index: Int, value: T): Unit = js("array[index] = value")
fun <T : JsAny> get(array: JsArray<T>, index: Int): T? = js("array[index]")

fun <A: JsAny, B> JsArray<A>.map(converter: (A) -> B): List<B> = sequence<B> {
    (0 until length).forEach { index ->
        yield(converter(get(this@map, index)!!))
    }
}.toList()


expect fun <A: JsAny> jsArray(vararg values: A): JsArray<A>
expect fun <A, B : JsAny> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B>
fun <T : JsAny> createJsObject(): T = js("({ })")

