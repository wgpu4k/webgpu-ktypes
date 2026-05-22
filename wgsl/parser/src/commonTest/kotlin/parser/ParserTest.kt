package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.lexer.Lexer

class ParserTest : FunSpec({
    test("parse empty") {
        val lexer = Lexer("")
        val parser = Parser(lexer)
        val unit = parser.parse()
        unit shouldBe TranslationUnit.empty()
    }

    test("parse empty with whitespace") {
        val lexer = Lexer("   \n\t  ")
        val parser = Parser(lexer)
        val unit = parser.parse()
        unit.declarations shouldHaveSize 0
    }

    test("parse simple function") {
        val source = "fn main() {}"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        unit.declarations shouldHaveSize 1
        val func = unit.declarations[0] as FunctionDecl
        func.name shouldBe "main"
    }

    test("parse function with return type") {
        val source = "fn add(a: i32, b: i32) -> i32 { return a + b; }"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        unit.declarations shouldHaveSize 1
        val func = unit.declarations[0] as FunctionDecl
        func.name shouldBe "add"
        func.parameters shouldHaveSize 2
        val returnType = func.returnType as ScalarType
        returnType.kind shouldBe ScalarKind.I32
    }
})
