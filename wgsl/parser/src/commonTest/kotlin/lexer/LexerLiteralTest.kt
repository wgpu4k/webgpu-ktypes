package io.ygdrasil.wgsl.lexer

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class LexerLiteralTest : FunSpec({
    context("WGSL numeric literals") {
        test("Decimal and hexadecimal integers") {
            val source = "42 0x2a 42u 0x2au"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.INT_LITERAL, TokenKind.INT_LITERAL,
                TokenKind.UINT_LITERAL, TokenKind.UINT_LITERAL
            )
        }

        test("Integers with size suffixes") {
            val source = "42i 42u 42I 42U 0x2aI 0x2aU 0x2a"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.INT_LITERAL, TokenKind.UINT_LITERAL,
                TokenKind.INT_LITERAL, TokenKind.UINT_LITERAL,
                TokenKind.INT_LITERAL, TokenKind.UINT_LITERAL,
                TokenKind.INT_LITERAL
            )
        }

        test("Floating point numbers") {
            val source = "3.14 1.0e-5 .5 0x1.0p1 3.14f 3.14h 0x1p+0 0x1.8p+1 0x.1p1 3.14F 3.14H 0x1p0H 0x1.8p+1F"
            val tokens = tokenizeSignificant(source)
            tokens.forEach { it.kind shouldBe TokenKind.FLOAT_LITERAL }
            tokens.last().literal shouldBe "0x1.8p+1F"
        }

        test("Digit separators") {
            val source = "1_234 0x1_234 3.141_592 42_ identifier"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.INT_LITERAL, TokenKind.INT_LITERAL, TokenKind.FLOAT_LITERAL,
                TokenKind.INT_LITERAL, TokenKind.UNDERSCORE, TokenKind.IDENTIFIER
            )
            tokens[0].literal shouldBe "1_234"
            tokens[1].literal shouldBe "0x1_234"
            tokens[2].literal shouldBe "3.141_592"
            tokens[3].literal shouldBe "42"
        }

        test("Negative sign treated as separate operator") {
            val tokens = tokenizeSignificant("-42")
            tokens shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.MINUS
            tokens[1].kind shouldBe TokenKind.INT_LITERAL
        }
    }

    context("WGSL boolean literals") {
        test("True and false values") {
            val source = "true false"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.TRUE, TokenKind.FALSE
            )
        }
    }

    context("WGSL string literals") {
        test("Strings with escapes") {
            val source = "\"\" \"hello\" \"he\\\"llo\" \"he\\\\llo\" \"\\n\\r\\t\" \"\\u{1F600}\" \"\\x41\""
            val tokens = tokenizeSignificant(source)
            tokens.forEach { it.kind shouldBe TokenKind.STRING_LITERAL }
            
            tokens[0].literal shouldBe ""
            tokens[1].literal shouldBe "hello"
            tokens[2].literal shouldBe "he\"llo"
            tokens[3].literal shouldBe "he\\llo"
            tokens[4].literal shouldBe "\n\r\t"
            tokens[5].literal shouldBe "\uD83D\uDE00" // 😀
            tokens[6].literal shouldBe "A"
        }

        test("Strings with invalid escapes") {
            // In WGSL standard, invalid escapes should be handled gracefully or reported.
            // Let's document current behavior: \z escapes to z, unicode with invalid hex or out of bounds.
            val source = "\"he\\zllo\" \"\\u{}\" \"\\u{110000}\" \"\\x1G\""
            val tokens = tokenizeSignificant(source)
            tokens.forEach { it.kind shouldBe TokenKind.STRING_LITERAL }
            tokens[0].literal shouldBe "hezllo"
            tokens[1].literal shouldBe ""
            tokens[2].literal shouldBe ""
            tokens[3].literal shouldBe ""
        }

        test("Unterminated strings") {
            // A string not terminated by closing quote at the end of line or file.
            val tokensAtEof = tokenizeSignificant("\"unterminated")
            tokensAtEof shouldHaveSize 1
            tokensAtEof[0].kind shouldBe TokenKind.STRING_LITERAL
            tokensAtEof[0].literal shouldBe "unterminated"

            val tokensAtEol = tokenizeSignificant("\"line1\n\"line2\"")
            tokensAtEol shouldHaveSize 2
            tokensAtEol[0].kind shouldBe TokenKind.STRING_LITERAL
            tokensAtEol[0].literal shouldBe "line1"
            tokensAtEol[1].kind shouldBe TokenKind.STRING_LITERAL
            tokensAtEol[1].literal shouldBe "line2"
        }

        test("Single quotes faux-ami (WGSL only supports double quotes)") {
            // Single quotes 'hello' is not a valid string in WGSL, it must generate UNKNOWN for the quotes
            val tokens = tokenizeSignificant("'hello'")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.UNKNOWN, TokenKind.IDENTIFIER, TokenKind.UNKNOWN
            )
            tokens[1].literal shouldBe "hello"
        }
    }
})
