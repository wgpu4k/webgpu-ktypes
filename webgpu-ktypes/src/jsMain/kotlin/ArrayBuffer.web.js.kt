package io.ygdrasil.webgpu

import js.typedarrays.Int8Array

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun ByteArray.toArrayBuffer(): ArrayBuffer
    = ArrayBuffer.from(unsafeCast<Int8Array<js.buffer.ArrayBuffer>>().buffer)