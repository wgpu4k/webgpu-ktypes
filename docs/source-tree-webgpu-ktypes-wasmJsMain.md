# webgpu-ktypes - wasmJsMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/wasmJsMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 1
**Total Lines:** 332

## Overview

This directory contains **WebAssembly/JS-specific implementations** of the WebGPU ArrayBuffer abstraction. It uses Kotlin/Wasm interoperability with JavaScript typed arrays for efficient binary data handling targeting WebAssembly compilation.

## Directory Structure

```
webgpu-ktypes/src/wasmJsMain/kotlin/
└── ArrayBuffer.wasmJs.kt     (332 lines) - Wasm/JS ArrayBuffer implementation
```

## Critical Files

### `ArrayBuffer.wasmJs.kt`

**Purpose:** WebAssembly/JavaScript platform implementation of ArrayBuffer conversion and manipulation functions.

**Key Technologies:**
- Kotlin/Wasm interop with JavaScript
- JavaScript typed arrays: `Int8Array`, `Int16Array`, `Int32Array`, `Float32Array`, `Float64Array`
- JavaScript typed arrays (unsigned): `Uint8Array`, `Uint16Array`, `Uint32Array`
- Kotlin/Wasm primitives: `JsPrimitives.toJsByte()`, `toJsShort()`, `toJsInt()`, `toJsFloat()`, `toJsUByte()`
- `js.core.JsPrimitives` - JavaScript primitive type conversions
- `@OptIn(ExperimentalWasmJsInterop::class)` - Opt-in for experimental Wasm/JS interop

**File Structure:** Organized into 5 main sections:

### Section 1: Extension Functions (Lines 1-52)
**Purpose:** Kotlin typed arrays to ArrayBuffer conversion

**Functions:**
- `ByteArray.toArrayBuffer()` - Creates JsArray, converts to Int8Array, wraps buffer
- `ShortArray.toArrayBuffer()` - Creates JsArray, converts to Int16Array, wraps buffer
- `IntArray.toArrayBuffer()` - Creates JsArray, converts to Int32Array, wraps buffer
- `FloatArray.toArrayBuffer()` - Creates JsArray, converts to Float32Array, wraps buffer
- `DoubleArray.toArrayBuffer()` - Creates JsArray, converts to Float64Array, wraps buffer
- `UByteArray.toArrayBuffer()` - Creates JsArray, converts to Uint8Array, wraps buffer
- `UShortArray.toArrayBuffer()` - Creates JsArray, converts to Uint16Array, wraps buffer
- `UIntArray.toArrayBuffer()` - Creates JsArray, converts to Uint32Array, wraps buffer

**Pattern:** All follow same pattern: create JsArray, populate with converted values, create typed array, wrap buffer

### Section 2: Read Methods - Full Array (Lines 55-83)
**Purpose:** Convert JavaScript ArrayBuffer to Kotlin typed arrays

**Functions:**
- `js.buffer.ArrayBuffer.readByteArray(): ByteArray` - Creates Int8Array view, extracts bytes
- `readShortArray(): ShortArray` - Creates Int16Array view, extracts shorts
- `readIntArray(): IntArray` - Creates Int32Array view, extracts ints
- `readFloatArray(): FloatArray` - Creates Float32Array view, extracts floats
- `readDoubleArray(): DoubleArray` - Creates Float64Array view, extracts doubles
- `readUByteArray(): UByteArray` - Creates Uint8Array view, extracts unsigned bytes
- `readUShortArray(): UShortArray` - Creates Uint16Array view, extracts unsigned shorts
- `readUIntArray(): UIntArray` - Creates Uint32Array view, extracts unsigned ints

**Pattern:** All follow: create typed array view on this buffer, map values to Kotlin types

### Section 3: Read Methods - Indexed (Lines 86-123)
**Purpose:** Read single values at specific offsets

**Functions:**
- `readByte(offset: Int): Byte` - Reads byte at offset using Int8Array
- `readShort(offset: Int): Short` - Reads short at offset using Int16Array
- `readInt(offset: Int): Int` - Reads int at offset using Int32Array
- `readFloat(offset: Int): Float` - Reads float at offset using Float32Array
- `readDouble(offset: Int): Double` - Reads double at offset using Float64Array
- `readUByte(offset: Int): UByte` - Reads unsigned byte at offset using Uint8Array
- `readUShort(offset: Int): UShort` - Reads unsigned short at offset using Uint16Array
- `readUInt(offset: Int): UInt` - Reads unsigned int at offset using Uint32Array

**Pattern:** Create typed array view with size 1 at offset, extract value from index 0

### Section 4: Write Methods - Indexed (Lines 126-163)
**Purpose:** Write single values at specific offsets

**Functions:**
- `writeByte(offset: Int, value: Byte)` - Writes byte using Int8Array
- `writeShort(offset: Int, value: Short)` - Writes short using Int16Array
- `writeInt(offset: Int, value: Int)` - Writes int using Int32Array
- `writeFloat(offset: Int, value: Float)` - Writes float using Float32Array
- `writeDouble(offset: Int, value: Double)` - Writes double using Float64Array
- `writeUByte(offset: Int, value: UByte)` - Writes unsigned byte using Uint8Array
- `writeUShort(offset: Int, value: UShort)` - Writes unsigned short using Uint16Array
- `writeUInt(offset: Int, value: UInt)` - Writes unsigned int using Uint32Array

**Pattern:** Create typed array view with size 1 at offset, set value at index 0

### Section 5: Write Methods - Array (Lines 166-233)
**Purpose:** Write entire Kotlin typed arrays at specific offsets

**Functions:**
- `writeByteArray(offset: Int, array: ByteArray)` - Writes byte array via Int8Array
- `writeShortArray(offset: Int, array: ShortArray)` - Writes short array via Int16Array
- `writeIntArray(offset: Int, array: IntArray)` - Writes int array via Int32Array
- `writeFloatArray(offset: Int, array: FloatArray)` - Writes float array via Float32Array
- `writeDoubleArray(offset: Int, array: DoubleArray)` - Writes double array via Float64Array
- `writeUByteArray(offset: Int, array: UByteArray)` - Writes unsigned byte array via Uint8Array
- `writeUShortArray(offset: Int, array: UShortArray)` - Writes unsigned short array via Uint16Array
- `writeUIntArray(offset: Int, array: UIntArray)` - Writes unsigned int array via Uint32Array

**Pattern:** Create typed array view with array.size at offset, use forEachIndexed to copy values

## Implementation Characteristics

**Common Patterns:**
1. All functions are `internal actual inline` with `@Suppress("NOTHING_TO_INLINE")`
2. Uses `unsafeCast` or JS primitive conversions for type interop
3. All functions operate on `js.buffer.ArrayBuffer` receiver
4. Heavy use of JavaScript typed arrays for memory access
5. Consistent naming convention matching commonMain expect declarations

**Type Conversion Strategy:**
- Kotlin → JS: Uses `toJsByte()`, `toJsShort()`, `toJsInt()`, `toJsFloat()`, `toJsUByte()`
- JS → Kotlin: Uses `.toInt().toByte()`, `.toInt().toShort()`, `.toDouble().toFloat()`
- Unsigned types: Converts through signed types (e.g., `value.toShort().toJsShort()`)

**Memory Model:**
- Uses JavaScript ArrayBuffer as backing storage
- Typed arrays provide views into the buffer
- No direct memory allocation - relies on JavaScript runtime

## Integration Points

- **Dependencies:** Kotlin/Wasm standard library, JavaScript typed arrays
- **Platform Target:** WebAssembly compilation to JavaScript
- **Multiplatform:** Provides `actual` implementations for commonMain `expect` declarations
- **Related Modules:** Works with commonMain ArrayBuffer interface

## Key Observations

1. **Platform Specialization:** Pure Kotlin/Wasm implementation targeting JavaScript/Wasm runtime
2. **Type Safety:** Uses Kotlin type system with JavaScript interop conversions
3. **Performance:** All functions are inlined for zero-overhead abstraction
4. **Completeness:** Provides comprehensive coverage for all primitive numeric types
5. **Pattern Consistency:** Follows same structure as jsMain implementation but adapted for Wasm
6. **No Native Code:** Uses only JavaScript APIs available in Wasm environment
