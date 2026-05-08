@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

actual fun<A: JsAny> JsArray<A>.bridge_get(index: Int): A = get(index)!!