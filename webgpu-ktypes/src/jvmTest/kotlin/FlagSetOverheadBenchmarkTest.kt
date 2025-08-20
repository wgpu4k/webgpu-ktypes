package io.ygdrasil.webgpu

import kotlin.test.Test
import kotlin.test.assertEquals

class FlagSetOverheadBenchmarkTest {

    private inline fun measureNanos(block: () -> Unit): Long {
        val start = System.nanoTime()
        block()
        return System.nanoTime() - start
    }

    private fun prettyMillis(nanos: Long): String = "%.3f".format(nanos / 1_000_000.0)

    @Test
    fun toFlagULong_overhead_smallSets() {
        // Prepare several representative sets
        val empty: Set<FlagEnumeration> = emptySet()
        val single: Set<FlagEnumeration> = setOf(GPUBufferUsage.CopySrc)
        val triple: Set<FlagEnumeration> = setOf(
            GPUBufferUsage.CopySrc,
            GPUBufferUsage.CopyDst,
            GPUBufferUsage.Vertex
        )

        // Expected values for correctness
        val expectedEmpty = 0uL
        val expectedSingle = GPUBufferUsage.CopySrc.value
        val expectedTriple = GPUBufferUsage.CopySrc.value or GPUBufferUsage.CopyDst.value or GPUBufferUsage.Vertex.value

        assertEquals(expectedEmpty, empty.toFlagULong())
        assertEquals(expectedSingle, single.toFlagULong())
        assertEquals(expectedTriple, triple.toFlagULong())

        // Warm-up (let JIT kick in on JVM)
        repeat(100_000) {
            empty.toFlagULong()
            single.toFlagULong()
            triple.toFlagULong()
        }

        val iterations = 2_000_000

        // Measure Set<FlagEnumeration>.toFlagULong
        val setNanos = measureNanos {
            var sink = 0uL
            repeat(iterations) {
                // mix across sizes to avoid constant folding
                sink = sink xor empty.toFlagULong()
                sink = sink xor single.toFlagULong()
                sink = sink xor triple.toFlagULong()
            }
            if (sink == 42uL) println("[DEBUG_LOG] ignore: $sink")
        }

        // Measure direct bitmask ORs
        val directNanos = measureNanos {
            var sink = 0uL
            repeat(iterations) {
                val a = 0uL
                val b = GPUBufferUsage.CopySrc.value
                val c = GPUBufferUsage.CopySrc.value or GPUBufferUsage.CopyDst.value or GPUBufferUsage.Vertex.value
                sink = sink xor a
                sink = sink xor b
                sink = sink xor c
            }
            if (sink == 42uL) println("[DEBUG_LOG] ignore: $sink")
        }

        println("[DEBUG_LOG] toFlagULong: set-based=${prettyMillis(setNanos)} ms, direct=${prettyMillis(directNanos)} ms, ratio=${"%.2f".format(setNanos.toDouble()/directNanos)}x")
    }

    @Test
    fun toFlagInt_overhead_smallSets() {
        val triple: Set<FlagEnumeration> = setOf(
            GPUBufferUsage.CopySrc,
            GPUBufferUsage.CopyDst,
            GPUBufferUsage.Vertex
        )
        val expectedTripleInt = (GPUBufferUsage.CopySrc.value or GPUBufferUsage.CopyDst.value or GPUBufferUsage.Vertex.value).toInt()
        assertEquals(expectedTripleInt, triple.toFlagInt())

        // Warm-up
        repeat(100_000) { triple.toFlagInt() }

        val iterations = 2_000_000

        val setNanos = measureNanos {
            var sink = 0
            repeat(iterations) {
                sink = sink xor triple.toFlagInt()
            }
            if (sink == 42) println("[DEBUG_LOG] ignore: $sink")
        }

        val directNanos = measureNanos {
            var sink = 0
            val mask = expectedTripleInt
            repeat(iterations) {
                sink = sink xor mask
            }
            if (sink == 42) println("[DEBUG_LOG] ignore: $sink")
        }

        println("[DEBUG_LOG] toFlagInt: set-based=${prettyMillis(setNanos)} ms, direct=${prettyMillis(directNanos)} ms, ratio=${"%.2f".format(setNanos.toDouble()/directNanos)}x")
    }
}
