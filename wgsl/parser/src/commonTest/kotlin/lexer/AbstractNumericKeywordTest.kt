package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.booleans.shouldBeTrue

class AbstractNumericKeywordTest : FunSpec({
    context("Abstract numeric type keywords support") {
        test("ABSTRACT token kind is a keyword") {
            TokenKind.ABSTRACT.isKeyword.shouldBeTrue()
        }

        test("INT token kind is a keyword") {
            TokenKind.INT.isKeyword.shouldBeTrue()
        }

        test("FLOAT token kind is a keyword") {
            TokenKind.FLOAT.isKeyword.shouldBeTrue()
        }

        test("Lexer recognizes 'abstract int' keywords") {
            val tokens = tokenizeSignificant("abstract int")
            tokens.map { it.kind } shouldContain TokenKind.ABSTRACT
            tokens.map { it.kind } shouldContain TokenKind.INT
        }

        test("Lexer recognizes 'abstract float' keywords") {
            val tokens = tokenizeSignificant("abstract float")
            tokens.map { it.kind } shouldContain TokenKind.ABSTRACT
            tokens.map { it.kind } shouldContain TokenKind.FLOAT
        }

        test("Lexer recognizes 'abstract' keyword alone") {
            val tokens = tokenizeSignificant("abstract")
            tokens.map { it.kind } shouldContain TokenKind.ABSTRACT
        }

        test("Lexer recognizes 'int' keyword alone") {
            val tokens = tokenizeSignificant("int")
            tokens.map { it.kind } shouldContain TokenKind.INT
        }

        test("Lexer recognizes 'float' keyword alone") {
            val tokens = tokenizeSignificant("float")
            tokens.map { it.kind } shouldContain TokenKind.FLOAT
        }

        test("Lexer recognizes abstract numeric types in type alias context") {
            val source = "type MyInt = abstract int;"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContain TokenKind.ABSTRACT
            tokens.map { it.kind } shouldContain TokenKind.INT
        }

        test("Lexer recognizes abstract float in type alias context") {
            val source = "type MyFloat = abstract float;"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContain TokenKind.ABSTRACT
            tokens.map { it.kind } shouldContain TokenKind.FLOAT
        }
    }
})
