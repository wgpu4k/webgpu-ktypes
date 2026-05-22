package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.BlockStatement
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.NamedType
import io.ygdrasil.wgsl.ast.Param
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.StructDecl
import io.ygdrasil.wgsl.ast.StructMember
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.ir.Span

class ModuleIndexerTest : FunSpec({
    test("empty translation unit") {
        val indexer = ModuleIndexer()
        val unit = TranslationUnit(emptyList(), Span.UNDEFINED)
        val result = indexer.reorderDeclarations(unit)
        result.declarations shouldHaveSize 0
    }

    test("single function no dependencies") {
        val indexer = ModuleIndexer()
        val func = FunctionDecl(
            attributes = emptyList(),
            name = "main",
            templateParams = emptyList(),
            parameters = emptyList(),
            returnType = null,
            body = BlockStatement(emptyList(), Span.UNDEFINED),
            span = Span.UNDEFINED
        )
        val unit = TranslationUnit(listOf(func), Span.UNDEFINED)
        val result = indexer.reorderDeclarations(unit)
        result.declarations shouldHaveSize 1
        (result.declarations[0] as FunctionDecl).name shouldBe "main"
    }

    test("build dependency graph for function depending on struct") {
        val indexer = ModuleIndexer()
        val myStruct = StructDecl(
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
        val func = FunctionDecl(
            attributes = emptyList(),
            name = "process",
            templateParams = emptyList(),
            parameters = listOf(
                Param(
                    attributes = emptyList(),
                    name = "data",
                    type = NamedType("MyStruct", Span.UNDEFINED),
                    defaultValue = null,
                    span = Span.UNDEFINED
                )
            ),
            returnType = null,
            body = BlockStatement(emptyList(), Span.UNDEFINED),
            span = Span.UNDEFINED
        )
        val unit = TranslationUnit(listOf(func, myStruct), Span.UNDEFINED)
        val graph = indexer.buildDependencyGraph(unit)
        graph.keys shouldContainExactly setOf("MyStruct", "process")
        graph["process"] shouldBe setOf("MyStruct")
        graph["MyStruct"] shouldBe setOf()
    }

    test("build dependency graph for multiple functions") {
        val indexer = ModuleIndexer()
        val structA = StructDecl(
            attributes = emptyList(),
            name = "StructA",
            templateParams = emptyList(),
            members = emptyList(),
            span = Span.UNDEFINED
        )
        val structB = StructDecl(
            attributes = emptyList(),
            name = "StructB",
            templateParams = emptyList(),
            members = listOf(
                StructMember(
                    attributes = emptyList(),
                    name = "a",
                    type = NamedType("StructA", Span.UNDEFINED),
                    defaultValue = null,
                    span = Span.UNDEFINED
                )
            ),
            span = Span.UNDEFINED
        )
        val func = FunctionDecl(
            attributes = emptyList(),
            name = "func",
            templateParams = emptyList(),
            parameters = listOf(
                Param(
                    attributes = emptyList(),
                    name = "b",
                    type = NamedType("StructB", Span.UNDEFINED),
                    defaultValue = null,
                    span = Span.UNDEFINED
                )
            ),
            returnType = null,
            body = BlockStatement(emptyList(), Span.UNDEFINED),
            span = Span.UNDEFINED
        )
        val unit = TranslationUnit(listOf(func, structA, structB), Span.UNDEFINED)
        val graph = indexer.buildDependencyGraph(unit)
        graph.keys shouldContainExactly setOf("StructA", "StructB", "func")
        graph["StructA"] shouldBe setOf()
        graph["StructB"] shouldBe setOf("StructA")
        graph["func"] shouldBe setOf("StructB")
    }
})
