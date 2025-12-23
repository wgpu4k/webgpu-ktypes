@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import js.typedarrays.*

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ByteArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Int8Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ShortArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Int16Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun IntArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Int32Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun LongArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<BigInt64Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun FloatArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Float32Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun DoubleArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Float64Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun UByteArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Uint8Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun UShortArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Uint16Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun UIntArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Uint32Array<js.buffer.ArrayBuffer>>().buffer)

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ULongArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<BigUint64Array<js.buffer.ArrayBuffer>>().buffer)