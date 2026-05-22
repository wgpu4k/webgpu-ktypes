package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.booleans.shouldBeTrue

class EnumKeywordTest : FunSpec({
    context("Enum keyword support") {
        test("ENUM token kind is a keyword") {
            TokenKind.ENUM.isKeyword.shouldBeTrue()
        }

        test("Lexer recognizes 'enum' keyword") {
            val tokens = tokenizeSignificant("enum MyEnum {}")
            tokens.map { it.kind } shouldContain TokenKind.ENUM
        }

        test("Enum keyword appears in declaration context") {
            val source = "enum Color { RED, GREEN, BLUE }"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContain TokenKind.ENUM
        }
    }
})
