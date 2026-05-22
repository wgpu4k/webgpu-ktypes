# webgpu-ktypes - commonMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/commonMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 6
**Total Lines:** 5,690

## Overview

This directory contains the **platform-agnostic Kotlin source code** for the webgpu-ktypes library, providing core WebGPU API bindings and abstractions that work across all target platforms (JS, JVM, Native, WasmJs, etc.).

## Directory Structure

```
webgpu-ktypes/src/commonMain/kotlin/
├── ArrayBuffer.kt                    (311 lines)  - Core binary buffer abstraction
├── bitflags.kt                       (128 lines)  - Bitflag value classes
├── enumerations.kt                  (~1000+ lines) - WebGPU enum definitions
├── FlagEnumeration.kt                (44 lines)   - Generic flag enum interface
├── interfaces.kt                    (1323+ lines) - WebGPU interface definitions
└── typealiases.kt                    (19 lines)   - Type aliases
```

## Critical Directories & Files

### `ArrayBuffer.kt`

**Purpose:** Provides the core `ArrayBuffer` sealed interface abstraction for handling raw binary data compatible with WebGPU operations.

**Contains:**
- `expect sealed interface ArrayBuffer` - Main buffer interface
- Read methods: `toByteArray()`, `toShortArray()`, `toIntArray()`, `toFloatArray()`, `toDoubleArray()`, `toUByteArray()`, `toUShortArray()`, `toUIntArray()`
- Indexed read methods: `getByte()`, `getShort()`, `getInt()`, `getFloat()`, `getDouble()`, `getUByte()`, `getUShort()`, `getUInt()`
- Indexed write methods: `setByte()`, `setShort()`, `setInt()`, `setFloat()`, `setDouble()`, `setUByte()`, `setUShort()`, `setUInt()`
- Array write methods: `setBytes()`, `setShorts()`, `setInts()`, `setFloats()`, `setDoubles()`, `setUBytes()`, `setUShorts()`, `setUInts()`
- Companion object with factory methods: `allocate()`, `of()` for various array types

**Entry Points:** Companion object factory methods
**Integration:** Used throughout the WebGPU API for buffer operations

### `bitflags.kt`

**Purpose:** Contains generated bitflag value classes for WebGPU configuration options.

**Contains:**
- `GPUBufferUsage` - Buffer usage flags (MapRead, MapWrite, CopySrc, CopyDst, Index, Vertex, Uniform, Storage, Indirect, QueryResolve)
- `GPUColorWrite` - Color write flags (None, Red, Green, Blue, Alpha, All)
- `GPUMapMode` - Buffer mapping modes (None, Read, Write)
- `GPUShaderStage` - Shader stage flags (None, Vertex, Fragment, Compute)
- `GPUTextureUsage` - Texture usage flags (None, CopySrc, CopyDst, TextureBinding, StorageBinding, RenderAttachment)

**Note:** File is generated (DO NO EDIT)

### `enumerations.kt`

**Purpose:** Contains generated WebGPU enumeration classes.

**Contains:**
- `GPUAddressMode` - Texture coordinate addressing (ClampToEdge, Repeat, MirrorRepeat)
- `GPUBlendFactor` - Blend factors (Zero, One, Src, OneMinusSrc, SrcAlpha, etc.)
- And many more WebGPU enums...

**Note:** File is generated (DO NO EDIT), references W3C WebGPU specification

### `FlagEnumeration.kt`

**Purpose:** Generic interface for flag-based enumerations.

**Contains:**
- `interface FlagEnumeration` with `val value: ULong`
- Extension functions: `Set<FlagEnumeration>.toFlagInt()`, `Set<FlagEnumeration>.toFlagULong()`

### `interfaces.kt`

**Purpose:** Core WebGPU interface definitions (the heart of the API).

**Contains:**
- `GPUBindingResource` - Sealed interface for binding resources
- `GPUSampler` - Texture sampling configuration
- `GPUTextureView` - Texture view interface
- `GPUBufferBinding` - Buffer binding descriptor
- `GPUColor` - RGBA color representation
- `GPUOrigin2D`, `GPUOrigin3D` - Coordinate origins
- `GPUExtent3D` - 3D extent/dimensions
- `GPUObjectBase` - Base interface with `label` property
- `GPUSupportedLimits` - GPU capability limits
- `GPUAdapterInfo` - Adapter information
- `GPUAdapter` - GPU adapter interface
- `GPUDevice` - Main GPU device interface with all creation methods
- `GPUBuffer` - Buffer interface
- `GPUTexture` - Texture interface
- `GPUBindGroupLayout`, `GPUBindGroup` - Binding group interfaces
- `GPUPipelineLayout`, `GPUPipelineBase` - Pipeline interfaces
- `GPUShaderModule` - Shader module interface
- `GPUCompilationMessage`, `GPUCompilationInfo` - Shader compilation
- `GPUComputePipeline`, `GPURenderPipeline` - Pipeline interfaces
- `GPUCommandBuffer` - Command buffer interface
- `GPUCommandsMixin`, `GPUBindingCommandsMixin`, `GPUDebugCommandsMixin` - Command mixins
- `GPUComputePassEncoder` - Compute pass encoder
- `GPURenderPassEncoder`, `GPURenderCommandsMixin` - Render pass encoding

**Note:** File is generated (DO NO EDIT), extensive documentation with W3C spec references

### `typealiases.kt`

**Purpose:** Type aliases for WebGPU primitive types.

**Contains:**
- `GPUPipelineConstantValue = Double`
- `GPUBufferDynamicOffset = UInt`
- `GPUStencilValue = UInt`
- `GPUSampleMask = UInt`
- `GPUDepthBias = Int`
- `GPUSize64 = ULong`
- `GPUIntegerCoordinate = UInt`
- `GPUIndex32 = UInt`
- `GPUSize32 = UInt`
- `GPUSignedOffset32 = Int`
- `GPUSize64Out = ULong`
- `GPUIntegerCoordinateOut = UInt`
- `GPUSize32Out = UInt`
- `GPUFlagsConstant = UInt`
- `GPUSupportedFeatures = Set<GPUFeatureName>`

**Note:** File is generated (DO NO EDIT)

## File Organization Patterns

### Generated Code

- **Pattern:** Files marked with `@file:Suppress("unused")` and `// This file has been generated DO NO EDIT`
- **Purpose:** Automatic code generation from WebGPU specification
- **Examples:** bitflags.kt, enumerations.kt, interfaces.kt, typealiases.kt
- **Total:** 4 out of 6 files (67%)

### Manual Code

- **Pattern:** Files without generation marker
- **Purpose:** Core abstractions and utilities
- **Examples:** ArrayBuffer.kt, FlagEnumeration.kt
- **Total:** 2 out of 6 files (33%)

## Code Characteristics

- **Package:** `io.ygdrasil.webgpu`
- **Language:** Kotlin (Multiplatform)
- **Paradigm:** Object-oriented with functional elements
- **Documentation:** Comprehensive KDoc with W3C spec references
- **Generation:** ~67% of code is auto-generated from WebGPU spec
- **Platform:** Platform-agnostic (expect declarations for Multiplatform)

## Notes for Development

1. **Generated Files:** Do not edit files marked with "DO NO EDIT" - they are regenerated from the WebGPU specification
2. **Multiplatform:** Uses `expect` declarations for platform-specific implementations
3. **API Coverage:** Appears to cover the full WebGPU API surface
4. **Quality:** Well-documented with references to official W3C specification

---

_Generated using BMAD Method `document-project` workflow - Batch 1: webgpu-ktypes/src/commonMain/kotlin/_
