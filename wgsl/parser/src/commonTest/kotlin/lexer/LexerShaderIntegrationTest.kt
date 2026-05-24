package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactly

/**
 * End-to-end lexer tests that mirror real WGSL shader programs.
 *
 * These tests verify that the lexer correctly tokenizes complete,
 * meaningful shader constructs as a user would write them.
 * Each test represents a self-contained shader feature.
 */
class LexerShaderIntegrationTest : FunSpec({

    context("Vertex shader: position passthrough") {
        /**
         * @vertex
         * fn vs_main(@builtin(vertex_index) vi: u32) -> @builtin(position) vec4<f32> {
         *     return vec4(0.0, 0.0, 0.0, 1.0);
         * }
         */
        test("@vertex function with builtin inputs and return type is fully tokenized") {
            val source = """
                @vertex
                fn vs_main(@builtin(vertex_index) vi: u32) -> @builtin(position) vec4<f32> {
                    return vec4(0.0, 0.0, 0.0, 1.0);
                }
            """.trimIndent()
            val tokens = tokenizeSignificant(source)

            // Must not contain any UNKNOWN token
            tokens.none { it.kind == TokenKind.UNKNOWN } shouldBe true

            // Key structural tokens present
            tokens.any { it.kind == TokenKind.AT } shouldBe true
            tokens.any { it.kind == TokenKind.VERTEX } shouldBe true
            tokens.any { it.kind == TokenKind.FN } shouldBe true
            tokens.any { it.kind == TokenKind.RETURN } shouldBe true
            tokens.any { it.kind == TokenKind.BUILTIN } shouldBe true
            tokens.any { it.kind == TokenKind.VERTEX_INDEX } shouldBe true
            tokens.any { it.kind == TokenKind.POSITION } shouldBe true
            tokens.any { it.kind == TokenKind.VEC } shouldBe true
            tokens.any { it.kind == TokenKind.F32 } shouldBe true
        }
    }

    context("Vertex shader: draw index input") {
        test("@builtin(draw_index) is tokenized as a builtin value") {
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
            val tokens = tokenizeSignificant(source)

            tokens.none { it.kind == TokenKind.UNKNOWN } shouldBe true
            tokens.any { it.kind == TokenKind.ENABLE } shouldBe true
            tokens.count { it.kind == TokenKind.DRAW_INDEX } shouldBe 4
        }
    }

    context("Fragment shader: uniform buffer and texture sampling") {
        /**
         * @group(0) @binding(0) var<uniform> ubo: MyUBO;
         * @group(0) @binding(1) var tex: texture_2d<f32>;
         * @group(0) @binding(2) var smp: sampler;
         */
        test("uniform, texture and sampler binding declarations are tokenized") {
            val source = """
                @group(0) @binding(0) var<uniform> ubo: MyUBO;
                @group(0) @binding(1) var tex: texture_2d<f32>;
                @group(0) @binding(2) var smp: sampler;
            """.trimIndent()
            val tokens = tokenizeSignificant(source)

            tokens.none { it.kind == TokenKind.UNKNOWN } shouldBe true
            tokens.count { it.kind == TokenKind.VAR } shouldBe 3
            tokens.count { it.kind == TokenKind.AT } shouldBe 6
            tokens.any { it.kind == TokenKind.UNIFORM } shouldBe true
            tokens.any { it.kind == TokenKind.TEXTURE_2D } shouldBe true
            tokens.any { it.kind == TokenKind.SAMPLER } shouldBe true
        }
    }

    context("Compute shader: workgroup declaration and dispatch") {
        /**
         * @compute @workgroup_size(8, 8, 1)
         * fn cs_main(@builtin(global_invocation_id) id: vec3<u32>) { }
         */
        test("compute shader entry point with workgroup_size attribute is tokenized") {
            val source = """
                @compute @workgroup_size(8, 8, 1)
                fn cs_main(@builtin(global_invocation_id) id: vec3<u32>) { }
            """.trimIndent()
            val tokens = tokenizeSignificant(source)

            tokens.none { it.kind == TokenKind.UNKNOWN } shouldBe true
            tokens.any { it.kind == TokenKind.COMPUTE } shouldBe true
            tokens.any { it.kind == TokenKind.GLOBAL_INVOCATION_ID } shouldBe true
            tokens.any { it.kind == TokenKind.VEC } shouldBe true
            tokens.any { it.kind == TokenKind.U32 } shouldBe true
        }
    }

    context("Struct with multiple annotated members") {
        /**
         * struct VertexOutput {
         *     @builtin(position) pos: vec4<f32>,
         *     @location(0) uv: vec2<f32>,
         * }
         */
        test("struct with builtin and location annotated members is tokenized") {
            val source = """
                struct VertexOutput {
                    @builtin(position) pos: vec4<f32>,
                    @location(0) uv: vec2<f32>,
                }
            """.trimIndent()
            val tokens = tokenizeSignificant(source)

            tokens.none { it.kind == TokenKind.UNKNOWN } shouldBe true
            tokens.any { it.kind == TokenKind.STRUCT } shouldBe true
            tokens.any { it.kind == TokenKind.BUILTIN } shouldBe true
            tokens.any { it.kind == TokenKind.POSITION } shouldBe true
            tokens.any { it.kind == TokenKind.LOCATION } shouldBe true
            tokens.count { it.kind == TokenKind.VEC } shouldBe 2
        }
    }

    context("WGSL enable directives and diagnostic rules") {
        test("enable extension directive is tokenized") {
            val source = "enable f16;"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.ENABLE, TokenKind.F16, TokenKind.SEMICOLON
            )
        }

        test("diagnostic directive with severity and rule is tokenized") {
            val source = "diagnostic(off, derivative_uniformity);"
            val tokens = tokenizeSignificant(source)
            tokens[0].kind shouldBe TokenKind.DIAGNOSTIC
            tokens.any { it.literal == "off" } shouldBe true
            tokens.any { it.literal == "derivative_uniformity" } shouldBe true
        }

        test("requires directive is tokenized") {
            val source = "requires readonly_and_readwrite_storage_textures;"
            val tokens = tokenizeSignificant(source)
            tokens[0].kind shouldBe TokenKind.REQUIRES
            tokens[1].kind shouldBe TokenKind.IDENTIFIER
        }
    }

    context("Increment and decrement in for-loop update clause") {
        test("for loop with i++ update is tokenized as INC not expression") {
            val source = "for (var i = 0; i < 10; i++) {}"
            val tokens = tokenizeSignificant(source)

            tokens.none { it.kind == TokenKind.UNKNOWN } shouldBe true
            tokens.any { it.kind == TokenKind.FOR } shouldBe true
            tokens.any { it.kind == TokenKind.INCREMENT } shouldBe true
        }

        test("for loop with i-- update is tokenized") {
            val source = "for (var i = 10; i > 0; i--) {}"
            val tokens = tokenizeSignificant(source)

            tokens.any { it.kind == TokenKind.DECREMENT } shouldBe true
        }
    }
})
