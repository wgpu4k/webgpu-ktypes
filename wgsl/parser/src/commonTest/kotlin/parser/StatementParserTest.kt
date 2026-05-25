package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

class StatementParserTest : FunSpec({
    test("parse loop with continuing block") {
        val source = "fn f() { loop { if true { break; } continuing { break if true; } } }"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val func = unit.declarations[0] as FunctionDecl
        val loopStmt = func.body!!.statements[0] as LoopStatement
        loopStmt.continuing.shouldNotBeNull()
        val breakIf = loopStmt.continuing.statements[0] as BreakIfStatement
        breakIf.condition.shouldBeInstanceOf<BoolLiteral>()
    }

    test("parse switch with multiple selectors") {
        val source = "fn f() { switch 1 { case 1, 2, 3: { break; } default: {} } }"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val func = unit.declarations[0] as FunctionDecl
        val switchStmt = func.body!!.statements[0] as SwitchStatement
        val case = switchStmt.body.cases[0] as Case
        case.selectors shouldHaveSize 3
    }

    test("parse switch selectors with trailing comma before block") {
        val source = "fn f() { switch 1 { case 1, { break; } case 2, 3, { break; } default {} } }"
        val parser = Parser(Lexer(source))
        val unit = parser.parse()

        parser.errors shouldBe emptyList()
        val func = unit.declarations[0] as FunctionDecl
        val switchStmt = func.body!!.statements[0] as SwitchStatement
        val firstCase = switchStmt.body.cases[0] as Case
        val secondCase = switchStmt.body.cases[1] as Case
        firstCase.selectors shouldHaveSize 1
        secondCase.selectors shouldHaveSize 2
    }

    test("parse phony assignment") {
        val source = "fn f() { _ = 1; }"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val func = unit.declarations[0] as FunctionDecl
        val phony = func.body!!.statements[0] as PhonyAssignmentStatement
        phony.expression.shouldBeInstanceOf<IntLiteral>()
    }

    test("parse static_assert") {
        val source = "static_assert 1 == 1;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        unit.declarations[0].shouldBeInstanceOf<ConstAssertDecl>()
    }

    test("trailing comma in function arguments") {
        val source = "fn f() { g(1,); }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("empty statement") {
        val source = "fn f() { ; }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("multiple empty statements") {
        val source = "fn f() { ;; }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("diagnostic in function") {
        val source = "fn f() { diagnostic(off, derivative_uniformity); }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }

    test("diagnostic in block") {
        val source = "fn f() { { diagnostic(error, derivative_uniformity); } }"
        val parser = Parser(Lexer(source))
        parser.parse()
        parser.errors shouldBe emptyList()
    }
})
