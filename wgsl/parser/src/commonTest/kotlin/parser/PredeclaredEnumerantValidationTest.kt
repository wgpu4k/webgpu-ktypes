package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Tests for validation of predeclared enumerant values.
 */
class PredeclaredEnumerantValidationTest : FunSpec({
    
    context("Valid enumerant values") {
        test("AddressMode.clamp_to_edge is valid") {
            val source = "let mode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("AddressMode.repeat is valid") {
            val source = "let mode = AddressMode.repeat;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("AddressMode.mirror_repeat is valid") {
            val source = "let mode = AddressMode.mirror_repeat;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("FilterMode.nearest is valid") {
            val source = "let mode = FilterMode.nearest;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("FilterMode.linear is valid") {
            val source = "let mode = FilterMode.linear;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("all AddressMode values are valid") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            for (value in values) {
                val source = "let mode = AddressMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all FilterMode values are valid") {
            val values = listOf("nearest", "linear")
            for (value in values) {
                val source = "let mode = FilterMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all MipmapFilterMode values are valid") {
            val values = listOf("nearest", "linear")
            for (value in values) {
                val source = "let mode = MipmapFilterMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all ComparisonFunction values are valid") {
            val values = listOf("never", "less", "equal", "less_equal", "greater", "not_equal", "greater_equal", "always")
            for (value in values) {
                val source = "let func = ComparisonFunction.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all WrappingMode values are valid") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            for (value in values) {
                val source = "let mode = WrappingMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all SamplerAddressMode values are valid") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat", "clamp_to_border")
            for (value in values) {
                val source = "let mode = SamplerAddressMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all SamplerFilterMode values are valid") {
            val values = listOf("nearest", "linear")
            for (value in values) {
                val source = "let mode = SamplerFilterMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all SamplerMipmapFilterMode values are valid") {
            val values = listOf("nearest", "linear")
            for (value in values) {
                val source = "let mode = SamplerMipmapFilterMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all SamplerBorderColor values are valid") {
            val values = listOf("transparent_black", "opaque_black", "opaque_white")
            for (value in values) {
                val source = "let color = SamplerBorderColor.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all InterpolationType values are valid") {
            val values = listOf("perspective", "linear", "flat")
            for (value in values) {
                val source = "let x = InterpolationType.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all InterpolationSampling values are valid") {
            val values = listOf("center", "centroid", "sample")
            for (value in values) {
                val source = "let sampling = InterpolationSampling.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all BuiltinValue values are valid") {
            val values = listOf("position", "vertex_index", "instance_index", "draw_index", "front_facing",
                "primitive_index", "sample_index", "sample_mask", "viewport_index")
            for (value in values) {
                val source = "let x = BuiltinValue.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                
                parser.errors.isEmpty() shouldBe true
            }
        }
    }
    
    context("Invalid enumerant values") {
        test("AddressMode.invalid_value generates error") {
            val source = "let mode = AddressMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            
            // Check error message contains information about valid values
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid AddressMode value 'invalid_value'. Valid values are: clamp_to_edge, repeat, mirror_repeat"
        }
        
        test("FilterMode.invalid_value generates error") {
            val source = "let mode = FilterMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid FilterMode value 'invalid_value'. Valid values are: nearest, linear"
        }
        
        test("MipmapFilterMode.invalid_value generates error") {
            val source = "let mode = MipmapFilterMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("InterpolationType.invalid_value generates error") {
            val source = "let x = InterpolationType.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("WrappingMode.invalid_value generates error") {
            val source = "let mode = WrappingMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid WrappingMode value 'invalid_value'. Valid values are: clamp_to_edge, repeat, mirror_repeat"
        }
        
        test("ComparisonFunction.invalid_value generates error") {
            val source = "let func = ComparisonFunction.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid ComparisonFunction value 'invalid_value'. Valid values are: never, less, equal, less_equal, greater, not_equal, greater_equal, always"
        }
        
        test("SamplerAddressMode.invalid_value generates error") {
            val source = "let mode = SamplerAddressMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid SamplerAddressMode value 'invalid_value'. Valid values are: clamp_to_edge, repeat, mirror_repeat, clamp_to_border"
        }
        
        test("SamplerFilterMode.invalid_value generates error") {
            val source = "let mode = SamplerFilterMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid SamplerFilterMode value 'invalid_value'. Valid values are: nearest, linear"
        }
        
        test("SamplerMipmapFilterMode.invalid_value generates error") {
            val source = "let mode = SamplerMipmapFilterMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid SamplerMipmapFilterMode value 'invalid_value'. Valid values are: nearest, linear"
        }
        
        test("SamplerBorderColor.invalid_value generates error") {
            val source = "let color = SamplerBorderColor.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid SamplerBorderColor value 'invalid_value'. Valid values are: transparent_black, opaque_black, opaque_white"
        }
        
        test("InterpolationSampling.invalid_value generates error") {
            val source = "let sampling = InterpolationSampling.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid InterpolationSampling value 'invalid_value'. Valid values are: center, centroid, sample"
        }
        
        test("BuiltinValue.invalid_value generates error") {
            val source = "let x = BuiltinValue.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid BuiltinValue value 'invalid_value'. Valid values are: position, vertex_index, instance_index, draw_index, front_facing, primitive_index, sample_index, sample_mask, viewport_index"
        }
        
        test("MipmapFilterMode.invalid_value generates error with message") {
            val source = "let mode = MipmapFilterMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid MipmapFilterMode value 'invalid_value'. Valid values are: nearest, linear"
        }
    }
    
    context("Unknown categories") {
        test("UnknownCategory.value does not generate error but creates MemberAccessExpr") {
            val source = "let x = UnknownCategory.value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Should parse without errors (falls back to MemberAccessExpr)
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            decl.initializer.shouldBeInstanceOf<MemberAccessExpr>()
        }
    }
    
    context("Error message format") {
        test("error message includes category name") {
            val source = "let mode = AddressMode.bad_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldBe "Invalid AddressMode value 'bad_value'. Valid values are: clamp_to_edge, repeat, mirror_repeat"
        }
        
        test("error message includes invalid value") {
            val source = "let mode = AddressMode.xyz123;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage.contains("xyz123") shouldBe true
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
    
    context("Multiple errors") {
        test("multiple invalid enumerant values") {
            val source = """
                let mode1 = AddressMode.invalid1;
                let mode2 = AddressMode.invalid2;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors shouldHaveSize 2
        }
    }
    
    context("Mixed valid and invalid") {
        test("valid and invalid in same module") {
            val source = """
                let valid = AddressMode.clamp_to_edge;
                let invalid = AddressMode.invalid_value;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors shouldHaveSize 1
            
            // The valid one should still be parsed correctly
            val validDecl = unit.declarations[0] as VariableDecl
            validDecl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
        }
    }
})
