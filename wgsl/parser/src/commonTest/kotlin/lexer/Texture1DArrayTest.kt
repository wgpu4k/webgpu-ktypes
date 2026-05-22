package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

/**
 * Tests for WGSL texture_1d_array type keyword.
 * Part of the WGSL texture types family.
 */
class Texture1DArrayTest : FunSpec({
    context("Texture 1D Array Type") {
        test("texture_1d_array keyword is recognized") {
            val source = "texture_1d_array"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.TEXTURE_1D_ARRAY
        }

        test("texture_1d_array as function parameter type") {
            val source = "fn sample(tex: texture_1d_array<f32>) { }"
            val tokens = tokenizeSignificant(source)
            // Verify texture_1d_array is properly tokenized
            tokens.filter { it.kind == TokenKind.TEXTURE_1D_ARRAY }.shouldHaveSize(1)
        }

        test("texture_1d_array with sampler") {
            val source = "@binding(0) var tex: texture_1d_array<f32>;"
            val tokens = tokenizeSignificant(source)
            tokens.filter { it.kind == TokenKind.TEXTURE_1D_ARRAY }.shouldHaveSize(1)
        }

        test("texture_1d_array in type declaration") {
            val source = "type MyTexture = texture_1d_array<f32>;"
            val tokens = tokenizeSignificant(source)
            tokens.filter { it.kind == TokenKind.TEXTURE_1D_ARRAY }.shouldHaveSize(1)
        }
    }
})
