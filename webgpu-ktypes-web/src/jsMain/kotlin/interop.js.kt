@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu


@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asLong(): Long = unsafeCast<Long>()

