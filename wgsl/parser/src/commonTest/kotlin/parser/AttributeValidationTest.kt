package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.IdentExpr
import io.ygdrasil.wgsl.ast.StructDecl
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Tests for WGSL attribute validation in parser.
 * Ensures that attributes like @invariant and @must_use are properly recognized
 * and handled during parsing of WGSL shader code.
 */
class AttributeValidationTest : FunSpec({
    context("Attribute Recognition") {
        test("INVARIANT attribute on function is parsed correctly") {
            val source = """
                @invariant
                fn main() {}
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("MUST_USE attribute on function is parsed correctly") {
            val source = """
                @must_use
                fn main() {}
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("INVARIANT attribute on variable is parsed correctly") {
            val source = """
                @invariant var x: i32;
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("combined INVARIANT and location attributes on function") {
            val source = """
                @invariant @location(0)
                fn main() {}
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("INVARIANT attribute on function parameter") {
            val source = """
                fn process(@builtin(position) @invariant pos: vec4<f32>) { }
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("INVARIANT attribute on function return type") {
            val source = """
                @vertex
                fn vs() -> @builtin(position) @invariant vec4<f32> {
                    return vec4<f32>(0.0);
                }
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("draw_index builtin attribute remains a builtin argument, not an unresolved identifier") {
            val source = """
                enable draw_index;

                struct Input {
                    @builtin(draw_index) draw_index: u32,
                }

                @vertex
                fn vertex(input: Input) -> @builtin(position) vec4<f32> {
                    return vec4<f32>(f32(input.draw_index), 1.0, 1.0, 1.0);
                }
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            val input = unit.declarations.filterIsInstance<StructDecl>().single()
            val vertex = unit.declarations.filterIsInstance<FunctionDecl>().single()

            (input.members.single().attributes.single { it.name == "builtin" }.args.single() as IdentExpr).name shouldBe "draw_index"
            (vertex.returnAttributes.single { it.name == "builtin" }.args.single() as IdentExpr).name shouldBe "position"
            TypeResolver().resolve(unit).isSuccess shouldBe true
        }
    }

    context("Access Mode in Pointer Types") {
        test("ptr with read access mode parses correctly") {
            val source = """
                fn main() {
                    var ptr_val: ptr<storage, i32, read>;
                }
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("ptr with write access mode parses correctly") {
            val source = """
                fn main() {
                    var ptr_val: ptr<storage, i32, write>;
                }
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }

        test("ptr with read_write access mode parses correctly") {
            val source = """
                fn main() {
                    var ptr_val: ptr<storage, i32, read_write>;
                }
            """.trimIndent()
            val lexer = Lexer(source)
            val parser = Parser(lexer)
            val unit = parser.parse()
            unit.declarations.shouldNotBeEmpty()
        }
    }

    context("Global attributes") {
        test("attributes on global variables") {
            val source = "@group(0) @binding(0) var<uniform> a: i32;"
            val parser = Parser(Lexer(source))
            parser.parse()
            parser.errors shouldBe emptyList()
        }

        test("attributes on override") {
            val source = "@id(1) override a: i32 = 1;"
            val parser = Parser(Lexer(source))
            parser.parse()
            parser.errors shouldBe emptyList()
        }
    }
})
