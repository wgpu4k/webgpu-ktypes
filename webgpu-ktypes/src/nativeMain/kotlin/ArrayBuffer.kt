package io.ygdrasil.webgpu

import kotlinx.cinterop.COpaque
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * Represents a platform-specific abstraction over raw binary data stored in an ArrayBuffer.
 *
 * This interface provides a unified way to interact with binary data across platforms,
 * allowing efficient manipulation and processing of raw buffers. It is primarily used
 * in contexts where native interop or web functionalities like WebGPU and WebGL require
 * a representation of binary buffers.
 *
 * Implementations of this interface may vary depending on the target platform, such as
 * browser environments or native environments. They typically map to platform-native
 * concepts like `org.khronos.webgl.ArrayBuffer` in JavaScript or analogous native
 * structures in other environments.
 *
 * Intended for use with platform-specific operations where binary data transfer or
 * manipulation is necessary.
 */
actual sealed interface ArrayBuffer


/**
 * Represents a native array buffer backed by a byte array, providing platform-specific
 * functionality for handling raw binary data efficiently.
 *
 * This value class wraps a `ByteArray` and implements the `ArrayBuffer` interface,
 * serving as a lightweight representation of binary data. It is primarily intended
 * for use cases that require interoperation with native systems or other low-level
 * data processing tasks where buffers are commonly utilized.
 *
 * The use of `@OptIn(ExperimentalForeignApi::class)` indicates that this class makes
 * use of experimental API functionality, which may be subject to change in future versions.
 */
@OptIn(ExperimentalForeignApi::class)
value class NativeArrayBuffer(val pointer: ByteArray): ArrayBuffer

