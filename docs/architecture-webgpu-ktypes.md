# WebGPU Kotlin Toolkit - webgpu-ktypes Architecture Documentation

**Date:** 2026-05-19  
**Module:** webgpu-ktypes (Core Module)  
**Version:** 1.0  
**Language:** English  
**Document Type:** Architecture Specification  

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [Technology Stack](#technology-stack)
3. [Architecture Pattern](#architecture-pattern)
4. [Data Architecture](#data-architecture)
5. [Component Overview](#component-overview)
6. [Source Tree](#source-tree)
7. [Development Workflow](#development-workflow)
8. [Deployment Architecture](#deployment-architecture)
9. [Testing Strategy](#testing-strategy)

---

## Executive Summary

The **webgpu-ktypes** module is the **root of the WebGPU type hierarchy** and serves as the foundational core for the entire WebGPU Kotlin Toolkit. This module provides platform-independent WebGPU API bindings and type definitions that work across all 19 target platforms.

### Key Facts

| Attribute | Value |
|-----------|-------|
| **Module Role** | Core WebGPU Bindings |
| **Technology** | Kotlin Multiplatform |
| **Platform Coverage** | All 19 targets (JVM, JS, Native, Android, WasmJs, iOS, macOS, Linux, Windows, watchOS) |
| **Lines of Code** | ~20,000+ (estimated across all sources) |
| **Generated Code** | ~67% (from W3C WebGPU specification) |
| **Test Coverage** | 100% for ArrayBuffer (27 test cases) |

### Purpose

This module provides:
- **Type-safe Kotlin wrappers** around the WebGPU API (W3C specification)
- **Platform-agnostic abstractions** for WebGPU resources (buffers, textures, pipelines, etc.)
- **Unified type system** that works across all target platforms
- **Direct memory access** via platform-native APIs (FFM, C interop, JS typed arrays)

### Relationship to Other Modules

```
┌─────────────────────────────────────────────────────────────┐
│                    DEPENDENCY HIERARCHY                        │
├─────────────────────────────────────────────────────────────┤
│                                                                 │
│                      webgpu-ktypes                              │
│                         (CORE)                                  │
│                          ▲   ▲    ▲                              │
│                          │   │    │                              │
│  ┌───────────────────────┼───────────────────────┐          │
│  │                       │                       │          │
│  ▼                       ▼                       ▼          │
│webgpu-ktypes-   webgpu-ktypes-      webgpu-ktypes-         │
│descriptors       web                   specifications      │
│                                                                 │
│                          ▲                                      │
│                          │                                      │
│                          ▼                                      │
│                       wgsl                                     │
│                  (Independent)                                  │
└─────────────────────────────────────────────────────────────┘
```

All other modules depend on `webgpu-ktypes` either directly or indirectly, making it the foundation of the entire toolkit.

---

## Technology Stack

### Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Kotlin** | 2.0+ | Primary programming language |
| **Kotlin Multiplatform** | Latest | Multiplatform compilation |
| **Gradle** | 9.5.0 | Build system (KTS DSL) |
| **Java Toolchain** | 25 | JVM compilation |
| **Kotest** | Latest | Test framework |

### Platform-Specific Technologies

| Platform | Technology | Purpose |
|----------|------------|---------|
| **JVM** | Java 25 FFM API | Foreign Function & Memory API for direct memory access |
| **JS/Node.js** | Kotlin/JS Typed Arrays | JavaScript typed array interop |
| **WasmJs** | Kotlin/Wasm | WebAssembly JavaScript interop |
| **Native** | Kotlin/Native C Interop | C interoperability with automatic memory cleanup |
| **Android** | Java NIO ByteBuffer | Direct buffer operations with ByteBuffer |

### Build Plugins

```kotlin
// webgpu-ktypes/build.gradle.kts
plugins {
    kotlin("multiplatform")
    `kotlinx-serialization`
    `kotlin-parcelize`
}
```

---

## Architecture Pattern

### Primary Pattern: Modular Multiplatform Library

This module implements a **Hierarchical Multiplatform Library** pattern with the following characteristics:

1. **Platform-Agnostic Core**: Common code in `commonMain` with `expect` declarations
2. **Platform-Specific Implementations**: Each platform provides `actual` implementations
3. **Sealed Interface Hierarchy**: Type-safe abstractions with sealed interfaces
4. **Expect/Actual Pattern**: Kotlin Multiplatform's mechanism for platform-specific code

### Design Patterns Used

| Pattern | Usage | Location |
|---------|-------|----------|
| **Sealed Interface** | ArrayBuffer abstraction | `commonMain/kotlin/ArrayBuffer.kt` |
| **Expect/Actual** | Platform-specific implementations | All platform modules |
| **Factory Method** | ArrayBuffer companion object | `ArrayBuffer.kt` |
| **Value Class** | Inline value classes for zero-overhead | `AndroidArrayBuffer.kt` |
| **Type Alias** | GPU primitive type aliases | `typealiases.kt` |

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    webgpu-ktypes MODULE ARCHITECTURE                       │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                        COMMON MAIN (Platform-Agnostic)               │ │
│  │  ┌────────────────┐  ┌────────────────┐  ┌──────────────────────┐  │ │
│  │  │   interfaces.kt │  │  enumerations.kt │  │    ArrayBuffer.kt     │  │ │
│  │  │  (1650+ lines)  │  │ (1000+ lines)   │  │   (Sealed Interface)  │  │ │
│  │  │                │  │                 │  │                      │  │ │
│  │  │ expect fun ... │  │ expect enum ... │  │ expect interface ... │  │ │
│  │  └────────────────┘  └────────────────┘  └──────────────────────┘  │ │
│  │  ┌────────────────┐  ┌────────────────┐  ┌──────────────────────┐  │ │
│  │  │   bitflags.kt   │  │ FlagEnumeration │  │    typealiases.kt     │  │ │
│  │  │                │  │       .kt      │  │                      │  │ │
│  │  └────────────────┘  └────────────────┘  └──────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                              ▲      ▲      ▲                                 │
│                              │      │      │                                 │
│  ┌───────────────────────┬──────┬──────┬───────────────────────────────┐ │
│  │                       │      │      │                               │ │
│  ▼                       ▼      ▼      ▼                               ▼ │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐   │
│  │  jvmMain    │  │ nativeMain  │  │  jsMain     │  │ androidMain │   │
│  │  (JVM)      │  │ (Native)    │  │ (JS/Node)   │  │ (Android)   │   │
│  │             │  │             │  │             │  │             │   │
│  │ actual fun  │  │ actual fun  │  │ actual fun  │  │ actual fun  │   │
│  │ MemorySeg.  │  │ COpaquePtr  │  │ TypedArray  │  │ ByteBuffer  │   │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘   │
│                              ▲      ▲      ▲                                 │
│                              │      │      │                                 │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                     wasmJsMain (WebAssembly/JS)                       │ │
│  │  ┌─────────────────────────────────────────────────────────────┐  │ │
│  │  │              ArrayBuffer.wasmJs.kt (332 lines)               │  │ │
│  │  │          JavaScript typed arrays via Kotlin/Wasm               │  │ │
│  │  └─────────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

### Key Architectural Decisions

1. **Code Generation from W3C Spec**: ~67% of `commonMain` code is generated from the official WebGPU specification, ensuring API completeness and correctness
2. **Direct Memory Access**: Each platform implementation uses the most efficient memory access mechanism available
3. **Zero-Copy Operations**: Where possible, implementations avoid copying data (e.g., JVM uses MemorySegment, Android uses direct ByteBuffer)
4. **Sealed Interface for ArrayBuffer**: Provides type-safe buffer operations with platform-specific implementations

---

## Data Architecture

### Core Data Types

#### 1. ArrayBuffer (Sealed Interface)

The central data abstraction for binary data in WebGPU operations:

```kotlin
// commonMain/kotlin/ArrayBuffer.kt
expect sealed interface ArrayBuffer {
    // Read methods
    fun toByteArray(): ByteArray
    fun toShortArray(): ShortArray
    fun toIntArray(): IntArray
    fun toFloatArray(): FloatArray
    fun toDoubleArray(): DoubleArray
    fun toUByteArray(): UByteArray
    fun toUShortArray(): UShortArray
    fun toUIntArray(): UIntArray
    
    // Indexed read
    fun getByte(index: Int): Byte
    fun getShort(index: Int): Short
    fun getInt(index: Int): Int
    fun getFloat(index: Int): Float
    fun getDouble(index: Int): Double
    fun getUByte(index: Int): UByte
    fun getUShort(index: Int): UShort
    fun getUInt(index: Int): UInt
    
    // Indexed write
    fun setByte(index: Int, value: Byte)
    fun setShort(index: Int, value: Short)
    fun setInt(index: Int, value: Int)
    fun setFloat(index: Int, value: Float)
    fun setDouble(index: Int, value: Double)
    fun setUByte(index: Int, value: UByte)
    fun setUShort(index: Int, value: UShort)
    fun setUInt(index: Int, value: UInt)
    
    // Array write
    fun setBytes(offset: Int, array: ByteArray)
    fun setShorts(offset: Int, array: ShortArray)
    fun setInts(offset: Int, array: IntArray)
    fun setFloats(offset: Int, array: FloatArray)
    fun setDoubles(offset: Int, array: DoubleArray)
    fun setUBytes(offset: Int, array: UByteArray)
    fun setUShorts(offset: Int, array: UShortArray)
    fun setUInts(offset: Int, array: UIntArray)
    
    companion object {
        fun allocate(size: Int): ArrayBuffer
        fun of(array: ByteArray): ArrayBuffer
        fun of(array: ShortArray): ArrayBuffer
        fun of(array: IntArray): ArrayBuffer
        fun of(array: FloatArray): ArrayBuffer
        fun of(array: DoubleArray): ArrayBuffer
        fun of(array: UByteArray): ArrayBuffer
        fun of(array: UShortArray): ArrayBuffer
        fun of(array: UIntArray): ArrayBuffer
    }
}
```

#### 2. WebGPU Interfaces (Generated)

Core WebGPU API interfaces defined in `interfaces.kt` (1650+ lines, generated):

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
- `GPUDevice` - Main GPU device interface
- `GPUBuffer` - Buffer interface
- `GPUTexture` - Texture interface
- `GPUBindGroupLayout`, `GPUBindGroup` - Binding group interfaces
- `GPUPipelineLayout`, `GPUPipelineBase` - Pipeline interfaces
- `GPUShaderModule` - Shader module interface
- `GPUComputePipeline`, `GPURenderPipeline` - Pipeline interfaces
- `GPUCommandBuffer` - Command buffer interface
- `GPUComputePassEncoder` - Compute pass encoder
- `GPURenderPassEncoder` - Render pass encoder

#### 3. Enumerations (Generated)

WebGPU enumeration types in `enumerations.kt` (1000+ lines, generated):

- `GPUAddressMode` - Texture coordinate addressing
- `GPUBlendFactor` - Blend factors
- `GPUBlendOperation` - Blend operations
- `GPUBufferMapState` - Buffer mapping states
- `GPUComparisonFunction` - Comparison functions
- `GPUCullMode` - Culling modes
- `GPUDepthBias` - Depth bias
- And many more...

#### 4. Bit Flags (Generated)

Bit flag value classes in `bitflags.kt` (128 lines, generated):

- `GPUBufferUsage` - Buffer usage flags (MapRead, MapWrite, CopySrc, CopyDst, Index, Vertex, Uniform, Storage, Indirect, QueryResolve)
- `GPUColorWrite` - Color write flags (None, Red, Green, Blue, Alpha, All)
- `GPUMapMode` - Buffer mapping modes (None, Read, Write)
- `GPUShaderStage` - Shader stage flags (None, Vertex, Fragment, Compute)
- `GPUTextureUsage` - Texture usage flags (None, CopySrc, CopyDst, TextureBinding, StorageBinding, RenderAttachment)

#### 5. Type Aliases (Generated)

Type aliases for WebGPU primitive types in `typealiases.kt`:

```kotlin
GPUPipelineConstantValue = Double
GPUBufferDynamicOffset = UInt
GPUStencilValue = UInt
GPUSampleMask = UInt
GPUDepthBias = Int
GPUSize64 = ULong
GPUIntegerCoordinate = UInt
GPUIndex32 = UInt
GPUSize32 = UInt
GPUSignedOffset32 = Int
GPUSize64Out = ULong
GPUIntegerCoordinateOut = UInt
GPUSize32Out = UInt
GPUFlagsConstant = UInt
GPUSupportedFeatures = Set<GPUFeatureName>
```

### Platform-Specific Implementations

#### JVM Implementation

**Files:**
- `jvmMain/kotlin/ArrayBuffer.jvm.kt` (68 lines) - JVM interface
- `jvmMain/kotlin/JvmArrayBuffer.kt` (422 lines) - MemorySegment-based implementation

**Technology:** Java 21+ Foreign Function & Memory API

**Key Features:**
- Uses `Arena`, `MemorySegment`, `ValueLayout` from FFM API
- Direct memory access for zero-copy operations
- `MemorySegment` wrapping for existing native memory
- Automatic memory cleanup via `Arena`

#### Native Implementation

**Files:**
- `nativeMain/kotlin/ArrayBuffer.native.kt` - Native interface
- `nativeMain/kotlin/enumerations.kt` (2401 lines, 100% generated) - Native enum implementations

**Technology:** Kotlin/Native C Interop

**Key Features:**
- Uses `COpaquePointer` for opaque C pointers
- Automatic memory cleanup via Kotlin/Native GC
- Efficient C interop with minimal overhead

#### JavaScript/Node.js Implementation

**Files:**
- `jsMain/kotlin/ArrayBuffer.js.kt` (284 lines) - JS/Node.js implementation

**Technology:** Kotlin/JS Typed Arrays

**Key Features:**
- Uses `js.typedarrays.*` (Int8Array, Int16Array, Int32Array, Float32Array, etc.)
- Seamless interop with JavaScript typed arrays
- Efficient buffer operations

#### WebAssembly/JS Implementation

**Files:**
- `wasmJsMain/kotlin/ArrayBuffer.wasmJs.kt` (332 lines) - WebAssembly implementation

**Technology:** Kotlin/Wasm with JavaScript interop

**Key Features:**
- Similar to JS implementation but optimized for Wasm
- Uses JavaScript typed arrays via Kotlin/Wasm interop
- Efficient memory access patterns

#### Android Implementation

**Files:**
- `androidMain/kotlin/ArrayBuffer.android.kt` (273 lines) - Android interface
- `androidMain/kotlin/AndroidArrayBuffer.kt` (164 lines) - ByteBuffer wrapper

**Technology:** Java NIO ByteBuffer

**Key Features:**
- Uses direct `ByteBuffer` (with native order)
- `ByteBuffer.asShortBuffer()`, `.asIntBuffer()`, etc. for view buffers
- `@JvmInline value class` for zero-overhead abstraction
- Efficient buffer slicing and duplication

### Data Flow Patterns

```
User Code → ArrayBuffer.Companion.allocate(size) → Platform-Specific Implementation
       → Direct Memory Access → WebGPU API Calls
```

All data flows through the `ArrayBuffer` sealed interface, which delegates to platform-specific implementations for actual memory operations.

---

## Component Overview

### Core Components

| Component | Location | Responsibility | Lines | Generated |
|-----------|----------|----------------|-------|-----------|
| **ArrayBuffer** | `commonMain/kotlin/ArrayBuffer.kt` | Binary data buffer abstraction | 311 | No |
| **WebGPU Interfaces** | `commonMain/kotlin/interfaces.kt` | WebGPU API type definitions | 1650+ | Yes |
| **Enumerations** | `commonMain/kotlin/enumerations.kt` | WebGPU enumeration types | 1000+ | Yes |
| **Bit Flags** | `commonMain/kotlin/bitflags.kt` | Bit flag value classes | 128 | Yes |
| **FlagEnumeration** | `commonMain/kotlin/FlagEnumeration.kt` | Generic flag enum interface | 44 | No |
| **Type Aliases** | `commonMain/kotlin/typealiases.kt` | Type aliases | 19 | Yes |

### Platform Components

| Platform | Component | Location | Lines | Purpose |
|----------|-----------|----------|-------|---------|
| **JVM** | JvmArrayBuffer | `jvmMain/kotlin/JvmArrayBuffer.kt` | 422 | MemorySegment-based buffer |
| **Native** | ArrayBuffer.native | `nativeMain/kotlin/ArrayBuffer.native.kt` | N/A | Native interface |
| **Native** | Native Enumerations | `nativeMain/kotlin/enumerations.kt` | 2401 | Native enum implementations |
| **JS** | ArrayBuffer.js | `jsMain/kotlin/ArrayBuffer.js.kt` | 284 | JS typed array buffer |
| **WasmJs** | ArrayBuffer.wasmJs | `wasmJsMain/kotlin/ArrayBuffer.wasmJs.kt` | 332 | Wasm/JS typed array buffer |
| **Android** | AndroidArrayBuffer | `androidMain/kotlin/AndroidArrayBuffer.kt` | 164 | ByteBuffer wrapper |
| **Android** | ArrayBuffer.android | `androidMain/kotlin/ArrayBuffer.android.kt` | 273 | Android interface |

### Component Relationships

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    COMPONENT RELATIONSHIPS                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌─────────────────────┐     ┌─────────────────────┐                      │
│  │      interfaces.kt   │◄────│    typealiases.kt    │                      │
│  │  (WebGPU Interfaces) │     │   (Type Aliases)     │                      │
│  └──────────────┬───────┘     └──────────┬───────────┘                      │
│                 │                             │                                  │
│                 ▼                             ▼                                  │
│  ┌─────────────────────┐     ┌─────────────────────┐                      │
│  │   enumerations.kt    │     │     bitflags.kt      │                      │
│  │  (Enum Types)        │     │   (Bit Flag Types)   │                      │
│  └──────────────┬───────┘     └──────────┬───────────┘                      │
│                 │                             │                                  │
│                 └─────────────┬─────────────┘                                  │
│                               │                                              │
│                               ▼                                              │
│              ┌─────────────────────────────────────┐                        │
│              │         ArrayBuffer.kt              │                        │
│              │    (Sealed Interface)               │                        │
│              └─────────────┬───────────────────────┘                        │
│                        │   │   │   │   │                                      │
│                        ▼   ▼   ▼   ▼   ▼                                      │
│              ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐                     │
│              │ JVM  │ │Native│ │ JS   │ │WasmJs│ │Android│                     │
│              │Impl  │ │Impl  │ │Impl  │ │Impl  │ │Impl   │                     │
│              └──────┘ └──────┘ └──────┘ └──────┘ └──────┘                     │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Source Tree

### Directory Structure

```
webgpu-ktypes/
├── api/                          # Generated API bindings (empty - code gen target)
│
├── build.gradle.kts              # Module build configuration
│
└── src/
    ├── androidMain/
    │   └── kotlin/
    │       ├── AndroidArrayBuffer.kt    # ByteBuffer wrapper (164 lines)
    │       └── ArrayBuffer.android.kt     # Android interface (273 lines)
    │
    ├── commonMain/
    │   └── kotlin/
    │       ├── ArrayBuffer.kt          # ArrayBuffer sealed interface (311 lines)
    │       ├── FlagEnumeration.kt      # Flag enumeration utilities (44 lines)
    │       ├── bitflags.kt             # Bit flag definitions (128 lines, generated)
    │       ├── enumerations.kt         # WebGPU enumerations (1000+ lines, generated)
    │       ├── interfaces.kt            # WebGPU interfaces (1650+ lines, generated)
    │       └── typealiases.kt          # Type aliases (19 lines, generated)
    │
    ├── commonNativeMain/
    │   └── kotlin/
    │       └── enumerations.kt        # Native enum implementations (2401 lines, generated)
    │
    ├── commonTest/
    │   └── kotlin/
    │       └── ArrayBufferTest.kt       # Common tests (417 lines, 27 test cases)
    │
    ├── jsMain/
    │   └── kotlin/
    │       └── ArrayBuffer.js.kt        # JS typed array implementation (284 lines)
    │
    ├── jvmMain/
    │   └── kotlin/
    │       ├── ArrayBuffer.jvm.kt        # JVM interface (68 lines)
    │       └── JvmArrayBuffer.kt        # JVM MemorySegment implementation (422 lines)
    │
    ├── jvmTest/
    │   └── kotlin/
    │       └── JvmArrayBufferTest.kt    # JVM-specific tests (185 lines)
    │
    ├── nativeMain/
    │   └── kotlin/
    │       ├── ArrayBuffer.native.kt    # Native interface
    │       └── enumerations.kt          # Native enums (2401 lines, generated)
    │
    ├── nativeTest/
    │   └── kotlin/
    │       └── OpaquePointerArrayBufferTest.kt  # Native tests (229 lines)
    │
    ├── wasmJsMain/
    │   └── kotlin/
    │       └── ArrayBuffer.wasmJs.kt    # Wasm/JS typed array implementation (332 lines)
    │
    └── webMain/
        └── kotlin/
            ├── ArrayBuffer.web.kt       # Web interface (377 lines)
            ├── WebArrayBuffer.kt        # Web ArrayBuffer wrapper (58 lines)
            └── enumerations.kt           # Web enum implementations (generated)
```

### File Statistics

| Directory | Files | Total Lines | Generated | Purpose |
|-----------|-------|-------------|-----------|---------|
| commonMain | 6 | 5,690+ | 4/6 (67%) | Core platform-agnostic code |
| commonNativeMain | 1 | 2,401 | 1/1 (100%) | Native enum implementations |
| jvmMain | 2 | 490 | 0/2 (0%) | JVM-specific implementations |
| jsMain | 1 | 284 | 0/1 (0%) | JS-specific implementation |
| wasmJsMain | 1 | 332 | 0/1 (0%) | Wasm/JS-specific implementation |
| androidMain | 2 | 437 | 0/2 (0%) | Android-specific implementations |
| nativeMain | 2 | 631+ | 1/2 (50%) | Native-specific implementations |
| commonTest | 1 | 417 | 0/1 (0%) | Common tests |
| jvmTest | 1 | 185 | 0/1 (0%) | JVM-specific tests |
| nativeTest | 1 | 229 | 0/1 (0%) | Native-specific tests |

### Package Structure

All Kotlin code is in the `io.ygdrasil.webgpu` package.

---

## Development Workflow

### Prerequisites

- **Java JDK**: 25+ (for JVM compilation)
- **Gradle**: 9.5.0 (via wrapper)
- **Kotlin**: 2.0+
- **Platform-Specific Tools**:
  - macOS: Xcode Command Line Tools
  - Linux: glslang-tools, spirv-tools
  - Windows: Visual Studio 2022

### Code Generation

~67% of the `commonMain` code is **generated from the W3C WebGPU specification**:

- `interfaces.kt` - Generated from WebGPU IDL
- `enumerations.kt` - Generated from WebGPU enum definitions
- `bitflags.kt` - Generated from WebGPU bit flag definitions
- `typealiases.kt` - Generated from WebGPU type aliases

**Important:** Files marked with `// This file has been generated DO NO EDIT` should not be modified manually.

### Build Configuration

The module uses Gradle Kotlin DSL for build configuration:

```kotlin
// webgpu-ktypes/build.gradle.kts
kotlin {
    jvm {
        jvmTarget = JVM_25
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs {
            // Node.js specific configuration
        }
    }
    // Other targets...
    sourceSets {
        val commonMain by getting {
            dependencies {
                // No external dependencies for commonMain
            }
        }
        // Platform-specific source sets...
    }
}
```

### Development Process

1. **Modify Core Interfaces**: Update `ArrayBuffer.kt` or other manual files
2. **Generate Code**: Run code generation from W3C spec (if interfaces change)
3. **Implement Platform-Specific Code**: Add/update `actual` implementations for new platforms
4. **Write Tests**: Add tests in `commonTest`, `jvmTest`, `nativeTest`, etc.
5. **Verify**: Run `./gradlew :webgpu-ktypes:build`

### Code Quality

- **Documentation**: Comprehensive KDoc with W3C spec references
- **Testing**: 100% test coverage for ArrayBuffer API
- **Code Style**: Follows Kotlin conventions and project standards

---

## Deployment Architecture

### Artifact Publishing

The `webgpu-ktypes` module is published as a **Kotlin Multiplatform library** to:

- **Maven Central**: Primary repository for JVM/Android artifacts
- **GitHub Packages**: Optional (not currently configured)

### Build Outputs

The build produces artifacts for all 19 target platforms:

| Platform Category | Targets | Artifact Type |
|-------------------|---------|---------------|
| **JVM** | JVM | JAR with JVM classes |
| **JS** | JS (IR), JS (Legacy) | JS modules |
| **Wasm** | WasmJs | Wasm modules |
| **Android** | Android | AAR/Android library |
| **Native** | macOS, iOS, watchOS, tvOS, Linux, Windows, Mingw | Native binaries |

### Dependency Management

Other modules depend on `webgpu-ktypes` via Gradle project dependencies:

```kotlin
// In webgpu-ktypes-descriptors/build.gradle.kts
dependencies {
    implementation(project(":webgpu-ktypes"))
}

// In webgpu-ktypes-web/build.gradle.kts
dependencies {
    implementation(project(":webgpu-ktypes"))
}
```

### Versioning

The module follows **Semantic Versioning** (SemVer):
- **MAJOR**: Breaking API changes
- **MINOR**: Backward-compatible new features
- **PATCH**: Backward-compatible bug fixes

Version is set via GitHub Release tag and managed by the CI/CD pipeline.

---

## Testing Strategy

### Test Coverage

| Component | Test Location | Coverage | Test Cases |
|-----------|---------------|----------|------------|
| **ArrayBuffer** | `commonTest/kotlin/ArrayBufferTest.kt` | 100% | 27 test cases |
| **JvmArrayBuffer** | `jvmTest/kotlin/JvmArrayBufferTest.kt` | High | 13 test cases |
| **Native ArrayBuffer** | `nativeTest/kotlin/OpaquePointerArrayBufferTest.kt` | High | 11 test cases |

### Test Types

#### Common Tests

Platform-independent tests in `commonTest`:

```kotlin
// ArrayBufferTest.kt
class ArrayBufferTest {
    @Test
    fun testAllocate() {
        val buffer = ArrayBuffer.allocate(100)
        assertEquals(100, buffer.toByteArray().size)
    }
    
    @Test
    fun testOfByteArray() {
        val bytes = byteArrayOf(1, 2, 3, 4, 5)
        val buffer = ArrayBuffer.of(bytes)
        assertEquals(5, buffer.toByteArray().size)
    }
    
    @Test
    fun testGetSetByte() {
        val buffer = ArrayBuffer.allocate(10)
        buffer.setByte(0, 42)
        assertEquals(42, buffer.getByte(0))
    }
    // ... 24 more test cases
}
```

#### Platform-Specific Tests

Tests specific to each platform:

- **JVM**: Tests using Java 21+ FFM API
- **JS**: Tests using Kotlin/JS typed arrays
- **Native**: Tests using C interop
- **Android**: Tests using Java NIO ByteBuffer
- **WasmJs**: Tests using Kotlin/Wasm interop

### Test Framework

- **Test Framework**: Kotest
- **Assertion Library**: Kotest assertions
- **Test Runner**: JUnit Platform (JVM), Karma (JS), Native test runner (Native)

### Test Execution

Run all tests:
```bash
./gradlew :webgpu-ktypes:allTests
```

Run specific platform tests:
```bash
./gradlew :webgpu-ktypes:jvmTest
./gradlew :webgpu-ktypes:jsTest
./gradlew :webgpu-ktypes:nativeTest
```

### CI/CD Integration

All tests are executed in GitHub Actions workflows:

- **test.yml**: Runs on push/PR to master, release/**, feature/**, fix/** branches
- **Execution Matrix**: Tests on macOS, Ubuntu, Windows
- **Platform Coverage**: All 19 targets tested in CI

---

## Cross-References

### Dependencies

- **Depends on**: None (root module)
- **Used by**: 
  - [webgpu-ktypes-descriptors](./architecture-webgpu-ktypes-descriptors.md) (direct)
  - [webgpu-ktypes-web](./architecture-webgpu-ktypes-web.md) (direct)
  - [webgpu-ktypes-specifications](./architecture-webgpu-ktypes-specifications.md) (indirect)

### Related Documentation

- [Source Tree Analysis - webgpu-ktypes](../source-tree-analysis.md)
- [Source Tree - commonMain](../source-tree-webgpu-ktypes-commonMain.md)
- [Integration Architecture](../integration-architecture.md)
- [Development Guide](../development-guide.md)
- [Deployment Guide](../deployment-guide.md)

---

*Document generated using BMAD Method `document-project` workflow - Step 8: Architecture Documentation*
