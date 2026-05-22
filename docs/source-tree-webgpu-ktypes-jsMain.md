# webgpu-ktypes - jsMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/jsMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 1
**Total Lines:** 284

## Overview

This directory contains **JavaScript/Node.js-specific implementations** of the WebGPU ArrayBuffer abstraction. It uses Kotlin/JS interoperability with JavaScript typed arrays to provide efficient binary data handling for the JS target.

## Directory Structure

```
webgpu-ktypes/src/jsMain/kotlin/
└── ArrayBuffer.js.kt     (284 lines) - JS platform ArrayBuffer implementation
```

## Critical Files

### `ArrayBuffer.js.kt`

**Purpose:** JavaScript platform implementation of ArrayBuffer conversion and manipulation functions.

**Key Technologies:**
- Kotlin/JS interop (`js.typedarrays.*`)
- JavaScript typed arrays: `Int8Array`, `Int16Array`, `Int32Array`, `Float32Array`, `Float64Array`
- JavaScript typed arrays (unsigned): `Uint8Array`, `Uint16Array`, `Uint32Array`
- `js.buffer.ArrayBuffer` - JavaScript native ArrayBuffer API

**Contains:**
- **Extension functions** for Kotlin typed arrays to JavaScript ArrayBuffer conversion:
  - `ByteArray.toArrayBuffer()` - Wraps as Int8Array
  - `ShortArray.toArrayBuffer()` - Wraps as Int16Array
  - `IntArray.toArrayBuffer()` - Wraps as Int32Array
  - `FloatArray.toArrayBuffer()` - Wraps as Float32Array
  - `DoubleArray.toArrayBuffer()` - Wraps as Float64Array
  - `UByteArray.toArrayBuffer()` - Converts via ByteArray
  - `UShortArray.toArrayBuffer()` - Converts via ShortArray
  - `UIntArray.toArrayBuffer()` - Converts via IntArray

- **Read methods** for JavaScript ArrayBuffer (indexed):
  - `js.buffer.ArrayBuffer.readByte(offset: Int): Byte`
  - `readShort(offset: Int): Short`
  - `readInt(offset: Int): Int`
  - `readFloat(offset: Int): Float`
  - `readDouble(offset: Int): Double`
  - `readUByte(offset: Int): UByte`
  - `readUShort(offset: Int): UShort`
  - `readUInt(offset: Int): UInt`

- **Read methods** for JavaScript ArrayBuffer (full array):
  - `js.buffer.ArrayBuffer.readByteArray(): ByteArray`
  - `readShortArray(): ShortArray`
  - `readIntArray(): IntArray`
  - `readFloatArray(): FloatArray`
  - `readDoubleArray(): DoubleArray`
  - `readUByteArray(): UByteArray`
  - `readUShortArray(): UShortArray`
  - `readUIntArray(): UIntArray`

- **Write methods** for JavaScript ArrayBuffer (indexed):
  - `js.buffer.ArrayBuffer.writeByte(offset: Int, value: Byte)`
  - `writeShort(offset: Int, value: Short)`
  - `writeInt(offset: Int, value: Int)`
  - `writeFloat(offset: Int, value: Float)`
  - `writeDouble(offset: Int, value: Double)`
  - `writeUByte(offset: Int, value: UByte)`
  - `writeUShort(offset: Int, value: UShort)`
  - `writeUInt(offset: Int, value: UInt)`

- **Write methods** for JavaScript ArrayBuffer (array):
  - `js.buffer.ArrayBuffer.writeByteArray(offset: Int, array: ByteArray)`
  - `writeShortArray(offset: Int, array: ShortArray)`
  - `writeIntArray(offset: Int, array: IntArray)`
  - `writeFloatArray(offset: Int, array: FloatArray)`
  - `writeDoubleArray(offset: Int, array: DoubleArray)`
  - `writeUByteArray(offset: Int, array: UByteArray)`
  - `writeUShortArray(offset: Int, array: UShortArray)`
  - `writeUIntArray(offset: Int, array: UIntArray)`

**Pattern:** All functions are marked as `internal actual inline` with `@Suppress("NOTHING_TO_INLINE")` for optimal inlining behavior. Uses `unsafeCast<Type>()` for JavaScript interoperability type conversions.

**Implementation Strategy:**
- Converts Kotlin typed arrays to JavaScript typed arrays using `unsafeCast`
- Uses `ArrayBuffer.wrap()` to create JavaScript ArrayBuffer from typed array buffers
- For unsigned types (UByte, UShort, UInt), delegates to signed counterparts and converts
- Read methods create typed array views on the ArrayBuffer and extract values
- Write methods create typed array views and set values at specified offsets
- Array write methods use `forEachIndexed` to copy entire arrays efficiently

## Integration Points

- **Dependencies:** Imports `js.typedarrays.*` from Kotlin/JS standard library
- **Platform Target:** Compiles to JavaScript for browser and Node.js environments
- **Multiplatform:** Provides `actual` implementations for the `expect` declarations in commonMain
- **Related Modules:** Works with commonMain ArrayBuffer interface, other platform modules (jvmMain, nativeMain, webMain)

## Key Observations

1. **Platform Specialization:** This is a pure Kotlin/JS implementation targeting JavaScript runtime
2. **Type Safety:** Uses Kotlin's type system with `unsafeCast` for JS interop
3. **Performance:** All functions are inlined for zero-overhead abstraction
4. **Completeness:** Provides comprehensive coverage for all primitive numeric types (signed and unsigned)
5. **Pattern Consistency:** Follows the same pattern as jvmMain and nativeMain implementations but using JS-specific APIs
