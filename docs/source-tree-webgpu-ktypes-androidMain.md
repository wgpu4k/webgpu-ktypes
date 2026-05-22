# webgpu-ktypes - androidMain Source Tree Analysis

**Date:** 2026-05-19
**Batch:** webgpu-ktypes/src/androidMain/kotlin/
**Scan Level:** Exhaustive
**Files:** 2
**Total Lines:** 337 (ArrayBuffer.android.kt: 273 + AndroidArrayBuffer.kt: 164)

## Overview

This directory contains **Android-specific implementations** of the WebGPU ArrayBuffer abstraction. It uses Java NIO ByteBuffer for efficient binary data handling on Android platforms.

## Directory Structure

```
webgpu-ktypes/src/androidMain/kotlin/
├── ArrayBuffer.android.kt     (273 lines) - Android ArrayBuffer interface and companion
└── AndroidArrayBuffer.kt     (164 lines) - Android ArrayBuffer implementation
```

## Critical Files

### `ArrayBuffer.android.kt`

**Purpose:** Android platform declarations for the ArrayBuffer sealed interface and companion object.

**Key Technologies:**
- Java NIO: `java.nio.ByteBuffer` - Direct buffer for binary data
- Kotlin experimental: `@OptIn(ExperimentalUnsignedTypes::class)` - Unsigned type support

**Structure:**

#### Sealed Interface: `ArrayBuffer` (actual)

**Properties:**
- `actual val size: ULong` - Size of buffer in bytes (from ByteBuffer.capacity())

**Methods - Full Array Conversion:**
- `toByteArray(): ByteArray` - Converts entire buffer to byte array
- `toShortArray(): ShortArray` - Converts to short array (size must be multiple of 2)
- `toIntArray(): IntArray` - Converts to int array (size must be multiple of 4)
- `toFloatArray(): FloatArray` - Converts to float array (size must be multiple of 4)
- `toDoubleArray(): DoubleArray` - Converts to double array (size must be multiple of 8)
- `toUByteArray(): UByteArray` - Converts to unsigned byte array
- `toUShortArray(): UShortArray` - Converts to unsigned short array (size must be multiple of 2)
- `toUIntArray(): UIntArray` - Converts to unsigned int array (size must be multiple of 4)

**Methods - Indexed Read:**
- `getByte(offset: ULong): Byte` - Reads byte at offset
- `getShort(offset: ULong): Short` - Reads short at offset (aligned to 2 bytes)
- `getInt(offset: ULong): Int` - Reads int at offset (aligned to 4 bytes)
- `getFloat(offset: ULong): Float` - Reads float at offset (aligned to 4 bytes)
- `getDouble(offset: ULong): Double` - Reads double at offset (aligned to 8 bytes)
- `getUByte(offset: ULong): UByte` - Reads unsigned byte at offset
- `getUShort(offset: ULong): UShort` - Reads unsigned short at offset (aligned to 2 bytes)
- `getUInt(offset: ULong): UInt` - Reads unsigned int at offset (aligned to 4 bytes)

**Methods - Indexed Write:**
- `setByte(offset: ULong, value: Byte)` - Writes byte at offset
- `setShort(offset: ULong, value: Short)` - Writes short at offset (aligned to 2 bytes)
- `setInt(offset: ULong, value: Int)` - Writes int at offset (aligned to 4 bytes)
- `setFloat(offset: ULong, value: Float)` - Writes float at offset (aligned to 4 bytes)
- `setDouble(offset: ULong, value: Double)` - Writes double at offset (aligned to 8 bytes)
- `setUByte(offset: ULong, value: UByte)` - Writes unsigned byte at offset
- `setUShort(offset: ULong, value: UShort)` - Writes unsigned short at offset (aligned to 2 bytes)
- `setUInt(offset: ULong, value: UInt)` - Writes unsigned int at offset (aligned to 4 bytes)

**Methods - Array Write:**
- `setBytes(offset: ULong, array: ByteArray)` - Writes byte array at offset
- `setShorts(offset: ULong, array: ShortArray)` - Writes short array at offset (aligned to 2 bytes)
- `setInts(offset: ULong, array: IntArray)` - Writes int array at offset (aligned to 4 bytes)
- `setFloats(offset: ULong, array: FloatArray)` - Writes float array at offset (aligned to 4 bytes)
- `setDoubles(offset: ULong, array: DoubleArray)` - Writes double array at offset (aligned to 8 bytes)
- `setUBytes(offset: ULong, array: UByteArray)` - Writes unsigned byte array at offset
- `setUShorts(offset: ULong, array: UShortArray)` - Writes unsigned short array at offset (aligned to 2 bytes)
- `setUInts(offset: ULong, array: UIntArray)` - Writes unsigned int array at offset (aligned to 4 bytes)

**Companion Object:**

**Factory Methods:**
- `allocate(sizeInBytes: ULong): ArrayBuffer` - Creates new zero-initialized direct ByteBuffer
- `wrap(buffer: ByteBuffer): ArrayBuffer` - Wraps existing direct ByteBuffer (throws if not direct)

**From Array Methods:**
- `of(array: ByteArray): ArrayBuffer` - Creates from byte array
- `of(array: ShortArray): ArrayBuffer` - Creates from short array
- `of(array: IntArray): ArrayBuffer` - Creates from int array
- `of(array: FloatArray): ArrayBuffer` - Creates from float array
- `of(array: DoubleArray): ArrayBuffer` - Creates from double array
- `of(array: UByteArray): ArrayBuffer` - Creates from unsigned byte array
- `of(array: UShortArray): ArrayBuffer` - Creates from unsigned short array
- `of(array: UIntArray): ArrayBuffer` - Creates from unsigned int array

**Documentation:** Extensive KDoc comments for all methods and parameters

### `AndroidArrayBuffer.kt`

**Purpose:** Concrete Android implementation of ArrayBuffer using direct ByteBuffer.

**Key Technologies:**
- `@JvmInline value class` - Inline value class for zero-overhead wrapper
- `java.nio.ByteBuffer` - Direct buffer for binary data
- `ByteBuffer.asShortBuffer()`, `.asIntBuffer()`, `.asFloatBuffer()`, `.asDoubleBuffer()` - View buffers

**Class Declaration:**
```kotlin
@JvmInline
value class AndroidArrayBuffer internal constructor(val buffer: ByteBuffer): ArrayBuffer
```

**Validation:** Constructor throws `IllegalStateException` if ByteBuffer is not direct

**Size Property:** `buffer.capacity().toULong()`

**Implementation Strategy:**

**Array Conversion Methods:**
- Use `buffer.duplicate()` to avoid modifying position
- Use view buffers (asShortBuffer, asIntBuffer, etc.) for typed access
- Call `.get(array)` to read into pre-allocated arrays
- Unsigned array conversions delegate to signed counterparts with `.asUByteArray()`, `.asUShortArray()`, `.asUIntArray()`

**Indexed Read Methods:**
- Direct delegation to ByteBuffer methods: `get()`, `getShort()`, `getInt()`, `getFloat()`, `getDouble()`
- Unsigned methods delegate to signed methods with type conversion: `getByte().toUByte()`, `getShort().toUShort()`, `getInt().toUInt()`

**Indexed Write Methods:**
- Direct delegation to ByteBuffer methods: `put()`, `putShort()`, `putInt()`, `putFloat()`, `putDouble()`
- Unsigned methods delegate to signed methods: `setByte(offset, value.toByte())`, etc.

**Array Write Methods:**
- Create duplicate buffer to preserve position
- Set position using `duplicate.position(offset.toInt())`
- Use view buffers for typed writes
- Call `.put(array)` to write entire array

## Implementation Characteristics

**Common Patterns:**
1. All methods are `override` implementations of ArrayBuffer interface
2. Uses `buffer.duplicate()` extensively to avoid position changes
3. Delegates to ByteBuffer methods for actual operations
4. Unsigned types are handled via conversion from signed types
5. Consistent parameter naming: `offset` for position, `value` or `array` for data

**Memory Model:**
- Uses direct ByteBuffer (allocateDirect) for off-heap memory
- ByteBuffer provides native byte order access
- View buffers allow typed access without copying

**Performance:**
- Inline value class minimizes allocation overhead
- Direct buffer access avoids copying
- Duplicate buffers for isolation without allocation

## Integration Points

- **Dependencies:** Java NIO (ByteBuffer, ShortBuffer, IntBuffer, FloatBuffer, DoubleBuffer)
- **Platform Target:** Android (uses Java standard library available on Android)
- **Multiplatform:** Provides `actual` implementations for commonMain `expect` declarations
- **Related Modules:** Works with commonMain ArrayBuffer interface, other platform modules

## Key Observations

1. **Platform Specialization:** Android-specific implementation using Java NIO
2. **Memory Efficiency:** Uses direct buffers to avoid JVM heap overhead
3. **Type Safety:** Comprehensive support for all primitive types (signed and unsigned)
4. **Inline Class:** Zero-overhead wrapper using Kotlin inline classes
5. **Comprehensive Documentation:** Extensive KDoc comments for all public APIs
6. **Consistency:** Follows same pattern as other platform implementations
7. **Validation:** Ensures direct ByteBuffer usage only (throws on non-direct)
8. **Error Handling:** No explicit error handling shown (relies on ByteBuffer exceptions)
