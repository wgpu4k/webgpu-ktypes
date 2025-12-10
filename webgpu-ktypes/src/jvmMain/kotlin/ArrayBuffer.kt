package io.ygdrasil.webgpu

import java.nio.ByteBuffer

/**
 * Represents a platform-specific abstraction for handling raw binary data buffers.
 *
 * `ArrayBuffer` is a sealed interface that provides a common type for working with
 * binary data across multiple platforms. It is typically associated with use cases
 * such as data transfer, WebGPU operations, or interfacing with native libraries.
 *
 * This interface is intended to be implemented by platform-specific classes
 * or value types that wrap the underlying buffer implementation, such as `ByteBuffer`
 * on JVM or `org.khronos.webgl.ArrayBuffer` on JavaScript or Wasm.
 */
actual sealed interface ArrayBuffer


/**
 * A JVM-specific implementation of the `ArrayBuffer` interface.
 *
 * `JvmArrayBuffer` provides a lightweight wrapper around the `ByteBuffer` class, allowing
 * JVM platforms to manage, access, and manipulate raw binary data in a way that conforms
 * to the `ArrayBuffer` abstraction.
 *
 * This class leverages the `@JvmInline` annotation, making it a value class. This ensures
 * minimal runtime overhead and allows the `ByteBuffer` instance to be used with improved
 * performance due to inlining and reduced object allocations.
 *
 * @param buffer The underlying `ByteBuffer` instance that serves as the basis for this array buffer.
 */
@JvmInline
value class JvmArrayBuffer(val buffer: ByteBuffer): ArrayBuffer
