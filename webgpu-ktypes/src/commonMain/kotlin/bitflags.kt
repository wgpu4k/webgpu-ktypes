@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

public sealed interface GPUBufferUsage {
  public val `value`: kotlin.ULong

  public infix fun or(other: io.ygdrasil.webgpu.GPUBufferUsage): io.ygdrasil.webgpu.GPUBufferUsage = GPUBufferUsageComposedFlag(value or other.value)

  public object None : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 0uL
  }

  public object MapRead : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 1uL
  }

  public object MapWrite : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 2uL
  }

  public object CopySrc : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 4uL
  }

  public object CopyDst : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 8uL
  }

  public object Index : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 16uL
  }

  public object Vertex : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 32uL
  }

  public object Uniform : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 64uL
  }

  public object Storage : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 128uL
  }

  public object Indirect : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 256uL
  }

  public object QueryResolve : io.ygdrasil.webgpu.GPUBufferUsage {
    override val `value`: kotlin.ULong = 512uL
  }
}

internal value class GPUBufferUsageComposedFlag(
  override val `value`: kotlin.ULong,
) : io.ygdrasil.webgpu.GPUBufferUsage

public sealed interface GPUColorWriteMask {
  public val `value`: kotlin.ULong

  public infix fun or(other: io.ygdrasil.webgpu.GPUColorWriteMask): io.ygdrasil.webgpu.GPUColorWriteMask = GPUColorWriteMaskComposedFlag(value or other.value)

  public object None : io.ygdrasil.webgpu.GPUColorWriteMask {
    override val `value`: kotlin.ULong = 0uL
  }

  public object Red : io.ygdrasil.webgpu.GPUColorWriteMask {
    override val `value`: kotlin.ULong = 1uL
  }

  public object Green : io.ygdrasil.webgpu.GPUColorWriteMask {
    override val `value`: kotlin.ULong = 2uL
  }

  public object Blue : io.ygdrasil.webgpu.GPUColorWriteMask {
    override val `value`: kotlin.ULong = 4uL
  }

  public object Alpha : io.ygdrasil.webgpu.GPUColorWriteMask {
    override val `value`: kotlin.ULong = 8uL
  }

  public object All : io.ygdrasil.webgpu.GPUColorWriteMask {
    override val `value`: kotlin.ULong = 15uL
  }
}

internal value class GPUColorWriteMaskComposedFlag(
  override val `value`: kotlin.ULong,
) : io.ygdrasil.webgpu.GPUColorWriteMask

public sealed interface GPUMapMode {
  public val `value`: kotlin.ULong

  public infix fun or(other: io.ygdrasil.webgpu.GPUMapMode): io.ygdrasil.webgpu.GPUMapMode = GPUMapModeComposedFlag(value or other.value)

  public object None : io.ygdrasil.webgpu.GPUMapMode {
    override val `value`: kotlin.ULong = 0uL
  }

  public object Read : io.ygdrasil.webgpu.GPUMapMode {
    override val `value`: kotlin.ULong = 1uL
  }

  public object Write : io.ygdrasil.webgpu.GPUMapMode {
    override val `value`: kotlin.ULong = 2uL
  }
}

internal value class GPUMapModeComposedFlag(
  override val `value`: kotlin.ULong,
) : io.ygdrasil.webgpu.GPUMapMode

public sealed interface GPUShaderStage {
  public val `value`: kotlin.ULong

  public infix fun or(other: io.ygdrasil.webgpu.GPUShaderStage): io.ygdrasil.webgpu.GPUShaderStage = GPUShaderStageComposedFlag(value or other.value)

  public object None : io.ygdrasil.webgpu.GPUShaderStage {
    override val `value`: kotlin.ULong = 0uL
  }

  public object Vertex : io.ygdrasil.webgpu.GPUShaderStage {
    override val `value`: kotlin.ULong = 1uL
  }

  public object Fragment : io.ygdrasil.webgpu.GPUShaderStage {
    override val `value`: kotlin.ULong = 2uL
  }

  public object Compute : io.ygdrasil.webgpu.GPUShaderStage {
    override val `value`: kotlin.ULong = 4uL
  }
}

internal value class GPUShaderStageComposedFlag(
  override val `value`: kotlin.ULong,
) : io.ygdrasil.webgpu.GPUShaderStage

public sealed interface GPUTextureUsage {
  public val `value`: kotlin.ULong

  public infix fun or(other: io.ygdrasil.webgpu.GPUTextureUsage): io.ygdrasil.webgpu.GPUTextureUsage = GPUTextureUsageComposedFlag(value or other.value)

  public object None : io.ygdrasil.webgpu.GPUTextureUsage {
    override val `value`: kotlin.ULong = 0uL
  }

  public object CopySrc : io.ygdrasil.webgpu.GPUTextureUsage {
    override val `value`: kotlin.ULong = 1uL
  }

  public object CopyDst : io.ygdrasil.webgpu.GPUTextureUsage {
    override val `value`: kotlin.ULong = 2uL
  }

  public object TextureBinding : io.ygdrasil.webgpu.GPUTextureUsage {
    override val `value`: kotlin.ULong = 4uL
  }

  public object StorageBinding : io.ygdrasil.webgpu.GPUTextureUsage {
    override val `value`: kotlin.ULong = 8uL
  }

  public object RenderAttachment : io.ygdrasil.webgpu.GPUTextureUsage {
    override val `value`: kotlin.ULong = 16uL
  }
}

internal value class GPUTextureUsageComposedFlag(
  override val `value`: kotlin.ULong,
) : io.ygdrasil.webgpu.GPUTextureUsage
