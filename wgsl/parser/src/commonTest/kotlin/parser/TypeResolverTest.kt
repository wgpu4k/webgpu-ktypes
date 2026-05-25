package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.BlockStatement
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.NamedType
import io.ygdrasil.wgsl.ast.Param
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.ir.Span

class TypeResolverTest : FunSpec({
    test("resolve empty unit") {
        val resolver = TypeResolver()
        val unit = TranslationUnit(emptyList(), Span.UNDEFINED)
        val result = resolver.resolve(unit)
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 0
    }

    test("resolve scalar type in function parameter") {
        val resolver = TypeResolver()
        val func = FunctionDecl(
            attributes = emptyList(),
            name = "main",
            templateParams = emptyList(),
            parameters = listOf(
                Param(
                    attributes = emptyList(),
                    name = "x",
                    type = ScalarType(ScalarKind.I32, Span.UNDEFINED),
                    defaultValue = null,
                    span = Span.UNDEFINED
                )
            ),
            returnType = null,
            body = BlockStatement(emptyList(), Span.UNDEFINED),
            span = Span.UNDEFINED
        )
        val unit = TranslationUnit(listOf(func), Span.UNDEFINED)
        val result = resolver.resolve(unit)
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
    }

    test("fail to resolve unknown type") {
        val resolver = TypeResolver()
        val func = FunctionDecl(
            attributes = emptyList(),
            name = "main",
            templateParams = emptyList(),
            parameters = listOf(
                Param(
                    attributes = emptyList(),
                    name = "x",
                    type = NamedType("UnknownType", Span.UNDEFINED),
                    defaultValue = null,
                    span = Span.UNDEFINED
                )
            ),
            returnType = null,
            body = BlockStatement(emptyList(), Span.UNDEFINED),
            span = Span.UNDEFINED
        )
        val unit = TranslationUnit(listOf(func), Span.UNDEFINED)
        val result = resolver.resolve(unit)
        result.isSuccess shouldBe false
        result.unresolvedReferences shouldHaveSize 1
        result.unresolvedReferences[0].name shouldBe "UnknownType"
    }

    test("resolve builtin scalar type by name") {
        val resolver = TypeResolver()
        val func = FunctionDecl(
            attributes = emptyList(),
            name = "main",
            templateParams = emptyList(),
            parameters = listOf(
                Param(
                    attributes = emptyList(),
                    name = "x",
                    type = NamedType("i32", Span.UNDEFINED),
                    defaultValue = null,
                    span = Span.UNDEFINED
                )
            ),
            returnType = null,
            body = BlockStatement(emptyList(), Span.UNDEFINED),
            span = Span.UNDEFINED
        )
        val unit = TranslationUnit(listOf(func), Span.UNDEFINED)
        val result = resolver.resolve(unit)
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
    }

    test("resolve unicode local identifiers and contextual keyword function calls") {
        val source = """
            var<storage> asdf: f32;

            fn compute() -> f32 {
              let θ2 = asdf + 9001.0;
              return θ2;
            }

            @compute @workgroup_size(1, 1)
            fn main() {
              compute();
            }
        """.trimIndent()

        val parseResult = parseWgslResult(source)
        parseResult.isSuccess shouldBe true

        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult.translationUnit)

        result.isSuccess shouldBe true
        result.unresolvedReferences shouldHaveSize 0
    }

    test("resolve relational expressions after nested template types") {
        val source = """
            struct AtomicStruct {
                atomic_arr: array<atomic<i32>, 2>,
            }

            fn main() {
                let value: i32 = 1i;
                if value > 0i {
                    return;
                }
            }
        """.trimIndent()

        val parseResult = parseWgslResult(source)
        parseResult.isSuccess shouldBe true

        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult.translationUnit)

        result.isSuccess shouldBe true
        result.unresolvedReferences shouldHaveSize 0
    }

    test("resolve stage keyword parameter names when referenced in expressions") {
        val source = """
            struct Vertex {
                position: vec3<f32>,
            }

            @vertex
            fn vs_main(vertex: Vertex) -> vec4<f32> {
                return vec4<f32>(vertex.position, 1.0);
            }
        """.trimIndent()

        val parseResult = parseWgslResult(source)
        parseResult.isSuccess shouldBe true

        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult.translationUnit)

        result.isSuccess shouldBe true
        result.unresolvedReferences shouldHaveSize 0
    }

    test("resolve loop body locals from continuing break-if condition") {
        val source = """
            fn main(a: bool) {
                loop {
                    var d = a;
                    var e = a != d;

                    continuing {
                        break if a == e;
                    }
                }
            }
        """.trimIndent()

        val parseResult = parseWgslResult(source)
        parseResult.isSuccess shouldBe true

        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult.translationUnit)

        result.isSuccess shouldBe true
        result.unresolvedReferences shouldHaveSize 0
    }
})
