package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactly

/**
 * Tests for lexer span accuracy (position tracking).
 *
 * The WGSL spec requires accurate source positions to produce useful
 * error messages. This test suite validates that the lexer correctly
 * tracks byte offsets across whitespace, newlines and multi-char tokens.
 */
class LexerSpanTest : FunSpec({

    context("Byte offset accuracy on a single line") {
        test("first token starts at offset 0") {
            val tokens = tokenizeSignificant("foo")
            tokens shouldHaveSize 1
            tokens[0].span.start shouldBe 0u
            tokens[0].span.end   shouldBe 3u
        }

        test("second token starts after whitespace") {
            val tokens = tokenizeSignificant("foo bar")
            tokens shouldHaveSize 2
            tokens[1].span.start shouldBe 4u
            tokens[1].span.end   shouldBe 7u
        }

        test("operator span covers all characters of multi-char token") {
            // <<= is 3 bytes
            val tokens = tokenizeSignificant("<<=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.LEFT_SHIFT_ASSIGN
            tokens[0].span.start shouldBe 0u
            tokens[0].span.end   shouldBe 3u
        }

        test("integer literal span is tight") {
            val tokens = tokenizeSignificant("  42u  ")
            tokens shouldHaveSize 1
            tokens[0].span.start shouldBe 2u
            tokens[0].span.end   shouldBe 5u
        }
    }

    context("Byte offset accuracy across newlines") {
        test("token on second line has correct offset") {
            // "foo\n" is 4 bytes, so "bar" starts at offset 4
            val tokens = tokenizeSignificant("foo\nbar")
            tokens shouldHaveSize 2
            tokens[1].span.start shouldBe 4u
            tokens[1].span.end   shouldBe 7u
        }

        test("token on third line accounts for all preceding bytes") {
            val source = "a\nb\nc"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 3
            tokens[2].span.start shouldBe 4u
            tokens[2].span.end   shouldBe 5u
        }

        test("span after multi-line comment is correct") {
            // "/* x */\n" = 8 bytes, so "id" starts at 8
            val source = "/* x */\nid"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].span.start shouldBe 8u
        }
    }
})
