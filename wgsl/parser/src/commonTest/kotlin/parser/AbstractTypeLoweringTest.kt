package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.ir.ScalarKind
import io.ygdrasil.wgsl.ir.TypeInner
import io.ygdrasil.wgsl.lexer.Lexer
import io.ygdrasil.wgsl.parser.Lowerer

/**
 * Tests for lowering abstract numeric types from AST to IR.
 */
class AbstractTypeLoweringTest : FunSpec({
    
    context("AbstractIntType lowering") {
        test("lower AbstractIntType to IR") {
            val source = "let x: abstract int;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            // Should have at least one type (the abstract int)
            module.types.count shouldBe 1
            
            val type = module.types.get(0)
            type.inner shouldBeInstanceOf<TypeInner.Abstract>()
            val abstractInner = type.inner as TypeInner.Abstract
            abstractInner.scalar shouldBe ScalarKind.AbstractInt
        }
        
        test("lower AbstractIntType in function parameter") {
            val source = "fn f(x: abstract int) { }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            // Should have the abstract int type for the parameter
            module.types.count shouldBe 1
            val type = module.types.get(0)
            type.inner shouldBeInstanceOf<TypeInner.Abstract>()
            val abstractInner = type.inner as TypeInner.Abstract
            abstractInner.scalar shouldBe ScalarKind.AbstractInt
        }
    }
    
    context("AbstractFloatType lowering") {
        test("lower AbstractFloatType to IR") {
            val source = "let y: abstract float;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            // Should have at least one type (the abstract float)
            module.types.count shouldBe 1
            
            val type = module.types.get(0)
            type.inner shouldBeInstanceOf<TypeInner.Abstract>()
            val abstractInner = type.inner as TypeInner.Abstract
            abstractInner.scalar shouldBe ScalarKind.AbstractFloat
        }
        
        test("lower AbstractFloatType as return type") {
            val source = "fn g() -> abstract float { return 1.0; }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            // Should have the abstract float type
            module.types.count shouldBe 1
            val type = module.types.get(0)
            type.inner shouldBeInstanceOf<TypeInner.Abstract>()
            val abstractInner = type.inner as TypeInner.Abstract
            abstractInner.scalar shouldBe ScalarKind.AbstractFloat
        }
    }
    
    context("Mixed abstract types") {
        test("lower both abstract int and abstract float") {
            val source = """
                let a: abstract int;
                let b: abstract float;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            // Should have both abstract types
            module.types.count shouldBe 2
            
            val types = module.types.map { it.inner }
            val abstractTypes = types.filterIsInstance<TypeInner.Abstract>()
            abstractTypes.size shouldBe 2
            
            val scalarKinds = abstractTypes.map { it.scalar }
            scalarKinds shouldBe listOf(ScalarKind.AbstractInt, ScalarKind.AbstractFloat)
        }
    }
})
