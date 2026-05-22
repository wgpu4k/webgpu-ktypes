# webgpu-ktypes - jvmMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/jvmMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 2
**Total Lines:** 490

## Overview

This directory contains **JVM-specific implementations** of the WebGPU ArrayBuffer abstraction. It uses Java 21+ Foreign Function & Memory API (JEP 442) for efficient off-heap memory management.

## Directory Structure

```
webgpu-ktypes/src/jvmMain/kotlin/
├── ArrayBuffer.jvm.kt      (356 lines) - JVM ArrayBuffer interface implementation
└── JvmArrayBuffer.kt     (134 lines) - Concrete JVM ArrayBuffer implementation
```

## Critical Files

### `ArrayBuffer.jvm.kt`

**Purpose:** JVM platform implementation of the `ArrayBuffer` sealed interface.

**Key Technologies:**
- `java.lang.foreign.Arena` - Memory allocation arena
- `java.lang.foreign.MemorySegment` - Off-heap memory segment
- Java 21+ Foreign Function & Memory API (JEP 442)

**Contains:**
- `actual sealed interface ArrayBuffer` - JVM-specific interface implementation
- All `actual` method declarations (read, write, conversion methods)
- `actual companion object` with factory methods:
  - `allocate(sizeInBytes: ULong)` - Creates buffer using `Arena.ofAuto().allocate()`
  - `wrap(segment: MemorySegment)` - Wraps existing MemorySegment
  - `of(array: ByteArray/ShortArray/IntArray/FloatArray/DoubleArray/UByteArray/UShortArray/UIntArray)` - Creates from typed arrays

**Pattern:** Each method delegates to `JvmArrayBuffer` implementation

### `JvmArrayBuffer.kt`

**Purpose:** Concrete JVM implementation of ArrayBuffer using MemorySegment.

**Key Technologies:**
- `@JvmInline` value class - Zero-cost abstraction, no runtime overhead
- `MemorySegment` - Backing off-heap memory
- `ValueLayout` - Memory layout descriptors (JAVA_BYTE, JAVA_SHORT_UNALIGNED, etc.)

**Class Definition:**
```kotlin
@JvmInline
value class JvmArrayBuffer internal constructor(val buffer: MemorySegment): ArrayBuffer
```

**Implementation Strategy:**
- **Read methods:** Use `MemorySegment.toArray()` for bulk conversion, `MemorySegment.get()` for indexed access
- **Write methods:** Use `MemorySegment.copy()` for bulk writes, `MemorySegment.set()` for indexed writes
- **Unsigned types:** Convert signed to unsigned via extension methods (`toUByte()`, `toUShort()`, `toUInt()`)
- **Array writes:** Use `MemorySegment.copy()` with appropriate ValueLayout

**ValueLayout Usage:**
- `JAVA_BYTE` - 8-bit signed/unsigned
- `JAVA_SHORT_UNALIGNED` - 16-bit signed/unsigned (unaligned access)
- `JAVA_INT_UNALIGNED` - 32-bit signed/unsigned (unaligned access)
- `JAVA_FLOAT_UNALIGNED` - 32-bit float (unaligned access)
- `JAVA_DOUBLE_UNALIGNED` - 64-bit double (unaligned access)

## Technical Details

### Memory Management
- Uses `Arena.ofAuto()` for automatic memory management
- Memory is freed when Arena is closed (automatic with `ofAuto`)
- No manual memory management required

### Performance Characteristics
- `@JvmInline` - Zero object allocation overhead for the wrapper
- `MemorySegment` - Direct off-heap access
- Unaligned layouts - Better performance on modern hardware

### Platform Requirements
- **Java Version:** 21+ (for Foreign Function & Memory API)
- **Module:** `java.base` (no additional dependencies)

## Relationship to commonMain

These files provide the **JVM platform implementation** for:
- `webgpu-ktypes/src/commonMain/kotlin/ArrayBuffer.kt` (expect interface)

The Kotlin Multiplatform compiler uses these `actual` declarations when compiling for JVM targets.

## Code Quality

- **Style:** Clean, idiomatic Kotlin
- **Documentation:** Comprehensive KDoc comments
- **Error Handling:** Uses Kotlin's type system (ULong for sizes)
- **Performance:** Optimized for JVM with inline classes and direct memory access

---

_Generated using BMAD Method `document-project` workflow - Batch 4: webgpu-ktypes/src/jvmMain/kotlin/_
