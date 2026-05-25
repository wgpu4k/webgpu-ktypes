package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

class ExpressionParserTest : FunSpec({
    test("parse bitcast") {
        val source = "fn f() { _ = bitcast<f32>(1); }"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val func = unit.declarations[0] as FunctionDecl
        val phony = func.body!!.statements[0] as PhonyAssignmentStatement
        val bitcast = phony.expression as BitcastExpr
        bitcast.type.shouldBeInstanceOf<ScalarType>()
        bitcast.type.kind shouldBe ScalarKind.F32
        bitcast.expr.shouldBeInstanceOf<IntLiteral>()
    }

    test("parse ray_query type") {
        val source = "var<private> q: ray_query;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val variable = unit.declarations[0] as VariableDecl
        variable.type.shouldBeInstanceOf<RayQueryType>()
    }

    test("parse push_constant storage class") {
        val source = "var<push_constant> pc: u32;"
        val lexer = Lexer(source)
        val parser = Parser(lexer)
        val unit = parser.parse()
        
        unit.declarations shouldHaveSize 1
        val variable = unit.declarations[0] as VariableDecl
        variable.storageClass shouldBe "push_constant"
    }

    test("parse storage keyword reused as function call identifier") {
        val source = """
            fn storage() {}
            fn main() { storage(); }
        """.trimIndent()
        val parser = Parser(Lexer(source))
        val unit = parser.parse()

        parser.errors shouldHaveSize 0
        unit.declarations shouldHaveSize 2
        val main = unit.declarations[1] as FunctionDecl
        val statement = main.body!!.statements.single() as ExpressionStatement
        val call = statement.expr as CallExpr
        (call.callee as IdentExpr).name shouldBe "storage"
    }
})
