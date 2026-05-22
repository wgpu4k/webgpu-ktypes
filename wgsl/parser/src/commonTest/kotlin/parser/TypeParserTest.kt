package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

class TypeParserTest : FunSpec({
    test("parse scalar types") {
        val types = listOf(
            "bool" to ScalarKind.BOOL,
            "i32" to ScalarKind.I32,
            "u32" to ScalarKind.U32,
            "f32" to ScalarKind.F32,
            "f16" to ScalarKind.F16,
            "i64" to ScalarKind.I64,
            "u64" to ScalarKind.U64
        )

        for ((source, kind) in types) {
            val parser = Parser(Lexer(source))
            val type = parser.parseTypeDecl()
            type.shouldBeInstanceOf<ScalarType>()
            type.kind shouldBe kind
        }
    }

    test("parse vector types") {
        val sources = listOf("vec2<f32>", "vec3<i32>", "vec4<u32>")
        for (source in sources) {
            val parser = Parser(Lexer(source))
            val type = parser.parseTypeDecl()
            type.shouldBeInstanceOf<VectorType>()
            type.size shouldBe source[3].toString().toInt()
            type.elementType.shouldBeInstanceOf<ScalarType>()
        }
    }

    test("parse matrix types") {
        val sources = listOf("mat2x2<f32>", "mat3x4<f32>")
        for (source in sources) {
            val parser = Parser(Lexer(source))
            val type = parser.parseTypeDecl()
            type.shouldBeInstanceOf<MatrixType>()
            type.columns shouldBe source[3].toString().toInt()
            type.rows shouldBe source[5].toString().toInt()
            type.elementType.shouldBeInstanceOf<ScalarType>()
        }
    }

    test("parse atomic types") {
        val source = "atomic<i32>"
        val parser = Parser(Lexer(source))
        val type = parser.parseTypeDecl()
        type.shouldBeInstanceOf<AtomicType>()
        val elementType = type.elementType
        elementType.shouldBeInstanceOf<ScalarType>()
        elementType.kind shouldBe ScalarKind.I32
    }

    test("parse sampler types") {
        val sources = listOf(
            "sampler" to false,
            "sampler_comparison" to true
        )
        for ((source, isComparison) in sources) {
            val parser = Parser(Lexer(source))
            val type = parser.parseTypeDecl()
            type.shouldBeInstanceOf<SamplerType>()
            type.isComparison shouldBe isComparison
        }
    }

    test("parse texture types") {
        val sources = listOf(
            "texture_1d<f32>" to TextureKind.TEXTURE_1D,
            "texture_2d<i32>" to TextureKind.TEXTURE_2D,
            "texture_3d<u32>" to TextureKind.TEXTURE_3D,
            "texture_cube<f32>" to TextureKind.TEXTURE_CUBE,
            "texture_external" to TextureKind.TEXTURE_EXTERNAL
        )
        for ((source, kind) in sources) {
            val parser = Parser(Lexer(source))
            val type = parser.parseTypeDecl()
            type.shouldBeInstanceOf<TextureType>()
            type.kind shouldBe kind
        }
    }

    test("parse storage texture types") {
        val source = "texture_storage_2d<rgba8unorm, write>"
        val parser = Parser(Lexer(source))
        val type = parser.parseTypeDecl()
        type.shouldBeInstanceOf<TextureType>()
        type.kind shouldBe TextureKind.TEXTURE_STORAGE_2D
        val elementType = type.elementType
        elementType.shouldBeInstanceOf<NamedType>()
        elementType.name shouldBe "rgba8unorm"
        type.accessMode shouldBe "write"
    }

    test("parse generic template types") {
        val source = "binding_array<sampler_comparison, 5>"
        val parser = Parser(Lexer(source))
        val type = parser.parseTypeDecl()
        type.shouldBeInstanceOf<TemplateType>()
        type.name shouldBe "binding_array"
        type.args shouldHaveSize 2
        
        val arg0 = type.args[0]
        arg0.shouldBeInstanceOf<SamplerType>()
        arg0.isComparison shouldBe true
        
        val arg1 = type.args[1]
        arg1.shouldBeInstanceOf<ConstantType>()
        val constantExpr = arg1.expression
        constantExpr.shouldBeInstanceOf<IntLiteral>()
        constantExpr.value shouldBe 5
    }

    test("parse types with trailing commas") {
        val sources = listOf(
            "vec3<f32,>",
            "atomic<i32,>",
            "array<f32, 4,>",
            "binding_array<sampler, 5,>"
        )
        for (source in sources) {
            val parser = Parser(Lexer(source))
            val type = parser.parseTypeDecl()
            parser.errors shouldBe emptyList()
        }
    }
})
