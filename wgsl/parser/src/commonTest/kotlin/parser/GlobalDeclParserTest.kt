package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

class GlobalDeclParserTest : FunSpec({
    test("parse struct with optional semicolon") {
        val source = """
            struct S {
                a: i32,
            }
            
            struct T {
                b: f32,
            };
        """.trimIndent()
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 2
        unit.declarations[0].shouldBeInstanceOf<StructDecl>()
        unit.declarations[1].shouldBeInstanceOf<StructDecl>()
    }

    test("parse override declaration") {
        val source = "override depth: f32 = 1.0;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val override = unit.declarations[0] as OverrideDecl
        override.name shouldBe "depth"
        override.type.shouldBeInstanceOf<ScalarType>()
        override.initializer.shouldBeInstanceOf<FloatLiteral>()
    }

    test("parse override without type") {
        val source = "override width = 100;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val override = unit.declarations[0] as OverrideDecl
        override.name shouldBe "width"
        override.type shouldBe null
        override.initializer.shouldBeInstanceOf<IntLiteral>()
    }

    test("trailing comma in function parameters") {
        val source = "fn f(a: i32,) {}"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("trailing comma in template parameters") {
        val source = "struct S<T,> { a: T; }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("optional semicolon after function") {
        val source = "fn f() {};"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("trailing semicolon in struct body") {
        val source = "struct S { a: i32; ; };"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("attributes on struct") {
        val source = "@must_use struct S { a: i32; }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("no comma between struct members") {
        val source = "struct S { a: i32; b: f32; }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }
})
