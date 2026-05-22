# WebGPU Kotlin Toolkit - webgpu-ktypes-web Architecture Documentation

**Date:** 2026-05-19  
**Module:** webgpu-ktypes-web  
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

The **webgpu-ktypes-web** module provides **Web/JS-specific type extensions and interop utilities** for the WebGPU Kotlin Toolkit. This module is specialized for **JavaScript/Node.js and WebAssembly** targets, offering platform-specific implementations and interoperability with JavaScript typed arrays and browser APIs.

### Key Facts

| Attribute | Value |
|-----------|-------|
| **Module Role** | Web/JS-Specific Type Extensions and Interop |
| **Technology** | Kotlin Multiplatform, Kotlin/JS, Kotlin/Wasm |
| **Platform Coverage** | JS, WasmJs only (2 targets) |
| **Dependency** | webgpu-ktypes (core module) |
| **Architecture** | Platform specialization, type bridging, extension functions |
| **Communication** | Kotlin/JS and Kotlin/Wasm interop with JavaScript typed arrays |

### Purpose

This module provides:
- **Platform-specific type extensions** for Web/JS targets
- **JavaScript interop utilities** for working with browser APIs
- **Type bridging** between Kotlin and JavaScript types
- **Extension functions** for convenient Web/JS-specific operations
- **WebAssembly support** with Kotlin/Wasm interop

### Use Cases

The web module is used for:
- **Browser-based WebGPU applications** - Full browser WebGPU API access
- **Node.js WebGPU applications** - Server-side WebGPU with Node.js
- **WebAssembly applications** - WebGPU in WebAssembly runtimes
- **JavaScript typed array interop** - Efficient data exchange with JS arrays

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
│                     (THIS MODULE)                              │
│                     JS/WasmJs Only                             │
│                                                                 │
│                          ▲                                      │
│                          │                                      │
│                          ▼                                      │
│                       wgsl                                     │
│                  (Independent)                                  │
└─────────────────────────────────────────────────────────────┘
```

This module **depends on** `webgpu-ktypes` and provides **platform-specific implementations** for JS and WasmJs targets.

---

## Technology Stack

### Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Kotlin** | 2.0+ | Primary programming language |
| **Kotlin Multiplatform** | Latest | Multiplatform compilation |
| **Kotlin/JS** | Latest | JavaScript compilation |
| **Kotlin/Wasm** | Latest | WebAssembly compilation |
| **Gradle** | 9.5.0 | Build system (KTS DSL) |
| **Java Toolchain** | 25 | JVM compilation (for build) |

### Platform-Specific Technologies

| Platform | Technology | Purpose |
|----------|------------|---------|
| **JS** | Kotlin/JS IR Compiler | Compiles Kotlin to JavaScript |
| **JS** | JavaScript Typed Arrays | `Int8Array`, `Int16Array`, `Int32Array`, `Float32Array`, etc. |
| **JS** | Browser APIs | `navigator.gpu`, `window`, `EventTarget`, etc. |
| **WasmJs** | Kotlin/Wasm | Compiles Kotlin to WebAssembly |
| **WasmJs** | JS Interop | JavaScript interop from WebAssembly |

### Key Dependencies

```kotlin
// webgpu-ktypes-web/build.gradle.kts
plugins {
    kotlin("multiplatform")
}

dependencies {
    implementation(project(":webgpu-ktypes"))
}
```

### External Interfaces

The module defines external JavaScript interfaces for browser APIs:

```kotlin
@JsModule("navigator")
external val navigator: Navigator

@JsModule("window")
external val window: Window
```

---

## Architecture Pattern

### Primary Pattern: Platform Specialization

This module implements a **Platform Specialization** pattern with the following characteristics:

1. **Target-Specific Code**: Only compiles for JS and WasmJs targets
2. **Type Bridging**: Converts between Kotlin and JavaScript types
3. **Extension Functions**: Adds JS/Wasm-specific convenience methods
4. **External Declarations**: Declares external JavaScript APIs

### Design Patterns Used

| Pattern | Usage | Location |
|---------|-------|----------|
| **External Declaration** | JavaScript API declarations | `interop.kt` |
| **Type Bridging** | Kotlin/JS type conversion | `jsnumber-interops.kt` |
| **Extension Function** | Convenience methods for JS types | `jsarray-interop.kt` |
| **Platform Specialization** | JS/Wasm-specific implementations | All files in module |

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                  webgpu-ktypes-web MODULE ARCHITECTURE                      │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                        COMMON MAIN (JS + WasmJs)                     │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │                      interop.kt                                   │ │ │
│  │  │  External JavaScript API declarations                           │ │ │
│  │  │                                                                  │ │ │
│  │  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐           │ │ │
│  │  │  │   External   │  │   External   │  │   External   │           │ │ │
│  │  │  │  Interfaces  │  │    Objects   │  │   Functions  │           │ │ │
│  │  │  │              │  │              │  │              │           │ │ │
│  │  │  │ - EventTarget│  │ - navigator  │  │ - js()       │           │ │ │
│  │  │  │ - DOMException│  │ - window    │  │ - toLong()   │           │ │ │
│  │  │  │ - GPU        │  │ - devicePixel│  │ - toULong()  │           │ │ │
│  │  │  │              │  │  Ratio       │  │              │           │ │ │
│  │  │  └─────────────┘  └─────────────┘  └─────────────┘           │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │                  jsarray-interop.kt                               │ │ │
│  │  │  JavaScript typed array interoperability                       │ │ │
│  │  │                                                                  │ │ │
│  │  │  - Conversion between Kotlin arrays and JS typed arrays      │ │ │
│  │  │  - Extension functions for typed array operations             │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │                 jsnumber-interops.kt                              │ │ │
│  │  │  JavaScript number to Kotlin number conversions                 │ │ │
│  │  │                                                                  │ │ │
│  │  │  - JsNumber.toLong()                                           │ │ │
│  │  │  - JsNumber.toULong()                                          │ │ │
│  │  │  - BigInt conversions                                           │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │                      types.kt                                     │ │ │
│  │  │  Web-specific type definitions                                  │ │ │
│  │  │                                                                  │ │ │
│  │  │  - Web-specific type aliases                                     │ │ │
│  │  │  - Platform-specific implementations                           │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                        JS MAIN (JavaScript)                           │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │              jsarray-interop.js.kt                              │ │ │
│  │  │  JS-specific implementations of typed array interop           │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │              jsnumber-interops.js.kt                             │ │ │
│  │  │  JS-specific number conversion implementations                    │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                      WASMJS MAIN (WebAssembly)                       │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │              jsarray-interop.wasmJs.kt                            │ │ │
│  │  │  WasmJs-specific implementations of typed array interop       │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  │                                                                   │ │
│  │  ┌──────────────────────────────────────────────────────────────┐ │ │
│  │  │              jsnumber-interops.wasmJs.kt                           │ │ │
│  │  │  WasmJs-specific number conversion implementations                │ │ │
│  │  └──────────────────────────────────────────────────────────────┘ │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
│  DEPENDENCIES:                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                    webgpu-ktypes (Core Module)                       │ │
│  │  - Provides core WebGPU types                                        │ │
│  │  - ArrayBuffer sealed interface                                     │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

### Key Architectural Decisions

1. **Target-Specific Compilation**: Module only compiles for JS and WasmJs targets, not for other platforms
2. **External API Declarations**: Uses Kotlin/JS external declarations to interface with browser APIs
3. **Type Bridging**: Provides seamless conversion between Kotlin and JavaScript types
4. **Platform-Specific Implementations**: Separate implementations for JS and WasmJs where needed
5. **JavaScript Interop**: Leverages Kotlin/JS interop features for efficient communication

---

## Data Architecture

### Core Interop Types

#### 1. External JavaScript Interfaces

Defined in `interop.kt`:

```kotlin
// External JavaScript interfaces for browser APIs
@file:Suppress("unused")
@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

import js.promise.Promise
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.JsNumber
import kotlin.js.js

// Browser APIs
external interface EventTarget: JsAny
external interface DOMException: JsAny
external interface Event: JsAny
external interface EventInit: JsAny

// Navigator GPU
external object navigator {
    val gpu: GPU?
}

// Window
external object window {
    var devicePixelRatio: JsNumber
}

// WebGPU global
external interface GPU: JsAny {
    fun getPreferredCanvasFormat(): String
    fun requestAdapter(): Promise<JsAny>
    fun requestAdapter(descriptor: WGPURequestAdapterOptions): Promise<JsAny>
    var wgslLanguageFeatures: JsAny /* WGSLLanguageFeatures */
}
```

#### 2. JavaScript Number Interop

Defined in `jsnumber-interops.kt`:

```kotlin
@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun JsNumber.toLong(): Long = toLong(this)
fun JsNumber.toULong(): ULong = toULong(this)

private fun toLong(ref: JsNumber): Long = js("BigInt(ref)")
private fun toULong(ref: JsNumber): ULong = js("BigInt(ref)")
```

These functions provide **efficient conversion** between JavaScript numbers and Kotlin Long/ULong types, using JavaScript's BigInt for precision.

#### 3. JavaScript Array Interop

Defined in `jsarray-interop.kt`:

```kotlin
// Type definitions for JavaScript typed arrays
@file:Suppress("unused")

package io.ygdrasil.webgpu

import kotlin.js.JsAny

// Typed array types (external declarations)
external class Int8Array : JsAny
external class Int16Array : JsAny
external class Int32Array : JsAny
external class Uint8Array : JsAny
external class Uint16Array : JsAny
external class Uint32Array : JsAny
external class Float32Array : JsAny
external class Float64Array : JsAny
external class BigInt64Array : JsAny
external class BigUint64Array : JsAny

// Extension functions for typed arrays
fun Int8Array.toByteArray(): ByteArray
fun Int16Array.toShortArray(): ShortArray
fun Int32Array.toIntArray(): IntArray
fun Uint8Array.toUByteArray(): UByteArray
fun Uint16Array.toUShortArray(): UShortArray
fun Uint32Array.toUIntArray(): UIntArray
fun Float32Array.toFloatArray(): FloatArray
fun Float64Array.toDoubleArray(): DoubleArray

// Conversion from Kotlin arrays to JS typed arrays
fun ByteArray.toInt8Array(): Int8Array
fun ShortArray.toInt16Array(): Int16Array
fun IntArray.toInt32Array(): Int32Array
fun UByteArray.toUint8Array(): Uint8Array
fun UShortArray.toUint16Array(): Uint16Array
fun UIntArray.toUint32Array(): Uint32Array
fun FloatArray.toFloat32Array(): Float32Array
fun DoubleArray.toFloat64Array(): Float64Array
```

#### 4. Web-Specific Types

Defined in `types.kt`:

```kotlin
// Web-specific type definitions
@file:Suppress("unused")

package io.ygdrasil.webgpu

import kotlin.js.JsAny

// Web-specific type aliases or wrapper types
typealias WebArrayBuffer = JsAny  // Browser ArrayBuffer
typealias WebMemory = JsAny        // Memory types

// Platform-specific implementations of core types
expect class WebArrayBufferImpl : ArrayBuffer {
    // Web-specific ArrayBuffer implementation
}
```

### Platform-Specific Implementations

#### JavaScript (JS) Implementation

Files in `jsMain/kotlin/`:
- `jsarray-interop.js.kt` - JS-specific typed array implementations
- `jsnumber-interops.js.kt` - JS-specific number conversion

These files provide **JavaScript-specific implementations** using Kotlin/JS features.

#### WebAssembly/JS (WasmJs) Implementation

Files in `wasmJsMain/kotlin/`:
- `jsarray-interop.wasmJs.kt` - WasmJs-specific typed array implementations
- `jsnumber-interops.wasmJs.kt` - WasmJs-specific number conversion

These files provide **WebAssembly-specific implementations** using Kotlin/Wasm interop.

### Data Flow Patterns

```
Kotlin Code → Extension Functions → JavaScript Interop → Browser APIs
           ↓
   JS Typed Arrays ↔ Kotlin Arrays
           ↓
   WebGPU Operations
```

All data flows through **type bridging** between Kotlin and JavaScript types.

---

## API Design

### Public API Surface

The module exposes a **JavaScript-interop focused API**:

```kotlin
// Using external browser APIs
import io.ygdrasil.webgpu.navigator
import io.ygdrasil.webgpu.window

// Access WebGPU from browser
val adapterPromise = navigator.gpu?.requestAdapter()

// Access window properties
val pixelRatio = window.devicePixelRatio

// Convert between JavaScript and Kotlin numbers
val jsNumber: JsNumber = js("42")
val kotlinLong: Long = jsNumber.toLong()
val kotlinULong: ULong = jsNumber.toULong()

// Convert between JavaScript typed arrays and Kotlin arrays
val jsArray = int8ArrayOf(1, 2, 3, 4, 5)
val kotlinArray: ByteArray = jsArray.toByteArray()

val kotlinArray = byteArrayOf(1, 2, 3, 4, 5)
val jsArray: Int8Array = kotlinArray.toInt8Array()
```

### API Characteristics

| Characteristic | Description |
|----------------|-------------|
| **Platform-Specific** | Only available on JS and WasmJs targets |
| **Zero Overhead** | Direct JavaScript interop with minimal wrapping |
| **Type Safety** | Kotlin type system applied to JavaScript types |
| **Null Safety** | Nullable types for optional JavaScript values |
| **Efficient** | Uses JavaScript's native types where possible |

### API Categories

#### 1. Browser API Access
- `navigator` - Access to `navigator.gpu` for WebGPU
- `window` - Access to browser window properties
- External interfaces for DOM and WebGPU types

#### 2. Number Conversion
- `JsNumber.toLong()` - Convert JS number to Kotlin Long
- `JsNumber.toULong()` - Convert JS number to Kotlin ULong
- Uses JavaScript BigInt for 64-bit precision

#### 3. Typed Array Conversion
- Extensions for converting between Kotlin arrays and JS typed arrays
- Support for all typed array types (Int8, Int16, Int32, Uint8, Uint16, Uint32, Float32, Float64)
- Zero-copy where possible, efficient copying otherwise

#### 4. Web-Specific Types
- Web-specific type aliases
- Platform-specific implementations of core types

---

## Component Overview

### Core Components

| Component | Location | Responsibility | Lines | Platform |
|-----------|----------|----------------|-------|----------|
| **interop.kt** | `src/commonMain/kotlin/interop.kt` | External JS API declarations | ~40 | Common (JS/WasmJs) |
| **jsarray-interop.kt** | `src/commonMain/kotlin/jsarray-interop.kt` | JS typed array interop | N/A | Common (JS/WasmJs) |
| **jsnumber-interops.kt** | `src/commonMain/kotlin/jsnumber-interops.kt` | JS number conversions | ~10 | Common (JS/WasmJs) |
| **types.kt** | `src/commonMain/kotlin/types.kt` | Web-specific types | N/A | Common (JS/WasmJs) |

### Platform-Specific Components

| Platform | Component | Location | Responsibility |
|----------|-----------|----------|----------------|
| **JS** | jsarray-interop.js.kt | `src/jsMain/kotlin/jsarray-interop.js.kt` | JS-specific typed array impl |
| **JS** | jsnumber-interops.js.kt | `src/jsMain/kotlin/jsnumber-interops.js.kt` | JS-specific number conversion |
| **WasmJs** | jsarray-interop.wasmJs.kt | `src/wasmJsMain/kotlin/jsarray-interop.wasmJs.kt` | WasmJs-specific typed array impl |
| **WasmJs** | jsnumber-interops.wasmJs.kt | `src/wasmJsMain/kotlin/jsnumber-interops.wasmJs.kt` | WasmJs-specific number conversion |

### Component Relationships

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    COMPONENT RELATIONSHIPS                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                    COMMON MAIN (Shared JS/WasmJs)                     │ │
│  │  ┌────────────────┐  ┌────────────────┐  ┌────────────────┐        │ │
│  │  │   interop.kt    │  │ jsarray-interop │  │ jsnumber-       │        │ │
│  │  │ (External APIs) │  │ .kt            │  │ interops.kt     │        │ │
│  │  │                 │  │ (Common interop)│  │ (Conversions)   │        │ │
│  │  └────────┬────────┘  └────────┬────────┘  └────────┬────────┘        │ │
│  │           │                 │                  │                   │ │
│  └───────────┼─────────────────┼──────────────────┼───────────────────┘ │
│              │                 │                  │                         │
│  ┌───────────▼─────┐     ┌─────▼─────┐      ┌──────▼──────────────┐   │
│  │   JS MAIN       │     │ WASMJS    │      │   types.kt           │   │
│  │                 │     │ MAIN     │      │ (Web-specific types) │   │
│  │ ┌─────────────┐ │     │           │      │                      │   │
│  │ │jsarray-     │ │     │ ┌───────┐ │      │                      │   │
│  │ │interop     │ │     │ │jsarray│ │      │                      │   │
│  │ │.js.kt      │ │     │ │-interop│ │      │                      │   │
│  │ └─────────────┘ │     │ │.wasmJs│ │      │                      │   │
│  │                 │     │ │.kt    │ │      │                      │   │
│  │ ┌─────────────┐ │     │ └───────┘ │      │                      │   │
│  │ │jsnumber-    │ │     │           │      │                      │   │
│  │ │interops    │ │     │ ┌───────┐ │      │                      │   │
│  │ │.js.kt      │ │     │ │jsnumber│ │      │                      │   │
│  │ └─────────────┘ │     │ │-interops│ │      │                      │   │
│  └─────────────────────┘     │ │.wasmJs│ │      └──────────────────────┘   │
│                            │ │.kt    │ │                          │
│                            └───────┘ └──────────────────────────────┘ │
│                                                                       │
│  USES TYPES FROM:                                                    │
│  ┌────────────────────────────────────────────────────────────────┐  │
│  │                    webgpu-ktypes (Core Module)                     │  │
│  │  - ArrayBuffer sealed interface                                  │  │
│  │  - Core WebGPU types                                              │  │
│  └────────────────────────────────────────────────────────────────┘  │
│                                                                       │
└───────────────────────────────────────────────────────────────────────┘
```

---

## Source Tree

### Directory Structure

```
webgpu-ktypes-web/
├── build.gradle.kts            # Module build configuration
│
└── src/
    ├── commonMain/
    │   └── kotlin/
    │       ├── interop.kt              # External JS API declarations
    │       ├── jsarray-interop.kt       # JS typed array interop (common)
    │       ├── jsnumber-interops.kt     # JS number conversions (common)
    │       └── types.kt                # Web-specific types
    │
    ├── commonTest/
    │   └── kotlin/
    │       └── JsArrayInteropTest.kt   # Common interop tests
    │
    ├── jsMain/
    │   └── kotlin/
    │       ├── jsarray-interop.js.kt   # JS-specific typed array implementation
    │       └── jsnumber-interops.js.kt # JS-specific number conversion
    │
    ├── jsTest/
    │   └── kotlin/
    │       └── JsArrayInteropTest.js.kt   # JS-specific tests
    │
    └── wasmJsMain/
        └── kotlin/
            ├── jsarray-interop.wasmJs.kt   # WasmJs-specific typed array implementation
            └── jsnumber-interops.wasmJs.kt # WasmJs-specific number conversion
```

### File Details

#### interop.kt

**Purpose:** External JavaScript API declarations

**Contains:**
- External interface declarations for browser APIs
- External object declarations for `navigator` and `window`
- External interface for `GPU` (WebGPU global)
- Type definitions for JavaScript types used in interop

**Technology:** Kotlin/JS external declarations

**Example:**
```kotlin
external interface EventTarget: JsAny
external object navigator {
    val gpu: GPU?
}
external object window {
    var devicePixelRatio: JsNumber
}
```

#### jsarray-interop.kt

**Purpose:** JavaScript typed array interoperability (common definitions)

**Contains:**
- External class declarations for all JavaScript typed array types
- Extension functions for converting between Kotlin arrays and JS typed arrays
- Common interop utilities

**Technology:** Kotlin/JS with external declarations

**Example:**
```kotlin
external class Int8Array : JsAny

external class Float32Array : JsAny

fun Int8Array.toByteArray(): ByteArray {
    // Implementation in platform-specific files
}

fun ByteArray.toInt8Array(): Int8Array {
    // Implementation in platform-specific files
}
```

#### jsnumber-interops.kt

**Purpose:** JavaScript number to Kotlin number conversions (common definitions)

**Contains:**
- Extension functions for `JsNumber` type
- Conversion functions to Kotlin `Long` and `ULong`
- Private helper functions for BigInt conversion

**Technology:** Kotlin/JS with inline JavaScript

**Example:**
```kotlin
@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun JsNumber.toLong(): Long = toLong(this)

private fun toLong(ref: JsNumber): Long = js("BigInt(ref)")
```

#### types.kt

**Purpose:** Web-specific type definitions

**Contains:**
- Type aliases for JavaScript types
- Web-specific implementations of core types
- Platform-specific type definitions

**Technology:** Kotlin with expect/actual pattern

#### Platform-Specific Files

**jsarray-interop.js.kt**: JS-specific implementation
- Uses Kotlin/JS compiler features
- Direct access to JavaScript typed array APIs

**jsarray-interop.wasmJs.kt**: WasmJs-specific implementation
- Uses Kotlin/Wasm interop features
- JavaScript interop from WebAssembly

**jsnumber-interops.js.kt**: JS-specific number conversion
- Direct JavaScript BigInt access

**jsnumber-interops.wasmJs.kt**: WasmJs-specific number conversion
- WebAssembly-compatible BigInt access

### Statistics

| Directory | Files | Purpose |
|-----------|-------|---------|
| commonMain | 4 | Common JS/WasmJs definitions |
| commonTest | 1 | Common interop tests |
| jsMain | 2 | JS-specific implementations |
| jsTest | 1 | JS-specific tests |
| wasmJsMain | 2 | WasmJs-specific implementations |

### Package Structure

All Kotlin code is in the `io.ygdrasil.webgpu` package, sharing the namespace with other `webgpu-ktypes` modules.

---

## Development Workflow

### Prerequisites

Same as `webgpu-ktypes` module, plus:
- **Node.js**: 20+ (for JS compilation and testing)

### Platform-Specific Development

This module requires **platform-specific implementations** for JS and WasmJs:

1. **Add Common Definition**: Define external declaration or extension in `commonMain`
2. **Implement for JS**: Add JS-specific implementation in `jsMain`
3. **Implement for WasmJs**: Add WasmJs-specific implementation in `wasmJsMain`
4. **Verify Both Platforms**: Test on both JS and WasmJs targets

### Build Configuration

```kotlin
// webgpu-ktypes-web/build.gradle.kts
kotlin {
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
    wasmJs {
        // WasmJs specific configuration
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":webgpu-ktypes"))
            }
        }
        val jsMain by getting {
            dependencies {
                // JS-specific dependencies
            }
        }
        val wasmJsMain by getting {
            dependencies {
                // WasmJs-specific dependencies
            }
        }
    }
}
```

### Development Process

1. **Declare External API**: Add external declarations in `interop.kt`
2. **Add Common Interop**: Define common interop utilities in `jsarray-interop.kt` and `jsnumber-interops.kt`
3. **Implement Platform-Specific**: Add implementations for JS and WasmJs
4. **Add Tests**: Add tests in `commonTest`, `jsTest`
5. **Verify**: Run `./gradlew :webgpu-ktypes-web:build`

### Code Quality

- **Type Safety**: All JavaScript types are properly typed in Kotlin
- **Null Safety**: Nullable types for optional JavaScript values
- **Efficiency**: Direct JavaScript access where possible, minimal wrapping
- **Documentation**: Comprehensive KDoc for all public APIs

---

## Deployment Architecture

### Artifact Publishing

The `webgpu-ktypes-web` module is published as part of the WebGPU Kotlin Toolkit:

- **Maven Central**: Primary repository (JS/WasmJs artifacts)
- **GitHub Packages**: Optional (not currently configured)

### Build Outputs

The module produces artifacts for **2 targets**:

| Platform | Artifact Type | Contains |
|----------|---------------|----------|
| **JS** | JS modules | JavaScript code + source maps |
| **WasmJs** | Wasm modules | WebAssembly code + JS interop |

### Dependency Management

Applications depend on this module for Web/JS-specific WebGPU functionality:

```kotlin
// In application/build.gradle.kts
dependencies {
    implementation("io.ygdrasil:webgpu-ktypes-web:VERSION")
    // Or for local development:
    implementation(project(":webgpu-ktypes-web"))
}
```

**Note:** This dependency **transitively includes** `webgpu-ktypes`.

### Versioning

The module version **matches** the `webgpu-ktypes` version:
- All modules are released together
- All share the same version number
- All are published in the same release

### Target-Specific Dependencies

When using this module, applications need:
- **For Browser**: No additional dependencies (uses browser WebGPU API)
- **For Node.js**: Node.js WebGPU support (experimental as of 2024)
- **For WasmJs**: Wasm runtime with JavaScript interop support

---

## Testing Strategy

### Test Coverage

| Component | Test Location | Coverage | Test Cases |
|-----------|---------------|----------|------------|
| **JS Array Interop** | `commonTest/kotlin/JsArrayInteropTest.kt` | High | Multiple |
| **JS Array Interop (JS)** | `jsTest/kotlin/JsArrayInteropTest.js.kt` | High | Multiple |

### Test Types

#### Common Interop Tests

Platform-independent tests that run on both JS and WasmJs:

```kotlin
// JsArrayInteropTest.kt
class JsArrayInteropTest {
    @Test
    fun testInt8ArrayConversion() {
        val kotlinArray = byteArrayOf(1, 2, 3, 4, 5)
        val jsArray = kotlinArray.toInt8Array()
        val backToKotlin = jsArray.toByteArray()
        
        assertEquals(kotlinArray.size, backToKotlin.size)
        // Verify all elements match (may need platform-specific assertion)
    }
    
    @Test
    fun testFloat32ArrayConversion() {
        val kotlinArray = floatArrayOf(1.0f, 2.0f, 3.0f)
        val jsArray = kotlinArray.toFloat32Array()
        val backToKotlin = jsArray.toFloatArray()
        
        assertEquals(kotlinArray.size, backToKotlin.size)
    }
}
```

#### JavaScript-Specific Tests

Tests that only run on JS target:

```kotlin
// JsArrayInteropTest.js.kt
class JsArrayInteropJsTest {
    @Test
    fun testBrowserNavigatorAccess() {
        // Only runs in browser environment
        assertNotNull(navigator)
        assertNotNull(navigator.gpu)
    }
}
```

### Test Framework

- **Test Framework**: Kotest
- **Test Runner**: Karma (browser), Node.js (for Node.js tests)
- **JS Compiler**: Kotlin/JS IR compiler

### Test Execution

Run all tests:
```bash
./gradlew :webgpu-ktypes-web:allTests
```

Run specific platform tests:
```bash
./gradlew :webgpu-ktypes-web:jsTest
```

### CI/CD Integration

Tests are executed as part of the **monorepo CI/CD pipeline**:
- Runs on macOS, Ubuntu, Windows (for Node.js)
- Executed in GitHub Actions workflows
- Runs on push/PR to relevant branches

---

## Cross-References

### Dependencies

- **Depends on**: [webgpu-ktypes](./architecture-webgpu-ktypes.md) (core module)
- **Used by**: Web/JS applications using WebGPU Kotlin Toolkit

### Related Documentation

- [Integration Architecture](../integration-architecture.md)
- [Development Guide](../development-guide.md)
- [Kotlin/JS Documentation](https://kotlinlang.org/docs/js-overview.html)
- [Kotlin/Wasm Documentation](https://kotlinlang.org/docs/wasm-overview.html)
- [WebGPU Specification](https://www.w3.org/TR/webgpu/)

### See Also

- [MDN WebGPU API](https://developer.mozilla.org/en-US/docs/Web/API/WebGPU_API) - Browser WebGPU reference
- [WebGPU in Node.js](https://nodejs.org/api/webgl.html) - Node.js WebGPU support

---

*Document generated using BMAD Method `document-project` workflow - Step 8: Architecture Documentation*
