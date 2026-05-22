package io.ygdrasil.wgsl.ast

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ir.Span

/**
 * Tests for predeclared enumerant types.
 */
class PredeclaredEnumsTest : FunSpec({
    
    context("AddressMode enumerants") {
        test("ClampToEdge has correct properties") {
            val span = Span(0u, 12u)
            val mode = ClampToEdge(span)
            
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "clamp_to_edge"
            mode.span shouldBe span
            mode shouldBeInstanceOf<AddressMode>()
        }
        
        test("Repeat has correct properties") {
            val span = Span(0u, 6u)
            val mode = Repeat(span)
            
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "repeat"
            mode.span shouldBe span
        }
        
        test("MirrorRepeat has correct properties") {
            val span = Span(0u, 13u)
            val mode = MirrorRepeat(span)
            
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "mirror_repeat"
            mode.span shouldBe span
        }
    }
    
    context("FilterMode enumerants") {
        test("Nearest has correct properties") {
            val span = Span(0u, 7u)
            val mode = Nearest(span)
            
            mode.category shouldBe "FilterMode"
            mode.value shouldBe "nearest"
            mode.span shouldBe span
        }
        
        test("Linear has correct properties") {
            val span = Span(0u, 6u)
            val mode = Linear(span)
            
            mode.category shouldBe "FilterMode"
            mode.value shouldBe "linear"
            mode.span shouldBe span
        }
    }
    
    context("MipmapFilterMode enumerants") {
        test("MipmapNearest has correct properties") {
            val span = Span(0u, 13u)
            val mode = MipmapNearest(span)
            
            mode.category shouldBe "MipmapFilterMode"
            mode.value shouldBe "nearest"
            mode.span shouldBe span
        }
        
        test("MipmapLinear has correct properties") {
            val span = Span(0u, 12u)
            val mode = MipmapLinear(span)
            
            mode.category shouldBe "MipmapFilterMode"
            mode.value shouldBe "linear"
            mode.span shouldBe span
        }
    }
    
    context("InterpolationType enumerants") {
        test("Perspective has correct properties") {
            val span = Span(0u, 10u)
            val mode = Perspective(span)
            
            mode.category shouldBe "InterpolationType"
            mode.value shouldBe "perspective"
            mode.span shouldBe span
        }
        
        test("LinearInterpolation has correct properties") {
            val span = Span(0u, 6u)
            val mode = LinearInterpolation(span)
            
            mode.category shouldBe "InterpolationType"
            mode.value shouldBe "linear"
            mode.span shouldBe span
        }
        
        test("Flat has correct properties") {
            val span = Span(0u, 4u)
            val mode = Flat(span)
            
            mode.category shouldBe "InterpolationType"
            mode.value shouldBe "flat"
            mode.span shouldBe span
        }
    }
    
    context("InterpolationSampling enumerants") {
        test("Center has correct properties") {
            val span = Span(0u, 6u)
            val mode = Center(span)
            
            mode.category shouldBe "InterpolationSampling"
            mode.value shouldBe "center"
            mode.span shouldBe span
        }
        
        test("Centroid has correct properties") {
            val span = Span(0u, 8u)
            val mode = Centroid(span)
            
            mode.category shouldBe "InterpolationSampling"
            mode.value shouldBe "centroid"
            mode.span shouldBe span
        }
        
        test("Sample has correct properties") {
            val span = Span(0u, 6u)
            val mode = Sample(span)
            
            mode.category shouldBe "InterpolationSampling"
            mode.value shouldBe "sample"
            mode.span shouldBe span
        }
    }
    
    context("BuiltinValue enumerants") {
        test("Position has correct properties") {
            val span = Span(0u, 8u)
            val value = Position(span)
            
            value.category shouldBe "BuiltinValue"
            value.value shouldBe "position"
            value.span shouldBe span
        }
        
        test("VertexIndex has correct properties") {
            val span = Span(0u, 11u)
            val value = VertexIndex(span)
            
            value.category shouldBe "BuiltinValue"
            value.value shouldBe "vertex_index"
            value.span shouldBe span
        }
        
        test("InstanceIndex has correct properties") {
            val span = Span(0u, 13u)
            val value = InstanceIndex(span)
            
            value.category shouldBe "BuiltinValue"
            value.value shouldBe "instance_index"
            value.span shouldBe span
        }
        
        test("FrontFacing has correct properties") {
            val span = Span(0u, 12u)
            val value = FrontFacing(span)
            
            value.category shouldBe "BuiltinValue"
            value.value shouldBe "front_facing"
            value.span shouldBe span
        }
    }
    
    context("Helper functions") {
        test("getPredeclaredEnumerant returns correct AddressMode") {
            val span = Span(0u, 12u)
            val enumerant = getPredeclaredEnumerant("AddressMode", "clamp_to_edge", span)
            
            enumerant shouldBeInstanceOf<ClampToEdge>()
            enumerant?.value shouldBe "clamp_to_edge"
        }
        
        test("getPredeclaredEnumerant returns correct FilterMode") {
            val span = Span(0u, 7u)
            val enumerant = getPredeclaredEnumerant("FilterMode", "nearest", span)
            
            enumerant shouldBeInstanceOf<Nearest>()
            enumerant?.value shouldBe "nearest"
        }
        
        test("getPredeclaredEnumerant returns null for unknown category") {
            val span = Span(0u, 5u)
            val enumerant = getPredeclaredEnumerant("UnknownCategory", "value", span)
            
            enumerant shouldBe null
        }
        
        test("getPredeclaredEnumerant returns null for unknown value") {
            val span = Span(0u, 5u)
            val enumerant = getPredeclaredEnumerant("AddressMode", "unknown", span)
            
            enumerant shouldBe null
        }
        
        test("allPredeclaredEnumerantCategories contains all categories") {
            allPredeclaredEnumerantCategories shouldHaveSize 6
            allPredeclaredEnumerantCategories shouldContain "AddressMode"
            allPredeclaredEnumerantCategories shouldContain "FilterMode"
            allPredeclaredEnumerantCategories shouldContain "MipmapFilterMode"
            allPredeclaredEnumerantCategories shouldContain "InterpolationType"
            allPredeclaredEnumerantCategories shouldContain "InterpolationSampling"
            allPredeclaredEnumerantCategories shouldContain "BuiltinValue"
        }
        
        test("getPredeclaredEnumerantValues returns correct values for AddressMode") {
            val values = getPredeclaredEnumerantValues("AddressMode")
            values shouldHaveSize 3
            values shouldContain "clamp_to_edge"
            values shouldContain "repeat"
            values shouldContain "mirror_repeat"
        }
        
        test("getPredeclaredEnumerantValues returns correct values for FilterMode") {
            val values = getPredeclaredEnumerantValues("FilterMode")
            values shouldHaveSize 2
            values shouldContain "nearest"
            values shouldContain "linear"
        }
        
        test("getPredeclaredEnumerantValues returns empty list for unknown category") {
            val values = getPredeclaredEnumerantValues("UnknownCategory")
            values shouldHaveSize 0
        }
    }
})
