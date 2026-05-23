package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactly

/**
 * Tests for edge cases in WGSL identifier lexing.
 *
 * WGSL identifiers start with a letter or underscore, and may contain
 * letters, digits and underscores. An isolated underscore is a special
 * phony token (UNDERSCORE), not an identifier.
 */
class LexerIdentifierTest : FunSpec({

    context("Identifier forms allowed by the WGSL spec") {
        test("identifier starting with underscore prefix is an identifier") {
            val tokens = tokenizeSignificant("_myVar")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "_myVar"
        }

        test("double underscore prefix identifier is an identifier") {
            val tokens = tokenizeSignificant("__internal")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "__internal"
        }

        test("identifier containing digits is an identifier") {
            val tokens = tokenizeSignificant("v3rtex0")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "v3rtex0"
        }

        test("mixed case identifier is preserved verbatim") {
            val tokens = tokenizeSignificant("MyStruct")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "MyStruct"
        }

        test("identifier starting with underscore followed by digits is an identifier") {
            val tokens = tokenizeSignificant("_42")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "_42"
        }

        test("identifier starting with double underscore followed by digits is an identifier") {
            val tokens = tokenizeSignificant("__1")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "__1"
        }
    }

    context("Isolated underscore is the UNDERSCORE token, not an identifier") {
        test("bare underscore is UNDERSCORE") {
            val tokens = tokenizeSignificant("_")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.UNDERSCORE
        }

        test("underscore used as phony assignment target") {
            // _ = expr;  ← valid WGSL phony assignment
            val tokens = tokenizeSignificant("_ = 0;")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.UNDERSCORE, TokenKind.ASSIGN, TokenKind.INT_LITERAL, TokenKind.SEMICOLON
            )
        }

        test("underscore surrounded by identifiers does not bleed") {
            val tokens = tokenizeSignificant("a _ b")
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IDENTIFIER, TokenKind.UNDERSCORE, TokenKind.IDENTIFIER
            )
        }
    }

    context("Keyword-prefix identifiers are not keywords") {
        test("forloop is an identifier, not a for keyword") {
            val tokens = tokenizeSignificant("forloop")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "forloop"
        }

        test("returnValue is an identifier, not a return keyword") {
            val tokens = tokenizeSignificant("returnValue")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "returnValue"
        }

        test("letX is an identifier, not a let keyword") {
            val tokens = tokenizeSignificant("letX")
            tokens shouldHaveSize 1
            tokens[0].kind    shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "letX"
        }

        test("fn_name is an identifier, not a fn keyword") {
            val tokens = tokenizeSignificant("fn_name")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "fn_name"
        }

        test("constance is an identifier, not a const keyword") {
            val tokens = tokenizeSignificant("constance")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "constance"
        }

        test("variable is an identifier, not a var keyword") {
            val tokens = tokenizeSignificant("variable")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "variable"
        }

        test("struct_type is an identifier, not a struct keyword") {
            val tokens = tokenizeSignificant("struct_type")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "struct_type"
        }
    }
})
