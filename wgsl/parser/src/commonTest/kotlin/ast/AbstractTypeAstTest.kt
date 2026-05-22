package io.ygdrasil.wgsl.ast

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ir.Span

/**
 * Tests for AbstractIntType and AbstractFloatType AST nodes.
 * 
 * Verifies that:
 * - Abstract types can be created manually
 * - Abstract types are distinguishable from concrete types
 * - Abstract types are serializable
 * - Abstract types can be used where scalar types are expected
 */
class AbstractTypeAstTest : FunSpec({
    
    context("AbstractIntType") {
        test("can be created manually") {
            val span = Span(0u, 12u)
            val abstractInt = AbstractIntType(span)
            
            abstractInt shouldBeInstanceOf<TypeDecl>()
            abstractInt.span shouldBe span
        }
        
        test("is distinguishable from concrete integer types") {
            val span = Span.UNDEFINED
            val abstractInt = AbstractIntType(span)
            val concreteInt = ScalarType(ScalarKind.I32, span)
            
            abstractInt shouldBeInstanceOf<AbstractIntType>()
            abstractInt shouldBeInstanceOf<TypeDecl>()
            
            concreteInt shouldBeInstanceOf<ScalarType>()
            concreteInt shouldBeInstanceOf<TypeDecl>()
            
            // They should be different types
            abstractInt::class shouldBe AbstractIntType::class
            concreteInt::class shouldBe ScalarType::class
        }
        
        test("is serializable via toString") {
            val span = Span(0u, 12u)
            val abstractInt = AbstractIntType(span)
            
            val str = abstractInt.toString()
            str shouldBe "AbstractIntType(span=${span.toString()})"
        }
        
        test("can be used as a TypeDecl") {
            val span = Span.UNDEFINED
            val abstractInt = AbstractIntType(span)
            
            // Should be usable anywhere a TypeDecl is expected
            val typeDecl: TypeDecl = abstractInt
            typeDecl shouldBeInstanceOf<AbstractIntType>()
        }
    }
    
    context("AbstractFloatType") {
        test("can be created manually") {
            val span = Span(0u, 14u)
            val abstractFloat = AbstractFloatType(span)
            
            abstractFloat shouldBeInstanceOf<TypeDecl>()
            abstractFloat.span shouldBe span
        }
        
        test("is distinguishable from concrete float types") {
            val span = Span.UNDEFINED
            val abstractFloat = AbstractFloatType(span)
            val concreteFloat = ScalarType(ScalarKind.F32, span)
            
            abstractFloat shouldBeInstanceOf<AbstractFloatType>()
            abstractFloat shouldBeInstanceOf<TypeDecl>()
            
            concreteFloat shouldBeInstanceOf<ScalarType>()
            concreteFloat shouldBeInstanceOf<TypeDecl>()
            
            // They should be different types
            abstractFloat::class shouldBe AbstractFloatType::class
            concreteFloat::class shouldBe ScalarType::class
        }
        
        test("is serializable via toString") {
            val span = Span(0u, 14u)
            val abstractFloat = AbstractFloatType(span)
            
            val str = abstractFloat.toString()
            str shouldBe "AbstractFloatType(span=${span.toString()})"
        }
        
        test("can be used as a TypeDecl") {
            val span = Span.UNDEFINED
            val abstractFloat = AbstractFloatType(span)
            
            // Should be usable anywhere a TypeDecl is expected
            val typeDecl: TypeDecl = abstractFloat
            typeDecl shouldBeInstanceOf<AbstractFloatType>()
        }
    }
    
    context("Abstract types in collections") {
        test("can be stored in a list of TypeDecl") {
            val span = Span.UNDEFINED
            val types: List<TypeDecl> = listOf(
                AbstractIntType(span),
                AbstractFloatType(span),
                ScalarType(ScalarKind.I32, span),
                ScalarType(ScalarKind.F32, span)
            )
            
            types shouldHaveSize 4
            types[0] shouldBeInstanceOf<AbstractIntType>()
            types[1] shouldBeInstanceOf<AbstractFloatType>()
            types[2] shouldBeInstanceOf<ScalarType>()
            types[3] shouldBeInstanceOf<ScalarType>()
        }
    }
    
    context("Equality") {
        test("AbstractIntType instances with same span are equal") {
            val span = Span(0u, 12u)
            val int1 = AbstractIntType(span)
            val int2 = AbstractIntType(span)
            
            int1 shouldBe int2
        }
        
        test("AbstractFloatType instances with same span are equal") {
            val span = Span(0u, 14u)
            val float1 = AbstractFloatType(span)
            val float2 = AbstractFloatType(span)
            
            float1 shouldBe float2
        }
        
        test("AbstractIntType and AbstractFloatType are not equal") {
            val span = Span.UNDEFINED
            val abstractInt = AbstractIntType(span)
            val abstractFloat = AbstractFloatType(span)
            
            abstractInt shouldBeInstanceOf<AbstractIntType>()
            abstractFloat shouldBeInstanceOf<AbstractFloatType>()
            
            // Different types should not be equal
            (abstractInt == abstractFloat) shouldBe false
        }
    }
})
