package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Statement as IrStatement
import io.ygdrasil.wgsl.parser.findLiteralExpressionInFunction
import io.ygdrasil.wgsl.parser.findBinaryExpressionInFunction
import io.ygdrasil.wgsl.parser.lowerWgsl

class ExpressionLoweringTest : FunSpec({
    test("T008: should lower integer literal") {
        val module = lowerWgsl("fn main() -> i32 { return 42; }")
        
        // Find the literal expression in the main function
        val foundLiteral = module.findLiteralExpressionInFunction("main", ScalarValue.I32(42))
        foundLiteral shouldNotBe null
        foundLiteral!!.kind shouldBe ExpressionKind.Literal(
            LiteralValue.Scalar(ScalarValue.I32(42))
        )
    }

    test("T009: should lower float literal") {
        val module = lowerWgsl("fn main() -> f32 { return 3.14; }")
        
        val foundLiteral = module.findLiteralExpressionInFunction("main", ScalarValue.F32(3.14f))
        foundLiteral shouldNotBe null
        foundLiteral!!.kind shouldBe ExpressionKind.Literal(
            LiteralValue.Scalar(ScalarValue.F32(3.14f))
        )
    }

    test("T010: should lower bool literal true") {
        val module = lowerWgsl("fn main() -> bool { return true; }")
        
        val foundLiteral = module.findLiteralExpressionInFunction("main", ScalarValue.Bool(true))
        foundLiteral shouldNotBe null
        foundLiteral!!.kind shouldBe ExpressionKind.Literal(
            LiteralValue.Scalar(ScalarValue.Bool(true))
        )
    }

    test("T011: should lower bool literal false") {
        val module = lowerWgsl("fn main() -> bool { return false; }")
        
        val foundLiteral = module.findLiteralExpressionInFunction("main", ScalarValue.Bool(false))
        foundLiteral shouldNotBe null
        foundLiteral!!.kind shouldBe ExpressionKind.Literal(
            LiteralValue.Scalar(ScalarValue.Bool(false))
        )
    }

    test("T012: should lower binary addition") {
        val module = lowerWgsl("fn main() -> i32 { return 1 + 2; }")
        
        // Find binary expression with Add operator
        val binaryExpr = module.findBinaryExpressionInFunction("main", BinaryOperator.Add)
        binaryExpr shouldNotBe null
        
        // Verify it's a binary expression with Add operator
        val binary = binaryExpr!!.kind as ExpressionKind.Binary
        binary.operator shouldBe BinaryOperator.Add
    }
})
