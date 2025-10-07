@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import js.typedarrays.Float32Array
import js.typedarrays.Float64Array
import js.typedarrays.Int32Array
import js.typedarrays.Int16Array
import js.typedarrays.Int8Array
import kotlin.js.unsafeCast


actual fun ByteArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int8Array<ArrayBuffer>>().buffer
actual fun ShortArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int16Array<ArrayBuffer>>().buffer
actual fun UShortArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int16Array<ArrayBuffer>>().buffer
actual fun IntArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int32Array<ArrayBuffer>>().buffer
actual fun UIntArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int32Array<ArrayBuffer>>().buffer
actual fun LongArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int32Array<ArrayBuffer>>().buffer
actual fun ULongArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int32Array<ArrayBuffer>>().buffer
actual fun FloatArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Float32Array<ArrayBuffer>>().buffer
actual fun DoubleArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Float64Array<ArrayBuffer>>().buffer
