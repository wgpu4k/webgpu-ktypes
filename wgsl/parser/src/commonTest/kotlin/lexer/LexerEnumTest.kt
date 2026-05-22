package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

/**
 * Tests for lexing enum-related tokens.
 */
class LexerEnumTest : FunSpec({
    
    context("enum keyword") {
        test("recognizes enum as keyword") {
            val tokens = Lexer("enum").tokenizeSignificant()
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.ENUM
        }
        
        test("enum is a keyword") {
            TokenKind.ENUM.isKeyword shouldBe true
        }
        
        test("enum keyword in context") {
            val tokens = Lexer("enum MyEnum {}").tokenizeSignificant()
            tokens shouldContain TokenKind.ENUM
            tokens shouldContain TokenKind.IDENTIFIER
            tokens shouldContain TokenKind.LEFT_BRACE
            tokens shouldContain TokenKind.RIGHT_BRACE
        }
    }
    
    context("enum declaration lexing") {
        test("lex simple enum declaration") {
            val source = "enum Color { RED, GREEN, BLUE }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldHaveSize 7
            tokens[0].kind shouldBe TokenKind.ENUM
            tokens[1].kind shouldBe TokenKind.IDENTIFIER
            tokens[1].literal shouldBe "Color"
            tokens[2].kind shouldBe TokenKind.LEFT_BRACE
            tokens[3].kind shouldBe TokenKind.IDENTIFIER
            tokens[3].literal shouldBe "RED"
            tokens[4].kind shouldBe TokenKind.COMMA
            tokens[5].kind shouldBe TokenKind.IDENTIFIER
            tokens[5].literal shouldBe "GREEN"
            tokens[6].kind shouldBe TokenKind.IDENTIFIER
            tokens[6].literal shouldBe "BLUE"
        }
        
        test("lex enum with explicit values") {
            val source = "enum Status { ACTIVE = 1, INACTIVE = 0 }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.ENUM
            tokens shouldContain TokenKind.IDENTIFIER
            tokens shouldContain TokenKind.ASSIGN
            tokens shouldContain TokenKind.INT_LITERAL
        }
        
        test("lex enum with attributes") {
            val source = "@packed enum MyEnum { A, B }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.AT
            tokens shouldContain TokenKind.IDENTIFIER
            tokens shouldContain TokenKind.ENUM
        }
        
        test("lex enum with member attributes") {
            val source = "enum MyEnum { @attr1 A, @attr2 B }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.ENUM
            tokens shouldContain TokenKind.AT
            tokens shouldContain TokenKind.IDENTIFIER
        }
        
        test("lex enum with trailing comma") {
            val source = "enum Color { RED, GREEN, BLUE, }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.COMMA
        }
        
        test("lex empty enum") {
            val source = "enum Empty { }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldHaveSize 4
            tokens[0].kind shouldBe TokenKind.ENUM
            tokens[1].kind shouldBe TokenKind.IDENTIFIER
            tokens[2].kind shouldBe TokenKind.LEFT_BRACE
            tokens[3].kind shouldBe TokenKind.RIGHT_BRACE
        }
    }
    
    context("enum member names") {
        test("lex enum member with uppercase name") {
            val source = "enum Test { UPPERCASE }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.IDENTIFIER
        }
        
        test("lex enum member with snake_case name") {
            val source = "enum Test { snake_case }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.IDENTIFIER
        }
        
        test("lex enum member with mixed case name") {
            val source = "enum Test { MixedCase }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.IDENTIFIER
        }
    }
    
    context("enum with various value types") {
        test("lex enum with integer values") {
            val source = "enum Test { A = 42, B = -5 }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.INT_LITERAL
        }
        
        test("lex enum with hex values") {
            val source = "enum Test { A = 0xFF, B = 0x0 }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.INT_LITERAL
        }
        
        test("lex enum with float values") {
            val source = "enum Test { A = 3.14 }"
            val tokens = Lexer(source).tokenizeSignificant()
            
            tokens shouldContain TokenKind.FLOAT_LITERAL
        }
    }
    
    context("multiple enums") {
        test("lex multiple enum declarations") {
            val source = """
                enum Color { RED, GREEN }
                enum Status { ACTIVE, INACTIVE }
            """
            val tokens = Lexer(source).tokenizeSignificant()
            
            val enumCount = tokens.count { it.kind == TokenKind.ENUM }
            enumCount shouldBe 2
        }
    }
})
