package io.ygdrasil.webgpu

import kotlin.js.toJsNumber

@Suppress("NOTHING_TO_INLINE")
actual inline fun Float.asJsNumber(): JsNumber = toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Double.asJsNumber(): JsNumber = toJsNumber()
@Suppress(names = ["NOTHING_TO_INLINE"])
actual inline fun Byte.asJsNumber(): JsNumber = toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Short.asJsNumber(): JsNumber = toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Int.asJsNumber(): JsNumber = toJsNumber()
@Suppress("NOTHING_TO_INLINE")
actual inline fun Long.asJsNumber(): JsNumber = toJsNumber()
@Suppress(names = ["NOTHING_TO_INLINE"])
actual inline fun UShort.asJsNumber(): JsNumber = toInt().asJsNumber()
@Suppress(names = ["NOTHING_TO_INLINE"])
actual inline fun UInt.asJsNumber(): JsNumber = toLong().asJsNumber()
@Suppress(names = ["NOTHING_TO_INLINE"])
actual inline fun ULong.asJsNumber(): JsNumber = toLong().asJsNumber()


fun Float.toJsNumber(): kotlin.js.JsNumber = toJsNumber(this)
private fun toJsNumber(x: Float): kotlin.js.JsNumber = js("x")

fun Byte.toJsNumber(): kotlin.js.JsNumber = toJsNumber(this)
private fun toJsNumber(x: Byte): kotlin.js.JsNumber = js("x")

fun Short.toJsNumber(): kotlin.js.JsNumber = toJsNumber(this)
private fun toJsNumber(x: Short): kotlin.js.JsNumber = js("x")

fun Long.toJsNumber(): kotlin.js.JsNumber = toJsNumber(this)
private fun toJsNumber(x: Long): kotlin.js.JsNumber = js("x")
