@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu


@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asFloat(): Float = unsafeCast<Float>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asDouble(): Double = unsafeCast<Double>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asLong(): Long = unsafeCast<Long>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asInt(): Int = unsafeCast<Int>()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asShort(): Short = unsafeCast<Short>()

