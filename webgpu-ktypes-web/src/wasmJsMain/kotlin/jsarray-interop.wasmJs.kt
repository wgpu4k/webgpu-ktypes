package io.ygdrasil.webgpu

actual fun <A, B : JsObject> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B> {
    val output = JsArray<B>()
    forEachIndexed { index, value ->
        output[index] = converter(value)
    }
    return output.unsafeCast()
}

actual fun <A: JsObject> jsArray(vararg values: A): JsArray<A> = js("Array.from(values)")

actual fun <T: JsObject> set(array: JsArray<T>, index: Int, value: T): Unit = js("array[index] = value")
actual fun <T : JsObject> get(array: JsArray<T>, index: Int): T? = js("array[index]")