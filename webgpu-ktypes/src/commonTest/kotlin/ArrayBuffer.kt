@file:OptIn(ExperimentalUnsignedTypes::class)

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe


class ArrayBufferTest : FreeSpec({
    "this is a place holder" {
        // Given
        var value: Boolean = false

        // When
        value = true

        value shouldBe true
    }
})