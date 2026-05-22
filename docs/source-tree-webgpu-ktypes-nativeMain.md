# webgpu-ktypes - nativeMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/nativeMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 2
**Total Lines:** 631

## Overview

This directory contains **Kotlin/Native-specific implementations** of the WebGPU ArrayBuffer abstraction. It uses Kotlin/Native's C interop capabilities for direct native memory manipulation.

## Directory Structure

```
webgpu-ktypes/src/nativeMain/kotlin/
├── ArrayBuffer.native.kt     (368 lines) - Native ArrayBuffer interface implementation
└── OpaquePointerArrayBuffer.kt (263 lines) - Concrete native buffer implementation
```

## Critical Files

### `ArrayBuffer.native.kt`

**Purpose:** Native platform implementation of the `ArrayBuffer` sealed interface.

**Key Technologies:**
- `kotlinx.cinterop.COpaquePointer` - Opaque pointer to native memory
- `kotlinx.cinterop.ExperimentalForeignApi` - C interop API

**Contains:**
- `actual sealed interface ArrayBuffer` - Native-specific interface implementation
- All `actual` method declarations (read, write, conversion methods)
- `actual companion object` with factory methods:
  - `allocate(sizeInBytes: ULong)` - Allocates using `OpaquePointerArrayBuffer(sizeInBytes)`
  - `wrap(pointer: COpaquePointer, size: ULong)` - Wraps existing native pointer
  - `of(array: ByteArray/ShortArray/IntArray/FloatArray/DoubleArray/UByteArray/UShortArray/UIntArray)` - Creates from typed arrays

**Pattern:** Delegates to `OpaquePointerArrayBuffer` implementation

### `OpaquePointerArrayBuffer.kt`

**Purpose:** Concrete Native implementation of ArrayBuffer using native memory pointers.

**Key Technologies:**
- `kotlinx.cinterop.COpaquePointer` - Native memory pointer
- `kotlinx.cinterop.CPointer<ByteVar>` - Typed pointer for byte access
- `kotlinx.cinterop.reinterpret<TypeVar>()` - Pointer type casting
- `kotlinx.cinterop.nativeHeap` - Native memory allocator
- `kotlinx.cinterop.memcpy` - C memory copy function
- `kotlinx.cinterop.usePinned` - Pin Kotlin arrays for native access
- `kotlinx.cinterop.addressOf` - Get array element address
- `kotlin.native.ref.createCleaner` - Automatic memory cleanup
- `platform.posix.memcpy` - POSIX memory copy

**Class Definition:**
```kotlin
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
class OpaquePointerArrayBuffer private constructor(
    val pointer: COpaquePointer,
    override val size: ULong,
    private val ownsMemory: Boolean = true
) : ArrayBuffer
```

**Memory Management:**
- **Owned buffers:** Allocated via `nativeHeap.allocArray<ByteVar>(size.toInt())`
- **Cleanup:** Uses `createCleaner` for automatic memory deallocation via `nativeHeap.free()`
- **Non-owned buffers:** Can wrap external pointers without taking ownership

**Implementation Strategy:**

#### Read Methods (Array Conversion)
- Uses `array.usePinned { pinned -> memcpy(pinned.addressOf(0), bytePtr, size.convert()) }`
- Validates alignment: `require(size % Type.SIZE_BYTES.toUInt() == 0uL)`
- For unsigned types: Converts signed to unsigned via extension methods

#### Read Methods (Indexed)
- Uses pointer indexing: `bytePtr[offset.toInt()]` for bytes
- Uses reinterpreted pointers: `pointer.reinterpret<ShortVar>()[index]` for other types
- Converts to unsigned types via `toUByte()`, `toUShort()`, `toUInt()`

#### Write Methods (Indexed)
- Direct assignment: `bytePtr[offset.toInt()] = value`
- For multi-byte types: Uses reinterpreted pointer indexing
- For unsigned types: Converts to signed before writing

#### Write Methods (Array)
- Uses `array.usePinned { pinned -> memcpy(destPtr, pinned.addressOf(0), size.convert()) }`
- Calculates destination pointer: `interpretCPointer<ByteVar>(bytePtr.rawValue + offset.toLong())`
- For unsigned types: Converts to signed arrays before writing

## Technical Details

### Memory Model
- **Backing:** Raw native memory via `COpaquePointer`
- **Allocation:** `nativeHeap.allocArray<ByteVar>(size)` - allocates on native heap
- **Deallocation:** `nativeHeap.free(ptr)` - automatic via cleaner
- **Alignment:** Natural alignment based on type sizes

### Performance Characteristics
- **Zero-copy:** Uses `memcpy` for bulk operations
- **Direct access:** Pointer indexing for individual element access
- **Pinned arrays:** Temporary pinning during copy operations
- **No boxing:** Primitive types used directly

### Platform Requirements
- **Kotlin/Native:** Required for C interop
- **Dependencies:** `kotlinx.cinterop` (included in Kotlin/Native)
- **Platform:** Any Kotlin/Native target (iOS, macOS, Linux, Windows, etc.)

## Relationship to commonMain

These files provide the **Native platform implementation** for:
- `webgpu-ktypes/src/commonMain/kotlin/ArrayBuffer.kt` (expect interface)

The Kotlin Multiplatform compiler uses these `actual` declarations when compiling for Native targets.

## Comparison with Other Platform Implementations

| Feature | Native Implementation | JVM Implementation | Web Implementation |
|---------|---------------------|-------------------|-------------------|
| Backing | `COpaquePointer` | `MemorySegment` | `js.buffer.ArrayBuffer` |
| Allocation | `nativeHeap.allocArray` | `Arena.ofAuto().allocate` | `new ArrayBuffer()` |
| Deallocation | `nativeHeap.free` (via cleaner) | Arena-based | JavaScript GC |
| Copy | `memcpy` | `MemorySegment.copy` | Typed array operations |
| Type Access | Pointer reinterpretation | `ValueLayout` | JS typed arrays |

## Code Quality

- **Style:** Clean Kotlin/Native code with proper C interop
- **Documentation:** Comprehensive KDoc comments
- **Safety:** Uses `usePinned` to prevent GC during native operations
- **Memory:** Automatic cleanup via `createCleaner`
- **Error Handling:** Validates alignment requirements
- **Performance:** Minimal overhead, direct native memory access

---

_Generated using BMAD Method `document-project` workflow - Batch 6: webgpu-ktypes/src/nativeMain/kotlin/_
