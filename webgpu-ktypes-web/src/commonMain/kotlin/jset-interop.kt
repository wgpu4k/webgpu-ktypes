package io.ygdrasil.webgpu

import kotlin.js.JsAny

external interface JsSet<T: JsAny> : JsAny {
    val size: Int
    fun has(value: T): Boolean
}

