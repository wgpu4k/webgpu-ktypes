@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.webgpu.ArrayBuffer
import kotlinx.cinterop.*

/**
 * Tests for ArrayBuffer.of() method which is native-specific.
 * This method allows wrapping an existing native pointer without copying data.
 */
class OpaquePointerArrayBufferTest : FreeSpec({

    "ArrayBuffer.of() - create buffer from existing memory pointer" {
        // Given - allocate native memory and fill it with data
        val size = 5
        val ptr = nativeHeap.allocArray<ByteVar>(size)
        val sourceArray = byteArrayOf(10, 20, 30, 40, 50)
        for (i in 0 until size) {
            ptr[i] = sourceArray[i]
        }

        // When - create a buffer using of() that wraps the memory
        val wrappedBuffer = ArrayBuffer.of(ptr.reinterpret(), size.toULong())

        // Then - the wrapped buffer should have the same data
        wrappedBuffer.toByteArray() shouldBe sourceArray

        // Cleanup
        nativeHeap.free(ptr)
    }

    "ArrayBuffer.of() - modifications in wrapped buffer are visible" {
        // Given - allocate native memory
        val size = 5
        val ptr = nativeHeap.allocArray<ByteVar>(size)
        val sourceArray = byteArrayOf(1, 2, 3, 4, 5)
        for (i in 0 until size) {
            ptr[i] = sourceArray[i]
        }

        // When - create a wrapped buffer and modify it
        val wrappedBuffer = ArrayBuffer.of(ptr.reinterpret(), size.toULong())
        wrappedBuffer.setByte(0, 99)
        wrappedBuffer.setByte(2, 88)

        // Then - modifications should be visible in the wrapped buffer
        wrappedBuffer.getByte(0) shouldBe 99
        wrappedBuffer.getByte(2) shouldBe 88

        // Cleanup
        nativeHeap.free(ptr)
    }

    "ArrayBuffer.of() - wrapping IntArray memory" {
        // Given - allocate int array memory
        val size = 4
        val ptr = nativeHeap.allocArray<IntVar>(size)
        val sourceInts = intArrayOf(100, 200, 300, 400)
        for (i in 0 until size) {
            ptr[i] = sourceInts[i]
        }

        // When - wrap it using of()
        val wrappedBuffer = ArrayBuffer.of(
            ptr.reinterpret(),
            (size * Int.SIZE_BYTES).toULong()
        )

        // Then - should be able to read the same int values
        wrappedBuffer.toIntArray() shouldBe sourceInts

        // Cleanup
        nativeHeap.free(ptr)
    }

    "ArrayBuffer.of() - wrapping FloatArray memory" {
        // Given - allocate float array memory
        val size = 4
        val ptr = nativeHeap.allocArray<FloatVar>(size)
        val sourceFloats = floatArrayOf(1.5f, 2.5f, 3.5f, 4.5f)
        for (i in 0 until size) {
            ptr[i] = sourceFloats[i]
        }

        // When - wrap it using of()
        val wrappedBuffer = ArrayBuffer.of(
            ptr.reinterpret(),
            (size * Float.SIZE_BYTES).toULong()
        )

        // Then - should preserve float values
        wrappedBuffer.toFloatArray() shouldBe sourceFloats

        // Cleanup
        nativeHeap.free(ptr)
    }

    "ArrayBuffer.of() - wrapping DoubleArray memory" {
        // Given - allocate double array memory
        val size = 4
        val ptr = nativeHeap.allocArray<DoubleVar>(size)
        val sourceDoubles = doubleArrayOf(1.5, 2.5, 3.5, 4.5)
        for (i in 0 until size) {
            ptr[i] = sourceDoubles[i]
        }

        // When - wrap it using of()
        val wrappedBuffer = ArrayBuffer.of(
            ptr.reinterpret(),
            (size * Double.SIZE_BYTES).toULong()
        )

        // Then - should preserve double values
        wrappedBuffer.toDoubleArray() shouldBe sourceDoubles

        // Cleanup
        nativeHeap.free(ptr)
    }

    "ArrayBuffer.of() - partial buffer wrapping" {
        // Given - allocate 10 bytes
        val size = 10
        val ptr = nativeHeap.allocArray<ByteVar>(size)
        for (i in 0 until size) {
            ptr[i] = (i + 1).toByte()
        }

        // When - wrap only the first 5 bytes
        val partialBuffer = ArrayBuffer.of(ptr.reinterpret(), 5u)

        // Then - should only contain the first 5 bytes
        partialBuffer.size shouldBe 5u
        partialBuffer.toByteArray() shouldBe byteArrayOf(1, 2, 3, 4, 5)

        // Cleanup
        nativeHeap.free(ptr)
    }

    "ArrayBuffer.of() - read and write all data types" {
        // Given - allocate native memory large enough for all types
        val bufferSize = 64
        val ptr = nativeHeap.allocArray<ByteVar>(bufferSize)

        // When - wrap it and write various data types
        val wrappedBuffer = ArrayBuffer.of(ptr.reinterpret(), bufferSize.toULong())

        wrappedBuffer.setByte(0, 42)
        wrappedBuffer.setShort(4, 1000)
        wrappedBuffer.setInt(8, 100000)
        wrappedBuffer.setFloat(12, 3.14f)
        wrappedBuffer.setDouble(16, 2.71828)
        wrappedBuffer.setUByte(24, 255u)
        wrappedBuffer.setUShort(28, 65535u)
        wrappedBuffer.setUInt(32, 4294967295u)

        // Then - should be able to read all values back
        wrappedBuffer.getByte(0) shouldBe 42
        wrappedBuffer.getShort(4) shouldBe 1000
        wrappedBuffer.getInt(8) shouldBe 100000
        wrappedBuffer.getFloat(12) shouldBe 3.14f
        wrappedBuffer.getDouble(16) shouldBe 2.71828
        wrappedBuffer.getUByte(24) shouldBe 255u
        wrappedBuffer.getUShort(28) shouldBe 65535u
        wrappedBuffer.getUInt(32) shouldBe 4294967295u

        // Cleanup
        nativeHeap.free(ptr)
    }
})
