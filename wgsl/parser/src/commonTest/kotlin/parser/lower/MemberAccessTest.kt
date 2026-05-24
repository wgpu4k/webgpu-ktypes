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

    test("texture_sample_result_allows_alpha_component_access") {
        val module = lowerWgsl("""
            @group(0) @binding(0) var tex : texture_2d<f32>;
            @group(0) @binding(1) var smp : sampler;

            @fragment
            fn main(@location(0) uv : vec2<f32>) -> @location(0) vec4<f32> {
                let color = textureSample(tex, smp, uv);
                let alpha = color.a;
                return color * alpha;
            }
        """)

        val mainFunc = module.functions.toList().first { it.name == "main" }
        val color = mainFunc.localVariables.toList().first { it.name == "color" }
        val colorType = module.types[color.type].inner as TypeInner.Vector
        colorType.size shouldBe VectorSize.Quad
        module.types[colorType.scalar].inner shouldBe TypeInner.Scalar(ScalarKind.F32, 4)

        val alpha = mainFunc.localVariables.toList().first { it.name == "alpha" }
        val alphaInit = mainFunc.expressions[alpha.init!!].kind as ExpressionKind.AccessIndex
        alphaInit.index shouldBe 3u
    }
})
