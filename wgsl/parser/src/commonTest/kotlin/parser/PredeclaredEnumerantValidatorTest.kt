package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Tests for validation of predeclared enumerant values.
 * 
 * These tests verify that:
 * - Invalid enumerant values generate appropriate error messages
 * - Error messages include the category name, invalid value, and valid values
 * - Validation works in different contexts (variable declarations, parameters, etc.)
 */
class PredeclaredEnumerantValidatorTest : FunSpec({
    
    context("valid enumerant values") {
        test("all AddressMode values are valid") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            values.forEach { value ->
                val source = "let mode = AddressMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all FilterMode values are valid") {
            val values = listOf("nearest", "linear")
            values.forEach { value ->
                val source = "let filter = FilterMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all MipmapFilterMode values are valid") {
            val values = listOf("nearest", "linear")
            values.forEach { value ->
                val source = "let mipmap = MipmapFilterMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all ComparisonFunction values are valid") {
            val values = listOf("never", "less", "equal", "less_equal", "greater", "not_equal", "greater_equal", "always")
            values.forEach { value ->
                val source = "let func = ComparisonFunction.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all WrappingMode values are valid") {
            val values = listOf("clamp_to_edge", "repeat", "mirror_repeat")
            values.forEach { value ->
                val source = "let mode = WrappingMode.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                parser.errors.isEmpty() shouldBe true
            }
        }
        
        test("all Sampler categories values are valid") {
            val categories = listOf(
                "SamplerAddressMode" to listOf("clamp_to_edge", "repeat", "mirror_repeat", "clamp_to_border"),
                "SamplerFilterMode" to listOf("nearest", "linear"),
                "SamplerMipmapFilterMode" to listOf("nearest", "linear"),
                "SamplerBorderColor" to listOf("transparent_black", "opaque_black", "opaque_white")
            )
            
            categories.forEach { (category, values) ->
                values.forEach { value ->
                    val source = "let x = ${category}.$value;"
                    val parser = Parser(Lexer(source))
                    val unit = parser.parse()
                    parser.errors.isEmpty() shouldBe true
                }
            }
        }
        
        test("all Interpolation categories values are valid") {
            val categories = listOf(
                "InterpolationType" to listOf("perspective", "linear", "flat"),
                "InterpolationSampling" to listOf("center", "centroid", "sample")
            )
            
            categories.forEach { (category, values) ->
                values.forEach { value ->
                    val source = "let x = ${category}.$value;"
                    val parser = Parser(Lexer(source))
                    val unit = parser.parse()
                    parser.errors.isEmpty() shouldBe true
                }
            }
        }
        
        test("all BuiltinValue values are valid") {
            val values = listOf(
                "position", "vertex_index", "instance_index", "front_facing",
                "primitive_index", "sample_index", "sample_mask", "viewport_index"
            )
            values.forEach { value ->
                val source = "let x = BuiltinValue.$value;"
                val parser = Parser(Lexer(source))
                val unit = parser.parse()
                parser.errors.isEmpty() shouldBe true
            }
        }
    }
    
    context("invalid enumerant values") {
        test("AddressMode.invalid_value generates error") {
            val source = "let mode = AddressMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors shouldHaveSize 1
            parser.errors[0].message shouldContain "invalid_value"
            parser.errors[0].message shouldContain "AddressMode"
        }
        
        test("FilterMode.invalid_value generates error") {
            val source = "let filter = FilterMode.invalid;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors[0].message shouldContain "invalid"
            parser.errors[0].message shouldContain "FilterMode"
        }
        
        test("MipmapFilterMode.invalid_value generates error") {
            val source = "let mipmap = MipmapFilterMode.invalid;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors[0].message shouldContain "invalid"
            parser.errors[0].message shouldContain "MipmapFilterMode"
        }
        
        test("unknown category generates error") {
            val source = "let x = UnknownCategory.value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // UnknownCategory n'est pas une catégorie connue, donc ça tombe en MemberAccessExpr
            // sans générer d'erreur
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("error message format") {
        test("error message includes category name") {
            val source = "let mode = AddressMode.bad_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors[0].message shouldContain "AddressMode"
        }
        
        test("error message includes invalid value") {
            val source = "let mode = AddressMode.xyz123;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors[0].message shouldContain "xyz123"
        }
        
        test("error message includes valid values for AddressMode") {
            val source = "let mode = AddressMode.wrong;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldContain "clamp_to_edge"
            errorMessage shouldContain "repeat"
            errorMessage shouldContain "mirror_repeat"
        }
        
        test("error message includes valid values for FilterMode") {
            val source = "let filter = FilterMode.wrong;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            errorMessage shouldContain "nearest"
            errorMessage shouldContain "linear"
        }
        
        test("error message format is consistent") {
            val source = "let mode = AddressMode.invalid;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            val errorMessage = parser.errors[0].message
            // Format : "Invalid AddressMode value 'invalid'. Valid values are: clamp_to_edge, repeat, mirror_repeat"
            errorMessage shouldContain "Invalid"
            errorMessage shouldContain "value"
            errorMessage shouldContain "Valid values are:"
        }
    }
    
    context("validation in different contexts") {
        test("invalid enumerant in variable declaration") {
            val source = "let mode = AddressMode.invalid_value;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("invalid enumerant in function parameter default value") {
            val source = """
                fn sample(mode: AddressMode = AddressMode.invalid) {
                }
            "".trimIndent()
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("invalid enumerant in expression") {
            val source = """
                let mode = AddressMode.clamp_to_edge;
                let isInvalid = mode == AddressMode.invalid_value;
            """.trimIndent()
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("invalid enumerant in struct initializer") {
            val source = """
                struct Config {
                    mode: AddressMode,
                }
                let config = Config(AddressMode.invalid_value);
            """.trimIndent()
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
        }
    }
    
    context("multiple validation errors") {
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
        
        test("mixed valid and invalid enumerants") {
            val source = """
                let valid = AddressMode.clamp_to_edge;
                let invalid = AddressMode.invalid_value;
            """.trimIndent()
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors shouldHaveSize 1
            // valid devrait ne pas générer d'erreur
        }
        
        test("multiple invalid values in different categories") {
            val source = """
                let mode = AddressMode.invalid;
                let filter = FilterMode.invalid;
            """.trimIndent()
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isNotEmpty() shouldBe true
            parser.errors shouldHaveSize 2
        }
    }
    
    context("edge cases") {
        test("empty string as value generates error") {
            // Cela ne peut pas vraiment se produire dans un parsing normal
            // mais on teste que le validateur le gère correctement
            val source = "let mode = AddressMode.;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Cela devrait générer une erreur de parsing (pas de membre après .)
            parser.errors.isNotEmpty() shouldBe true
        }
        
        test("numeric value as enumerant generates error") {
            val source = "let mode = AddressMode.0;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // 0 n'est pas une valeur valide pour AddressMode
            parser.errors.isNotEmpty() shouldBe true
        }
    }
})
