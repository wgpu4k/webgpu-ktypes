package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ir.ExpressionKind
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

    test("T021: should lower discard statement") {
        val module = lowerWgsl("""
            @fragment
            fn main() {
                discard;
            }
        """)
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 1
        bodyBlock.statements[0] shouldBe io.ygdrasil.wgsl.ir.Statement.Discard
    }

    test("T022: should lower switch statement with multiple selectors and default") {
        val module = lowerWgsl("""
            fn main() {
                var material: i32 = 2;
                var result: i32 = 0;
                switch (material) {
                    case 1: { result = 10; }
                    case 2, 3: { result = 20; }
                    default: { result = 0; }
                }
            }
        """)
        
        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 3
        
        bodyBlock.statements[2] should beInstanceOf<io.ygdrasil.wgsl.ir.Statement.Switch>()
        val switchStmt = bodyBlock.statements[2] as io.ygdrasil.wgsl.ir.Statement.Switch
        
        // Assert cases
        switchStmt.cases shouldHaveSize 4 // case 1, case 2, case 3, and default (Naga maps multiple selectors and default as separate cases)
        
        // 1st case: value 1
        val case1 = switchStmt.cases[0]
        case1.selector should beInstanceOf<io.ygdrasil.wgsl.ir.CaseSelector.Value>()
        val val1 = case1.selector as io.ygdrasil.wgsl.ir.CaseSelector.Value
        val1.value should beInstanceOf<io.ygdrasil.wgsl.ir.ScalarValue.I32>()
        (val1.value as io.ygdrasil.wgsl.ir.ScalarValue.I32).value shouldBe 1
        
        // 2nd case: value 2
        val case2 = switchStmt.cases[1]
        case2.selector should beInstanceOf<io.ygdrasil.wgsl.ir.CaseSelector.Value>()
        val val2 = case2.selector as io.ygdrasil.wgsl.ir.CaseSelector.Value
        (val2.value as io.ygdrasil.wgsl.ir.ScalarValue.I32).value shouldBe 2
        
        // 3rd case: value 3
        val case3 = switchStmt.cases[2]
        case3.selector should beInstanceOf<io.ygdrasil.wgsl.ir.CaseSelector.Value>()
        val val3 = case3.selector as io.ygdrasil.wgsl.ir.CaseSelector.Value
        (val3.value as io.ygdrasil.wgsl.ir.ScalarValue.I32).value shouldBe 3
        
        // 4th case: default
        val case4 = switchStmt.cases[3]
        case4.selector should beInstanceOf<io.ygdrasil.wgsl.ir.CaseSelector.Default>()
        
        // Default case handle must not be null
        switchStmt.default shouldNotBe null
    }

    test("function call expression statement is preserved as emit") {
        val module = lowerWgsl("""
            fn callee() {
            }

            fn main() {
                callee();
            }
        """)

        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 1

        val emit = bodyBlock.statements[0].shouldBeInstanceOf<Statement.Emit>()
        emit.range.count shouldBe 1
        val call = mainFunc.expressions[emit.range.start].kind.shouldBeInstanceOf<ExpressionKind.Call>()
        module.functions[call.function].name shouldBe "callee"
    }

    test("phony assignment expression is preserved as emit") {
        val module = lowerWgsl("""
            fn callee() -> i32 {
                return 1;
            }

            fn main() {
                _ = callee();
            }
        """)

        val mainFunc = module.functions.toList().first { func -> func.name == "main" }
        val bodyBlock = mainFunc.blocks[mainFunc.body]
        bodyBlock.statements shouldHaveSize 1

        val emit = bodyBlock.statements[0].shouldBeInstanceOf<Statement.Emit>()
        emit.range.count shouldBe 1
        val call = mainFunc.expressions[emit.range.start].kind.shouldBeInstanceOf<ExpressionKind.Call>()
        module.functions[call.function].name shouldBe "callee"
    }
})
