@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import js.typedarrays.Float32Array
import js.typedarrays.Float64Array
import js.typedarrays.Int32Array
import js.typedarrays.Int16Array
import js.typedarrays.Int8Array
import kotlin.js.unsafeCast


actual fun ByteArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Int8Array<js.buffer.ArrayBuffer>>().buffer)
actual fun ShortArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Int16Array<js.buffer.ArrayBuffer>>().buffer)
actual fun UShortArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Int16Array<js.buffer.ArrayBuffer>>().buffer)
actual fun IntArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Int32Array<js.buffer.ArrayBuffer>>().buffer)
actual fun UIntArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Int32Array<js.buffer.ArrayBuffer>>().buffer)
actual fun LongArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Int32Array<js.buffer.ArrayBuffer>>().buffer)
actual fun ULongArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Int32Array<js.buffer.ArrayBuffer>>().buffer)
actual fun FloatArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Float32Array<js.buffer.ArrayBuffer>>().buffer)
actual fun DoubleArray.asArrayBuffer(): io.ygdrasil.webgpu.ArrayBuffer
        = ArrayBuffer.from(unsafeCast<Float64Array<js.buffer.ArrayBuffer>>().buffer)
