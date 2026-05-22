package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import io.ygdrasil.wgsl.ir.Statement
import io.ygdrasil.wgsl.parser.TestUtils.lowerWgsl

class StatementLoweringTest : FunSpec({
    test("T013: should lower return statement") {
        val module = lowerWgsl("fn main() -> i32 { return 0; }")
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 1
        bodyBlock.statements[0] should beInstanceOf<Statement.Return>()
    }

    test("T014: should lower empty block") {
        val module = lowerWgsl("fn main() { { } }")
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 1
        bodyBlock.statements[0] should beInstanceOf<Statement.Block>()
    }

    test("T015: should lower variable declaration with initializer") {
        val module = lowerWgsl("""
            fn main() {
                let x: i32 = 42;
            }
        """)
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 1
        // Variable declaration with initializer produces Init, not Declare
        bodyBlock.statements[0] should beInstanceOf<Statement.Init>()
    }

    test("T016: should lower assignment statement") {
        val module = lowerWgsl("""
            fn main() {
                var x: i32 = 0;
                x = 42;
            }
        """)
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 2
        bodyBlock.statements[1] should beInstanceOf<Statement.Assign>()
    }
})
