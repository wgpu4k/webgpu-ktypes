package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Tests for parsing abstract numeric types.
 */
class AbstractTypeParserTest : FunSpec({
    
    context("abstract int type") {
        test("parse abstract int in variable declaration") {
            val source = "let x: abstract int;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            unit.declarations shouldHaveSize 1
            val varDecl = unit.declarations[0] as VariableDecl
            varDecl.type.shouldBeInstanceOf<AbstractIntType>()
        }
        
        test("parse abstract int with initializer") {
            val source = "let x: abstract int = 42;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val varDecl = unit.declarations[0] as VariableDecl
            varDecl.type.shouldBeInstanceOf<AbstractIntType>()
            varDecl.initializer shouldNotBe null
        }
        
        test("parse abstract int as function parameter") {
            val source = "fn process(x: abstract int) { }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val funcDecl = unit.declarations[0] as FunctionDecl
            funcDecl.parameters shouldHaveSize 1
            funcDecl.parameters[0].type.shouldBeInstanceOf<AbstractIntType>()
        }
        
        test("parse abstract int as function return type") {
            val source = "fn getValue() -> abstract int { return 42; }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val funcDecl = unit.declarations[0] as FunctionDecl
            funcDecl.returnType.shouldBeInstanceOf<AbstractIntType>()
        }
    }
    
    context("abstract float type") {
        test("parse abstract float in variable declaration") {
            val source = "let y: abstract float;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val varDecl = unit.declarations[0] as VariableDecl
            varDecl.type.shouldBeInstanceOf<AbstractFloatType>()
        }
        
        test("parse abstract float with initializer") {
            val source = "let y: abstract float = 3.14;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val varDecl = unit.declarations[0] as VariableDecl
            varDecl.type.shouldBeInstanceOf<AbstractFloatType>()
        }
        
        test("parse abstract float in struct") {
            val source = """
                struct Config {
                    value: abstract float,
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val structDecl = unit.declarations[0] as StructDecl
            structDecl.members shouldHaveSize 1
            structDecl.members[0].type.shouldBeInstanceOf<AbstractFloatType>()
        }
    }
    
    context("mixed usage") {
        test("parse multiple abstract types in one module") {
            val source = """
                let a: abstract int = 10;
                let b: abstract float = 2.5;
                
                fn convert(x: abstract int) -> abstract float {
                    return 2.0;
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should parse without errors
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 3
        }
    }
    
    context("error handling") {
        test("error on incomplete abstract type") {
            val source = "let x: abstract;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should have an error
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("error on invalid abstract type") {
            val source = "let x: abstract bool;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should have an error
            parser.errors.isNotEmpty() shouldBe true
        }
    }
})
