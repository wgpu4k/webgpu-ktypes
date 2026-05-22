package io.ygdrasil.wgsl.lexer

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize

class LexerKeywordTest : FunSpec({
    context("WGSL keywords") {
        test("Control flow keywords") {
            val source = "if else switch case default loop while for break continue return discard continuing"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 13
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.IF, TokenKind.ELSE, TokenKind.SWITCH, TokenKind.CASE,
                TokenKind.DEFAULT, TokenKind.LOOP, TokenKind.WHILE, TokenKind.FOR,
                TokenKind.BREAK, TokenKind.CONTINUE, TokenKind.RETURN,
                TokenKind.DISCARD, TokenKind.CONTINUING
            )
        }

        test("Declaration and alias keywords") {
            val source = "abstract fn let const var type enum struct alias const_assert diagnostic"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 11
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.ABSTRACT, TokenKind.FN, TokenKind.LET, TokenKind.CONST, TokenKind.VAR,
                TokenKind.TYPE, TokenKind.ENUM, TokenKind.STRUCT, TokenKind.ALIAS, TokenKind.CONST_ASSERT,
                TokenKind.DIAGNOSTIC
            )
        }

        test("Scalar type keywords") {
            val source = "bool i8 u8 i16 u16 i32 u32 i64 u64 f16 f32 f64 float int"
            val tokens = tokenizeSignificant(source)
            tokens shouldHaveSize 14
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.BOOL, TokenKind.I8, TokenKind.U8, TokenKind.I16, TokenKind.U16,
                TokenKind.I32, TokenKind.U32, TokenKind.I64, TokenKind.U64,
                TokenKind.F16, TokenKind.F32, TokenKind.F64, TokenKind.FLOAT, TokenKind.INT
            )
        }

        test("Type constructors") {
            val source = "array vec2 vec3 vec4 mat2x2 mat3x3 mat4x4 ptr atomic"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.ARRAY, TokenKind.VEC, TokenKind.VEC, TokenKind.VEC,
                TokenKind.MAT, TokenKind.MAT, TokenKind.MAT, TokenKind.PTR,
                TokenKind.ATOMIC
            )
        }

        test("Storage classes and access modes") {
            val source = "uniform storage workgroup private function read write read_write"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.UNIFORM, TokenKind.STORAGE, TokenKind.WORKGROUP,
                TokenKind.PRIVATE, TokenKind.FUNCTION,
                TokenKind.READ, TokenKind.WRITE, TokenKind.READ_WRITE
            )
        }

        test("Shader stage and validation attributes") {
            val source = "@location @builtin @enable @requires @interpolate @invariant @must_use @override @compute @fragment @vertex"
            val tokens = tokenizeSignificant(source)
            tokens.filter { it.kind != TokenKind.AT }.map { it.kind } shouldContainExactly listOf(
                TokenKind.LOCATION, TokenKind.BUILTIN, TokenKind.ENABLE, TokenKind.REQUIRES,
                TokenKind.INTERPOLATE, TokenKind.INVARIANT, TokenKind.MUST_USE, TokenKind.OVERRIDE,
                TokenKind.COMPUTE, TokenKind.FRAGMENT, TokenKind.VERTEX
            )
        }

        test("Texture and sampler types") {
            val source = "sampler texture_1d texture_1d_array texture_2d texture_2d_array texture_3d texture_cube texture_cube_array texture_multisampled_2d texture_depth_2d texture_depth_2d_array texture_depth_cube texture_depth_cube_array texture_depth_multisampled_2d texture_external texture_storage_1d texture_storage_2d texture_storage_2d_array texture_storage_3d"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.SAMPLER, TokenKind.TEXTURE_1D, TokenKind.TEXTURE_1D_ARRAY, TokenKind.TEXTURE_2D,
                TokenKind.TEXTURE_2D_ARRAY, TokenKind.TEXTURE_3D, TokenKind.TEXTURE_CUBE,
                TokenKind.TEXTURE_CUBE_ARRAY, TokenKind.TEXTURE_MULTISAMPLED_2D,
                TokenKind.TEXTURE_DEPTH_2D, TokenKind.TEXTURE_DEPTH_2D_ARRAY,
                TokenKind.TEXTURE_DEPTH_CUBE, TokenKind.TEXTURE_DEPTH_CUBE_ARRAY,
                TokenKind.TEXTURE_DEPTH_MULTISAMPLED_2D, TokenKind.TEXTURE_EXTERNAL,
                TokenKind.TEXTURE_STORAGE_1D, TokenKind.TEXTURE_STORAGE_2D,
                TokenKind.TEXTURE_STORAGE_2D_ARRAY, TokenKind.TEXTURE_STORAGE_3D
            )
        }

        test("Built-in values") {
            val source = "position vertex_index instance_index front_facing primitive_index sample_index sample_mask viewport_index pointsize clip_distances cull_distances device_index view_index workgroup_id num_workgroups global_invocation_id local_invocation_id local_invocation_index"
            val tokens = tokenizeSignificant(source)
            tokens.map { it.kind } shouldContainExactly listOf(
                TokenKind.POSITION, TokenKind.VERTEX_INDEX, TokenKind.INSTANCE_INDEX,
                TokenKind.FRONT_FACING, TokenKind.PRIMITIVE_INDEX, TokenKind.SAMPLE_INDEX,
                TokenKind.SAMPLE_MASK, TokenKind.VIEWPORT_INDEX, TokenKind.POINTSIZE,
                TokenKind.CLIP_DISTANCES, TokenKind.CULL_DISTANCES, TokenKind.DEVICE_INDEX,
                TokenKind.VIEW_INDEX, TokenKind.WORKGROUP_ID, TokenKind.NUM_WORKGROUPS,
                TokenKind.GLOBAL_INVOCATION_ID, TokenKind.LOCAL_INVOCATION_ID,
                TokenKind.LOCAL_INVOCATION_INDEX
            )
        }
    }
})
