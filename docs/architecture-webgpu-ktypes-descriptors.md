# WebGPU Kotlin Toolkit - webgpu-ktypes-descriptors Architecture Documentation

**Date:** 2026-05-19  
**Module:** webgpu-ktypes-descriptors  
**Version:** 1.0  
**Language:** English  
**Document Type:** Architecture Specification  

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [Technology Stack](#technology-stack)
3. [Architecture Pattern](#architecture-pattern)
4. [Data Architecture](#data-architecture)
5. [API Design](#api-design)
6. [Component Overview](#component-overview)
7. [Source Tree](#source-tree)
8. [Development Workflow](#development-workflow)
9. [Deployment Architecture](#deployment-architecture)
10. [Testing Strategy](#testing-strategy)

---

## Executive Summary

The **webgpu-ktypes-descriptors** module provides **descriptor type definitions for WebGPU operations**. This module builds upon the core `webgpu-ktypes` module to offer type-safe, idiomatic Kotlin representations of all WebGPU descriptor types used in pipeline configuration, shader module descriptions, render pass descriptors, and bind group layouts.

### Key Facts

| Attribute | Value |
|-----------|-------|
| **Module Role** | Descriptor Type Definitions |
| **Technology** | Kotlin, Kotlin Multiplatform |
| **Platform Coverage** | All 19 targets (JVM, JS, Native, Android, WasmJs, iOS, macOS, Linux, Windows, watchOS) |
| **Dependency** | webgpu-ktypes (core module) |
| **Architecture** | Type extension pattern, pure type definitions |
| **Integration** | Uses core types, no runtime communication |

### Purpose

This module provides:
- **Descriptor data classes** for all WebGPU configuration objects
- **Type-safe builders** for complex descriptor structures
- **Kotlin-idiomatic APIs** for WebGPU descriptor creation
- **Zero runtime overhead** - pure data classes with no runtime communication

### Use Cases

The descriptors defined in this module are used for:
- **GPU Pipeline Configuration** - Configuring render and compute pipelines
- **Shader Module Descriptions** - Describing shader modules for compilation
- **Render Pass Descriptors** - Defining render pass configurations
- **Bind Group Layouts** - Specifying bind group layouts for resource binding
- **Buffer Descriptors** - Configuring buffer creation
- **Texture Descriptors** - Configuring texture creation
- **Sampler Descriptors** - Configuring sampler creation

### Relationship to Other Modules

```
┌─────────────────────────────────────────────────────────────┐
│                    DEPENDENCY HIERARCHY                        │
├─────────────────────────────────────────────────────────────┤
│                                                                 │
│                      webgpu-ktypes                              │
│                         (CORE)                                  │
│                          ▲                                      │
│                          │                                      │
│  ┌───────────────────────┼───────────────────────┐          │
│  │                       │                       │          │
│  ▼                       ▼                       ▼          │
│webgpu-ktypes-   webgpu-ktypes-      webgpu-ktypes-         │
│descriptors       web                   specifications      │
│  (THIS MODULE)                                                         │
│                                                                 │
│                          ▲                                      │
│                          │                                      │
│                          ▼                                      │
│                       wgsl                                     │
│                  (Independent)                                  │
└─────────────────────────────────────────────────────────────┘
```

This module **depends on** `webgpu-ktypes` and is **used by** application code and higher-level abstractions.

---

## Technology Stack

### Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Kotlin** | 2.0+ | Primary programming language |
| **Kotlin Multiplatform** | Latest | Multiplatform compilation |
| **Gradle** | 9.5.0 | Build system (KTS DSL) |
| **Java Toolchain** | 25 | JVM compilation |

### Inherited Technologies

This module inherits all platform support from `webgpu-ktypes`:
- **JVM**: Java 25
- **JS/Node.js**: Kotlin/JS
- **WasmJs**: Kotlin/Wasm
- **Native**: Kotlin/Native with C interop
- **Android**: Java NIO ByteBuffer

### Build Configuration

```kotlin
// webgpu-ktypes-descriptors/build.gradle.kts
plugins {
    kotlin("multiplatform")
}

dependencies {
    implementation(project(":webgpu-ktypes"))
}
```

---

## Architecture Pattern

### Primary Pattern: Type Extension Pattern

This module implements a **Pure Type Definition** pattern with the following characteristics:

1. **No Runtime Logic**: All components are pure data classes
2. **Type Extension**: Extends types from `webgpu-ktypes` with descriptor variants
3. **Immutable Data**: All descriptor types are immutable (data classes with val properties)
4. **Builder Pattern**: Optional builder DSLs for complex descriptors

### Design Patterns Used

| Pattern | Usage | Location |
|---------|-------|----------|
| **Data Class** | Descriptor definitions | `descriptor.kt` |
| **Immutable Object** | All descriptor types | All files |
| **Type Alias** | Simplified type references | `deprecated.types.kt` |
| **Sealed Class/Interface** | Type hierarchies for descriptor variants | `descriptor.kt` |

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│               webgpu-ktypes-descriptors MODULE ARCHITECTURE                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                        COMMON MAIN (All Platforms)                  │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │                      descriptor.kt                               │ │ │
│  │  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │ │ │
│  │  │  │ GPURender    │  │ GPUBindGroup │  │ GPUPipeline  │           │ │ │
│  │  │  │ PipelineDesc │  │ LayoutDesc   │  │ LayoutDesc   │           │ │ │
│  │  │  └─────────────┘  └─────────────┘  └─────────────┘           │ │ │
│  │  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │ │ │
│  │  │  │ GPUShader   │  │ GPUBuffer   │  │ GPUTexture  │           │ │ │
│  │  │  │ ModuleDesc   │  │ Descriptor   │  │ Descriptor  │           │ │ │
│  │  │  └─────────────┘  └─────────────┘  └─────────────┘           │ │ │
│  │  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │ │ │
│  │  │  │ GPURender   │  │ GPUSampler  │  │ ...         │           │ │ │
│  │  │  │ PassDesc    │  │ Descriptor   │  │             │           │ │ │
│  │  │  └─────────────┘  └─────────────┘  └─────────────┘           │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │                   deprecated.types.kt                            │ │ │
│  │  │  (Legacy type aliases for backward compatibility)             │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
│  DEPENDENCIES:                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                    webgpu-ktypes (Core Module)                       │ │
│  │  - Provides GPU* base types used in descriptors                      │ │
│  │  - ArrayBuffer, GPUDevice, GPUBuffer, etc.                          │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

### Key Architectural Decisions

1. **Pure Data Classes**: All descriptors are immutable data classes, ensuring thread-safety and predictability
2. **No Runtime Logic**: The module contains only type definitions, no runtime behavior
3. **Type Safety**: Strong typing ensures compile-time validation of descriptor structures
4. **Backward Compatibility**: Deprecated type aliases maintain compatibility with older code

---

## Data Architecture

### Core Data Types

#### 1. Render Pipeline Descriptor

```kotlin
// From descriptor.kt
data class GPURenderPipelineDescriptor(
    val label: String? = null,
    val layout: GPUPipelineLayout? = null,
    val vertex: GPUVertexState,
    val primitive: GPUPrimitiveState? = null,
    val depthStencil: GPUDepthStencilState? = null,
    val multisample: GPUMultisampleState,
    val fragment: GPUFragmentState? = null
) : GPUObjectDescriptor
```

#### 2. Compute Pipeline Descriptor

```kotlin
data class GPUComputePipelineDescriptor(
    val label: String? = null,
    val layout: GPUPipelineLayout? = null,
    val compute: GPUProgrammableStage,
    val cache: GPUPipelineCache? = null
) : GPUObjectDescriptor
```

#### 3. Bind Group Layout Descriptor

```kotlin
data class GPUBindGroupLayoutDescriptor(
    val label: String? = null,
    val entries: List<GPUBindGroupLayoutEntry>
) : GPUObjectDescriptor
```

#### 4. Shader Module Descriptor

```kotlin
data class GPUShaderModuleDescriptor(
    val label: String? = null,
    val code: String,
    val sourceMap: Any? = null
) : GPUObjectDescriptor
```

#### 5. Buffer Descriptor

```kotlin
data class GPUBufferDescriptor(
    val label: String? = null,
    val size: GPUSize64,
    val usage: GPUBufferUsageFlags,
    val mappedAtCreation: Boolean = false
) : GPUObjectDescriptor
```

#### 6. Texture Descriptor

```kotlin
data class GPUTextureDescriptor(
    val label: String? = null,
    val size: GPUExtent3D,
    val mipLevelCount: GPUIntegerCoordinate = 1u,
    val arrayLayerCount: GPUIntegerCoordinate = 1u,
    val sampleCount: GPUSize32 = 1u,
    val dimension: GPUTextureDimension,
    val format: GPUTextureFormat,
    val usage: GPUTextureUsageFlags,
    val viewFormats: List<GPUTextureFormat>? = null
) : GPUObjectDescriptor
```

#### 7. Sampler Descriptor

```kotlin
data class GPUSamplerDescriptor(
    val label: String? = null,
    val addressModeU: GPUAddressMode = GPUAddressMode.ClampToEdge,
    val addressModeV: GPUAddressMode = GPUAddressMode.ClampToEdge,
    val addressModeW: GPUAddressMode = GPUAddressMode.ClampToEdge,
    val magFilter: GPUFilterMode = GPUFilterMode.Nearest,
    val minFilter: GPUFilterMode = GPUFilterMode.Nearest,
    val mipmapFilter: GPUMipmapFilterMode = GPUMipmapFilterMode.Nearest,
    val lodMinClamp: Double = 0.0,
    val lodMaxClamp: Double = Double.POSITIVE_INFINITY,
    val compare: GPUCompareFunction? = null,
    val maxAnisotropy: GPUSize32 = 1u
) : GPUObjectDescriptor
```

### Supporting Types

#### GPUObjectDescriptor (Base Interface)

```kotlin
sealed interface GPUObjectDescriptor {
    val label: String?
}
```

All descriptor types implement this base interface, providing a consistent `label` property for debugging and identification.

#### GPUProgrammableStage

```kotlin
data class GPUProgrammableStage(
    val module: GPUShaderModule,
    val entryPoint: String,
    val constants: Map<String, GPUPipelineConstantValue>? = null
)
```

#### GPUVertexState

```kotlin
data class GPUVertexState(
    val module: GPUShaderModule,
    val entryPoint: String,
    val constants: Map<String, GPUPipelineConstantValue>? = null,
    val buffers: List<GPUVertexBufferLayout>
)
```

#### GPUFragmentState

```kotlin
data class GPUFragmentState(
    val module: GPUShaderModule,
    val entryPoint: String,
    val constants: Map<String, GPUPipelineConstantValue>? = null,
    val targets: List<GPUColorTargetState>
)
```

### Deprecated Types

The `deprecated.types.kt` file contains legacy type aliases for backward compatibility:

```kotlin
// Example deprecated type aliases
@Deprecated("Use GPUBufferDescriptor instead", ReplaceWith("GPUBufferDescriptor"))
typealias BufferDescriptor = GPUBufferDescriptor

@Deprecated("Use GPUTextureDescriptor instead", ReplaceWith("GPUTextureDescriptor"))
typealias TextureDescriptor = GPUTextureDescriptor

@Deprecated("Use GPUSamplerDescriptor instead", ReplaceWith("GPUSamplerDescriptor"))
typealias SamplerDescriptor = GPUSamplerDescriptor
```

### Type Hierarchy

```
GPUObjectDescriptor (Sealed Interface)
├── GPURenderPipelineDescriptor
├── GPUComputePipelineDescriptor
├── GPUBindGroupLayoutDescriptor
├── GPUShaderModuleDescriptor
├── GPUBufferDescriptor
├── GPUTextureDescriptor
├── GPUSamplerDescriptor
├── GPURenderPassDescriptor
├── GPUCommandEncoderDescriptor
└── ... (other descriptor types)
```

---

## API Design

### Public API Surface

The module exposes a **pure type API** with no runtime behavior:

```kotlin
// Creating a simple buffer descriptor
val bufferDescriptor = GPUBufferDescriptor(
    label = "My Buffer",
    size = 1024uL,
    usage = GPUBufferUsage.COPY_DST or GPUBufferUsage.VERTEX,
    mappedAtCreation = false
)

// Creating a complex render pipeline descriptor
val renderPipelineDescriptor = GPURenderPipelineDescriptor(
    label = "My Render Pipeline",
    vertex = GPUVertexState(
        module = shaderModule,
        entryPoint = "vs_main",
        buffers = listOf(
            GPUVertexBufferLayout(
                arrayStride = 32u,
                stepMode = GPUVertexStepMode.Vertex,
                attributes = listOf(
                    GPUVertexAttribute(
                        format = GPUVertexFormat.Float32x3,
                        offset = 0u,
                        shaderLocation = 0u
                    )
                )
            )
        )
    ),
    fragment = GPUFragmentState(
        module = shaderModule,
        entryPoint = "fs_main",
        targets = listOf(
            GPUColorTargetState(
                format = GPUTextureFormat.RGBA8Unorm,
                blend = GPUBlendState(
                    color = GPUBlendComponent(
                        srcFactor = GPUBlendFactor.SrcAlpha,
                        dstFactor = GPUBlendFactor.OneMinusSrcAlpha,
                        operation = GPUBlendOperation.Add
                    ),
                    alpha = GPUBlendComponent.Add
                ),
                writeMask = GPUColorWrite.ALL
            )
        )
    ),
    primitive = GPUPrimitiveState(
        topology = GPUPrimitiveTopology.TriangleList,
        frontFace = GPUFrontFace.CCW,
        cullMode = GPUCullMode.None
    )
)
```

### API Characteristics

| Characteristic | Description |
|----------------|-------------|
| **Type Safety** | Strong typing prevents invalid descriptor combinations at compile time |
| **Immutability** | All descriptor types are immutable (data classes with val properties) |
| **Null Safety** | All optional properties use nullable types with sensible defaults |
| **Default Values** | All properties have sensible defaults for optional configuration |
| **Kotlin Idiomatic** | Uses data classes, sealed interfaces, and extension functions |

### API Evolution

The API follows these evolution principles:
1. **Backward Compatibility**: New properties are added with default values
2. **Deprecation**: Old APIs are deprecated with `@Deprecated` and `ReplaceWith` annotations
3. **Non-Breaking Changes**: Only additive changes in minor versions
4. **Semantic Versioning**: Breaking changes only in major versions

---

## Component Overview

### Core Components

| Component | Location | Responsibility | Lines | Generated |
|-----------|----------|----------------|-------|-----------|
| **descriptor.kt** | `src/commonMain/kotlin/descriptor.kt` | Main descriptor type definitions | N/A | Partially |
| **deprecated.types.kt** | `src/commonMain/kotlin/deprecated.types.kt` | Legacy type aliases | N/A | No |

### Descriptor Categories

#### 1. Pipeline Descriptors
- `GPURenderPipelineDescriptor` - Render pipeline configuration
- `GPUComputePipelineDescriptor` - Compute pipeline configuration

#### 2. Layout Descriptors
- `GPUBindGroupLayoutDescriptor` - Bind group layout configuration
- `GPUPipelineLayoutDescriptor` - Pipeline layout configuration

#### 3. Shader Descriptors
- `GPUShaderModuleDescriptor` - Shader module configuration
- `GPUProgrammableStage` - Shader stage configuration

#### 4. Resource Descriptors
- `GPUBufferDescriptor` - Buffer configuration
- `GPUTextureDescriptor` - Texture configuration
- `GPUSamplerDescriptor` - Sampler configuration

#### 5. State Descriptors
- `GPUVertexState` - Vertex stage state
- `GPUFragmentState` - Fragment stage state
- `GPUPrimitiveState` - Primitive state
- `GPUDepthStencilState` - Depth/stencil state
- `GPUMultisampleState` - Multisample state

#### 6. Render Pass Descriptors
- `GPURenderPassDescriptor` - Render pass configuration
- `GPUComputePassDescriptor` - Compute pass configuration

### Component Relationships

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    COMPONENT RELATIONSHIPS                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                    GPUObjectDescriptor (Root)                        │ │
│  │                         (Sealed Interface)                            │ │
│  └──────────────┬──────────────┬──────────────┬──────────────────────┘ │ │
│                 │              │              │                            │ │
│  ┌──────────────▼──────┐ ┌────▼──────┐ ┌──────────▼─────────────┐     │ │
│  │ Pipeline Descriptors │ │ Layout   │ │ Resource               │     │ │
│  │                     │ │ Descrip.│ │ Descriptors            │     │ │
│  │ - GPURenderPipeline │ │ - Bind   │ │ - Buffer               │     │ │
│  │ - GPUComputePipeline │ │   Group  │ │ - Texture              │     │ │
│  │                     │ │ - Pipeline│ │ - Sampler              │     │ │
│  └──────────────┬──────────┘ └──────────┘ └──────────────────────┘     │ │
│                 │                                                           │ │
│                 ▼                                                           │ │
│  ┌────────────────────────────────────────────────────────────────────┐ │ │
│  │                    State Descriptors                                  │ │ │
│  │  - GPUVertexState, GPUFragmentState, GPUPrimitiveState, etc.      │ │ │
│  └────────────────────────────────────────────────────────────────────┘ │ │
│                                                                           │ │
│  ┌────────────────────────────────────────────────────────────────────┐ │ │
│  │                    deprecated.types.kt                               │ │ │
│  │  (Legacy type aliases - points to new types)                       │ │ │
│  └────────────────────────────────────────────────────────────────────┘ │ │
│                                                                           │ │
│  USES TYPES FROM:                                                        │ │
│  ┌────────────────────────────────────────────────────────────────────┐ │ │
│  │                    webgpu-ktypes (Core Module)                       │ │ │
│  │  - GPUBufferUsage, GPUTextureFormat, GPUAddressMode, etc.          │ │ │
│  └────────────────────────────────────────────────────────────────────┘ │ │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Source Tree

### Directory Structure

```
webgpu-ktypes-descriptors/
├── build.gradle.kts            # Module build configuration
│
└── src/
    └── commonMain/
        └── kotlin/
            ├── descriptor.kt            # Main descriptor type definitions
            └── deprecated.types.kt       # Legacy type aliases for backward compatibility
```

### File Details

#### descriptor.kt

**Purpose:** Main descriptor type definitions for WebGPU operations

**Contains:**
- All WebGPU descriptor data classes
- Sealed interface `GPUObjectDescriptor`
- Pipeline descriptors (render, compute)
- Layout descriptors (bind group, pipeline)
- Shader descriptors (module, programmable stage)
- Resource descriptors (buffer, texture, sampler)
- State descriptors (vertex, fragment, primitive, depth/stencil, multisample)
- Render pass descriptors

**Technology:** Pure Kotlin data classes

**Generated:** Partially (some descriptors may be generated from W3C spec)

**Example:**
```kotlin
data class GPUBufferDescriptor(
    override val label: String? = null,
    val size: GPUSize64,
    val usage: GPUBufferUsageFlags,
    val mappedAtCreation: Boolean = false
) : GPUObjectDescriptor
```

#### deprecated.types.kt

**Purpose:** Legacy type aliases for backward compatibility

**Contains:**
- Deprecated type aliases pointing to new descriptor types
- `@Deprecated` annotations with `ReplaceWith` suggestions
- Ensures smooth migration path for existing code

**Technology:** Kotlin type aliases

**Generated:** No (manual)

**Example:**
```kotlin
@Deprecated(
    "Use GPUBufferDescriptor instead",
    ReplaceWith("GPUBufferDescriptor")
)
typealias BufferDescriptor = GPUBufferDescriptor
```

### Statistics

| File | Lines | Purpose | Generated |
|------|-------|---------|-----------|
| descriptor.kt | N/A | Main descriptor definitions | Partially |
| deprecated.types.kt | N/A | Legacy type aliases | No |

### Package Structure

All Kotlin code is in the `io.ygdrasil.webgpu` package, sharing the namespace with `webgpu-ktypes`.

---

## Development Workflow

### Prerequisites

Same as `webgpu-ktypes` module:
- **Java JDK**: 25+
- **Gradle**: 9.5.0 (via wrapper)
- **Kotlin**: 2.0+

### Code Generation

Some descriptor types may be **generated from the W3C WebGPU specification**:
- Descriptor structures that mirror WebGPU IDL
- Ensures API completeness and correctness

**Note:** Check file headers for generation markers (`// This file has been generated DO NO EDIT`).

### Build Configuration

The module's build configuration is minimal since it's a pure type library:

```kotlin
// webgpu-ktypes-descriptors/build.gradle.kts
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":webgpu-ktypes"))
            }
        }
    }
}
```

### Development Process

1. **Add New Descriptor**: Extend `GPUObjectDescriptor` with new data class
2. **Add Deprecated Alias**: If replacing an old type, add deprecated alias in `deprecated.types.kt`
3. **Verify Dependencies**: Ensure new descriptor uses only types from `webgpu-ktypes`
4. **Write Tests**: Add tests in `commonTest` (if this module has tests)
5. **Verify Build**: Run `./gradlew :webgpu-ktypes-descriptors:build`

### Code Quality

- **Documentation**: Comprehensive KDoc with W3C spec references
- **Type Safety**: All properties use appropriate types from `webgpu-ktypes`
- **Null Safety**: Optional properties are nullable with defaults
- **Immutability**: All descriptor types are immutable data classes

---

## Deployment Architecture

### Artifact Publishing

The `webgpu-ktypes-descriptors` module is published as part of the WebGPU Kotlin Toolkit:

- **Maven Central**: Primary repository
- **GitHub Packages**: Optional (not currently configured)

### Build Outputs

Since this is a **pure Kotlin Multiplatform library** with no platform-specific code, it produces:

| Platform Category | Artifact Type |
|-------------------|---------------|
| **All Platforms** | JAR/KLib with common code |

The module **inherits all platform targets** from `webgpu-ktypes`:
- JVM, JS, Native, Android, WasmJs, iOS, macOS, Linux, Windows, watchOS

### Dependency Management

Applications and other modules depend on this module via Gradle:

```kotlin
// In application/build.gradle.kts
dependencies {
    implementation("io.ygdrasil:webgpu-ktypes-descriptors:VERSION")
    // Or for local development:
    implementation(project(":webgpu-ktypes-descriptors"))
}
```

**Note:** When using project dependency, `webgpu-ktypes` is automatically included transitively.

### Versioning

The module version **matches** the `webgpu-ktypes` version:
- Both modules are released together
- Both share the same version number
- Both are published in the same release

---

## Testing Strategy

### Test Coverage

This module contains **pure data classes** with no runtime logic, so traditional unit testing is minimal. Testing focuses on:

1. **Compilation Tests**: Ensure all descriptor types compile correctly
2. **Type Safety Tests**: Verify type relationships at compile time
3. **Serialization Tests**: If serialization is supported
4. **Integration Tests**: Verify descriptors work correctly with `webgpu-ktypes`

### Test Types

#### Compilation Tests

```kotlin
// Verify descriptor types can be instantiated
val descriptor = GPUBufferDescriptor(
    size = 1024uL,
    usage = GPUBufferUsage.COPY_DST
)
// Compilation success = test passed
```

#### Type Safety Tests

```kotlin
// Verify type relationships
val descriptor: GPUObjectDescriptor = GPUBufferDescriptor(...)
// Compilation success = test passed
```

#### Integration Tests

These are typically in the **parent module** or **application code**, verifying that descriptors work with the actual WebGPU API:

```kotlin
// In integration tests
val buffer = device.createBuffer(GPUBufferDescriptor(
    size = 1024uL,
    usage = GPUBufferUsage.COPY_DST
))
// Verify buffer is created correctly
```

### Test Framework

- **Test Framework**: Kotest (inherited from parent)
- **Test Runner**: Same as `webgpu-ktypes`

### CI/CD Integration

Tests are executed as part of the **monorepo CI/CD pipeline**:
- Runs on all platform targets (via `webgpu-ktypes` targets)
- Executed in GitHub Actions workflows
- Runs on push/PR to relevant branches

---

## Cross-References

### Dependencies

- **Depends on**: [webgpu-ktypes](./architecture-webgpu-ktypes.md) (core module)
- **Used by**: Application code, higher-level abstractions

### Related Documentation

- [Integration Architecture](../integration-architecture.md)
- [Development Guide](../development-guide.md)
- [Source Tree Analysis - All Modules](../source-tree-analysis.md)
- [WebGPU Specification](https://www.w3.org/TR/webgpu/) - Official W3C specification

---

*Document generated using BMAD Method `document-project` workflow - Step 8: Architecture Documentation*
