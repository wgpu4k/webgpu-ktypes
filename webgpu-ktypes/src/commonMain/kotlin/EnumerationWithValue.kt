@file:Suppress("unused")
package io.ygdrasil.webgpu

interface EnumerationWithValue {
    val value: ULong

    infix fun or(other: ULong): ULong = value or other
    infix fun or(other: EnumerationWithValue): ULong = value or other.value
}

internal fun Set<EnumerationWithValue>.toFlagInt(): Int = when (size) {
    0 -> 0uL
    1 -> first().value
    else -> fold(0uL) { acc, enumerationWithValue -> acc or enumerationWithValue.value }
}.toInt()

internal fun Set<EnumerationWithValue>.toFlagULong(): ULong = when (size) {
    0 -> 0uL
    1 -> first().value
    else -> fold(0uL) { acc, enumerationWithValue -> acc or enumerationWithValue.value }
}
