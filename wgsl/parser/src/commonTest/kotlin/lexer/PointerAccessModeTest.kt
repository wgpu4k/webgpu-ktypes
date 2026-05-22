package io.ygdrasil.wgsl.lexer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

/**
 * Tests for WGSL pointer access mode keywords (read, write, read_write).
 * These are used in pointer type declarations like: ptr<storage, i32, read>
 */
class PointerAccessModeTest : FunSpec({
    context("Pointer access mode keywords") {
        test("Read keyword is recognized as access mode") {
            val source = "read"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.READ
        }

        test("Write keyword is recognized as access mode") {
            val source = "write"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.WRITE
        }

        test("Read_write keyword is recognized as access mode") {
            val source = "read_write"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 1
            tokens[0].kind shouldBe TokenKind.READ_WRITE
        }

        test("Access modes are properly tokenized in ptr declaration") {
            val source = "ptr<storage, i32, read>"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.PTR,
                TokenKind.LEFT_ANGLE,
                TokenKind.STORAGE,
                TokenKind.COMMA,
                TokenKind.I32,
                TokenKind.COMMA,
                TokenKind.READ,
                TokenKind.RIGHT_ANGLE
            )
        }

        test("Write access mode in ptr declaration") {
            val source = "ptr<function, vec4<f32>, write>"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.PTR,
                TokenKind.LEFT_ANGLE,
                TokenKind.FUNCTION,
                TokenKind.COMMA,
                TokenKind.VEC,
                TokenKind.LEFT_ANGLE,
                TokenKind.F32,
                TokenKind.RIGHT_ANGLE,
                TokenKind.COMMA,
                TokenKind.WRITE,
                TokenKind.RIGHT_ANGLE
            )
        }

        test("Read_write access mode in ptr declaration") {
            val source = "ptr<storage, mat4x4<f32>, read_write>"
            val tokens = tokenizeSignificant(source)
            // Verify READ_WRITE token is present
            tokens.filter { it.kind == TokenKind.READ_WRITE }.shouldHaveSize(1)
        }
    }
})
