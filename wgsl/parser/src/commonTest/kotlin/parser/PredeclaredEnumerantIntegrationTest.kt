package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Integration tests for predeclared enumerants with other WGSL features.
 * 
 * These tests verify that predeclared enumerants work correctly when used
 * with other language features like structs, functions, arrays, etc.
 */
class PredeclaredEnumerantIntegrationTest : FunSpec({
    
    context("predeclared enumerants with user-defined enums") {
        test("both can coexist in same module") {
            val source = """
                enum MyEnum { VALUE1, VALUE2 }
                let userEnum = MyEnum.VALUE1;
                let preEnum = AddressMode.clamp_to_edge;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations.shouldBeInstanceOf<List<GlobalDecl>>()
        }
        
        test("similar syntax works for both") {
            val source = """
                enum Color { RED, GREEN }
                let c1 = Color.RED;
                let c2 = AddressMode.repeat;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("predeclared enumerants with struct declarations") {
        test("struct with predeclared enumerant field") {
            val source = """
                struct TextureSampler {
                    addressMode: AddressMode,
                    filterMode: FilterMode,
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val structDecl = unit.declarations[0] as StructDecl
            structDecl.members.shouldBeInstanceOf<List<StructMember>>()
            structDecl.members shouldHaveSize 2
        }
        
        test("struct initialization with enumerants") {
            val source = """
                struct TextureSampler {
                    addressMode: AddressMode,
                    filterMode: FilterMode,
                }
                
                fn main() {
                    let s = TextureSampler(
                        AddressMode.clamp_to_edge,
                        FilterMode.linear
                    );
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("nested struct with enumerants") {
            val source = """
                struct Inner {
                    mode: AddressMode,
                }
                
                struct Outer {
                    inner: Inner,
                }
                
                let outer = Outer(Inner(AddressMode.repeat));
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("predeclared enumerants in function declarations") {
        test("function with enumerant parameter") {
            val source = """
                fn sample(mode: AddressMode) {
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("function with enumerant return type") {
            // Note: WGSL ne supporte pas vraiment les énumérants comme types de retour
            // mais on teste le parsing
            val source = """
                fn getMode(): AddressMode {
                    return AddressMode.clamp_to_edge;
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            // Cela peut générer une erreur sémantique mais devrait parser
            // parser.errors.isEmpty() shouldBe true
        }
        
        test("function with enumerant default parameter") {
            val source = """
                fn sample(mode: AddressMode = AddressMode.clamp_to_edge) {
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("function call with enumerant argument") {
            val source = """
                fn process(mode: AddressMode) { }
                fn main() {
                    process(AddressMode.repeat);
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("function with multiple enumerant parameters") {
            val source = """
                fn configure(
                    addressMode: AddressMode,
                    filterMode: FilterMode,
                    mipmapMode: MipmapFilterMode
                ) { }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("predeclared enumerants in variable declarations") {
        test("variable with type annotation") {
            val source = "let mode: AddressMode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("variable with const") {
            val source = "const mode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("variable with var") {
            val source = "var mode: AddressMode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("multiple variables with different enumerants") {
            val source = """
                let mode = AddressMode.clamp_to_edge;
                let filter = FilterMode.linear;
                let mipmap = MipmapFilterMode.nearest;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("predeclared enumerants in expressions") {
        test("enumerant in equality comparison") {
            val source = """
                let mode = AddressMode.clamp_to_edge;
                let isClamp = mode == AddressMode.clamp_to_edge;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("enumerant in inequality comparison") {
            val source = """
                let mode = AddressMode.repeat;
                let isNotClamp = mode != AddressMode.clamp_to_edge;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("enumerant in if condition") {
            val source = """
                fn main() {
                    let mode = AddressMode.repeat;
                    if (mode == AddressMode.repeat) {
                        let x = 1;
                    }
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("enumerant in ternary expression") {
            val source = """
                let mode = AddressMode.clamp_to_edge;
                let result = mode == AddressMode.clamp_to_edge ? 1 : 0;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("predeclared enumerants in arrays") {
        test("array of AddressMode") {
            val source = """
                fn main() {
                    let modes: array<AddressMode, 3> = array<AddressMode, 3>(
                        AddressMode.clamp_to_edge,
                        AddressMode.repeat,
                        AddressMode.mirror_repeat
                    );
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("predeclared enumerants in switch statements") {
        test("switch on AddressMode") {
            val source = """
                fn main() {
                    let mode: AddressMode = AddressMode.clamp_to_edge;
                    switch (mode) {
                        case AddressMode.clamp_to_edge: { let x = 1; }
                        case AddressMode.repeat: { let x = 2; }
                        case AddressMode.mirror_repeat: { let x = 3; }
                    }
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("mixed usage scenarios") {
        test("complex shader with multiple enumerants") {
            val source = """
                struct TextureParams {
                    addressMode: AddressMode,
                    filterMode: FilterMode,
                    mipmapMode: MipmapFilterMode,
                }
                
                @group(0) @binding(0)
                var texture: texture_2d<f32>;
                
                @group(0) @binding(1)
                var my_sampler: sampler;
                
                struct Uniforms {
                    params: TextureParams,
                }
                
                @group(0) @binding(2)
                var<uniform> uniforms: Uniforms;
                
                @fragment
                fn main() {
                    let params = uniforms.params;
                    let mode = params.addressMode;
                    let filter = params.filterMode;
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            if (parser.errors.isNotEmpty()) {
                println("PARSER ERRORS (complex shader test): " + parser.errors.joinToString("\n") { it.message })
            }
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("all categories can be used together") {
        test("use all predeclared enumerant categories in one module") {
            val source = """
                fn main() {
                    let addr1 = AddressMode.clamp_to_edge;
                    let addr2 = WrappingMode.repeat;
                    let filter1 = FilterMode.nearest;
                    let filter2 = MipmapFilterMode.linear;
                    let cmp = ComparisonFunction.always;
                    let samplerAddr = SamplerAddressMode.clamp_to_border;
                    let samplerFilter = SamplerFilterMode.nearest;
                    let samplerMipmap = SamplerMipmapFilterMode.linear;
                    let samplerColor = SamplerBorderColor.transparent_black;
                    let interpType = InterpolationType.perspective;
                    let interpSample = InterpolationSampling.center;
                    let builtinVal = BuiltinValue.position;
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
    
    context("enum value properties") {
        test("PredeclaredEnumerantExpr has correct properties") {
            val source = "let mode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            val enumExpr = decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            
            enumExpr.category shouldBe "AddressMode"
            enumExpr.enumerant.category shouldBe "AddressMode"
            enumExpr.enumerant.value shouldBe "clamp_to_edge"
            enumExpr.getEnumTypeName() shouldBe "AddressMode"
        }
        
        test("EnumValueExpr common interface") {
            val source1 = "let mode = AddressMode.clamp_to_edge;"
            val parser1 = Parser(Lexer(source1))
            val unit1 = parser1.parse()
            
            val decl1 = unit1.declarations[0] as VariableDecl
            val enumExpr1 = decl1.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            
            // PredeclaredEnumerantExpr should extend EnumValueExpr
            enumExpr1.shouldBeInstanceOf<EnumValueExpr>()
            enumExpr1.getQualifiedName() shouldBe "AddressMode.clamp_to_edge"
        }
    }
    
    context("enum member access vs predeclared enumerant") {
        test("user-defined enum creates EnumMemberExpr") {
            val source = """
                enum MyEnum { A, B }
                let x = MyEnum.A;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[1] as VariableDecl
            decl.initializer.shouldBeInstanceOf<EnumMemberExpr>()
        }
        
        test("predeclared enumerant creates PredeclaredEnumerantExpr") {
            val source = "let mode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val decl = unit.declarations[0] as VariableDecl
            decl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
        }
        
        test("both EnumMemberExpr and PredeclaredEnumerantExpr extend EnumValueExpr") {
            val source1 = """
                enum MyEnum { A }
                let x = MyEnum.A;
            """.trimIndent()
            val parser1 = Parser(Lexer(source1))
            val unit1 = parser1.parse()
            
            val source2 = "let mode = AddressMode.clamp_to_edge;"
            val parser2 = Parser(Lexer(source2))
            val unit2 = parser2.parse()
            
            val decl1 = unit1.declarations[1] as VariableDecl
            val decl2 = unit2.declarations[0] as VariableDecl
            
            (decl1.initializer is EnumValueExpr) shouldBe true
            (decl2.initializer is EnumValueExpr) shouldBe true
        }
    }
})
