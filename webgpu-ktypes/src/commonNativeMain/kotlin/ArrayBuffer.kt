package io.ygdrasil.webgpu


/**
 * Represents a fixed-length structure that holds raw binary data.
 *
 * ArrayBuffer is commonly used to handle low-level memory manipulation,
 * often for Web APIs or interoperability with native or system-level code.
 * It acts as a generic container for binary data, allowing a variety
 * of typed views to be created over the data it encapsulates.
 *
 * @constructor Creates an ArrayBuffer with a given raw pointer to memory.
 * @property rawPointer A ULong representing the raw memory pointer this buffer wraps.
 */
actual class ArrayBuffer(val rawPointer: ULong)
