package io.ygdrasil.webgpu


/**
 * Represents a buffer of raw binary data, which can be used in various WebGPU operations.
 * Provides a mechanism to work directly with raw memory for performance-critical computations
 * or data manipulation when working with GPU resources.
 *
 * This class is an abstraction for handling binary data in a format compatible with WebGPU,
 * allowing interoperability with GPU resources like buffers and textures.
 */
expect class ArrayBuffer