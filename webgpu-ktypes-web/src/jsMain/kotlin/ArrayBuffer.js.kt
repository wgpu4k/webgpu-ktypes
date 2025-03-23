@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import org.khronos.webgl.Float64Array
import org.khronos.webgl.Float32Array
import org.khronos.webgl.Int32Array
import org.khronos.webgl.Int16Array
import org.khronos.webgl.Int8Array

actual fun ByteArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int8Array>().buffer
actual fun ShortArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int16Array>().buffer
actual fun UShortArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int16Array>().buffer
actual fun IntArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int32Array>().buffer
actual fun UIntArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Int32Array>().buffer
actual fun FloatArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Float32Array>().buffer
actual fun DoubleArray.asArrayBuffer(): ArrayBuffer
        = unsafeCast<Float64Array>().buffer
