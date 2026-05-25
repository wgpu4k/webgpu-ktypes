package io.ygdrasil.wgsl.generator.glsl

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import io.ygdrasil.wgsl.arena.Arena
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Function
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.TypeResolver
import io.ygdrasil.wgsl.parser.parseWgsl

class GlslWriterTest : FunSpec({

    test("testEmptyModule") {
        val module = Module()
        val code = GlslModule.writeString(module)
        code shouldContain "#version 450 core"
    }

    test("testModuleWithStructAndFunction") {
        val module = Module()

        // Add a scalar type
        val f32Handle = module.types.append(Type(TypeInner.Scalar(ScalarKind.F32, 4)))

        // Add a struct
        module.types.append(
            Type(TypeInner.Struct(listOf(
                StructMember("a", f32Handle, null, 0)
            )))
        )

        // Add a function
        val expressions = Arena<Expression>()
        val blocks = Arena<io.ygdrasil.wgsl.ir.Block>()
        val body = blocks.append(io.ygdrasil.wgsl.ir.Block(emptyList()))

        module.functions.append(
            Function(
                name = "my_func",
                parameters = emptyList(),
                returnType = null,
                localVariables = Arena(),
                expressions = expressions,
                blocks = blocks,
                body = body
            )
        )

        val code = GlslModule.writeString(module)
        code shouldContain "struct Struct_1"
        code shouldContain "float a;"
        code shouldContain "void my_func()"
    }

    test("empty_vector_constructor_writes_typed_zero_value") {
        val module = lowerWgsl("""
            @compute @workgroup_size(1)
            fn main() {
                var i = vec4<i32>();
                var f = vec2<f32>();
            }
        """.trimIndent())

        val code = GlslModule.writeString(module)

        code shouldContain "ivec4 i = ivec4(0);"
        code shouldContain "vec2 f = vec2(0.0f);"
    }

    test("empty_matrix_constructor_writes_zero_columns") {
        val module = lowerWgsl("""
            @compute @workgroup_size(1)
            fn main() {
                var m = mat2x2<f32>();
                var n = mat2x2<f32>(vec2(), vec2());
            }
        """.trimIndent())

        val code = GlslModule.writeString(module)

        code shouldContain "mat2x2 m = mat2x2(vec2(0.0f), vec2(0.0f));"
        code shouldContain "mat2x2 n = mat2x2(vec2(0.0f), vec2(0.0f));"
    }

    test("empty native builtin stubs are not emitted") {
        val module = lowerWgsl("""
            @compute @workgroup_size(1)
            fn main() {
                let a = cross(vec3(1.0, 0.0, 0.0), vec3(0.0, 1.0, 0.0));
                let b = fma(vec2(2.0), vec2(0.5), vec2(0.5));
                let c = any(vec2(true, false));
            }
        """.trimIndent())

        val code = GlslModule.writeString(module)

        code shouldContain "cross("
        code shouldContain "fma("
        code shouldContain "any("
        code shouldNotContain "\nvec3 cross("
        code shouldNotContain "\nvec2 fma("
        code shouldNotContain "\nbool any("
    }

    test("non-finite float literals are valid GLSL constant expressions") {
        val f32 = Type(TypeInner.Scalar(ScalarKind.F32, 4))
        val f16 = Type(TypeInner.Scalar(ScalarKind.F16, 2))
        val f64 = Type(TypeInner.Scalar(ScalarKind.F64, 8))
        val module = Module()

        val posInf = module.globalExpressions.append(Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F32(Float.POSITIVE_INFINITY)))))
        val negInf = module.globalExpressions.append(Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F32(Float.NEGATIVE_INFINITY)))))
        val nan = module.globalExpressions.append(Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F32(Float.NaN)))))
        val posInf64 = module.globalExpressions.append(Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F64(Double.POSITIVE_INFINITY)))))
        val posInf16 = module.globalExpressions.append(Expression(ExpressionKind.Literal(LiteralValue.Scalar(ScalarValue.F16(Float.POSITIVE_INFINITY)))))

        val f32Handle = module.types.append(f32)
        val f16Handle = module.types.append(f16)
        val f64Handle = module.types.append(f64)
        module.globalVariables.append(GlobalVariable(name = "pos_inf", storageClass = StorageClass.Private, type = f32Handle, init = posInf))
        module.globalVariables.append(GlobalVariable(name = "neg_inf", storageClass = StorageClass.Private, type = f32Handle, init = negInf))
        module.globalVariables.append(GlobalVariable(name = "nan", storageClass = StorageClass.Private, type = f32Handle, init = nan))
        module.globalVariables.append(GlobalVariable(name = "pos_inf64", storageClass = StorageClass.Private, type = f64Handle, init = posInf64))
        module.globalVariables.append(GlobalVariable(name = "pos_inf16", storageClass = StorageClass.Private, type = f16Handle, init = posInf16))

        val code = GlslModule.writeString(module)

        code shouldContain "#extension GL_EXT_shader_explicit_arithmetic_types_float16 : require"
        code shouldContain "float global_0 = (1.0 / 0.0);"
        code shouldContain "float global_1 = (-1.0 / 0.0);"
        code shouldContain "float global_2 = (0.0 / 0.0);"
        code shouldContain "double global_3 = (1.0 / 0.0);"
        code shouldContain "float16_t global_4 = (1.0hf / 0.0hf);"
        code shouldNotContain "Infinity.0f"
        code shouldNotContain "NaN.0f"
    }
})

private fun lowerWgsl(source: String): Module {
    val unit = parseWgsl(source)
    val result = TypeResolver().resolve(unit)
    check(result.isSuccess) { "WGSL test fixture did not resolve: ${result.unresolvedReferences}" }
    return Lowerer().lower(result.resolvedUnit)
}
