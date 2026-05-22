package io.ygdrasil.wgsl.ast

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ygdrasil.wgsl.ir.Span

/**
 * Tests for predeclared enumerant registry functions.
 * 
 * Tests the helper functions that act as a registry for all predeclared enumerants:
 * - getPredeclaredEnumerant()
 * - getPredeclaredEnumerantValues()
 * - allPredeclaredEnumerantCategories
 */
class PredeclaredEnumerantRegistryTest : FunSpec({
    
    context("Predeclared enumerant categories") {
        test("all expected categories are defined") {
            val expectedCategories = listOf(
                "AddressMode",
                "WrappingMode",
                "FilterMode",
                "MipmapFilterMode",
                "ComparisonFunction",
                "SamplerAddressMode",
                "SamplerFilterMode",
                "SamplerMipmapFilterMode",
                "SamplerBorderColor",
                "InterpolationType",
                "InterpolationSampling",
                "BuiltinValue"
            )
            
            expectedCategories.forEach { category ->
                allPredeclaredEnumerantCategories shouldContain category
            }
        }
        
        test("all categories have values") {
            allPredeclaredEnumerantCategories.forEach { category ->
                val values = getPredeclaredEnumerantValues(category)
                values.isNotEmpty() shouldBe true
            }
        }
    }
    
    context("AddressMode category") {
        test("has all required values") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            val addressModes = getPredeclaredEnumerantValues("AddressMode")
            
            addressModes shouldHaveSize 3
            values.forEach { value ->
                addressModes shouldContain value
            }
        }
        
        test("getPredeclaredEnumerant returns enumerant for each value") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            val span = Span(0u, 10u)
            
            values.forEach { value ->
                val enumerant = getPredeclaredEnumerant("AddressMode", value, span)
                enumerant shouldNotBe null
                enumerant?.category shouldBe "AddressMode"
                enumerant?.value shouldBe value
            }
        }
        
        test("getPredeclaredEnumerant returns null for invalid value") {
            val span = Span(0u, 10u)
            val enumerant = getPredeclaredEnumerant("AddressMode", "invalid_value", span)
            enumerant shouldBe null
        }
        
        test("ClampToEdge has correct properties") {
            val span = Span(0u, 12u)
            val mode = ClampToEdge(span)
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "clamp_to_edge"
            mode.span shouldBe span
        }
        
        test("Repeat has correct properties") {
            val span = Span(0u, 6u)
            val mode = Repeat(span)
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "repeat"
        }
        
        test("MirrorRepeat has correct properties") {
            val span = Span(0u, 13u)
            val mode = MirrorRepeat(span)
            mode.category shouldBe "AddressMode"
            mode.value shouldBe "mirror_repeat"
        }
    }
    
    context("WrappingMode category") {
        test("has all required values") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            val wrappingModes = getPredeclaredEnumerantValues("WrappingMode")
            
            wrappingModes shouldHaveSize 3
            values.forEach { value ->
                wrappingModes shouldContain value
            }
        }
        
        test("getPredeclaredEnumerant returns enumerant for each value") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            val span = Span(0u, 10u)
            
            values.forEach { value ->
                val enumerant = getPredeclaredEnumerant("WrappingMode", value, span)
                enumerant shouldNotBe null
                enumerant?.category shouldBe "WrappingMode"
                enumerant?.value shouldBe value
            }
        }
        
        test("WrappingClampToEdge has correct properties") {
            val span = Span(0u, 12u)
            val mode = WrappingClampToEdge(span)
            mode.category shouldBe "WrappingMode"
            mode.value shouldBe "clamp_to_edge"
        }
        
        test("WrappingRepeat has correct properties") {
            val span = Span(0u, 6u)
            val mode = WrappingRepeat(span)
            mode.category shouldBe "WrappingMode"
            mode.value shouldBe "repeat"
        }
        
        test("WrappingMirrorRepeat has correct properties") {
            val span = Span(0u, 13u)
            val mode = WrappingMirrorRepeat(span)
            mode.category shouldBe "WrappingMode"
            mode.value shouldBe "mirror_repeat"
        }
    }
    
    context("FilterMode category") {
        test("has all required values") {
            val values = listOf("nearest", "linear")
            val filterModes = getPredeclaredEnumerantValues("FilterMode")
            
            filterModes shouldHaveSize 2
            values.forEach { value ->
                filterModes shouldContain value
            }
        }
        
        test("getPredeclaredEnumerant returns enumerant for each value") {
            val values = listOf("nearest", "linear")
            val span = Span(0u, 10u)
            
            values.forEach { value ->
                val enumerant = getPredeclaredEnumerant("FilterMode", value, span)
                enumerant shouldNotBe null
                enumerant?.category shouldBe "FilterMode"
                enumerant?.value shouldBe value
            }
        }
        
        test("Nearest has correct properties") {
            val span = Span(0u, 7u)
            val mode = Nearest(span)
            mode.category shouldBe "FilterMode"
            mode.value shouldBe "nearest"
        }
        
        test("Linear has correct properties") {
            val span = Span(0u, 6u)
            val mode = Linear(span)
            mode.category shouldBe "FilterMode"
            mode.value shouldBe "linear"
        }
    }
    
    context("MipmapFilterMode category") {
        test("has all required values") {
            val values = listOf("nearest", "linear")
            val mipmapModes = getPredeclaredEnumerantValues("MipmapFilterMode")
            
            mipmapModes shouldHaveSize 2
            values.forEach { value ->
                mipmapModes shouldContain value
            }
        }
        
        test("getPredeclaredEnumerant returns enumerant for each value") {
            val values = listOf("nearest", "linear")
            val span = Span(0u, 10u)
            
            values.forEach { value ->
                val enumerant = getPredeclaredEnumerant("MipmapFilterMode", value, span)
                enumerant shouldNotBe null
                enumerant?.category shouldBe "MipmapFilterMode"
                enumerant?.value shouldBe value
            }
        }
    }
    
    context("ComparisonFunction category") {
        test("has all required values") {
            val values = listOf("never", "less", "equal", "less_equal", "greater", "not_equal", "greater_equal", "always")
            val comparisonFunctions = getPredeclaredEnumerantValues("ComparisonFunction")
            
            comparisonFunctions shouldHaveSize 8
            values.forEach { value ->
                comparisonFunctions shouldContain value
            }
        }
        
        test("getPredeclaredEnumerant returns enumerant for each value") {
            val values = listOf("never", "less", "equal", "less_equal", "greater", "not_equal", "greater_equal", "always")
            val span = Span(0u, 10u)
            
            values.forEach { value ->
                val enumerant = getPredeclaredEnumerant("ComparisonFunction", value, span)
                enumerant shouldNotBe null
                enumerant?.category shouldBe "ComparisonFunction"
                enumerant?.value shouldBe value
            }
        }
    }
    
    context("Sampler categories") {
        test("SamplerAddressMode has all required values") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat", "clamp_to_border")
            val samplerAddressModes = getPredeclaredEnumerantValues("SamplerAddressMode")
            
            samplerAddressModes shouldHaveSize 4
            values.forEach { value ->
                samplerAddressModes shouldContain value
            }
        }
        
        test("SamplerFilterMode has all required values") {
            val values = listOf("nearest", "linear")
            val samplerFilterModes = getPredeclaredEnumerantValues("SamplerFilterMode")
            
            samplerFilterModes shouldHaveSize 2
            values.forEach { value ->
                samplerFilterModes shouldContain value
            }
        }
        
        test("SamplerMipmapFilterMode has all required values") {
            val values = listOf("nearest", "linear")
            val samplerMipmapModes = getPredeclaredEnumerantValues("SamplerMipmapFilterMode")
            
            samplerMipmapModes shouldHaveSize 2
            values.forEach { value ->
                samplerMipmapModes shouldContain value
            }
        }
        
        test("SamplerBorderColor has all required values") {
            val values = listOf("transparent_black", "opaque_black", "opaque_white")
            val samplerBorderColors = getPredeclaredEnumerantValues("SamplerBorderColor")
            
            samplerBorderColors shouldHaveSize 3
            values.forEach { value ->
                samplerBorderColors shouldContain value
            }
        }
    }
    
    context("Interpolation categories") {
        test("InterpolationType has all required values") {
            val values = listOf("perspective", "linear", "flat")
            val interpolationTypes = getPredeclaredEnumerantValues("InterpolationType")
            
            interpolationTypes shouldHaveSize 3
            values.forEach { value ->
                interpolationTypes shouldContain value
            }
        }
        
        test("InterpolationSampling has all required values") {
            val values = listOf("center", "centroid", "sample")
            val interpolationSamplings = getPredeclaredEnumerantValues("InterpolationSampling")
            
            interpolationSamplings shouldHaveSize 3
            values.forEach { value ->
                interpolationSamplings shouldContain value
            }
        }
    }
    
    context("BuiltinValue category") {
        test("has all required values") {
            val values = listOf(
                "position", "vertex_index", "instance_index", "front_facing",
                "primitive_index", "sample_index", "sample_mask", "viewport_index"
            )
            val builtinValues = getPredeclaredEnumerantValues("BuiltinValue")
            
            builtinValues shouldHaveSize 8
            values.forEach { value ->
                builtinValues shouldContain value
            }
        }
        
        test("getPredeclaredEnumerant returns enumerant for each value") {
            val values = listOf(
                "position", "vertex_index", "instance_index", "front_facing",
                "primitive_index", "sample_index", "sample_mask", "viewport_index"
            )
            val span = Span(0u, 10u)
            
            values.forEach { value ->
                val enumerant = getPredeclaredEnumerant("BuiltinValue", value, span)
                enumerant shouldNotBe null
                enumerant?.category shouldBe "BuiltinValue"
                enumerant?.value shouldBe value
            }
        }
    }
    
    context("Invalid categories") {
        test("getPredeclaredEnumerantValues returns empty list for unknown category") {
            val values = getPredeclaredEnumerantValues("UnknownCategory")
            values.isEmpty() shouldBe true
        }
        
        test("getPredeclaredEnumerant returns null for unknown category") {
            val span = Span(0u, 10u)
            val enumerant = getPredeclaredEnumerant("UnknownCategory", "value", span)
            enumerant shouldBe null
        }
    }
    
    context("All valid combinations") {
        test("getPredeclaredEnumerant returns enumerant for all valid category-value combinations") {
            val span = Span(0u, 10u)
            
            allPredeclaredEnumerantCategories.forEach { category ->
                val values = getPredeclaredEnumerantValues(category)
                values.forEach { value ->
                    val enumerant = getPredeclaredEnumerant(category, value, span)
                    enumerant shouldNotBe null
                    enumerant?.category shouldBe category
                    enumerant?.value shouldBe value
                }
            }
        }
    }
    
    context("Null handling") {
        test("getPredeclaredEnumerant returns null for invalid combinations") {
            val span = Span(0u, 10u)
            
            // Category valide mais valeur invalide
            val enumerant1 = getPredeclaredEnumerant("AddressMode", "invalid", span)
            enumerant1 shouldBe null
            
            // Catégorie invalide
            val enumerant2 = getPredeclaredEnumerant("InvalidCategory", "clamp_to_edge", span)
            enumerant2 shouldBe null
            
            // Les deux invalides
            val enumerant3 = getPredeclaredEnumerant("InvalidCategory", "invalid", span)
            enumerant3 shouldBe null
        }
    }
})
