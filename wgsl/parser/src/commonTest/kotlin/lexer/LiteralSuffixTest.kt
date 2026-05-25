package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.parser.Parser

/**
 * Tests for literal suffix support.
 * 
 * This test suite verifies supported suffixes:
 * - Integer: i, I, u, U, li, lu (or no suffix for signed int)
 * - Float: f, F, h, H, lf, LF
 *
 * Naga int64 fixtures use li/lu suffixes.
 */
class LiteralSuffixTest : FunSpec({
    
    context("Standard integer suffixes") {
        test("integer without suffix is valid") {
            val tokens = tokenizeSignificant("42")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "42"
        }
        
        test("integer with i suffix is valid") {
            val tokens = tokenizeSignificant("42i")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "42i"
        }
        
        test("integer with I suffix is valid") {
            val tokens = tokenizeSignificant("42I")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "42I"
        }
        
        test("integer with u suffix is valid") {
            val tokens = tokenizeSignificant("42u")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.UINT_LITERAL
            tokens[0].literal shouldBe "42u"
        }
        
        test("integer with U suffix is valid") {
            val tokens = tokenizeSignificant("42U")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.UINT_LITERAL
            tokens[0].literal shouldBe "42U"
        }
        
        test("hexadecimal integer without suffix is valid") {
            val tokens = tokenizeSignificant("0x2a")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "0x2a"
        }
        
        test("hexadecimal integer with u suffix is valid") {
            val tokens = tokenizeSignificant("0x2au")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.UINT_LITERAL
            tokens[0].literal shouldBe "0x2au"
        }
    }
    
    context("Standard float suffixes") {
        test("float with f suffix is valid") {
            val tokens = tokenizeSignificant("3.14f")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14f"
        }
        
        test("float with F suffix is valid") {
            val tokens = tokenizeSignificant("3.14F")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14F"
        }
        
        test("float with h suffix is valid") {
            val tokens = tokenizeSignificant("3.14h")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14h"
        }
        
        test("float with H suffix is valid") {
            val tokens = tokenizeSignificant("3.14H")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14H"
        }
        
        test("float without suffix is valid") {
            val tokens = tokenizeSignificant("3.14")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14"
        }
        
        test("hexadecimal float with f suffix is valid") {
            val tokens = tokenizeSignificant("0x1.8p1f")
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "0x1.8p1f"
        }
    }
    
    context("Naga int64 suffixes") {
        test("li suffix is accepted") {
            val tokens = tokenizeSignificant("42li")

            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "42li"
        }

        test("lu suffix is accepted") {
            val tokens = tokenizeSignificant("42lu")

            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.UINT_LITERAL
            tokens[0].literal shouldBe "42lu"
        }

        test("hexadecimal li and lu suffixes are accepted") {
            val tokens = tokenizeSignificant("0x2ali 0x2alu")

            tokens shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "0x2ali"
            tokens[1].kind shouldBe TokenKind.UINT_LITERAL
            tokens[1].literal shouldBe "0x2alu"
        }
    }

    context("Naga f64 suffixes") {
        test("lf suffix is accepted") {
            val tokens = tokenizeSignificant("3.14lf")

            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14lf"
        }

        test("LF suffix is accepted") {
            val tokens = tokenizeSignificant("3.14LF")

            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14LF"
        }

        test("integer-looking lf suffix is accepted as f64 literal") {
            val tokens = tokenizeSignificant("1lf")

            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "1lf"
        }

        test("exponent lf suffix is accepted") {
            val tokens = tokenizeSignificant("3e1lf")

            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3e1lf"
        }
    }
    
    context("Parsing with suffixes") {
        test("parse integer with i suffix") {
            val parser = Parser(Lexer("let x = 42i;"))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("parse integer with u suffix") {
            val parser = Parser(Lexer("let x = 42u;"))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("parse float with f suffix") {
            val parser = Parser(Lexer("let x = 3.14f;"))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("parse float with h suffix") {
            val parser = Parser(Lexer("let x = 3.14h;"))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("parse integer with li suffix") {
            val parser = Parser(Lexer("let x = 42li;"))
            val unit = parser.parse()

            parser.errors.isEmpty() shouldBe true
        }

        test("parse integer with lu suffix") {
            val parser = Parser(Lexer("let x = 42lu;"))
            val unit = parser.parse()

            parser.errors.isEmpty() shouldBe true
        }
        
        test("parse float with lf suffix") {
            val parser = Parser(Lexer("let x = 3.14lf;"))

            parser.parse()

            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("Edge cases") {
        test("integer followed by l identifier") {
            val tokens = tokenizeSignificant("42 l")
            tokens shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "42"
            tokens[1].kind shouldBe TokenKind.IDENTIFIER
            tokens[1].literal shouldBe "l"
        }
        
        test("integer followed by i identifier") {
            val tokens = tokenizeSignificant("42 i")
            tokens shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.INT_LITERAL
            tokens[0].literal shouldBe "42"
            tokens[1].kind shouldBe TokenKind.IDENTIFIER
            tokens[1].literal shouldBe "i"
        }
        
        test("float followed by f identifier") {
            val tokens = tokenizeSignificant("3.14 f")
            tokens shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.FLOAT_LITERAL
            tokens[0].literal shouldBe "3.14"
            tokens[1].kind shouldBe TokenKind.IDENTIFIER
            tokens[1].literal shouldBe "f"
        }
    }
})
