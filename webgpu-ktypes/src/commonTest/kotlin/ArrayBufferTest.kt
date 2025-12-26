@file:OptIn(ExperimentalUnsignedTypes::class)

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.webgpu.ArrayBuffer


class ArrayBufferTest : FreeSpec({
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

})