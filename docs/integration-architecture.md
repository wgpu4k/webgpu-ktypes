# WebGPU Kotlin Toolkit - Integration Architecture

**Date:** 2026-05-19  
**Repository Type:** Monorepo  
**Parts:** 5 (webgpu-ktypes, webgpu-ktypes-descriptors, webgpu-ktypes-specifications, webgpu-ktypes-web, wgsl)  

---

## Executive Summary

This document describes how the 5 parts of the WebGPU Kotlin Toolkit monorepo communicate and integrate with each other. The architecture follows a **hierarchical dependency pattern** with clear separation of concerns.

---

## Dependency Graph

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         MONOREPO ROOT                                   │
│                      webgpu-ktypes-root                                │
├─────────────────────────┬─────────────────────────┬──────────────────┤
│                         │                             │                  │
│  ┌───────────────────────▼─────────────────┐           │                  │
│  │                webgpu-ktypes                       │           │                  │
│  │            (Core WebGPU Bindings)                   │           │                  │
│  └───────────────────────┬─────────────────────────┘           │                  │
│                          │                                       │                  │
│                          ▼                                       ▼                  │
│  ┌───────────────────────────────┐         ┌───────────────────────┐  │
│  │     webgpu-ktypes-descriptors  │         │ webgpu-ktypes-web     │  │
│  │   (Descriptor Type Definitions) │         │ (Web/JS Interop)      │  │
│  └───────────────────────────────┘         └───────────────────────┘  │
│                                                          ▲                  │
│                                                          │                  │
│                          ┌──────────────────────────────┼───────────┐     │
│                          ▼                                  ▼           ▼     │
│                  ┌─────────────────────────────────────────────┐        │
│                  │       webgpu-ktypes-specifications           │        │
│                  │    (Specification Types & Documentation)   │        │
│                  └─────────────────────────────────────────────┘        │
│                                                                          │
│                          ┌─────────────────────────────────────────────┐
│                          │                    wgsl                         │
│                          │   (WebGPU Shading Language Processor)         │
│                          │                                                 │
│                          │  ┌──────────┐  ┌──────────┐  ┌────────────┐ │
│                          │  │   core   │  │  parser  │  │ generator  │ │
│                          │  └──────────┘  └──────────┘  └────────────┘ │
│                          │  ┌──────────┐  ┌──────────┐                   │
│                          │  │   cli    │  │   tests  │                   │
│                          │  └──────────┘  └──────────┘                   │
│                          └─────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Integration Points

### 1. webgpu-ktypes (Core Module)

**Role:** Root of the WebGPU type hierarchy

**Exports:**
- WebGPU interface definitions (`interfaces.kt`)
- WebGPU enumeration types (`enumerations.kt`)
- ArrayBuffer abstraction (`ArrayBuffer.kt`)
- Bit flag utilities (`bitflags.kt`)
- Flag enumeration support (`FlagEnumeration.kt`)
- Type aliases (`typealiases.kt`)

**Used By:**
- `webgpu-ktypes-descriptors` (direct dependency)
- `webgpu-ktypes-web` (direct dependency)
- `webgpu-ktypes-specifications` (indirect, via parent)

**Build Configuration:**
```kotlin
// webgpu-ktypes-descriptors/build.gradle.kts
dependencies {
    implementation(project(":webgpu-ktypes"))
}

// webgpu-ktypes-web/build.gradle.kts
dependencies {
    implementation(project(":webgpu-ktypes"))
}
```

**Integration Pattern:**
- **Expect/Actual Declarations:** Core module defines `expect` declarations
- **Platform Implementations:** Each platform module provides `actual` implementations
- **Shared Types:** All modules share common type definitions from core

**Communication Mechanism:**
- **Compile-time:** Through Gradle project dependencies
- **Runtime:** Direct method calls (same JVM/classloader)

---

### 2. webgpu-ktypes-descriptors

**Role:** Descriptor type definitions for WebGPU operations

**Exports:**
- Descriptor type definitions (`descriptor.kt`)
- Deprecated type aliases (`deprecated.types.kt`)

**Depends On:**
- `webgpu-ktypes` (core module)

**Integration Pattern:**
- **Type Extension:** Extends core types with descriptor-specific functionality
- **No Runtime Integration:** Pure type definitions, no runtime communication

**Use Cases:**
- GPU pipeline configuration
- Shader module descriptions
- Render pass descriptors
- Bind group layouts

**Example Integration:**
```kotlin
// In webgpu-ktypes-descriptors
import io.ygdrasil.webgpu.*  // From core module

// Descriptor uses core types
data class RenderPipelineDescriptor(
    val layout: PipelineLayout,
    val vertex: VertexState,
    val fragment: FragmentState,
    // ... uses types from webgpu-ktypes
)
```

---

### 3. webgpu-ktypes-web

**Role:** Web/JS-specific type extensions and interop utilities

**Exports:**
- Interop utilities (`interop.kt`)
- JS array interoperability (`jsarray-interop.kt`)
- JS number conversions (`jsnumber-interops.kt`)
- Web-specific types (`types.kt`)

**Depends On:**
- `webgpu-ktypes` (core module)

**Platform Targets:** JS, WasmJs only

**Integration Pattern:**
- **Platform Specialization:** Provides JS/Wasm-specific implementations
- **Type Bridging:** Converts between Kotlin types and JavaScript types
- **Extension Functions:** Adds JS-specific extensions to core types

**Communication Mechanism:**
- **Compile-time:** Platform-specific source sets
- **Runtime:** Uses Kotlin/JS and Kotlin/Wasm interop

**Example Integration:**
```kotlin
// In webgpu-ktypes-web/commonMain
import io.ygdrasil.webgpu.*  // From core module

// Extension function for JS interop
fun ArrayBuffer.toJsArray(): js.array.ReadonlyArray<JsNumber> {
    // Convert core ArrayBuffer to JS array
}
```

---

### 4. webgpu-ktypes-specifications

**Role:** Specification-related types and documentation

**Exports:**
- Specification type definitions
- WebGPU specification documentation (`webgpu.md` in resources)

**Depends On:**
- `webgpu-ktypes` (core module)

**Integration Pattern:**
- **Documentation:** Contains specification references
- **Type Safety:** May define types for spec compliance checking
- **Minimal Runtime:** Primarily for documentation and validation

**Special Feature:**
- Contains `webgpu.md` resource file with specification documentation
- Used for reference and validation purposes

---

### 5. wgsl (WebGPU Shading Language)

**Role:** Independent WGSL parser and processor

**Exports:**
- WGSL parsing capabilities
- Shader code generation
- CLI tooling for WGSL processing

**Depends On:** None (standalone module)

**Submodules:**
1. `wgsl/core` - Core WGSL processing logic
2. `wgsl/parser` - WGSL language parser
3. `wgsl/generator` - Code generation utilities
4. `wgsl/cli` - Command-line interface
5. `wgsl/tests` - WGSL-specific tests

**Integration Pattern:**
- **Loose Coupling:** Operates independently from webgpu-ktypes
- **Potential Future Integration:** May integrate with webgpu-ktypes for shader handling
- **Standalone Usage:** Can be used separately from WebGPU bindings

**Build Configuration:**
```kotlin
// settings.gradle.kts
listOf("core", "parser", "generator", "cli", "tests").forEach { project ->
    include(":wgsl:$project")
    project(":wgsl:$project").projectDir = file("wgsl/$project")
}
```

**Inter-Submodule Dependencies:**
```
wgsl/core (no dependencies)
    ▲
    │
wgsl/parser ── wgsl/generator (both depend on core)
    ▲        ▲
    │        │
wgsl/cli ─────┘ (depends on parser and generator)
    ▲
wgsl/tests (depends on core, parser, generator)
```

---

## Cross-Module Communication Patterns

### Pattern 1: Direct Method Calls (Same Classloader)

**Modules:** webgpu-ktypes ←→ webgpu-ktypes-descriptors  
**Mechanism:** Direct Java/Kotlin method invocation

```kotlin
// In webgpu-ktypes-descriptors
import io.ygdrasil.webgpu.ArrayBuffer  // From webgpu-ktypes

class DescriptorProcessor {
    fun process(buffer: ArrayBuffer) {
        // Direct method call to core module type
        buffer.size
        buffer.toByteArray()
    }
}
```

**Characteristics:**
- Zero overhead
- Same classloader
- Compile-time type checking
- No serialization needed

---

### Pattern 2: Platform-Specific Dispatch

**Modules:** webgpu-ktypes (common) ←→ webgpu-ktypes (jvmMain/jsMain/nativeMain/etc.)  
**Mechanism:** Kotlin Multiplatform expect/actual

```kotlin
// In webgpu-ktypes/commonMain
expect sealed interface ArrayBuffer {
    val size: ULong
    fun toByteArray(): ByteArray
    // ...
}

// In webgpu-ktypes/jvmMain
actual sealed interface ArrayBuffer {
    actual val size: ULong
    actual fun toByteArray(): ByteArray
    // JVM-specific implementation
}

// In webgpu-ktypes/jsMain
actual sealed interface ArrayBuffer {
    actual val size: ULong
    actual fun toByteArray(): ByteArray
    // JS-specific implementation
}
```

**Characteristics:**
- Compile-time platform selection
- Type-safe across platforms
- Platform-specific optimization

---

### Pattern 3: Type Sharing

**Modules:** All modules  
**Mechanism:** Shared Gradle project dependencies

```kotlin
// All modules share the same group and version
group = "io.ygdrasil"
version = "0.0.9-SNAPSHOT"

// webgpu-ktypes-descriptors can use types from webgpu-ktypes
dependencies {
    implementation(project(":webgpu-ktypes"))
}
```

**Characteristics:**
- Type safety across module boundaries
- Version alignment guaranteed
- Binary compatibility maintained

---

## Data Flow Analysis

### ArrayBuffer Data Flow

```
User Code
    │
    ▼
┌─────────────────────────────────────────────┐
│  webgpu-ktypes/commonMain:ArrayBuffer interface │
└─────────────────────────────────────────────┘
    │
    ├── ► webgpu-ktypes/jvmMain:JvmArrayBuffer (Java FFM API)
    │       │
    │       ▼
    │    MemorySegment (Java 21+)
    │
    ├── ► webgpu-ktypes/nativeMain:NativeArrayBuffer (C Interop)
    │       │
    │       ▼
    │    COpaquePointer (Kotlin/Native)
    │
    ├── ► webgpu-ktypes/jsMain:JsArrayBuffer (Kotlin/JS)
    │       │
    │       ▼
    │    js.typedarrays.* (JavaScript)
    │
    ├── ► webgpu-ktypes/wasmJsMain:WasmArrayBuffer (Kotlin/Wasm)
    │       │
    │       ▼
    │    js.typedarrays.* (WebAssembly)
    │
    └── ► webgpu-ktypes/androidMain:AndroidArrayBuffer (Java NIO)
            │
            ▼
         ByteBuffer (Direct)
```

### Descriptor Data Flow

```
User Code
    │
    ▼
┌─────────────────────────────────────────────┐
│   webgpu-ktypes-descriptors:Descriptor types   │
└─────────────────────────────────────────────┘
    │
    ▼
┌─────────────────────────────────────────────┐
│       webgpu-ktypes:Core WebGPU types         │
└─────────────────────────────────────────────┘
    │
    ▼
Platform-Specific Implementation (JVM/JS/Native/etc.)
```

### WGSL Processing Flow

```
WGSL Source Code
    │
    ▼
┌─────────────────────┐
│  wgsl/parser         │  ┌─────────────────────┐
│  (Lexing, Parsing)   │  │  wgsl/core          │
└─────────────────────┘  │  (AST, Semantic      │
         │               │   Analysis)         │
         ▼               └─────────────────────┘
┌─────────────────────┐         ▲
│  wgsl/generator      │         │
│  (Code Generation)   │─────────┘
└─────────────────────┘
         │
         ▼
┌─────────────────────┐
│  wgsl/cli           │
│  (Command-line       │
│   Interface)        │
└─────────────────────┘
         │
         ▼
  Generated Output (GLSL, SPIR-V, etc.)
```

---

## Shared Dependencies

### Common Dependencies Across All Modules

| Dependency | Version | Purpose | Used By |
|------------|---------|---------|---------|
| Kotlin Multiplatform | 2.0+ | Core language | All modules |
| Kotlin Serialization | N/A | Data serialization | Some modules |
| Android Library | N/A | Android support | Modules with Android targets |
| Kotest | N/A | Testing framework | All modules with tests |
| KSP | N/A | Kotlin Symbol Processing | Code generation |

### Gradle Plugins

```kotlin
// Root build.gradle.kts
plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotest) apply false
    alias(libs.plugins.ksp) apply false
    generator  // Custom code generation plugin
}
```

---

## Build Integration

### Unified Build

All modules are built together:

```bash
./gradlew build
```

**Build Order:**
1. `buildSrc` - Build logic and conventions
2. `:webgpu-ktypes` - Core module (no dependencies)
3. `:wgsl:core` - WGSL core (no dependencies)
4. `:wgsl:parser` - WGSL parser (depends on core)
5. `:wgsl:generator` - WGSL generator (depends on core)
6. `:wgsl:cli` - WGSL CLI (depends on parser, generator)
7. `:wgsl:tests` - WGSL tests (depends on core, parser, generator)
8. `:webgpu-ktypes-descriptors` - Descriptors (depends on webgpu-ktypes)
9. `:webgpu-ktypes-web` - Web types (depends on webgpu-ktypes)
10. `:webgpu-ktypes-specifications` - Specifications (depends on webgpu-ktypes)

### Incremental Builds

Gradle supports incremental builds:

```bash
# Build only changed modules
./gradlew build -x check

# Build specific module
./gradlew :webgpu-ktypes:build

# Build specific module with dependencies
./gradlew :webgpu-ktypes-descriptors:build
```

### Cross-Module Testing

Tests can verify integration:

```kotlin
// In webgpu-ktypes-descriptors test
import io.ygdrasil.webgpu.*  // From webgpu-ktypes
import io.ygdrasil.webgpu.descriptors.*  // From this module

class IntegrationTest : FunSpec({
    test("descriptors work with core types") {
        val buffer = ArrayBuffer.allocate(1024u)
        val descriptor = RenderPipelineDescriptor(
            // Uses types from both modules
        )
        // Test integration
    }
})
```

---

## Version Alignment

### Synchronized Versioning

All modules share the same version:

```kotlin
// In root build.gradle.kts
allprojects {
    group = "io.ygdrasil"
    version = System.getenv("VERSION")?.takeIf { it.isNotBlank() } ?: "0.0.9-SNAPSHOT"
}
```

**Benefits:**
- Consistent versioning across all artifacts
- Easy dependency management for users
- Simplified release process

### Version Propagation

```
┌─────────────────────────────────────────────┐
│              Release v0.0.10                  │
├─────────────────────────────────────────────┤
│  io.ygdrasil:webgpu-ktypes:0.0.10             │
│  io.ygdrasil:webgpu-ktypes-descriptors:0.0.10 │
│  io.ygdrasil:webgpu-ktypes-web:0.0.10         │
│  io.ygdrasil:webgpu-ktypes-specifications:0.0.10│
│  io.ygdrasil:wgsl-core:0.0.10                 │
│  io.ygdrasil:wgsl-parser:0.0.10               │
│  io.ygdrasil:wgsl-generator:0.0.10            │
│  io.ygdrasil:wgsl-cli:0.0.10                  │
└─────────────────────────────────────────────┘
```

---

## Potential Integration Improvements

### Suggested Enhancements

1. **Integrate wgsl with webgpu-ktypes**
   - Add optional dependency from webgpu-ktypes to wgsl modules
   - Provide shader compilation capabilities within WebGPU bindings
   - Enable end-to-end WebGPU pipeline with shader support

2. **Shared Test Utilities**
   - Extract common test utilities to a shared module
   - Reduce duplication across test suites
   - Improve test consistency

3. **Cross-Module Code Generation**
   - Use wgsl/generator to generate WebGPU bindings
   - Automate type generation from W3C spec
   - Reduce manual code maintenance

4. **Unified Error Handling**
   - Standardize error types across modules
   - Provide consistent error reporting
   - Improve debugging experience

5. **Performance Metrics**
   - Add cross-module performance testing
   - Benchmark integration overhead
   - Identify optimization opportunities

---

## Integration Testing Strategy

### Current State

- Each module has its own test suite
- Platform-specific tests verify platform implementations
- Limited cross-module integration tests

### Recommended Approach

1. **Add Integration Test Module**
   ```kotlin
   // build.gradle.kts
   include(":integration-tests")
   
   // integration-tests/build.gradle.kts
   dependencies {
       implementation(project(":webgpu-ktypes"))
       implementation(project(":webgpu-ktypes-descriptors"))
       implementation(project(":webgpu-ktypes-web"))
       implementation(project(":wgsl:parser"))
       testImplementation(kotest)
   }
   ```

2. **Test Cross-Module Scenarios**
   - Descriptor creation and usage
   - Web-specific type conversions
   - WGSL parsing with WebGPU types

3. **Verify Version Compatibility**
   - Test that all modules work together
   - Verify no breaking changes between modules

---

## Summary Table

| Aspect | webgpu-ktypes | webgpu-ktypes-descriptors | webgpu-ktypes-web | webgpu-ktypes-specifications | wgsl |
|--------|---------------|---------------------------|------------------|-------------------------------|------|
| **Role** | Core bindings | Descriptor types | Web interop | Spec types | Shader processing |
| **Dependencies** | None | webgpu-ktypes | webgpu-ktypes | webgpu-ktypes | None |
| **Platforms** | All 19 | All 19 | JS, WasmJs | All 19 | All 19 |
| **Integration Type** | Hub | Spoke | Spoke | Spoke | Independent |
| **Communication** | Expect/Actual | Direct calls | Direct calls | Direct calls | Internal |
| **Test Coverage** | High | Medium | Medium | Low | High |

---

## Architecture Diagram (ASCII)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                                                                          │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐    │
│  │ webgpu-ktypes   │    │wgsl             │    │   (External)    │    │
│  │                 │    │                 │    │                 │    │
│  │  ┌───────────┐  │    │  ┌───────────┐  │    │  ┌───────────┐  │    │
│  │  │commonMain │  │    │  │   core    │  │    │  │ WebGPU   │  │    │
│  │  └──────┬──────┘  │    │  └──────┬──────┘  │    │  │ Spec     │  │    │
│  │         │         │    │         │         │    │  └───────────┘  │    │
│  │   ┌─────┴─────┐   │    │   ┌─────┴─────┐   │    │                 │    │
│  │   ▼           ▼   │    │   ▼           ▼   │    │                 │    │
│  │┌─────┐  ┌────────┐│    │┌─────┐  ┌────────┐│    │                 │    │
│  ││jvm  │  │native   ││    ││parser│  │generator││    │                 │    │
│  │└─────┘  └────────┘│    │└─────┘  └────────┘│    │                 │    │
│  │┌─────┐  ┌────────┐│    │┌─────┐              │    │                 │    │
│  ││js   │  │android  ││    ││ cli  │              │    │                 │    │
│  │└─────┘  └────────┘│    │└─────┘              │    │                 │    │
│  │┌─────────────────────┐│    │                    │    │                 │    │
│  ││  webMain          ││    │┌─────────────────┐ │    │                 │    │
│  ││  wasmJsMain       ││    ││   tests         │ │    │                 │    │
│  │└─────────────────────┘│    │└─────────────────┘ │    │                 │    │
│  └────────┬────────────┘    └─────────────────────┘    │                 │    │
│           │                                                │                 │    │
│     ┌─────┴─────┐                                      ┌─────┴─────┐           │    │
│     ▼           ▼                                      ▼           ▼           │    │
│  ┌──────────┐   ┌──────────────────┐              ┌──────────┐   ┌────────┐    │    │
│  │descriptors│   │specifications     │              │ (Future) │   │ (User) │    │    │
│  └──────────┘   └──────────────────┘              │wgsl +    │   │  Code   │    │    │
│                                                  │webgpu-   │   │         │    │    │
│                                                  │ktypes    │   └────────┘    │    │
│                                                  │integration│                │    │
│                                                  └────────────┘                │    │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Key Integration Observations

1. **Clean Separation:** Modules have clear, single responsibilities
2. **Minimal Coupling:** Only necessary dependencies between modules
3. **Platform Flexibility:** Each module can target different platforms
4. **Version Alignment:** All modules share the same version
5. **Independent Development:** wgsl can be developed independently
6. **Gradual Integration:** wgsl integration with webgpu-ktypes is a future opportunity

---

*Generated by BMAD document-project workflow - Step 7*  
*Date: 2026-05-19T20:45:00Z*
