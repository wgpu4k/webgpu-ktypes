@file:Suppress("unused")
@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asLong(): Long = toLong()

public fun kotlin.js.JsNumber.toLong(): Long =
    externRefToKotlinLongAdapter(this)

internal fun externRefToKotlinLongAdapter(x: JsAny): Long =
    externrefToLong(x)

private fun externrefToLong(ref: JsAny): Long =
    js("BigInt(ref)")


