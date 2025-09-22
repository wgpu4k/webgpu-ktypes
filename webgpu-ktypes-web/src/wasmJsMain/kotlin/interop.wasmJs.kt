@file:Suppress("unused")
@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

import kotlinx.coroutines.await
import kotlin.js.Promise

@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asFloat(): Float = toFloat()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asDouble(): Double = toDouble()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asInt(): Int = toInt()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asLong(): Long = toLong()
@Suppress("NOTHING_TO_INLINE")
actual inline fun JsNumber.asShort(): Short = externRefToKotlinShortAdapter(this)

private fun newMap(): JsAny = js("new Map()")

public fun kotlin.js.JsNumber.toFloat(): Float =
    externRefToKotlinFloatAdapter(this)

internal fun externRefToKotlinFloatAdapter(x: JsAny): Float =
    externrefToFloat(x)

private fun externrefToFloat(ref: JsAny): Float =
    js("Number(ref)")

public fun kotlin.js.JsNumber.toLong(): Long =
    externRefToKotlinLongAdapter(this)

internal fun externRefToKotlinLongAdapter(x: JsAny): Long =
    externrefToLong(x)

private fun externrefToLong(ref: JsAny): Long =
    js("BigInt(ref)")

public fun kotlin.js.JsNumber.toShort(): Short =
    externRefToKotlinShortAdapter(this)

public fun externRefToKotlinShortAdapter(x: JsAny): Short =
    externrefToShort(x)

private fun externrefToShort(ref: JsAny): Short =
    js("Number(ref)")
