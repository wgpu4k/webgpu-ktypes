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

    test("texture query builtins are known values") {
        val index = TypeIndex()
        listOf("textureNumLayers", "textureNumLevels", "textureNumSamples").forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
    }

    test("packed dot-product builtins are known values") {
        val index = TypeIndex()
        listOf("dot4I8Packed", "dot4U8Packed").forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
    }

    test("bit manipulation builtins are known values") {
        val index = TypeIndex()
        listOf(
            "firstLeadingBit",
            "firstTrailingBit",
            "countLeadingZeros",
            "countTrailingZeros",
            "countOneBits",
            "reverseBits"
        ).forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
    }

    test("interpolation attribute enumerants are known values") {
        val index = TypeIndex()
        listOf("flat", "perspective", "linear", "first", "either", "center", "centroid", "sample").forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
    }

    test("mesh shader builtin enumerants are known values") {
        val index = TypeIndex()
        listOf(
            "triangle_indices",
            "mesh_task_size",
            "vertices",
            "primitives",
            "vertex_count",
            "primitive_count",
            "point_index",
            "line_indices",
            "cull_primitive"
        ).forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
    }

    test("ray query constants and functions are known values") {
        val index = TypeIndex()
        listOf(
            "RAY_FLAG_SKIP_CLOSEST_HIT_SHADER",
            "RAY_FLAG_SKIP_TRIANGLES",
            "RAY_FLAG_SKIP_AABBS",
            "RAY_QUERY_INTERSECTION_NONE",
            "RAY_QUERY_INTERSECTION_TRIANGLE",
            "RAY_QUERY_INTERSECTION_GENERATED",
            "RAY_QUERY_INTERSECTION_AABB",
            "rayQueryGetCommittedIntersection",
            "rayQueryGenerateIntersection",
            "rayQueryConfirmIntersection",
            "rayQueryTerminate"
        ).forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
    }

    test("cooperative matrix roles and functions are known values") {
        val index = TypeIndex()
        listOf("coop_mat8x8", "A", "B", "C", "coopLoad", "coopStore", "coopMultiplyAdd").forEach { name ->
            index.isBuiltinValue(name).shouldBeTrue()
        }
        listOf("coop_mat8x8", "A", "B", "C").forEach { name ->
            index.isKnownType(name).shouldBeTrue()
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
