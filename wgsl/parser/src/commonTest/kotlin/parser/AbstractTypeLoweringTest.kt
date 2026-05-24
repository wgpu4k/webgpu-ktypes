package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.ir.ExpressionKind
import io.ygdrasil.wgsl.ir.ScalarKind
import io.ygdrasil.wgsl.ir.Statement
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
            module.types.size shouldBe 1
            
            val type = module.types.toList()[0]
            type.inner.shouldBeInstanceOf<TypeInner.Abstract>()
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
            module.types.size shouldBe 1
            val type = module.types.toList()[0]
            type.inner.shouldBeInstanceOf<TypeInner.Abstract>()
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
            module.types.size shouldBe 1
            
            val type = module.types.toList()[0]
            type.inner.shouldBeInstanceOf<TypeInner.Abstract>()
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
            module.types.size shouldBe 1
            val type = module.types.toList()[0]
            type.inner.shouldBeInstanceOf<TypeInner.Abstract>()
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
            module.types.size shouldBe 2
            
            val types = module.types.toList().map { it.inner }
            val abstractTypes = types.filterIsInstance<TypeInner.Abstract>()
            abstractTypes.size shouldBe 2
            
            val scalarKinds = abstractTypes.map { it.scalar }
            scalarKinds shouldBe listOf(ScalarKind.AbstractInt, ScalarKind.AbstractFloat)
        }

        test("array constructor in return uses function return element type") {
            val source = """
                fn values() -> array<f32, 4> {
                    return array(1, 1, 1, 1);
                }
            """.trimIndent()

            val module = Lowerer().lower(parseWgsl(source))
            val function = module.functions.toList().single()
            val statement = function.blocks[function.body].statements.single()
                .shouldBeInstanceOf<Statement.Return>()
            val constructor = function.expressions[statement.value!!].kind
                .shouldBeInstanceOf<ExpressionKind.TypeConstructor>()
            val arrayType = module.types[constructor.type].inner.shouldBeInstanceOf<TypeInner.Array>()
            val elementType = module.types[arrayType.element].inner.shouldBeInstanceOf<TypeInner.Scalar>()

            elementType.kind shouldBe ScalarKind.F32
        }

        test("array constructor in return rejects mismatched expected array size") {
            val error = shouldThrow<LoweringError> {
                Lowerer().lower(parseWgsl("""
                    fn values() -> array<f32, 4> {
                        return array(1, 1, 1);
                    }
                """.trimIndent()))
            }

            error.message shouldContain "does not match expected array size 4"
        }
    }
})
