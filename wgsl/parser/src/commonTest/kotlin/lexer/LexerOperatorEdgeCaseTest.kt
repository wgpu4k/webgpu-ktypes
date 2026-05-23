package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactly

/**
 * Tests for WGSL operator tokenization edge cases.
 *
 * Covers multi-character operator disambiguation that the lexer must
 * handle via lookahead: <<= vs <<, >>= vs >>, <> as LEFT_ANGLE_RIGHT_ANGLE,
 * -> vs - and >, .* vs . and *, :: vs :, => vs = and >.
 */
class LexerOperatorEdgeCaseTest : FunSpec({

    context("Multi-character operator disambiguation") {
        test("<<= is LEFT_SHIFT_ASSIGN, not LEFT_SHIFT + ASSIGN") {
            val tokens = tokenizeSignificant("<<=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.LEFT_SHIFT_ASSIGN
        }

        test("<< is LEFT_SHIFT, not two LEFT_ANGLE") {
            val tokens = tokenizeSignificant("<<")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.LEFT_SHIFT
        }

        test(">>= is RIGHT_SHIFT_ASSIGN") {
            val tokens = tokenizeSignificant(">>=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.RIGHT_SHIFT_ASSIGN
        }

        test(">> is RIGHT_SHIFT") {
            val tokens = tokenizeSignificant(">>")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.RIGHT_SHIFT
        }

        test("<> is LEFT_ANGLE_RIGHT_ANGLE (empty template)") {
            val tokens = tokenizeSignificant("<>")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.LEFT_ANGLE_RIGHT_ANGLE
        }

        test("-> is ARROW, not MINUS + RIGHT_ANGLE") {
            val tokens = tokenizeSignificant("->")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.ARROW
        }

        test("=> is FAT_ARROW, not ASSIGN + RIGHT_ANGLE") {
            val tokens = tokenizeSignificant("=>")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FAT_ARROW
        }

        test(".* is DOT_STAR, not DOT + STAR") {
            val tokens = tokenizeSignificant(".*")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.DOT_STAR
        }

        test(":: is COLON_COLON, not two COLON") {
            val tokens = tokenizeSignificant("::")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.COLON_COLON
        }
    }

    context("Logical vs bitwise operator disambiguation") {
        test("&& is AND (logical), not two AMPERSAND") {
            val tokens = tokenizeSignificant("&&")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.AND
        }

        test("& is AMPERSAND (bitwise), not AND") {
            val tokens = tokenizeSignificant("&")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.AMPERSAND
        }

        test("|| is OR (logical), not two PIPE") {
            val tokens = tokenizeSignificant("||")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.OR
        }

        test("| is PIPE (bitwise), not OR") {
            val tokens = tokenizeSignificant("|")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.PIPE
        }
    }

    context("Compound assignment operators are distinct from simple ones") {
        test("+= is PLUS_ASSIGN, not PLUS + ASSIGN") {
            val tokens = tokenizeSignificant("+=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.PLUS_ASSIGN
        }

        test("-= is MINUS_ASSIGN") {
            val tokens = tokenizeSignificant("-=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.MINUS_ASSIGN
        }

        test("*= is STAR_ASSIGN") {
            val tokens = tokenizeSignificant("*=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.STAR_ASSIGN
        }

        test("/= is SLASH_ASSIGN") {
            val tokens = tokenizeSignificant("/=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.SLASH_ASSIGN
        }

        test("^= is XOR_ASSIGN") {
            val tokens = tokenizeSignificant("^=")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.XOR_ASSIGN
        }
    }

    context("Increment and decrement are not arithmetic") {
        test("++ is INCREMENT, not PLUS + PLUS") {
            val tokens = tokenizeSignificant("++")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.INCREMENT
        }

        test("-- is DECREMENT, not MINUS + MINUS") {
            val tokens = tokenizeSignificant("--")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.DECREMENT
        }
    }

    context("Developer expression compaction (no whitespace between operators)") {
        test("compact expression a+b is tokenized as ID, PLUS, ID") {
            val tokens = tokenizeSignificant("a+b")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.PLUS, TokenKind.IDENTIFIER
            )
            tokens[0].literal shouldBe "a"
            tokens[2].literal shouldBe "b"
        }

        test("compact expression a++b is tokenized as ID, INCREMENT, ID") {
            val tokens = tokenizeSignificant("a++b")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.INCREMENT, TokenKind.IDENTIFIER
            )
            tokens[0].literal shouldBe "a"
            tokens[2].literal shouldBe "b"
        }

        test("compact expression a+++b uses maximal munch to produce ID, INCREMENT, PLUS, ID") {
            val tokens = tokenizeSignificant("a+++b")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.INCREMENT, TokenKind.PLUS, TokenKind.IDENTIFIER
            )
            tokens[0].literal shouldBe "a"
            tokens[3].literal shouldBe "b"
        }

        test("compact assignment with negative sign x=-1 is ID, ASSIGN, MINUS, INT") {
            val tokens = tokenizeSignificant("x=-1")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.ASSIGN, TokenKind.MINUS, TokenKind.INT_LITERAL
            )
            tokens[0].literal shouldBe "x"
            tokens[3].literal shouldBe "1"
        }
    }
})
