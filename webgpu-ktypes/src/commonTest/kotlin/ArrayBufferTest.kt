@file:OptIn(ExperimentalUnsignedTypes::class)

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.webgpu.ArrayBuffer


class ArrayBufferTest : FreeSpec({
    "allocate creates zero-initialized buffer" {
        // When
        val buffer = ArrayBuffer.allocate(16u)

        // Then
        buffer.size shouldBe 16u
        val bytes = buffer.toByteArray()
        bytes.all { it == 0.toByte() } shouldBe true
    }

    "allocate with different sizes" {
        // Given
        val sizes = listOf(1uL, 8uL, 64uL, 256uL, 1024uL)

        sizes.forEach { size ->
            // When
            val buffer = ArrayBuffer.allocate(size)

            // Then
            buffer.size shouldBe size
            buffer.toByteArray().size shouldBe size.toInt()
        }
    }

    "allocate buffer can be written to" {
        // Given
        val buffer = ArrayBuffer.allocate(16u)

        // When
        buffer.setByte(0, 42)
        buffer.setInt(4, 12345)
        buffer.setFloat(8, 3.50f)

        // Then
        buffer.getByte(0) shouldBe 42
        buffer.getInt(4) shouldBe 12345
        buffer.getFloat(8) shouldBe 3.50f
    }

    "allocate buffer supports all typed array conversions" {
        // Given
        val buffer = ArrayBuffer.allocate(32u)

        // When - write some data
        buffer.setInt(0, 100)
        buffer.setInt(4, 200)

        // Then - can convert to different typed arrays
        buffer.toByteArray().size shouldBe 32
        buffer.toIntArray().size shouldBe 8
        buffer.toFloatArray().size shouldBe 8
        buffer.toDoubleArray().size shouldBe 4
    }

    "ByteArray round-trip" {
        // Given
        val input = byteArrayOf(1, 2, 3, 4, 5, -1, -2, -3)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toByteArray()

        // Then
        output shouldBe input
    }

    "ShortArray round-trip" {
        // Given
        val input = shortArrayOf(100, 200, 300, -100, -200, -300)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toShortArray()

        // Then
        output shouldBe input
    }

    "IntArray round-trip" {
        // Given
        val input = intArrayOf(1000, 2000, 3000, -1000, -2000, -3000)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toIntArray()

        // Then
        output shouldBe input
    }

    "FloatArray round-trip" {
        // Given
        val input = floatArrayOf(1.5f, 2.5f, 3.5f, -1.5f, -2.5f, -3.5f)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toFloatArray()

        // Then
        output shouldBe input
    }

    "DoubleArray round-trip" {
        // Given
        val input = doubleArrayOf(1.5, 2.5, 3.5, -1.5, -2.5, -3.5)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toDoubleArray()

        // Then
        output shouldBe input
    }

    "UByteArray round-trip" {
        // Given
        val input = ubyteArrayOf(1u, 2u, 3u, 4u, 5u, 255u, 254u, 253u)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toUByteArray()

        // Then
        output shouldBe input
    }

    "UShortArray round-trip" {
        // Given
        val input = ushortArrayOf(100u, 200u, 300u, 65535u, 65534u, 65533u)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toUShortArray()

        // Then
        output shouldBe input
    }

    "UIntArray round-trip" {
        // Given
        val input = uintArrayOf(1000u, 2000u, 3000u, 4294967295u, 4294967294u, 4294967293u)

        // When
        val buffer = ArrayBuffer.from(input)
        val output = buffer.toUIntArray()

        // Then
        output shouldBe input
    }

    "Indexed read/write - Byte" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(10))

        // When
        buffer.setByte(0, 42)
        buffer.setByte(5, -42)

        // Then
        buffer.getByte(0) shouldBe 42
        buffer.getByte(5) shouldBe -42
    }

    "Indexed read/write - Short" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(20))

        // When
        buffer.setShort(0, 1000)
        buffer.setShort(8, -1000)

        // Then
        buffer.getShort(0) shouldBe 1000
        buffer.getShort(8) shouldBe -1000
    }

    "Indexed read/write - Int" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(20))

        // When
        buffer.setInt(0, 100000)
        buffer.setInt(8, -100000)

        // Then
        buffer.getInt(0) shouldBe 100000
        buffer.getInt(8) shouldBe -100000
    }

    "Indexed read/write - Float" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(20))

        // When
        buffer.setFloat(0, 3.5f)
        buffer.setFloat(8, -3.5f)

        // Then
        buffer.getFloat(0) shouldBe 3.5f
        buffer.getFloat(8) shouldBe -3.5f
    }

    "Indexed read/write - Double" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(32))

        // When
        buffer.setDouble(0, 3.14159)
        buffer.setDouble(16, -3.14159)

        // Then
        buffer.getDouble(0) shouldBe 3.14159
        buffer.getDouble(16) shouldBe -3.14159
    }

    "Indexed read/write - UByte" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(10))

        // When
        buffer.setUByte(0, 255u)
        buffer.setUByte(5, 128u)

        // Then
        buffer.getUByte(0) shouldBe 255u
        buffer.getUByte(5) shouldBe 128u
    }

    "Indexed read/write - UShort" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(20))

        // When
        buffer.setUShort(0, 65535u)
        buffer.setUShort(8, 32768u)

        // Then
        buffer.getUShort(0) shouldBe 65535u
        buffer.getUShort(8) shouldBe 32768u
    }

    "Indexed read/write - UInt" {
        // Given
        val buffer = ArrayBuffer.from(ByteArray(20))

        // When
        buffer.setUInt(0, 4294967295u)
        buffer.setUInt(8, 2147483648u)

        // Then
        buffer.getUInt(0) shouldBe 4294967295u
        buffer.getUInt(8) shouldBe 2147483648u
    }

    "Array write - ByteArray" {
        // Given
        val buffer = ArrayBuffer.allocate(20u)
        val data = byteArrayOf(1, 2, 3, 4, 5)

        // When
        buffer.setBytes(0, data)
        buffer.setBytes(10, byteArrayOf(-1, -2, -3))

        // Then
        buffer.getByte(0) shouldBe 1
        buffer.getByte(4) shouldBe 5
        buffer.getByte(10) shouldBe -1
        buffer.getByte(12) shouldBe -3
    }

    "Array write - ShortArray" {
        // Given
        val buffer = ArrayBuffer.allocate(40u)
        val data = shortArrayOf(100, 200, 300)

        // When
        buffer.setShorts(0, data)
        buffer.setShorts(20, shortArrayOf(-100, -200))

        // Then
        buffer.getShort(0) shouldBe 100
        buffer.getShort(4) shouldBe 300
        buffer.getShort(20) shouldBe -100
        buffer.getShort(22) shouldBe -200
    }

    "Array write - IntArray" {
        // Given
        val buffer = ArrayBuffer.allocate(40u)
        val data = intArrayOf(1000, 2000, 3000)

        // When
        buffer.setInts(0, data)
        buffer.setInts(20, intArrayOf(-1000, -2000))

        // Then
        buffer.getInt(0) shouldBe 1000
        buffer.getInt(8) shouldBe 3000
        buffer.getInt(20) shouldBe -1000
        buffer.getInt(24) shouldBe -2000
    }

    "Array write - FloatArray" {
        // Given
        val buffer = ArrayBuffer.allocate(40u)
        val data = floatArrayOf(1.5f, 2.5f, 3.5f)

        // When
        buffer.setFloats(0, data)
        buffer.setFloats(20, floatArrayOf(-1.5f, -2.5f))

        // Then
        buffer.getFloat(0) shouldBe 1.5f
        buffer.getFloat(8) shouldBe 3.5f
        buffer.getFloat(20) shouldBe -1.5f
        buffer.getFloat(24) shouldBe -2.5f
    }

    "Array write - DoubleArray" {
        // Given
        val buffer = ArrayBuffer.allocate(64u)
        val data = doubleArrayOf(1.5, 2.5, 3.5)

        // When
        buffer.setDoubles(0, data)
        buffer.setDoubles(32, doubleArrayOf(-1.5, -2.5))

        // Then
        buffer.getDouble(0) shouldBe 1.5
        buffer.getDouble(16) shouldBe 3.5
        buffer.getDouble(32) shouldBe -1.5
        buffer.getDouble(40) shouldBe -2.5
    }

    "Array write - UByteArray" {
        // Given
        val buffer = ArrayBuffer.allocate(20u)
        val data = ubyteArrayOf(1u, 2u, 3u, 4u, 5u)

        // When
        buffer.setUBytes(0, data)
        buffer.setUBytes(10, ubyteArrayOf(255u, 254u, 253u))

        // Then
        buffer.getUByte(0) shouldBe 1u
        buffer.getUByte(4) shouldBe 5u
        buffer.getUByte(10) shouldBe 255u
        buffer.getUByte(12) shouldBe 253u
    }

    "Array write - UShortArray" {
        // Given
        val buffer = ArrayBuffer.allocate(40u)
        val data = ushortArrayOf(100u, 200u, 300u)

        // When
        buffer.setUShorts(0, data)
        buffer.setUShorts(20, ushortArrayOf(65535u, 65534u))

        // Then
        buffer.getUShort(0) shouldBe 100u
        buffer.getUShort(4) shouldBe 300u
        buffer.getUShort(20) shouldBe 65535u
        buffer.getUShort(22) shouldBe 65534u
    }

    "Array write - UIntArray" {
        // Given
        val buffer = ArrayBuffer.allocate(40u)
        val data = uintArrayOf(1000u, 2000u, 3000u)

        // When
        buffer.setUInts(0, data)
        buffer.setUInts(20, uintArrayOf(4294967295u, 4294967294u))

        // Then
        buffer.getUInt(0) shouldBe 1000u
        buffer.getUInt(8) shouldBe 3000u
        buffer.getUInt(20) shouldBe 4294967295u
        buffer.getUInt(24) shouldBe 4294967294u
    }

    "Array write - complete buffer overwrite" {
        // Given
        val buffer = ArrayBuffer.allocate(16u)
        val data = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)

        // When
        buffer.setBytes(0, data)

        // Then
        buffer.toByteArray() shouldBe data
    }

    "Array write - partial buffer with verification" {
        // Given
        val buffer = ArrayBuffer.allocate(20u)
        val intData = intArrayOf(100, 200)

        // When
        buffer.setInts(4, intData)

        // Then
        buffer.getByte(0) shouldBe 0 // Should remain zero
        buffer.getInt(4) shouldBe 100
        buffer.getInt(8) shouldBe 200
        buffer.getByte(12) shouldBe 0 // Should remain zero
    }

})