package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.MatrixType
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.VectorType
import io.ygdrasil.wgsl.ir.Span

class BuilderTest : FunSpec({
    test("create scalar type with scalarType method") {
        val builder = AstBuilder()
        val type = builder.scalarType(ScalarKind.I32, Span.UNDEFINED)
        type shouldBe ScalarType(ScalarKind.I32, Span.UNDEFINED)
        builder.typeCount shouldBe 1
    }

    test("create vector type with vectorType method") {
        val builder = AstBuilder()
        val scalar = builder.scalarType(ScalarKind.F32, Span.UNDEFINED)
        val vec = builder.vectorType(3, scalar, Span.UNDEFINED)
        vec shouldBe VectorType(3, scalar, Span.UNDEFINED)
        builder.typeCount shouldBe 2
    }

    test("create matrix type with matrixType method") {
        val builder = AstBuilder()
        val scalar = builder.scalarType(ScalarKind.F32, Span.UNDEFINED)
        val mat = builder.matrixType(2, 3, scalar, Span.UNDEFINED)
        mat shouldBe MatrixType(2, 3, scalar, Span.UNDEFINED)
        builder.typeCount shouldBe 2
    }

    test("create multiple types and verify counts") {
        val builder = AstBuilder()
        builder.scalarType(ScalarKind.I32, Span.UNDEFINED)
        builder.scalarType(ScalarKind.F32, Span.UNDEFINED)
        builder.scalarType(ScalarKind.U32, Span.UNDEFINED)

        builder.typeCount shouldBe 3
    }

    test("create nested vector type") {
        val builder = AstBuilder()
        val scalar = builder.scalarType(ScalarKind.I32, Span.UNDEFINED)
        val vec2 = builder.vectorType(2, scalar, Span.UNDEFINED)
        val vec3 = builder.vectorType(3, vec2, Span.UNDEFINED)

        vec3.size shouldBe 3
        vec3.elementType shouldBe vec2
        builder.typeCount shouldBe 3
    }

    test("create nested matrix type") {
        val builder = AstBuilder()
        val scalar = builder.scalarType(ScalarKind.F32, Span.UNDEFINED)
        val vec = builder.vectorType(4, scalar, Span.UNDEFINED)
        val mat = builder.matrixType(4, 4, vec, Span.UNDEFINED)

        mat.columns shouldBe 4
        mat.rows shouldBe 4
        mat.elementType shouldBe vec
        builder.typeCount shouldBe 3
    }

    test("reset statistics") {
        val builder = AstBuilder()
        builder.scalarType(ScalarKind.I32, Span.UNDEFINED)
        builder.scalarType(ScalarKind.F32, Span.UNDEFINED)
        builder.vectorType(2, ScalarType(ScalarKind.I32, Span.UNDEFINED), Span.UNDEFINED)

        builder.typeCount shouldBe 3

        builder.reset()

        builder.typeCount shouldBe 0
        builder.expressionCount shouldBe 0
        builder.declarationCount shouldBe 0
        builder.statementCount shouldBe 0
    }

    test("total count combines all") {
        val builder = AstBuilder()
        builder.scalarType(ScalarKind.I32, Span.UNDEFINED)
        builder.scalarType(ScalarKind.F32, Span.UNDEFINED)
        builder.vectorType(2, ScalarType(ScalarKind.I32, Span.UNDEFINED), Span.UNDEFINED)

        builder.totalCount shouldBe 3
    }

    test("builder creates distinct instances") {
        val builder = AstBuilder()
        val type1 = builder.scalarType(ScalarKind.I32, Span.UNDEFINED)
        val type2 = builder.scalarType(ScalarKind.I32, Span.UNDEFINED)

        type1 shouldBe ScalarType(ScalarKind.I32, Span.UNDEFINED)
        type2 shouldBe ScalarType(ScalarKind.I32, Span.UNDEFINED)
        type1 shouldBe type2
    }
})
