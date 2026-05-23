package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactly

/**
 * Tests for edge cases and error-tolerance of WGSL comment lexing.
 *
 * Comments in WGSL follow C-style conventions:
 * - // single-line comment
 * - /* ... */ block comment (nestable)
 * - /// doc comment (single-line)
 * - /** ... */ doc comment (block)
 *
 * The lexer must tolerate unterminated block comments without crashing.
 */
class LexerCommentEdgeCaseTest : FunSpec({

    context("Comment content is excluded from significant tokens") {
        test("identifier after single-line comment is visible") {
            val tokens = tokenizeSignificant("// ignored\nvisible")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "visible"
        }

        test("identifier after block comment on same line is visible") {
            val tokens = tokenizeSignificant("/* skipped */ seen")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "seen"
        }

        test("single-line comment does not consume the newline terminator") {
            // After the comment the newline must end it; next line is tokenized
            val source = "a // comment\nb"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.IDENTIFIER
            )
            tokens[0].literal shouldBe "a"
            tokens[1].literal shouldBe "b"
        }
    }

    context("Nested block comments") {
        test("depth-2 nesting is handled correctly") {
            val source = "/* outer /* inner */ still outer */ after"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].literal shouldBe "after"
        }

        test("depth-3 nesting is handled correctly") {
            val source = "/* 1 /* 2 /* 3 */ 2 */ 1 */ end"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].literal shouldBe "end"
        }
    }

    context("Unterminated comments are tolerated without crash") {
        test("unterminated block comment produces no tokens") {
            val tokens = tokenizeSignificant("/* no closing")
            tokens shouldHaveSize 0
        }

        test("code before unterminated comment is tokenized") {
            val tokens = tokenizeSignificant("x /* no close")
            tokens shouldHaveSize 1
            tokens[0].literal shouldBe "x"
        }
    }

    context("Doc comments are classified separately") {
        test("triple-slash is DOC_COMMENT, not SINGLE_LINE_COMMENT") {
            val all = tokenize("/// docs\n42")
                .filter { !it.isWhitespace && !it.isEof }
            all shouldHaveSize 2
            all[0].kind shouldBe TokenKind.DOC_COMMENT
            all[1].kind shouldBe TokenKind.INT_LITERAL
        }

        test("double-star block is DOC_COMMENT, not MULTI_LINE_COMMENT") {
            val all = tokenize("/** docs */x")
                .filter { !it.isWhitespace && !it.isEof }
            all shouldHaveSize 2
            all[0].kind shouldBe TokenKind.DOC_COMMENT
            all[1].kind shouldBe TokenKind.IDENTIFIER
        }
    }
})
