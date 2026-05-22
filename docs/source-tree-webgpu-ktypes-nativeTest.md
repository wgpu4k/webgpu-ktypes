# webgpu-ktypes - nativeTest Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/nativeTest/kotlin/
**Scan Level:** Exhaustive
**Files:** 1
**Total Lines:** 229

## Overview

This directory contains **Kotlin/Native-specific unit tests** for the WebGPU ArrayBuffer implementation. It uses Kotlin/Native interoperability with C pointers and Kotest testing framework.

## Directory Structure

```
webgpu-ktypes/src/nativeTest/kotlin/
└── OpaquePointerArrayBufferTest.kt     (229 lines) - Native ArrayBuffer tests
```

## Critical Files

### `OpaquePointerArrayBufferTest.kt`

**Purpose:** Comprehensive test suite for Kotlin/Native platform ArrayBuffer implementation using C interop.

**Key Technologies:**
- **Kotest** - Kotlin testing framework (FunSpec style)
- **Kotlin/Native C Interop** (`kotlinx.cinterop.*`):
  - `nativeHeap` - Native memory heap allocator
  - `ByteVar`, `IntVar`, `FloatVar`, `DoubleVar` - C pointer types
  - `reinterpret()` - Type punning for pointers
  - `@OptIn(ExperimentalForeignApi::class)` - Opt-in for experimental C interop API

**Test Framework:**
- Style: `FunSpec` (Kotest specification style)
- Assertions: `shouldBe` matcher
- Structure: Flat test cases (not nested in contexts)

**Test Coverage:**

#### ArrayBuffer.wrap() Tests

1. **Create buffer from existing memory pointer**
   - Allocates: 5 bytes via `nativeHeap.allocArray<ByteVar>(5)`
   - Fills: With byte values [10, 20, 30, 40, 50]
   - Tests: Wrapping and reading back via `toByteArray()`
   - Cleanup: Explicit `nativeHeap.free(ptr)`

2. **Modifications in wrapped buffer are visible**
   - Allocates: 5 bytes with initial values [1, 2, 3, 4, 5]
   - Modifies: Sets byte 0 to 99, byte 2 to 88 via ArrayBuffer
   - Verifies: Changes are visible when reading back
   - Cleanup: Explicit `nativeHeap.free(ptr)`

3. **Wrapping IntArray memory**
   - Allocates: 4 integers via `nativeHeap.allocArray<IntVar>(4)`
   - Fills: With int values [100, 200, 300, 400]
   - Tests: Wrapping and `toIntArray()` conversion
   - Cleanup: Explicit `nativeHeap.free(ptr)`

4. **Wrapping FloatArray memory**
   - Allocates: 4 floats via `nativeHeap.allocArray<FloatVar>(4)`
   - Fills: With float values [1.5f, 2.5f, 3.5f, 4.5f]
   - Tests: Wrapping and `toFloatArray()` conversion
   - Cleanup: Explicit `nativeHeap.free(ptr)`

5. **Wrapping DoubleArray memory**
   - Allocates: 4 doubles via `nativeHeap.allocArray<DoubleVar>(4)`
   - Fills: With double values [1.5, 2.5, 3.5, 4.5]
   - Tests: Wrapping and `toDoubleArray()` conversion
   - Cleanup: Explicit `nativeHeap.free(ptr)`

6. **Partial buffer wrapping**
   - Allocates: 10 bytes filled with [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
   - Wraps: Only first 5 bytes
   - Verifies: Wrapped buffer has size 5u and contains [1, 2, 3, 4, 5]
   - Cleanup: Explicit `nativeHeap.free(ptr)`

7. **Read and write all data types**
   - Allocates: 64 bytes buffer
   - Writes: All data types at various offsets:
     - Byte at offset 0: 42
     - Short at offset 4: 1000
     - Int at offset 8: 100000
     - Float at offset 12: 3.14f
     - Double at offset 16: 2.71828
     - UByte at offset 24: 255u
     - UShort at offset 28: 65535u
     - UInt at offset 32: 4294967295u
   - Verifies: All values can be read back correctly
   - Cleanup: Explicit `nativeHeap.free(ptr)`

#### ArrayBuffer.allocate() Tests

8. **Create zero-initialized buffer**
   - Allocates: 10 bytes via `ArrayBuffer.allocate(10u)`
   - Verifies: Size is 10u and all bytes are zero

9. **Write and read values**
   - Allocates: 32 bytes buffer
   - Writes: All data types at proper offsets:
     - Byte at 0: 42
     - Short at 4: 1000
     - Int at 8: 100000
     - Float at 12: 3.14f
     - Double at 16: 2.71828
     - UByte at 24: 255u
     - UShort at 26: 65535u
     - UInt at 28: 4294967295u
   - Verifies: All values can be read back correctly

10. **Convert to typed arrays**
    - Allocates: 16 bytes buffer
    - Fills: With int values [100, 200, 300, 400] at offsets 0, 4, 8, 12
    - Tests: `toIntArray()` returns [100, 200, 300, 400]

11. **Memory is automatically managed**
    - Allocates: 100 buffers of 1024 bytes each in a loop
    - Writes/reads: Each buffer at offset 0
    - Verifies: No memory leaks (test passes if doesn't crash)
    - Note: Uses implicit cleanup (no explicit free)

**Test Characteristics:**
- All tests use native memory allocation via `nativeHeap`
- Explicit memory cleanup with `nativeHeap.free()` for most tests
- Test 11 relies on automatic memory management (GC)
- Tests both allocation (`allocate()`) and wrapping (`wrap()`) patterns
- Tests all supported primitive types (signed and unsigned)
- Tests proper offset alignment for each data type
- Tests max values for unsigned types

**Coverage Analysis:**
- **Total Tests:** 11
- **Allocation Patterns:** Native heap allocation, ArrayBuffer.allocate()
- **Types Tested:** All primitive numeric types
- **Operations Tested:** Read, Write, Array conversion, Wrapping
- **Memory Models:** Native C pointers, Opaque pointers
- **Edge Cases:** Partial wrapping, max values, automatic management
- **Missing Coverage:** None significant for ArrayBuffer functionality

## Integration Points

- **Dependencies:** Kotest core, Kotlin stdlib, kotlinx.cinterop
- **Platform Target:** Kotlin/Native (macOS, iOS, Linux, Windows, etc.)
- **Multiplatform:** Native-specific tests complementing commonTest
- **Related Modules:** Tests the Native implementation (expect declarations in commonMain)

## Key Observations

1. **Memory Management:** Mixes explicit cleanup (nativeHeap.free) and automatic management (GC)
2. **C Interop:** Uses experimental Foreign API for native memory access
3. **Type Safety:** Uses Kotlin type system with C pointer types
4. **Comprehensive Coverage:** Tests all ArrayBuffer operations thoroughly
5. **Platform Specialization:** Native-specific implementation for Kotlin/Native targets
6. **Test Quality:** Clear test names, proper setup/teardown, good edge case coverage
