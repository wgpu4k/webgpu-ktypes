package io.ygdrasil.webgpu


actual fun <A, B : JsObject> Collection<A>.mapJsArray(converter: (A) -> B): JsArray<B> {
    return map { converter(it) }
        .toList()
        .toTypedArray()
        .unsafeCast<JsArray<B>>()
}

actual fun <A: JsObject> jsArray(vararg values: A): JsArray<A> {
    return js("Array.from(values)").unsafeCast<JsArray<A>>()
}

actual fun <T : JsObject> get(array: JsArray<T>, index: Int): T? = js("array[index]")