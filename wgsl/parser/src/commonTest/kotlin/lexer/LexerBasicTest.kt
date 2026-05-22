package io.ygdrasil.wgsl.lexer

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class LexerBasicTest : FunSpec({
    context("Basic tokenization") {
        test("Empty source") {
            val tokens = tokenize("")
            tokens shouldHaveSize 0
        }

        test("Empty source returns EOF") {
            val lexer = Lexer("")
            val token = lexer.next()
            token shouldNotBe null
            token?.isEof shouldBe true
        }

        test("Whitespace only") {
            val tokens = tokenizeSignificant("   \t\n\r  ")
            tokens shouldHaveSize 0
        }

        test("Single identifier") {
            val tokens = tokenizeSignificant("foo")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "foo"
        }

        test("Identifier with underscore") {
            val tokens = tokenizeSignificant("foo_bar")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "foo_bar"
        }

        test("Identifier with numbers") {
            val tokens = tokenizeSignificant("foo123")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "foo123"
        }
    }

    context("Span information") {
        test("Token spans are correct") {
            val tokens = tokenize("foo bar")
            // tokenize() skips whitespace, so we get: foo, bar, EOF
            // But EOF is not included, so we get: foo, bar
            tokens shouldHaveSize 2

            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[0].literal shouldBe "foo"
            tokens[0].span.start shouldBe 0u
            tokens[0].span.end shouldBe 3u

            tokens[1].kind shouldBe TokenKind.IDENTIFIER
            tokens[1].literal shouldBe "bar"
            tokens[1].span.start shouldBe 4u
            tokens[1].span.end shouldBe 7u
        }
    }

    context("Token stream interface") {
        test("Peek does not consume") {
            val lexer = Lexer("foo bar")
            val firstPeek = lexer.peek()
            val secondPeek = lexer.peek()

            firstPeek shouldNotBe null
            secondPeek shouldNotBe null
            firstPeek shouldBe secondPeek
        }

        test("Next consumes") {
            val lexer = Lexer("foo bar")
            val first = lexer.next()
            val second = lexer.next()

            first shouldNotBe null
            second shouldNotBe null
            first shouldNotBe second
        }

        test("Peek then next") {
            val lexer = Lexer("foo bar")
            val peeked = lexer.peek()
            val next = lexer.next()

            peeked shouldBe next
        }
    }
})

