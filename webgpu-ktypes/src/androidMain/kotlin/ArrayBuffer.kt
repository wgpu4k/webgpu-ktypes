package io.ygdrasil.webgpu

import java.nio.ByteBuffer

/**
 * Represents a fixed-length structure that holds raw binary data.
 *
 * ArrayBuffer is commonly used to handle low-level memory manipulation,
 * often for Web APIs or interoperability with native or system-level code.
 * It acts as a generic container for binary data, allowing a variety
 * of typed views to be created over the data it encapsulates.
 *
 * @constructor Creates an ArrayBuffer with a given raw pointer to memory and size.
 * @property rawPointer A ULong representing the raw memory pointer this buffer wraps.
 * It is typically used to point to memory allocated for binary data.
 * @property size A ULong representing the total length of the memory block
 * in bytes that this buffer encapsulates. This helps define the usable range in memory.
 *
 * Example usage:
 * ```Kotlin
 * val buffer = ArrayBuffer(rawPointer = 12345678uL, size = 1024uL)
 * println("Raw Pointer: ${buffer.rawPointer}, Size: ${buffer.size} bytes")
 * ```
 */
actual sealed interface ArrayBuffer {
    actual companion object {
        fun from(buffer: ByteBuffer): ArrayBuffer = AndroidArrayBuffer(buffer)
    }
}

@JvmInline
value class AndroidArrayBuffer internal constructor(val buffer: ByteBuffer): ArrayBuffer

