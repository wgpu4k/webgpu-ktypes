# webgpu-ktypes - webMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/webMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 3
**Total Lines:** 2,835

## Overview

This directory contains **Web/JS-specific implementations** of the WebGPU ArrayBuffer abstraction and enumeration classes. It leverages the browser's native `ArrayBuffer` API for efficient binary data handling.

## Directory Structure

```
webgpu-ktypes/src/webMain/kotlin/
├── ArrayBuffer.web.kt     (377 lines) - Web ArrayBuffer interface implementation
├── enumerations.kt       (2,401 lines) - Web enum implementations
└── WebArrayBuffer.kt      (58 lines) - Concrete Web ArrayBuffer wrapper
```

## Critical Files

### `ArrayBuffer.web.kt`

**Purpose:** Web platform implementation of the `ArrayBuffer` sealed interface.

**Key Technologies:**
- `js.buffer.ArrayBuffer` - Browser's native ArrayBuffer API
- Kotlin/JS interop

**Contains:**
- `actual sealed interface ArrayBuffer` - Web-specific interface implementation
- All `actual` method declarations (read, write, conversion methods)
- `actual companion object` with factory methods:
  - `allocate(sizeInBytes: ULong)` - Creates buffer using `js.buffer.ArrayBuffer(sizeInBytes.toInt())`
  - `wrap(buffer: js.buffer.ArrayBuffer)` - Wraps existing JavaScript ArrayBuffer
  - `of(array: ByteArray/ShortArray/IntArray/FloatArray/DoubleArray/UByteArray/UShortArray/UIntArray)` - Creates from typed arrays via extension functions

**Pattern:** Delegates to `WebArrayBuffer` implementation and uses `internal expect` extensions for JS-specific operations

**Internal Expect Declarations:**
- Typed array conversion: `ByteArray.toArrayBuffer()`, `ShortArray.toArrayBuffer()`, etc.
- ArrayBuffer reading: `js.buffer.ArrayBuffer.readByteArray()`, `readShortArray()`, etc.
- ArrayBuffer writing: `js.buffer.ArrayBuffer.writeByte()`, `writeShort()`, etc.
- Array writing: `js.buffer.ArrayBuffer.writeByteArray()`, `writeShortArray()`, etc.

### `WebArrayBuffer.kt`

**Purpose:** Concrete Web implementation of ArrayBuffer using JavaScript ArrayBuffer.

**Key Technologies:**
- `value class` - Zero-cost abstraction
- `js.buffer.ArrayBuffer` - Native browser ArrayBuffer

**Class Definition:**
```kotlin
value class WebArrayBuffer internal constructor(val buffer: js.buffer.ArrayBuffer): ArrayBuffer
```

**Implementation Strategy:**
- **Properties:** `size` returns `buffer.byteLength.toULong()`
- **Read methods:** Delegates to internal expect extension functions on `js.buffer.ArrayBuffer`
- **Write methods:** Delegates to internal expect extension functions on `js.buffer.ArrayBuffer`
- **Type conversion:** Uses `offset.toInt()` for JavaScript interop (JS uses Int for offsets)

**Note:** This is a thin wrapper that delegates all operations to the underlying JavaScript ArrayBuffer

### `enumerations.kt`

**Purpose:** Web platform implementations of WebGPU enumeration classes.

**Key Difference from Native:** Enum values are **Strings** (JavaScript WebGPU API uses string constants)

**Contains:**
- `actual enum class GPUAddressMode(val value: String)` - Texture addressing modes with string values
  - `ClampToEdge("clamp-to-edge")`
  - `Repeat("repeat")`
  - `MirrorRepeat("mirror-repeat")`
- `actual enum class GPUBlendFactor(val value: String)` - Blend factors with string values
- And all other WebGPU enums...

**Pattern:** Each enum value has a String value matching the JavaScript WebGPU API constant

**Companion Object:** Includes `of(value: String)` lookup function for value-to-enum conversion

**Note:** File is generated (DO NO EDIT)

## Technical Details

### Platform Interop
- **JavaScript API:** Uses browser's native `ArrayBuffer` and typed arrays
- **Type Mapping:** Kotlin types ↔ JavaScript types via Kotlin/JS interop
- **Offset Handling:** Converts `ULong` to `Int` for JavaScript API calls

### Memory Management
- **Allocation:** Uses JavaScript garbage collection (no manual management)
- **Lifetime:** Managed by JavaScript runtime

### Platform Requirements
- **Environment:** Browser or Node.js with Web API support
- **Kotlin/JS:** Requires Kotlin/JS compiler

## Relationship to commonMain

These files provide the **Web platform implementation** for:
- `webgpu-ktypes/src/commonMain/kotlin/ArrayBuffer.kt` (expect interface)
- `webgpu-ktypes/src/commonMain/kotlin/enumerations.kt` (expect enums)

The Kotlin Multiplatform compiler uses these `actual` declarations when compiling for Web/JS targets.

## Comparison with JVM Implementation

| Feature | Web Implementation | JVM Implementation |
|---------|-------------------|-------------------|
| Backing | `js.buffer.ArrayBuffer` | `java.lang.foreign.MemorySegment` |
| Enum values | `String` | `UInt` |
| Memory management | JavaScript GC | Arena-based |
| Type safety | Kotlin/JS interop | Java FFI API |
| Allocation | `new ArrayBuffer(size)` | `Arena.ofAuto().allocate(size)` |

## Code Quality

- **Style:** Clean Kotlin/JS code
- **Documentation:** Comprehensive KDoc comments
- **Interop:** Proper use of Kotlin/JS external declarations
- **Performance:** Minimal overhead, delegates to native JS APIs

---

_Generated using BMAD Method `document-project` workflow - Batch 5: webgpu-ktypes/src/webMain/kotlin/_
