package io.ygdrasil.webgpu

interface EnumerationWithValue {
    val value: ULong

    infix fun or(other: ULong): ULong = value or other
    infix fun or(other: EnumerationWithValue): ULong = value or other.value
}