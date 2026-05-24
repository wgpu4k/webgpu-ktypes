package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.parser.TestUtils.lowerWgsl

/**
 * Tests pour le lowering des boucles (for, while)
 */
class LoopLoweringTest : FunSpec({
    test("for_loop_should_generate_ir_loop") {
        val module = lowerWgsl("""
            fn main() {
                for (var i: i32 = 0; i < 10; i = i + 1) {}
            }
        """)
        
        val mainFunc = module.functions.toList().first { it.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        val hasLoop = bodyBlock.statements.any { stmt ->
            stmt is Statement.Loop || (stmt is Statement.Block && mainFunc.blocks[stmt.block].statements.any { it is Statement.Loop })
        }
        hasLoop shouldBe true
    }
    
    test("while_loop_should_generate_ir_loop") {
        val module = lowerWgsl("""
            fn main() {
                while (true) {}
            }
        """)
        
        val mainFunc = module.functions.toList().first { it.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements.any { it is Statement.Loop } shouldBe true
    }

    test("break_if_should_lower_to_conditional_break_in_continuing_block") {
        val module = lowerWgsl("""
            fn main(a: bool) {
                loop {
                    var d = a;
                    var e = a != d;

                    continuing {
                        break if a == e;
                    }
                }
            }
        """)

        val mainFunc = module.functions.toList().first { it.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        val loop = bodyBlock.statements.filterIsInstance<Statement.Loop>().single()
        val continuing = mainFunc.blocks[loop.continuing!!]
        val breakIf = continuing.statements.single() as Statement.If
        val accept = mainFunc.blocks[breakIf.accept]

        accept.statements.single() shouldBe Statement.Break
    }
})
