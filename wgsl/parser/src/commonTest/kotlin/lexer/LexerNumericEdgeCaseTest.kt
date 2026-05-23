package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactly

/**
 * Tests for numeric literal edge cases not covered by LexerLiteralTest.
 *
 * Covers:
 * - Decimal floats starting with a leading dot (.5)
 * - Hex floats with signed exponents (0x1.8p+1, 0x1.0p-4)
 * - Digit separators (1_000, 0x1_ABC)
 * - Abstract integers/floats (no suffix → abstract)
 * - Disambiguation between member access (foo.x) and float (.5)
 */
class LexerNumericEdgeCaseTest : FunSpec({

    context("Leading-dot float literals") {
        test("bare .5 is a FLOAT_LITERAL") {
            val tokens = tokenizeSignificant(".5")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe ".5"
        }

        test("leading-dot float with f suffix") {
            val tokens = tokenizeSignificant(".25f")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe ".25f"
        }

        test("dot followed by non-digit is DOT, not a float") {
            // .x is a member access, not a float
            val tokens = tokenizeSignificant(".x")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.DOT, TokenKind.IDENTIFIER
            )
        }
    }

    context("Hexadecimal float literals") {
        test("hex float without fractional part") {
            val tokens = tokenizeSignificant("0x1p0")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
        }

        test("hex float with fractional part and positive exponent") {
            val tokens = tokenizeSignificant("0x1.8p+1")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "0x1.8p+1"
        }

        test("hex float with negative exponent") {
            val tokens = tokenizeSignificant("0x1.0p-4")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "0x1.0p-4"
        }

        test("hex float with h suffix") {
            val tokens = tokenizeSignificant("0x1p0h")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
        }
    }

    context("Digit separators in numeric literals") {
        test("decimal integer with digit separator") {
            val tokens = tokenizeSignificant("1_000_000")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "1_000_000"
        }

        test("hex integer with digit separator") {
            val tokens = tokenizeSignificant("0xFF_AA_BB")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "0xFF_AA_BB"
        }

        test("float with digit separator in fraction") {
            val tokens = tokenizeSignificant("3.141_592")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.141_592"
        }

        test("trailing underscore is not part of the literal") {
            // "42_" → INT_LITERAL "42" + UNDERSCORE
            val tokens = tokenizeSignificant("42_")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.INT_LITERAL, TokenKind.UNDERSCORE
            )
            tokens[0].literal shouldBe "42"
        }
    }

    context("Disambiguation between member access and numeric start") {
        test("foo.x is IDENTIFIER DOT IDENTIFIER, not a float") {
            val tokens = tokenizeSignificant("foo.x")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.DOT, TokenKind.IDENTIFIER
            )
        }

        test("1.x is INT_LITERAL DOT IDENTIFIER for member access on number") {
            // This is unusual but the lexer must not absorb .x as part of 1.
            val tokens = tokenizeSignificant("1.x")
            // Expect INT_LITERAL, DOT, IDENTIFIER OR FLOAT_LITERAL – document the actual behaviour
            tokens.size shouldBe 3
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[1].kind shouldBe TokenKind.DOT
            tokens[2].kind shouldBe TokenKind.IDENTIFIER
        }
    }

    context("Abstract numeric values (no suffix)") {
        test("integer without suffix is INT_LITERAL (abstract int)") {
            val tokens = tokenizeSignificant("42")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "42"
        }

        test("float without suffix is FLOAT_LITERAL (abstract float)") {
            val tokens = tokenizeSignificant("1.0")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "1.0"
        }
    }

    context("Invalid or malformed numeric literals according to WGSL spec") {
        test("decimal integer starting with 0 followed by other digits (octal-like) is recognized as UNKNOWN or handled carefully") {
            // "052" should not be tokenized as a valid INT_LITERAL, or we should document if it produces UNKNOWN.
            // Under TDD, if we want strict reject, it should be TokenKind.UNKNOWN
            val tokens = tokenize("052")
            tokens.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN
        }

        test("0x or 0X isolated is UNKNOWN, not INT_LITERAL") {
            val tokens1 = tokenize("0x")
            tokens1.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN

            val tokens2 = tokenize("0X")
            tokens2.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN
        }

        test("0x followed by invalid character 0x_123 is UNKNOWN") {
            val tokens = tokenize("0x_123")
            tokens.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN
        }

        test("0x. isolated is UNKNOWN, not FLOAT_LITERAL") {
            val tokens = tokenize("0x.")
            tokens.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN
        }

        test("hex float with empty binary exponent 0x1p is UNKNOWN") {
            val tokens1 = tokenize("0x1p")
            tokens1.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN

            val tokens2 = tokenize("0x1.8p+")
            tokens2.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN
        }

        test("decimal float with empty exponent 1e is UNKNOWN") {
            val tokens1 = tokenize("1e")
            tokens1.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN

            val tokens2 = tokenize("1.0e+")
            tokens2.filter { !it.isEof }[0].kind shouldBe TokenKind.UNKNOWN
        }

        test("decimal float with complex exponent and sign and suffix") {
            val tokens1 = tokenizeSignificant("1.2e+3f")
            tokens1 shouldHaveSize 1
            tokens1[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens1[0].literal shouldBe "1.2e+3f"

            val tokens2 = tokenizeSignificant("0x1.8p-2h")
            tokens2 shouldHaveSize 1
            tokens2[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens2[0].literal shouldBe "0x1.8p-2h"
        }

        test("consecutive underscores 1__000 is tokenized as integer followed by identifier") {
            val tokens = tokenizeSignificant("1__000")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.INT_LITERAL, TokenKind.IDENTIFIER
            )
            tokens[0].literal shouldBe "1"
            tokens[1].literal shouldBe "__000"
        }
    }
})
