package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.StructDecl
import io.ygdrasil.wgsl.ast.StructMember
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.ir.Span

class TypeIndexTest : FunSpec({
    test("builtin scalar types are known") {
        val index = TypeIndex()
        index.isKnownType("i32").shouldBeTrue()
        index.isKnownType("f32").shouldBeTrue()
        index.isKnownType("bool").shouldBeTrue()
        index.isKnownType("u64").shouldBeTrue()
    }

    test("unknown type is not known") {
        val index = TypeIndex()
        index.isKnownType("MyCustomType").shouldBeFalse()
    }

    test("texture atomic builtins are known values") {
        val index = TypeIndex()
        listOf(
            "textureAtomicAdd",
            "textureAtomicAnd",
            "textureAtomicMax",
            "textureAtomicMin",
            "textureAtomicOr",
            "textureAtomicXor"
        ).forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
    }

    test("builtin vector type names") {
        val index = TypeIndex()
        index.isBuiltinVectorType("vec2").shouldBeTrue()
        index.isBuiltinVectorType("vec3").shouldBeTrue()
        index.isBuiltinVectorType("vec4").shouldBeTrue()
        index.isBuiltinVectorType("vec5").shouldBeFalse()
    }

    test("builtin matrix types are known") {
        val index = TypeIndex()
        index.isBuiltinMatrixType("mat2x2<f32>").shouldBeTrue()
        index.isBuiltinMatrixType("mat3x4<i32>").shouldBeTrue()
    }

    test("lookup builtin scalar type") {
        val index = TypeIndex()
        val type = index.getBuiltinScalarType("i32")
        type shouldBe ScalarType(ScalarKind.I32, Span.UNDEFINED)
    }

    test("parse vector type") {
        val index = TypeIndex()
        val result = index.parseBuiltinVectorType("vec3<f32>")
        result shouldBe Pair(3, "f32")
    }

    test("parse matrix type") {
        val index = TypeIndex()
        val result = index.parseBuiltinMatrixType("mat2x3<f32>")
        result shouldBe Triple(2, 3, "f32")
    }

    test("index struct declaration") {
        val index = TypeIndex()
        val struct = StructDecl(
            attributes = emptyList(),
            name = "MyStruct",
            templateParams = emptyList(),
            members = listOf(
                StructMember(
                    attributes = emptyList(),
                    name = "x",
                    type = ScalarType(ScalarKind.I32, Span.UNDEFINED),
                    defaultValue = null,
                    span = Span.UNDEFINED
                )
            ),
            span = Span.UNDEFINED
        )
        val unit = TranslationUnit(listOf(struct), Span.UNDEFINED)
        index.index(unit)
        index.isKnownType("MyStruct").shouldBeTrue()
        index.findStruct("MyStruct") shouldBe struct
    }
})
