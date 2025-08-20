@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

public value class GPUBufferUsage private constructor(
  public val `value`: kotlin.ULong,
) {
  public infix fun or(other: io.ygdrasil.webgpu.GPUBufferUsage): io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(value or other.value)

  public companion object {
    public val None: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(0uL)

    public val MapRead: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(1uL)

    public val MapWrite: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(2uL)

    public val CopySrc: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(4uL)

    public val CopyDst: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(8uL)

    public val Index: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(16uL)

    public val Vertex: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(32uL)

    public val Uniform: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(64uL)

    public val Storage: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(128uL)

    public val Indirect: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(256uL)

    public val QueryResolve: io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsage(512uL)

    public val values: kotlin.collections.Set<io.ygdrasil.webgpu.GPUBufferUsage> = setOf(None, MapRead, MapWrite, CopySrc, CopyDst, Index, Vertex, Uniform, Storage, Indirect, QueryResolve)
  }
}

public value class GPUColorWriteMask private constructor(
  public val `value`: kotlin.ULong,
) {
  public infix fun or(other: io.ygdrasil.webgpu.GPUColorWriteMask): io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMask(value or other.value)

  public companion object {
    public val None: io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMask(0uL)

    public val Red: io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMask(1uL)

    public val Green: io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMask(2uL)

    public val Blue: io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMask(4uL)

    public val Alpha: io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMask(8uL)

    public val All: io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMask(15uL)

    public val values: kotlin.collections.Set<io.ygdrasil.webgpu.GPUColorWriteMask> = setOf(None, Red, Green, Blue, Alpha, All)
  }
}

public value class GPUMapMode private constructor(
  public val `value`: kotlin.ULong,
) {
  public infix fun or(other: io.ygdrasil.webgpu.GPUMapMode): io.ygdrasil.webgpu.GPUMapMode = GPUMapMode(value or other.value)

  public companion object {
    public val None: io.ygdrasil.webgpu.GPUMapMode = GPUMapMode(0uL)

    public val Read: io.ygdrasil.webgpu.GPUMapMode = GPUMapMode(1uL)

    public val Write: io.ygdrasil.webgpu.GPUMapMode = GPUMapMode(2uL)

    public val values: kotlin.collections.Set<io.ygdrasil.webgpu.GPUMapMode> = setOf(None, Read, Write)
  }
}

public value class GPUShaderStage private constructor(
  public val `value`: kotlin.ULong,
) {
  public infix fun or(other: io.ygdrasil.webgpu.GPUShaderStage): io.ygdrasil.webgpu.GPUShaderStage = GPUShaderStage(value or other.value)

  public companion object {
    public val None: io.ygdrasil.webgpu.GPUShaderStage = GPUShaderStage(0uL)

    public val Vertex: io.ygdrasil.webgpu.GPUShaderStage = GPUShaderStage(1uL)

    public val Fragment: io.ygdrasil.webgpu.GPUShaderStage = GPUShaderStage(2uL)

    public val Compute: io.ygdrasil.webgpu.GPUShaderStage = GPUShaderStage(4uL)

    public val values: kotlin.collections.Set<io.ygdrasil.webgpu.GPUShaderStage> = setOf(None, Vertex, Fragment, Compute)
  }
}

public value class GPUTextureUsage private constructor(
  public val `value`: kotlin.ULong,
) {
  public infix fun or(other: io.ygdrasil.webgpu.GPUTextureUsage): io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsage(value or other.value)

  public companion object {
    public val None: io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsage(0uL)

    public val CopySrc: io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsage(1uL)

    public val CopyDst: io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsage(2uL)

    public val TextureBinding: io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsage(4uL)

    public val StorageBinding: io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsage(8uL)

    public val RenderAttachment: io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsage(16uL)

    public val values: kotlin.collections.Set<io.ygdrasil.webgpu.GPUTextureUsage> = setOf(None, CopySrc, CopyDst, TextureBinding, StorageBinding, RenderAttachment)
  }
}
