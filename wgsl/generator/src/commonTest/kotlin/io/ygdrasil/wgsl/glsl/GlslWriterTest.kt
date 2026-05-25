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
})

private fun lowerWgsl(source: String): Module {
    val unit = parseWgsl(source)
    val result = TypeResolver().resolve(unit)
    check(result.isSuccess) { "WGSL test fixture did not resolve: ${result.unresolvedReferences}" }
    return Lowerer().lower(result.resolvedUnit)
}
