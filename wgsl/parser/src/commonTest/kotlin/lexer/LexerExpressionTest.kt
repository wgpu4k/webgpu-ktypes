package io.ygdrasil.wgsl.lexer

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize

class LexerExpressionTest : FunSpec({
    context("Complex expressions") {
        test("Simple function call") {
            val source = "foo(42)"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 4
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.LEFT_PAREN,
                TokenKind.INT_LITERAL, TokenKind.RIGHT_PAREN
            )
        }

        test("Function declaration") {
            val source = "fn main() {}"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 6
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.FN, TokenKind.IDENTIFIER, TokenKind.LEFT_PAREN,
                TokenKind.RIGHT_PAREN, TokenKind.LEFT_BRACE, TokenKind.RIGHT_BRACE
            )
        }

        test("Variable declaration") {
            val source = "let x: i32 = 42;"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 7
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.LET, TokenKind.IDENTIFIER, TokenKind.COLON,
                TokenKind.I32, TokenKind.ASSIGN, TokenKind.INT_LITERAL,
                TokenKind.SEMICOLON
            )
        }

        test("Struct declaration") {
            val source = "struct Foo { x: i32 }"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 7
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.STRUCT, TokenKind.IDENTIFIER, TokenKind.LEFT_BRACE,
                TokenKind.IDENTIFIER, TokenKind.COLON, TokenKind.I32,
                TokenKind.RIGHT_BRACE
            )
        }

        test("If statement") {
            val source = "if (x > 0) { return; }"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IF, TokenKind.LEFT_PAREN, TokenKind.IDENTIFIER,
                TokenKind.RIGHT_ANGLE, TokenKind.INT_LITERAL, TokenKind.RIGHT_PAREN,
                TokenKind.LEFT_BRACE, TokenKind.RETURN, TokenKind.SEMICOLON,
                TokenKind.RIGHT_BRACE
            )
        }
    }
})
