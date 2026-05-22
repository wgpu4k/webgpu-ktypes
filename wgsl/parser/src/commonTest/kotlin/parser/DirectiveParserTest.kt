package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

class DirectiveParserTest : FunSpec({
    test("parse enable directive") {
        val source = "enable f16;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val enable = unit.declarations[0] as EnableDirective
        enable.extensions shouldHaveSize 1
        enable.extensions[0] shouldBe "f16"
    }

    test("parse multiple enable directives") {
        val source = """
            enable f16;
            enable ray_query;
        """.trimIndent()
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 2
        (unit.declarations[0] as EnableDirective).extensions[0] shouldBe "f16"
        (unit.declarations[1] as EnableDirective).extensions[0] shouldBe "ray_query"
    }

    test("parse requires directive") {
        val source = "requires readonly_and_readwrite_storage_textures;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val requires = unit.declarations[0] as RequiresDirective
        requires.features shouldHaveSize 1
        requires.features[0] shouldBe "readonly_and_readwrite_storage_textures"
    }

    test("parse diagnostic directive") {
        val source = "diagnostic(off, derivative_uniformity);"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val diagnostic = unit.declarations[0] as DiagnosticDirective
        diagnostic.severity shouldBe "off"
        diagnostic.rule shouldBe "derivative_uniformity"
    }
})
