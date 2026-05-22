package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.ir.Span

/**
 * Tests for abstract type resolution and compatibility in TypeResolver.
 */
class TypeResolverAbstractTypeTest : FunSpec({
    
    lateinit var typeResolver: TypeResolver
    
    beforeTest {
        typeResolver = TypeResolver()
    }
    
    // =========================================================================
    // TYPE COMPATIBILITY TESTS
    // =========================================================================
    
    context("Type compatibility for abstract types") {
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
})
