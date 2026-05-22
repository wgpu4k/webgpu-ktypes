package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.parser.TestUtils.lowerWgsl

/**
 * Tests pour l'accès aux membres de struct
 */
class MemberAccessTest : FunSpec({
    test("struct_member_access_should_use_correct_index") {
        val module = lowerWgsl("""
            struct S { a: i32, b: i32 }
            fn main() -> i32 { return S(1, 2).b; }
        """)
        
        val mainFunc = module.functions.toList().first { it.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        val returnStmt = bodyBlock.statements.first { it is Statement.Return } as Statement.Return
        val accessExpr = returnStmt.value?.let { mainFunc.expressions[it] }
        
        accessExpr shouldNotBe null
        
        if (accessExpr!!.kind is ExpressionKind.AccessIndex) {
            val index = (accessExpr.kind as ExpressionKind.AccessIndex).index
            // b est le 2ème membre (index 1), pas 0
            index shouldBe 1u
        }
    }
})
