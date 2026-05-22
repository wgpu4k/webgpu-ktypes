package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Tests for parsing references to predeclared enumerants.
 */
class PredeclaredEnumerantParserTest : FunSpec({
    
    context("AddressMode enumerants") {
        test("parse AddressMode.clamp_to_edge") {
            val source = "let mode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 1
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "AddressMode"
            enumExpr.enumerant.shouldBeInstanceOf<ClampToEdge>()
            enumExpr.enumerant.value shouldBe "clamp_to_edge"
        }
        
        test("parse AddressMode.repeat") {
            val source = "let mode = AddressMode.repeat;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "AddressMode"
            enumExpr.enumerant.shouldBeInstanceOf<Repeat>()
        }
        
        test("parse AddressMode.mirror_repeat") {
            val source = "let mode = AddressMode.mirror_repeat;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "AddressMode"
            enumExpr.enumerant.shouldBeInstanceOf<MirrorRepeat>()
        }
    }
    
    context("FilterMode enumerants") {
        test("parse FilterMode.nearest") {
            val source = "let mode = FilterMode.nearest;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "FilterMode"
            enumExpr.enumerant.shouldBeInstanceOf<Nearest>()
        }
        
        test("parse FilterMode.linear") {
            val source = "let mode = FilterMode.linear;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "FilterMode"
            enumExpr.enumerant.shouldBeInstanceOf<Linear>()
        }
    }
    
    context("MipmapFilterMode enumerants") {
        test("parse MipmapFilterMode.nearest") {
            val source = "let mode = MipmapFilterMode.nearest;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "MipmapFilterMode"
            enumExpr.enumerant.shouldBeInstanceOf<MipmapNearest>()
        }
        
        test("parse MipmapFilterMode.linear") {
            val source = "let mode = MipmapFilterMode.linear;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "MipmapFilterMode"
            enumExpr.enumerant.shouldBeInstanceOf<MipmapLinear>()
        }
    }
    
    context("InterpolationType enumerants") {
        test("parse InterpolationType.perspective") {
            val source = "let x = InterpolationType.perspective;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "InterpolationType"
            enumExpr.enumerant.shouldBeInstanceOf<Perspective>()
        }
        
        test("parse InterpolationType.linear") {
            val source = "let x = InterpolationType.linear;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "InterpolationType"
            enumExpr.enumerant.shouldBeInstanceOf<LinearInterpolation>()
        }
        
        test("parse InterpolationType.flat") {
            val source = "let x = InterpolationType.flat;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "InterpolationType"
            enumExpr.enumerant.shouldBeInstanceOf<Flat>()
        }
    }
    
    context("InterpolationSampling enumerants") {
        test("parse InterpolationSampling.center") {
            val source = "let sampling = InterpolationSampling.center;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "InterpolationSampling"
            enumExpr.enumerant.shouldBeInstanceOf<Center>()
        }
        
        test("parse InterpolationSampling.centroid") {
            val source = "let sampling = InterpolationSampling.centroid;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "InterpolationSampling"
            enumExpr.enumerant.shouldBeInstanceOf<Centroid>()
        }
        
        test("parse InterpolationSampling.sample") {
            val source = "let sampling = InterpolationSampling.sample;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "InterpolationSampling"
            enumExpr.enumerant.shouldBeInstanceOf<Sample>()
        }
    }
    
    context("Error handling") {
        test("error for invalid enumerant value") {
            val source = "let mode = AddressMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should have an error because invalid_value is not a valid AddressMode
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("error for unknown category") {
            val source = "let mode = UnknownCategory.value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should create a MemberAccessExpr instead of PredeclaredEnumerantExpr
            // because the category is not recognized
            unit.declarations shouldHaveSize 1
            val decl = unit.declarations[0] as VariableDecl
            // This should still parse as MemberAccessExpr (fallback)
            decl.initializer.shouldBeInstanceOf<MemberAccessExpr>()
        }
    }
    
    context("Usage in function parameters") {
        test("parse predeclared enumerant as function parameter") {
            val source = "fn sample(mode: AddressMode) { }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val func = unit.declarations[0] as FunctionDecl
            func.parameters shouldHaveSize 1
            func.parameters[0].type.shouldBeInstanceOf<NamedType>().name shouldBe "AddressMode"
        }
    }
    
    context("Multiple enumerants in one module") {
        test("parse multiple predeclared enumerant references") {
            val source = """
                let addr1 = AddressMode.clamp_to_edge;
                let addr2 = AddressMode.repeat;
                let filter1 = FilterMode.nearest;
                let filter2 = FilterMode.linear;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 4
            
            for (decl in unit.declarations) {
                (decl as VariableDecl).initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            }
        }
    }
})
