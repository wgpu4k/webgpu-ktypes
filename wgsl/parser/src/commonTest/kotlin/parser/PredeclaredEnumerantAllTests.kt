package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer
import io.ygdrasil.wgsl.lexer.TokenKind

/**
 * Comprehensive test suite for all predeclared enumerant functionality.
 * 
 * This suite combines:
 * - Lexing tests
 * - AST creation tests
 * - Parsing tests
 * - Validation tests
 * - Integration tests
 */
class PredeclaredEnumerantAllTests : FunSpec({
    
    // =========================================================================
    // REGISTRY/HELPER FUNCTIONS TESTS
    // =========================================================================
    
    context("Predeclared enumerant registry") {
        test("all categories are defined") {
            allPredeclaredEnumerantCategories shouldHaveSize 12
            allPredeclaredEnumerantCategories shouldContain "AddressMode"
            allPredeclaredEnumerantCategories shouldContain "FilterMode"
            allPredeclaredEnumerantCategories shouldContain "MipmapFilterMode"
            allPredeclaredEnumerantCategories shouldContain "InterpolationType"
            allPredeclaredEnumerantCategories shouldContain "InterpolationSampling"
            allPredeclaredEnumerantCategories shouldContain "BuiltinValue"
        }
        
        test("getPredeclaredEnumerantValues returns correct values for all categories") {
            val categories = allPredeclaredEnumerantCategories
            for (category in categories) {
                val values = getPredeclaredEnumerantValues(category)
                values.isNotEmpty() shouldBe true
            }
        }
        
        test("getPredeclaredEnumerant returns enumerant for all valid combinations") {
            val categories = allPredeclaredEnumerantCategories
            for (category in categories) {
                val values = getPredeclaredEnumerantValues(category)
                for (value in values) {
                    val span = io.ygdrasil.wgsl.ir.Span(0u, 10u)
                    val enumerant = getPredeclaredEnumerant(category, value, span)
                    enumerant shouldNotBe null
                    enumerant?.category shouldBe category
                    enumerant?.value shouldBe value
                }
            }
        }
        
        test("getPredeclaredEnumerant returns null for invalid combinations") {
            val span = io.ygdrasil.wgsl.ir.Span(0u, 10u)
            val enumerant = getPredeclaredEnumerant("AddressMode", "invalid_value", span)
            enumerant shouldBe null
        }
    }
    
    // =========================================================================
    // AST CLASSES TESTS
    // =========================================================================
    
    context("AddressMode AST classes") {
        test("ClampToEdge has correct properties") {
            val span = io.ygdrasil.wgsl.ir.Span(0u, 12u)
            val mode = ClampToEdge(span)
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "clamp_to_edge"
            mode.span shouldBe span
        }
        
        test("Repeat has correct properties") {
            val span = io.ygdrasil.wgsl.ir.Span(0u, 6u)
            val mode = Repeat(span)
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "repeat"
        }
        
        test("MirrorRepeat has correct properties") {
            val span = io.ygdrasil.wgsl.ir.Span(0u, 13u)
            val mode = MirrorRepeat(span)
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "mirror_repeat"
        }
    }
    
    context("FilterMode AST classes") {
        test("Nearest has correct properties") {
            val span = io.ygdrasil.wgsl.ir.Span(0u, 7u)
            val mode = Nearest(span)
            mode.category shouldBe "FilterMode"
            mode.value shouldBe "nearest"
        }
        
        test("Linear has correct properties") {
            val span = io.ygdrasil.wgsl.ir.Span(0u, 6u)
            val mode = Linear(span)
            mode.category shouldBe "FilterMode"
            mode.value shouldBe "linear"
        }
    }
    
    // =========================================================================
    // LEXING TESTS
    // =========================================================================
    
    context("Lexing: enumerant category keywords") {
        test("AddressMode keyword") {
            val tokens = Lexer("AddressMode").tokenizeSignificant()
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
        }
        
        test("FilterMode keyword") {
            val tokens = Lexer("FilterMode").tokenizeSignificant()
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
        }
        
        test("enumerant access syntax tokens") {
            val tokens = Lexer("AddressMode.clamp_to_edge").tokenizeSignificant()
            tokens shouldHaveSize 3
            tokens[0].kind shouldBe TokenKind.IDENTIFIER
            tokens[1].kind shouldBe TokenKind.DOT
            tokens[2].kind shouldBe TokenKind.IDENTIFIER
        }
    }
    
    // =========================================================================
    // PARSING TESTS
    // =========================================================================
    
    context("Parsing: valid enumerant references") {
        test("AddressMode.clamp_to_edge") {
            val source = "let mode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "AddressMode"
            enumExpr.enumerant.shouldBeInstanceOf<ClampToEdge>()
        }
        
        test("FilterMode.nearest") {
            val source = "let mode = FilterMode.nearest;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "FilterMode"
            enumExpr.enumerant.shouldBeInstanceOf<Nearest>()
        }
        
        test("all AddressMode values parse correctly") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            for (value in values) {
                val source = "let mode = AddressMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
                
                val decl = unit.declarations[0] as VariableDecl
                decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            }
        }
    }
    
    // =========================================================================
    // VALIDATION TESTS
    // =========================================================================
    
    context("Validation: invalid enumerant values") {
        test("AddressMode.invalid_value generates error") {
            val source = "let mode = AddressMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            
            val errorMessage = parser.errors[0].message
            errorMessage.contains("invalid_value") shouldBe true
            errorMessage.contains("AddressMode") shouldBe true
        }
        
        test("FilterMode.invalid_value generates error") {
            val source = "let mode = FilterMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("error message includes valid values") {
            val source = "let mode = AddressMode.wrong;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            
            val errorMessage = parser.errors[0].message
            errorMessage.contains("clamp_to_edge") shouldBe true
            errorMessage.contains("repeat") shouldBe true
            errorMessage.contains("mirror_repeat") shouldBe true
        }
    }
    
    // =========================================================================
    // INTEGRATION TESTS
    // =========================================================================
    
    context("Integration: multiple enumerants in one module") {
        test("parse multiple different enumerant categories") {
            val source = """
                let addr = AddressMode.clamp_to_edge;
                let filter = FilterMode.nearest;
                let mipmap = MipmapFilterMode.linear;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 3
            
            for (decl in unit.declarations) {
                (decl as VariableDecl).initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            }
        }
        
        test("parse all AddressMode values in one module") {
            val source = """
                let a = AddressMode.clamp_to_edge;
                let b = AddressMode.repeat;
                let c = AddressMode.mirror_repeat;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 3
        }
    }
    
    // =========================================================================
    // EDGE CASES
    // =========================================================================
    
    context("Edge cases") {
        test("unknown category falls back to MemberAccessExpr") {
            val source = "let x = UnknownCategory.value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should parse without errors (falls back to MemberAccessExpr)
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            decl.initializer.shouldBeInstanceOf<MemberAccessExpr>()
        }
        
        test("mixed valid and invalid enumerants") {
            val source = """
                let valid = AddressMode.clamp_to_edge;
                let invalid = AddressMode.bad_value;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors shouldHaveSize 1
        }
        
        test("enumerant in struct member default value") {
            val source = """
                struct Config {
                    mode: i32 = AddressMode.clamp_to_edge,
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // This might not be valid WGSL but should parse
            parser.errors.isEmpty() shouldBe true
        }
    }
})
