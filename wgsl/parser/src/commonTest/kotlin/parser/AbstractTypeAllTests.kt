package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.ir.ScalarKind as IrScalarKind
import io.ygdrasil.wgsl.ir.TypeInner
import io.ygdrasil.wgsl.ir.Span
import io.ygdrasil.wgsl.lexer.Lexer
import io.ygdrasil.wgsl.lexer.TokenKind
import io.ygdrasil.wgsl.lexer.isKeyword
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.TypeResolver

/**
 * Comprehensive test suite for abstract numeric types support.
 * 
 * This test suite combines lexing, parsing, lowering, and type resolution tests
 * to ensure complete support for abstract int and abstract float types.
 * 
 * Covers:
 * - Lexing: recognition of abstract, int, float keywords
 * - Parsing: abstract types in variables, parameters, return types, structs, type aliases
 * - Type Resolution: compatibility checking for abstract types
 * - Lowering: AST to IR conversion for abstract types
 * - AST: manual creation and properties of abstract type nodes
 * - Integration: complete modules with abstract types
 * - Error Handling: invalid abstract type usage
 */
class AbstractTypeAllTests : FunSpec({

    // =========================================================================
    // TYPE RESOLVER SETUP
    // =========================================================================

    lateinit var typeResolver: TypeResolver

    beforeTest {
        typeResolver = TypeResolver()
    }

    // =========================================================================
    // LEXING TESTS
    // =========================================================================
    
    context("Lexing: abstract type keywords") {
        test("recognizes abstract keyword") {
            val tokens = Lexer("abstract").tokenizeSignificant()
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.ABSTRACT
        }
        
        test("recognizes int keyword") {
            val tokens = Lexer("int").tokenizeSignificant()
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.INT
        }
        
        test("recognizes float keyword") {
            val tokens = Lexer("float").tokenizeSignificant()
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.FLOAT
        }
        
        test("recognizes abstract int sequence") {
            val tokens = Lexer("abstract int").tokenizeSignificant()
            tokens shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.ABSTRACT
            tokens[1].kind shouldBe TokenKind.INT
        }
        
        test("recognizes abstract float sequence") {
            val tokens = Lexer("abstract float").tokenizeSignificant()
            tokens shouldHaveSize 2
            tokens[0].kind shouldBe TokenKind.ABSTRACT
            tokens[1].kind shouldBe TokenKind.FLOAT
        }
        
        test("abstract types are keywords") {
            TokenKind.ABSTRACT.isKeyword shouldBe true
            TokenKind.INT.isKeyword shouldBe true
            TokenKind.FLOAT.isKeyword shouldBe true
        }
    }
    
    // =========================================================================
    // TYPE RESOLUTION TESTS
    // =========================================================================
    
    context("Type Resolution: abstract types compatibility") {
        test("AbstractIntType is compatible with concrete integer types") {
            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                ScalarType(ScalarKind.I32, Span.UNDEFINED)
            ) shouldBe true

            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                ScalarType(ScalarKind.U32, Span.UNDEFINED)
            ) shouldBe true

            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                ScalarType(ScalarKind.I16, Span.UNDEFINED)
            ) shouldBe true

            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                ScalarType(ScalarKind.U16, Span.UNDEFINED)
            ) shouldBe true
        }

        test("AbstractFloatType is compatible with concrete float types") {
            typeResolver.areTypesCompatible(
                AbstractFloatType(Span.UNDEFINED),
                ScalarType(ScalarKind.F32, Span.UNDEFINED)
            ) shouldBe true

            typeResolver.areTypesCompatible(
                AbstractFloatType(Span.UNDEFINED),
                ScalarType(ScalarKind.F64, Span.UNDEFINED)
            ) shouldBe true

            typeResolver.areTypesCompatible(
                AbstractFloatType(Span.UNDEFINED),
                ScalarType(ScalarKind.F16, Span.UNDEFINED)
            ) shouldBe true
        }

        test("AbstractIntType is NOT compatible with AbstractFloatType") {
            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                AbstractFloatType(Span.UNDEFINED)
            ) shouldBe false
        }

        test("AbstractIntType is NOT compatible with float types") {
            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                ScalarType(ScalarKind.F32, Span.UNDEFINED)
            ) shouldBe false

            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                ScalarType(ScalarKind.F64, Span.UNDEFINED)
            ) shouldBe false
        }

        test("AbstractFloatType is NOT compatible with integer types") {
            typeResolver.areTypesCompatible(
                AbstractFloatType(Span.UNDEFINED),
                ScalarType(ScalarKind.I32, Span.UNDEFINED)
            ) shouldBe false

            typeResolver.areTypesCompatible(
                AbstractFloatType(Span.UNDEFINED),
                ScalarType(ScalarKind.U32, Span.UNDEFINED)
            ) shouldBe false
        }

        test("AbstractIntType is compatible with itself") {
            typeResolver.areTypesCompatible(
                AbstractIntType(Span.UNDEFINED),
                AbstractIntType(Span.UNDEFINED)
            ) shouldBe true
        }

        test("AbstractFloatType is compatible with itself") {
            typeResolver.areTypesCompatible(
                AbstractFloatType(Span.UNDEFINED),
                AbstractFloatType(Span.UNDEFINED)
            ) shouldBe true
        }

        test("concrete integer types are compatible with AbstractIntType (reverse)") {
            typeResolver.areTypesCompatible(
                ScalarType(ScalarKind.I32, Span.UNDEFINED),
                AbstractIntType(Span.UNDEFINED)
            ) shouldBe true

            typeResolver.areTypesCompatible(
                ScalarType(ScalarKind.U16, Span.UNDEFINED),
                AbstractIntType(Span.UNDEFINED)
            ) shouldBe true
        }

        test("concrete float types are compatible with AbstractFloatType (reverse)") {
            typeResolver.areTypesCompatible(
                ScalarType(ScalarKind.F32, Span.UNDEFINED),
                AbstractFloatType(Span.UNDEFINED)
            ) shouldBe true

            typeResolver.areTypesCompatible(
                ScalarType(ScalarKind.F64, Span.UNDEFINED),
                AbstractFloatType(Span.UNDEFINED)
            ) shouldBe true
        }
    }

    // =========================================================================
    // PARSING TESTS
    // =========================================================================
    
    context("Parsing: abstract types in declarations") {
        test("parse abstract int variable") {
            val source = "let x: abstract int;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 1
            
            val decl = unit.declarations[0] as VariableDecl
            decl.type.shouldBeInstanceOf<AbstractIntType>()
        }
        
        test("parse abstract float variable") {
            val source = "let y: abstract float;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 1
            
            val decl = unit.declarations[0] as VariableDecl
            decl.type.shouldBeInstanceOf<AbstractFloatType>()
        }
        
        test("parse abstract int with initializer") {
            val source = "let x: abstract int = 42;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val decl = unit.declarations[0] as VariableDecl
            decl.type.shouldBeInstanceOf<AbstractIntType>()
            decl.initializer shouldNotBe null
        }
        
        test("parse abstract float with initializer") {
            val source = "let y: abstract float = 3.14;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val decl = unit.declarations[0] as VariableDecl
            decl.type.shouldBeInstanceOf<AbstractFloatType>()
            decl.initializer shouldNotBe null
        }
        
        test("parse abstract int function parameter") {
            val source = "fn f(x: abstract int) { }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val func = unit.declarations[0] as FunctionDecl
            func.parameters shouldHaveSize 1
            func.parameters[0].type.shouldBeInstanceOf<AbstractIntType>()
        }
        
        test("parse abstract float function parameter") {
            val source = "fn g(y: abstract float) { }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val func = unit.declarations[0] as FunctionDecl
            func.parameters shouldHaveSize 1
            func.parameters[0].type.shouldBeInstanceOf<AbstractFloatType>()
        }
        
        test("parse abstract int return type") {
            val source = "fn h() -> abstract int { return 42; }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val func = unit.declarations[0] as FunctionDecl
            func.returnType.shouldBeInstanceOf<AbstractIntType>()
        }
        
        test("parse abstract float return type") {
            val source = "fn k() -> abstract float { return 3.14; }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val func = unit.declarations[0] as FunctionDecl
            func.returnType.shouldBeInstanceOf<AbstractFloatType>()
        }
        
        test("parse abstract types in struct") {
            val source = """
                struct S {
                    a: abstract int,
                    b: abstract float,
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val struct = unit.declarations[0] as StructDecl
            struct.members shouldHaveSize 2
            struct.members[0].type.shouldBeInstanceOf<AbstractIntType>()
            struct.members[1].type.shouldBeInstanceOf<AbstractFloatType>()
        }
        
        test("parse type alias with abstract int") {
            val source = "type MyInt = abstract int;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val typeAlias = unit.declarations[0] as TypeAliasDecl
            typeAlias.type.shouldBeInstanceOf<AbstractIntType>()
        }
        
        test("parse type alias with abstract float") {
            val source = "type MyFloat = abstract float;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            val typeAlias = unit.declarations[0] as TypeAliasDecl
            typeAlias.type.shouldBeInstanceOf<AbstractFloatType>()
        }
    }
    
    // =========================================================================
    // ERROR HANDLING TESTS
    // =========================================================================
    
    context("Error handling: invalid abstract type usage") {
        test("error on incomplete abstract type") {
            val source = "let x: abstract;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("error on abstract with invalid type") {
            val source = "let x: abstract bool;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("error on abstract with scalar type") {
            val source = "let x: abstract i32;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
    }

    // =========================================================================
    // AST TESTS
    // =========================================================================
    
    context("AST: abstract type classes") {
        test("create AbstractIntType manually") {
            val span = Span(0u, 12u)
            val type = AbstractIntType(span)

            type.shouldBeInstanceOf<TypeDecl>()
            type.span shouldBe span
        }

        test("create AbstractFloatType manually") {
            val span = Span(0u, 14u)
            val type = AbstractFloatType(span)

            type.shouldBeInstanceOf<TypeDecl>()
            type.span shouldBe span
        }

        test("AbstractIntType is distinguishable from concrete integer types") {
            val span = Span.UNDEFINED
            val abstractInt = AbstractIntType(span)
            val concreteInt = ScalarType(ScalarKind.I32, span)

            abstractInt.shouldBeInstanceOf<AbstractIntType>()
            abstractInt.shouldBeInstanceOf<TypeDecl>()

            concreteInt.shouldBeInstanceOf<ScalarType>()
            concreteInt.shouldBeInstanceOf<TypeDecl>()

            // They should be different types
            abstractInt::class shouldBe AbstractIntType::class
            concreteInt::class shouldBe ScalarType::class
        }

        test("AbstractFloatType is distinguishable from concrete float types") {
            val span = Span.UNDEFINED
            val abstractFloat = AbstractFloatType(span)
            val concreteFloat = ScalarType(ScalarKind.F32, span)

            abstractFloat.shouldBeInstanceOf<AbstractFloatType>()
            abstractFloat.shouldBeInstanceOf<TypeDecl>()

            concreteFloat.shouldBeInstanceOf<ScalarType>()
            concreteFloat.shouldBeInstanceOf<TypeDecl>()

            // They should be different types
            abstractFloat::class shouldBe AbstractFloatType::class
            concreteFloat::class shouldBe ScalarType::class
        }

        test("AbstractIntType equality") {
            val type1 = AbstractIntType(Span(0u, 12u))
            val type2 = AbstractIntType(Span(10u, 22u))

            type1 shouldBe type2
            type1.hashCode() shouldBe type2.hashCode()
        }

        test("AbstractFloatType equality") {
            val type1 = AbstractFloatType(Span(0u, 14u))
            val type2 = AbstractFloatType(Span(10u, 24u))

            type1 shouldBe type2
            type1.hashCode() shouldBe type2.hashCode()
        }

        test("AbstractIntType and AbstractFloatType are not equal") {
            val intType = AbstractIntType(Span(0u, 12u))
            val floatType = AbstractFloatType(Span(0u, 14u))

            intType shouldNotBe floatType
        }

        test("Abstract types can be stored in a list of TypeDecl") {
            val span = Span.UNDEFINED
            val types: List<TypeDecl> = listOf(
                AbstractIntType(span),
                AbstractFloatType(span),
                ScalarType(ScalarKind.I32, span),
                ScalarType(ScalarKind.F32, span)
            )

            types shouldHaveSize 4
            types[0].shouldBeInstanceOf<AbstractIntType>()
            types[1].shouldBeInstanceOf<AbstractFloatType>()
            types[2].shouldBeInstanceOf<ScalarType>()
            types[3].shouldBeInstanceOf<ScalarType>()
        }

        test("AbstractIntType can be used as a TypeDecl") {
            val span = Span.UNDEFINED
            val abstractInt = AbstractIntType(span)

            // Should be usable anywhere a TypeDecl is expected
            val typeDecl: TypeDecl = abstractInt
            typeDecl.shouldBeInstanceOf<AbstractIntType>()
        }

        test("AbstractFloatType can be used as a TypeDecl") {
            val span = Span.UNDEFINED
            val abstractFloat = AbstractFloatType(span)

            // Should be usable anywhere a TypeDecl is expected
            val typeDecl: TypeDecl = abstractFloat
            typeDecl.shouldBeInstanceOf<AbstractFloatType>()
        }
    }

    // =========================================================================
    // LOWERING TESTS (AST to IR)
    // =========================================================================
    
    context("Lowering: abstract types to IR") {
        test("lower AbstractIntType to IR Abstract") {
            val source = "let x: abstract int;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            module.types.size shouldBe 1
            val type = module.types.toList()[0]
            type.inner.shouldBeInstanceOf<TypeInner.Abstract>()
            val abstractInner = type.inner as TypeInner.Abstract
            abstractInner.scalar shouldBe IrScalarKind.AbstractInt
        }
        
        test("lower AbstractFloatType to IR Abstract") {
            val source = "let y: abstract float;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            module.types.size shouldBe 1
            val type = module.types.toList()[0]
            type.inner.shouldBeInstanceOf<TypeInner.Abstract>()
            val abstractInner = type.inner as TypeInner.Abstract
            abstractInner.scalar shouldBe IrScalarKind.AbstractFloat
        }
        
        test("lower abstract types in function signature") {
            val source = "fn f(x: abstract int) -> abstract float { return 1.0; }"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            // Should have both abstract int (parameter) and abstract float (return)
            val abstractTypes = module.types.toList().map { it.inner }.filterIsInstance<TypeInner.Abstract>()
            abstractTypes.size shouldBe 2
            
            val scalarKinds = abstractTypes.map { it.scalar }
            scalarKinds shouldBe listOf(IrScalarKind.AbstractInt, IrScalarKind.AbstractFloat)
        }
        
        test("lower abstract types in struct") {
            val source = """
                struct S {
                    a: abstract int,
                    b: abstract float,
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            val abstractTypes = module.types.toList().map { it.inner }.filterIsInstance<TypeInner.Abstract>()
            abstractTypes.size shouldBe 2
        }
    }
    
    // =========================================================================
    // INTEGRATION TESTS
    // =========================================================================
    
    context("Integration: multiple abstract types in one module") {
        test("parse and lower module with various abstract types") {
            val source = """
                let a: abstract int = 10;
                let b: abstract float = 2.5;
                
                struct Config {
                    value: abstract int,
                    scale: abstract float,
                }
                
                fn process(x: abstract int, y: abstract float) -> abstract float {
                    return y;
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should parse without errors
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 4
            
            // Should lower to IR
            val lowerer = Lowerer()
            val module = lowerer.lower(unit)
            
            // Should have at least 2 abstract types (int and float)
            val abstractTypes = module.types.toList().map { it.inner }.filterIsInstance<TypeInner.Abstract>()
            abstractTypes.size shouldBe 2
        }

        test("parse abstract types with concrete types") {
            val source = """
                let a: i32 = 10;
                let b: abstract int = a;
                let c: f32 = 3.14;
                let d: abstract float = c;
            """.trimIndent()

            val parser = Parser(Lexer(source))
            val unit = parser.parse()

            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 4

            // Verify types
            val declA = unit.declarations[0] as VariableDecl
            declA.type.shouldBeInstanceOf<ScalarType>()

            val declB = unit.declarations[1] as VariableDecl
            declB.type.shouldBeInstanceOf<AbstractIntType>()

            val declC = unit.declarations[2] as VariableDecl
            declC.type.shouldBeInstanceOf<ScalarType>()

            val declD = unit.declarations[3] as VariableDecl
            declD.type.shouldBeInstanceOf<AbstractFloatType>()
        }

        test("parse abstract types with type aliases") {
            val source = """
                type MyInt = abstract int;
                type MyFloat = abstract float;
                
                let x: MyInt = 42;
                let y: MyFloat = 2.71;
            """.trimIndent()

            val parser = Parser(Lexer(source))
            val unit = parser.parse()

            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 4

            // First two are type aliases
            val typeAlias1 = unit.declarations[0] as TypeAliasDecl
            typeAlias1.type.shouldBeInstanceOf<AbstractIntType>()
 
            val typeAlias2 = unit.declarations[1] as TypeAliasDecl
            typeAlias2.type.shouldBeInstanceOf<AbstractFloatType>()
        }
    }
})
