package io.ygdrasil.wgsl.back

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import io.ygdrasil.wgsl.ir.Expression
import io.ygdrasil.wgsl.ir.ExpressionKind
import io.ygdrasil.wgsl.ir.LiteralValue
import io.ygdrasil.wgsl.ir.Module
import io.ygdrasil.wgsl.ir.ScalarValue
import io.ygdrasil.wgsl.valid.ModuleInfo

class IrWriterTest : FunSpec({
    test("serializes non-finite float literals from conversion clamp edge cases") {
        val module = Module()
        module.globalExpressions.append(
            Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F32(Float.POSITIVE_INFINITY))))
        )
        module.globalExpressions.append(
            Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F64(Double.NEGATIVE_INFINITY))))
        )
        module.globalExpressions.append(
            Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.AbstractFloat(Double.NaN))))
        )

        val json = IrWriter().write(module, ModuleInfo.empty())

        json shouldContain "Infinity"
        json shouldContain "-Infinity"
        json shouldContain "NaN"
    }
})
