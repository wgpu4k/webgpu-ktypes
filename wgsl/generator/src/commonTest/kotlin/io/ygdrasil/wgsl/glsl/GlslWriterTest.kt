package io.ygdrasil.wgsl.generator.glsl

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import io.ygdrasil.wgsl.arena.Arena
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Function
import io.ygdrasil.wgsl.valid.ModuleInfo

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
})
