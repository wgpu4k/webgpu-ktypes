import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.webgpu.ArrayBuffer
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class JvmArrayBufferTest: FreeSpec({

    "ArrayBuffer.wrap with MemorySegment" - {

        "should wrap native memory segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(16L)

                val buffer = ArrayBuffer.wrap(segment)

                buffer.size shouldBe 16u
            }
        }

        "should read and write bytes to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(10L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setByte(0, 42)
                buffer.setByte(5, -10)

                buffer.getByte(0) shouldBe 42
                buffer.getByte(5) shouldBe -10
            }
        }

        "should read and write shorts to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(10L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setShort(0, 1000)
                buffer.setShort(4, -2000)

                buffer.getShort(0) shouldBe 1000
                buffer.getShort(4) shouldBe -2000
            }
        }

        "should read and write ints to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(16L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setInt(0, 123456)
                buffer.setInt(8, -987654)

                buffer.getInt(0) shouldBe 123456
                buffer.getInt(8) shouldBe -987654
            }
        }

        "should read and write floats to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(16L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setFloat(0, 3.14f)
                buffer.setFloat(8, -2.71f)

                buffer.getFloat(0) shouldBe 3.14f
                buffer.getFloat(8) shouldBe -2.71f
            }
        }

        "should read and write doubles to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(32L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setDouble(0, 3.141592)
                buffer.setDouble(16, -2.718281)

                buffer.getDouble(0) shouldBe 3.141592
                buffer.getDouble(16) shouldBe -2.718281
            }
        }

        "should read and write unsigned bytes to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(10L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setUByte(0, 200u)
                buffer.setUByte(5, 255u)

                buffer.getUByte(0) shouldBe 200u
                buffer.getUByte(5) shouldBe 255u
            }
        }

        "should read and write unsigned shorts to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(10L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setUShort(0, 50000u)
                buffer.setUShort(4, 65535u)

                buffer.getUShort(0) shouldBe 50000u
                buffer.getUShort(4) shouldBe 65535u
            }
        }

        "should read and write unsigned ints to wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(16L)
                val buffer = ArrayBuffer.wrap(segment)

                buffer.setUInt(0, 3000000000u)
                buffer.setUInt(8, 4294967295u)

                buffer.getUInt(0) shouldBe 3000000000u
                buffer.getUInt(8) shouldBe 4294967295u
            }
        }

        "should convert wrapped segment to byte array" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(5L)
                segment.set(ValueLayout.JAVA_BYTE, 0L, 1.toByte())
                segment.set(ValueLayout.JAVA_BYTE, 1L, 2.toByte())
                segment.set(ValueLayout.JAVA_BYTE, 2L, 3.toByte())
                segment.set(ValueLayout.JAVA_BYTE, 3L, 4.toByte())
                segment.set(ValueLayout.JAVA_BYTE, 4L, 5.toByte())

                val buffer = ArrayBuffer.wrap(segment)
                val array = buffer.toByteArray()

                array shouldBe byteArrayOf(1, 2, 3, 4, 5)
            }
        }

        "should convert wrapped segment to int array" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(12L)
                segment.set(ValueLayout.JAVA_INT_UNALIGNED, 0L, 100)
                segment.set(ValueLayout.JAVA_INT_UNALIGNED, 4L, 200)
                segment.set(ValueLayout.JAVA_INT_UNALIGNED, 8L, 300)

                val buffer = ArrayBuffer.wrap(segment)
                val array = buffer.toIntArray()

                array shouldBe intArrayOf(100, 200, 300)
            }
        }

        "should wrap segment from existing array" {
            val original = byteArrayOf(10, 20, 30, 40, 50)
            val segment = MemorySegment.ofArray(original)

            val buffer = ArrayBuffer.wrap(segment)

            buffer.size shouldBe 5u
            buffer.getByte(0) shouldBe 10
            buffer.getByte(2) shouldBe 30
            buffer.getByte(4) shouldBe 50
        }

        "should reflect changes in wrapped segment" {
            Arena.ofConfined().use { arena ->
                val segment = arena.allocate(4L)
                segment.set(ValueLayout.JAVA_INT_UNALIGNED, 0L, 999)

                val buffer = ArrayBuffer.wrap(segment)

                buffer.getInt(0) shouldBe 999

                // Modify via buffer
                buffer.setInt(0, 111)

                // Verify change is reflected in segment
                segment.get(ValueLayout.JAVA_INT_UNALIGNED, 0L) shouldBe 111
            }
        }
    }
})