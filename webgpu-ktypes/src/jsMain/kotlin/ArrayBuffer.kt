package io.ygdrasil.webgpu


/**
 * Typealias for the WebGL implementation of ArrayBuffer in Kotlin.
 *
 * This provides a platform-specific definition of the `ArrayBuffer` class,
 * aligning it with the `org.khronos.webgl.ArrayBuffer` implementation.
 * It is used to represent raw binary data for interoperability between
 * WebGPU Buffer functionality in a Kotlin multiplatform context.
 */
actual typealias ArrayBuffer = org.khronos.webgl.ArrayBuffer
