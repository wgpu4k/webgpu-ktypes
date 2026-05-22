package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.StructDecl
import io.ygdrasil.wgsl.ast.VariableDecl
import io.ygdrasil.wgsl.ast.VariableDeclKind
import io.ygdrasil.wgsl.lexer.Lexer

class IntegrationTest : FunSpec({
    test("parse and resolve: empty function") {
        val source = "fn main() {}"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
        (result.resolvedUnit.declarations[0] as FunctionDecl).name shouldBe "main"
    }

    test("parse and resolve: empty translation unit") {
        val source = ""
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 0
    }

    test("parse and resolve: global variable with explicit scalar type") {
        val source = "let myConst: i32 = 42;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1

        val varDecl = result.resolvedUnit.declarations[0] as VariableDecl
        varDecl.name shouldBe "myConst"
        varDecl.kind shouldBe VariableDeclKind.LET
    }

    test("parse and resolve: multiple empty functions") {
        val source = """
            fn first() {}
            fn second() {}
        """.trimIndent()
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 2
    }

    test("parse and resolve: function without parameters") {
        val source = "fn getValue() {}"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
        val func = result.resolvedUnit.declarations[0] as FunctionDecl
        func.name shouldBe "getValue"
        func.parameters shouldHaveSize 0
    }

    test("parse and resolve: unknown type error") {
        val source = "fn test(x: UnknownType) {}"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe false
        result.unresolvedReferences shouldHaveSize 1
        result.unresolvedReferences[0].name shouldBe "UnknownType"
    }

    test("parse and resolve: const variable") {
        val source = "const PI: f32 = 3.14;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
        val varDecl = result.resolvedUnit.declarations[0] as VariableDecl
        varDecl.name shouldBe "PI"
        varDecl.kind shouldBe VariableDeclKind.CONST
    }

    test("parse and resolve: var variable") {
        val source = "var counter: i32;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
        val varDecl = result.resolvedUnit.declarations[0] as VariableDecl
        varDecl.name shouldBe "counter"
        varDecl.kind shouldBe VariableDeclKind.VAR
    }

    test("parse and resolve: function with block body") {
        val source = "fn test() { let x = 1; }"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
    }

    test("parse and resolve: empty struct") {
        val source = "struct Empty {}"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()

        val resolver = TypeResolver()
        val result = resolver.resolve(unit)

        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
        val struct = result.resolvedUnit.declarations[0] as StructDecl
        struct.name shouldBe "Empty"
        struct.members shouldHaveSize 0
    }
})
