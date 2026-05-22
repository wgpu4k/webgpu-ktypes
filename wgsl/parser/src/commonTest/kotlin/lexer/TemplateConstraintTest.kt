package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

/**
 * Tests for WGSL template constraint keyword (where).
 * Used in generic type declarations to specify constraints.
 */
class TemplateConstraintTest : FunSpec({
    context("Template constraint keywords") {
        test("Where keyword is recognized") {
            val source = "where"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.WHERE
        }

        test("Where in template constraint context") {
            val source = "fn process<T>(x: T) where T: num { }"
            val tokens = tokenizeSignificant(source)
            // Verify where is properly tokenized
            val kinds = tokens.map { it.kind }
            kinds shouldBe listOf(
                TokenKind.FN, TokenKind.IDENTIFIER, TokenKind.LEFT_ANGLE, TokenKind.IDENTIFIER, TokenKind.RIGHT_ANGLE,
                TokenKind.LEFT_PAREN, TokenKind.IDENTIFIER, TokenKind.COLON, TokenKind.IDENTIFIER,
                TokenKind.RIGHT_PAREN, TokenKind.WHERE, TokenKind.IDENTIFIER, TokenKind.COLON, TokenKind.IDENTIFIER,
                TokenKind.LEFT_BRACE, TokenKind.RIGHT_BRACE
            )
        }

        test("Where with multiple constraints") {
            val source = "fn generic<T, U>(t: T, u: U) where T: num, U: vec { }"
            val tokens = tokenizeSignificant(source)
            // Count where keyword
            tokens.filter { it.kind == TokenKind.WHERE }.shouldHaveSize(1)
        }
    }
})
