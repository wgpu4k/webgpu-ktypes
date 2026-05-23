package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import io.ygdrasil.wgsl.ir.Statement
import io.ygdrasil.wgsl.parser.TestUtils.lowerWgsl
import io.kotest.matchers.shouldNotBe

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

    test("T017: should lower Increment statement with signed integer i32") {
        val module = lowerWgsl("""
            fn main() {
                var i: i32 = 0;
                i++;
            }
        """)
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 2
        
        val incStmt = bodyBlock.statements[1]
        incStmt should beInstanceOf<Statement.Assign>()
        val assign = incStmt as Statement.Assign
        
        val valExpr = mainFunc.expressions[assign.value]
        valExpr.kind should beInstanceOf<io.ygdrasil.wgsl.ir.ExpressionKind.Binary>()
        val binary = valExpr.kind as io.ygdrasil.wgsl.ir.ExpressionKind.Binary
        binary.operator shouldBe io.ygdrasil.wgsl.ir.BinaryOperator.Add
        
        val rightExpr = mainFunc.expressions[binary.right]
        rightExpr.kind should beInstanceOf<io.ygdrasil.wgsl.ir.ExpressionKind.Literal>()
        val literal = rightExpr.kind as io.ygdrasil.wgsl.ir.ExpressionKind.Literal
        literal.value should beInstanceOf<io.ygdrasil.wgsl.ir.LiteralValue.Scalar>()
        val scalar = literal.value as io.ygdrasil.wgsl.ir.LiteralValue.Scalar
        scalar.value should beInstanceOf<io.ygdrasil.wgsl.ir.ScalarValue.I32>()
        (scalar.value as io.ygdrasil.wgsl.ir.ScalarValue.I32).value shouldBe 1
    }

    test("T018: should lower Decrement statement with unsigned integer u32") {
        val module = lowerWgsl("""
            fn main() {
                var u: u32 = 10u;
                u--;
            }
        """)
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 2
        
        val decStmt = bodyBlock.statements[1]
        decStmt should beInstanceOf<Statement.Assign>()
        val assign = decStmt as Statement.Assign
        
        val valExpr = mainFunc.expressions[assign.value]
        valExpr.kind should beInstanceOf<io.ygdrasil.wgsl.ir.ExpressionKind.Binary>()
        val binary = valExpr.kind as io.ygdrasil.wgsl.ir.ExpressionKind.Binary
        binary.operator shouldBe io.ygdrasil.wgsl.ir.BinaryOperator.Subtract
        
        val rightExpr = mainFunc.expressions[binary.right]
        rightExpr.kind should beInstanceOf<io.ygdrasil.wgsl.ir.ExpressionKind.Literal>()
        val literal = rightExpr.kind as io.ygdrasil.wgsl.ir.ExpressionKind.Literal
        literal.value should beInstanceOf<io.ygdrasil.wgsl.ir.LiteralValue.Scalar>()
        val scalar = literal.value as io.ygdrasil.wgsl.ir.LiteralValue.Scalar
        scalar.value should beInstanceOf<io.ygdrasil.wgsl.ir.ScalarValue.U32>()
        (scalar.value as io.ygdrasil.wgsl.ir.ScalarValue.U32).value shouldBe 1L
    }

    test("T019: should lower for loop with Increment update") {
        val module = lowerWgsl("""
            fn main() {
                for (var i: i32 = 0; i < 10; i++) {
                    var x: i32 = i;
                }
            }
        """)
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 1
        
        // The for loop is lowered into a Block containing the init statement and the Loop statement
        bodyBlock.statements[0] should beInstanceOf<Statement.Block>()
        val outerBlockStmt = bodyBlock.statements[0] as Statement.Block
        val outerBlock = mainFunc.blocks[outerBlockStmt.block]
        outerBlock.statements shouldHaveSize 2
        
        // 1st is init statement
        outerBlock.statements[0] should beInstanceOf<Statement.Init>()
        
        // 2nd is loop
        outerBlock.statements[1] should beInstanceOf<Statement.Loop>()
        val loop = outerBlock.statements[1] as Statement.Loop
        
        // The loop condition check block contains the If statement
        val condBlock = mainFunc.blocks[loop.body]
        condBlock.statements shouldHaveSize 1
        condBlock.statements[0] should beInstanceOf<Statement.If>()
        val ifStmt = condBlock.statements[0] as Statement.If
        
        // The If then block is the loop body which contains original body + update
        val bodyAndUpdateBlock = mainFunc.blocks[ifStmt.accept]
        bodyAndUpdateBlock.statements shouldHaveSize 2
        
        // 1st statement in body is the original block containing var x: i32 = i;
        bodyAndUpdateBlock.statements[0] should beInstanceOf<Statement.Block>()
        
        // 2nd statement in body is the increment update: i++
        bodyAndUpdateBlock.statements[1] should beInstanceOf<Statement.Assign>()
        val assign = bodyAndUpdateBlock.statements[1] as Statement.Assign
        
        val valExpr = mainFunc.expressions[assign.value]
        valExpr.kind should beInstanceOf<io.ygdrasil.wgsl.ir.ExpressionKind.Binary>()
    }

    test("T020: should lower global variable resource bindings (@group and @binding)") {
        val module = lowerWgsl("""
            struct Grid {
                id: u32,
            }
            @group(2) @binding(5) var<uniform> grid: Grid;
        """)
        
        module.globalVariables.size shouldBe 1
        val globalVar = module.globalVariables.toList()[0]
        globalVar.name shouldBe "grid"
        
        globalVar.binding shouldNotBe null
        val binding = globalVar.binding!!
        binding.group shouldBe 2
        binding.index shouldBe 5
    }
})
