package io.ygdrasil.wgsl.lexer

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class LexerCommentTest : FunSpec({
    context("WGSL comments") {
        test("Single line") {
            val tokens = tokenize("// comment\n42")
            tokens.filter { !it.isWhitespace && !it.isEof } shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.SINGLE_LINE_COMMENT
            tokens[1].kind shouldBe TokenKind.INT_LITERAL
        }

        test("Doc comments") {
            val tokens = tokenize("/// doc comment\n42")
            tokens.filter { !it.isWhitespace && !it.isEof } shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.DOC_COMMENT
            tokens[1].kind shouldBe TokenKind.INT_LITERAL
        }

        test("Multi-line") {
            val tokens = tokenize("/* comment */42")
            tokens.filter { !it.isWhitespace && !it.isEof } shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.MULTI_LINE_COMMENT
            tokens[1].kind shouldBe TokenKind.INT_LITERAL
        }

        test("Multi-line doc comments") {
            val tokens = tokenize("/** doc comment */42")
            tokens.filter { !it.isWhitespace && !it.isEof } shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.DOC_COMMENT
            tokens[1].kind shouldBe TokenKind.INT_LITERAL
        }

        test("Nested block comments") {
            val source = "/* outer /* inner */ outer */ identifier"
            val tokens = tokenize(source)
            val filtered = tokens.filter { !it.isWhitespace && !it.isEof }
            
            filtered shouldHaveSize 2
            filtered[0].kind shouldBe TokenKind.MULTI_LINE_COMMENT
            filtered[1].kind shouldBe TokenKind.IDENTIFIER
            filtered[1].literal shouldBe "identifier"
        }
    }
})
