@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

expect fun ByteArray.asArrayBuffer(): ArrayBuffer
expect fun ShortArray.asArrayBuffer(): ArrayBuffer
expect fun UShortArray.asArrayBuffer(): ArrayBuffer
expect fun IntArray.asArrayBuffer(): ArrayBuffer
expect fun UIntArray.asArrayBuffer(): ArrayBuffer
expect fun LongArray.asArrayBuffer(): ArrayBuffer
expect fun ULongArray.asArrayBuffer(): ArrayBuffer
expect fun FloatArray.asArrayBuffer(): ArrayBuffer
expect fun DoubleArray.asArrayBuffer(): ArrayBuffer