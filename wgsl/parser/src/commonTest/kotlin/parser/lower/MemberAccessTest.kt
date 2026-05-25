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

    test("texture_dimension_queries_expose_coordinate_components") {
        val module = lowerWgsl("""
            @group(0) @binding(0) var tex1d : texture_1d<f32>;
            @group(0) @binding(1) var tex2d : texture_2d<f32>;
            @group(0) @binding(2) var tex3d : texture_3d<f32>;

            fn main() {
                let dim1 = textureDimensions(tex1d);
                let dim2y = textureDimensions(tex2d).y;
                let dim3z = textureDimensions(tex3d).z;
                let levels = textureNumLevels(tex2d);
                let samples = textureNumSamples(tex2d);
            }
        """)

        val mainFunc = module.functions.toList().first { it.name == "main" }
        val dim1 = mainFunc.localVariables.toList().first { it.name == "dim1" }
        module.types[dim1.type].inner shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)

        val dim2y = mainFunc.localVariables.toList().first { it.name == "dim2y" }
        module.types[dim2y.type].inner shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)
        val dim2Access = mainFunc.expressions[dim2y.init!!].kind as ExpressionKind.AccessIndex
        dim2Access.index shouldBe 1u

        val dim3z = mainFunc.localVariables.toList().first { it.name == "dim3z" }
        module.types[dim3z.type].inner shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)
        val dim3Access = mainFunc.expressions[dim3z.init!!].kind as ExpressionKind.AccessIndex
        dim3Access.index shouldBe 2u

        val levels = mainFunc.localVariables.toList().first { it.name == "levels" }
        module.types[levels.type].inner shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)

        val samples = mainFunc.localVariables.toList().first { it.name == "samples" }
        module.types[samples.type].inner shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)
    }

    test("atomic_compare_exchange_result_exposes_old_value_and_exchanged_members") {
        val module = lowerWgsl("""
            @group(0) @binding(0)
            var<storage, read_write> values: array<atomic<u32>, 1>;

            @compute @workgroup_size(1)
            fn main() {
                let result = atomicCompareExchangeWeak(&values[0], 1u, 2u);
                let old = result.old_value;
                let exchanged = result.exchanged;
            }
        """)

        val mainFunc = module.functions.toList().first { it.name == "main" }
        val result = mainFunc.localVariables.toList().first { it.name == "result" }
        val resultType = module.types[result.type].inner as TypeInner.Struct
        resultType.members.map { it.name } shouldBe listOf("old_value", "exchanged")

        val loaded = mainFunc.localVariables.toList().first { it.name == "old" }
        module.types[loaded.type].inner shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)

        val oldValueType = module.types[resultType.members[0].type].inner
        oldValueType shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)

        val exchangedType = module.types[resultType.members[1].type].inner
        exchangedType shouldBe TypeInner.Scalar(ScalarKind.Bool, 1)

        val oldInit = mainFunc.expressions[loaded.init!!].kind as ExpressionKind.AccessIndex
        oldInit.index shouldBe 0u

        val exchanged = mainFunc.localVariables.toList().first { it.name == "exchanged" }
        val exchangedInit = mainFunc.expressions[exchanged.init!!].kind as ExpressionKind.AccessIndex
        exchangedInit.index shouldBe 1u
    }

    test("modf_result_exposes_fract_and_whole_members") {
        val module = lowerWgsl("""
            fn main() {
                let scalar = modf(1.5);
                let fract = scalar.fract;
                let whole = scalar.whole;
                let vectorWhole = modf(vec2(1.5, 2.5)).whole;
            }
        """)

        val mainFunc = module.functions.toList().first { it.name == "main" }
        val scalar = mainFunc.localVariables.toList().first { it.name == "scalar" }
        val resultType = module.types[scalar.type].inner as TypeInner.Struct
        resultType.members.map { it.name } shouldBe listOf("fract", "whole")

        val fract = mainFunc.localVariables.toList().first { it.name == "fract" }
        module.types[fract.type].inner shouldBe TypeInner.Scalar(ScalarKind.F32, 4)
        val fractInit = mainFunc.expressions[fract.init!!].kind as ExpressionKind.AccessIndex
        fractInit.index shouldBe 0u

        val whole = mainFunc.localVariables.toList().first { it.name == "whole" }
        val wholeInit = mainFunc.expressions[whole.init!!].kind as ExpressionKind.AccessIndex
        wholeInit.index shouldBe 1u

        val vectorWhole = mainFunc.localVariables.toList().first { it.name == "vectorWhole" }
        val vectorType = module.types[vectorWhole.type].inner as TypeInner.Vector
        vectorType.size shouldBe VectorSize.Bi
        module.types[vectorType.scalar].inner shouldBe TypeInner.Scalar(ScalarKind.F32, 4)
    }

    test("frexp_result_exposes_fract_and_exp_members") {
        val module = lowerWgsl("""
            fn main() {
                let scalar = frexp(1.5);
                let fract = scalar.fract;
                let exp = scalar.exp;
                let vectorExp = frexp(vec4(1.5, 2.5, 3.5, 4.5)).exp;
            }
        """)

        val mainFunc = module.functions.toList().first { it.name == "main" }
        val scalar = mainFunc.localVariables.toList().first { it.name == "scalar" }
        val resultType = module.types[scalar.type].inner as TypeInner.Struct
        resultType.members.map { it.name } shouldBe listOf("fract", "exp")

        val fract = mainFunc.localVariables.toList().first { it.name == "fract" }
        module.types[fract.type].inner shouldBe TypeInner.Scalar(ScalarKind.F32, 4)
        val fractInit = mainFunc.expressions[fract.init!!].kind as ExpressionKind.AccessIndex
        fractInit.index shouldBe 0u

        val exp = mainFunc.localVariables.toList().first { it.name == "exp" }
        module.types[exp.type].inner shouldBe TypeInner.Scalar(ScalarKind.Sint, 4)
        val expInit = mainFunc.expressions[exp.init!!].kind as ExpressionKind.AccessIndex
        expInit.index shouldBe 1u

        val vectorExp = mainFunc.localVariables.toList().first { it.name == "vectorExp" }
        val vectorType = module.types[vectorExp.type].inner as TypeInner.Vector
        vectorType.size shouldBe VectorSize.Quad
        module.types[vectorType.scalar].inner shouldBe TypeInner.Scalar(ScalarKind.Sint, 4)
    }

    test("packed_dot_product_builtins_expose_specified_result_types") {
        val module = lowerWgsl("""
            fn main() {
                let signed = dot4I8Packed(1u, 2u);
                let unsigned = dot4U8Packed(1u, 2u);
            }
        """)

        val mainFunc = module.functions.toList().first { it.name == "main" }
        val signed = mainFunc.localVariables.toList().first { it.name == "signed" }
        module.types[signed.type].inner shouldBe TypeInner.Scalar(ScalarKind.Sint, 4)

        val unsigned = mainFunc.localVariables.toList().first { it.name == "unsigned" }
        module.types[unsigned.type].inner shouldBe TypeInner.Scalar(ScalarKind.Uint, 4)
    }
})
