package io.ygdrasil.wgsl.lexer

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class LexerOperatorTest : FunSpec({
    context("WGSL operators") {
        test("Arithmetic operators") {
            val source = "+ - * / %"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.PLUS, TokenKind.MINUS, TokenKind.STAR, TokenKind.SLASH, TokenKind.PERCENT
            )
        }

        test("Increment and decrement") {
            val source = "++ --"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.INCREMENT, TokenKind.DECREMENT
            )
        }

        test("Assignment operators") {
            val source = "= += -= *= /= %= &= |= ^= <<= >>="
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.ASSIGN, TokenKind.PLUS_ASSIGN, TokenKind.MINUS_ASSIGN,
                TokenKind.STAR_ASSIGN, TokenKind.SLASH_ASSIGN, TokenKind.PERCENT_ASSIGN,
                TokenKind.AND_ASSIGN, TokenKind.OR_ASSIGN, TokenKind.XOR_ASSIGN,
                TokenKind.LEFT_SHIFT_ASSIGN, TokenKind.RIGHT_SHIFT_ASSIGN
            )
        }

        test("Comparison operators") {
            val source = "== != < > <= >="
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.EQ, TokenKind.NEQ, TokenKind.LEFT_ANGLE, TokenKind.RIGHT_ANGLE,
                TokenKind.LTE, TokenKind.GTE
            )
        }

        test("Logical operators") {
            val source = "&& || !"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.AND, TokenKind.OR, TokenKind.NOT
            )
        }

        test("Bitwise operators") {
            val source = "& | ^ ~ << >>"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.AMPERSAND, TokenKind.PIPE, TokenKind.CARET, TokenKind.TILDE,
                TokenKind.LEFT_SHIFT, TokenKind.RIGHT_SHIFT
            )
        }

        test("Special operators") {
            val source = "-> => .* :: ? _"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.ARROW, TokenKind.FAT_ARROW, TokenKind.DOT_STAR,
                TokenKind.COLON_COLON, TokenKind.QUESTION, TokenKind.UNDERSCORE
            )
        }
    }

    context("WGSL punctuation") {
        test("Parentheses, braces and brackets") {
            val source = "() {} []"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.LEFT_PAREN, TokenKind.RIGHT_PAREN,
                TokenKind.LEFT_BRACE, TokenKind.RIGHT_BRACE,
                TokenKind.LEFT_BRACKET, TokenKind.RIGHT_BRACKET
            )
        }

        test("Separators and delimiters") {
            val source = ", ; : . @ <>"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.COMMA, TokenKind.SEMICOLON, TokenKind.COLON, TokenKind.DOT,
                TokenKind.AT, TokenKind.LEFT_ANGLE_RIGHT_ANGLE
            )
        }
    }
})
