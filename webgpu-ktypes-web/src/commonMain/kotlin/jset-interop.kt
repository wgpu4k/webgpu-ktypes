package io.ygdrasil.webgpu

external interface JsSet<T: JsObject> : JsObject {
    val size: Int
    fun has(value: T): Boolean
}

