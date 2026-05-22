package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

/**
 * Tests for WGSL memory layout keywords (packed, aligned).
 * These are used in struct member annotations to control memory layout.
 */
class MemoryLayoutTest : FunSpec({
    context("Memory layout keywords") {
        test("Packed keyword is recognized") {
            val source = "packed"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.PACKED
        }

        test("Aligned keyword is recognized") {
            val source = "aligned"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.ALIGNED
        }

        test("Packed and aligned in struct declaration context") {
            val source = "struct Data { @packed @aligned(16) value: i32 }"
            val tokens = tokenizeSignificant(source)
            // Verify both layout keywords are present
            val kinds = tokens.map { it.kind }
            kinds shouldBe listOf(
                TokenKind.STRUCT, TokenKind.IDENTIFIER, TokenKind.LEFT_BRACE,
                TokenKind.AT, TokenKind.PACKED,
                TokenKind.AT, TokenKind.ALIGNED, TokenKind.LEFT_PAREN, TokenKind.INT_LITERAL, TokenKind.RIGHT_PAREN,
                TokenKind.IDENTIFIER, TokenKind.COLON, TokenKind.I32,
                TokenKind.RIGHT_BRACE
            )
        }
    }
})
