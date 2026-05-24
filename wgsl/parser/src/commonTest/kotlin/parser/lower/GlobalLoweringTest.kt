package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ir.ExpressionKind
import io.ygdrasil.wgsl.ir.Statement
import io.ygdrasil.wgsl.ir.StorageClass
import io.ygdrasil.wgsl.ir.TypeInner
import io.ygdrasil.wgsl.ir.VectorSize
import io.ygdrasil.wgsl.parser.lowerWgsl

class GlobalLoweringTest : FunSpec({
    test("T021: should lower private global variable") {
        val module = lowerWgsl("var<private> global: i32 = 0;")
        
        module.globalVariables.toList() shouldHaveSize 1
        val globalVar = module.globalVariables.toList()[0]
        
        globalVar.name shouldBe "global"
        globalVar.storageClass shouldBe StorageClass.Private
    }

    test("T022: should lower uniform global variable") {
        val module = lowerWgsl("""
            @group(0) @binding(0)
            var<uniform> uniforms: i32;
        """)
        
        module.globalVariables.toList() shouldHaveSize 1
        val globalVar = module.globalVariables.toList()[0]
        
        globalVar.name shouldBe "uniforms"
        globalVar.storageClass shouldBe StorageClass.Uniform
    }

    test("global_variables_should_preserve_initializers") {
        val module = lowerWgsl("var<private> global: i32 = 42;")
        
        val globalVar = module.globalVariables.toList().first()
        globalVar.init shouldNotBe null
    }

    test("global_const_with_inferred_type_should_be_available_to_functions") {
        val module = lowerWgsl("""
            const v_f32_zero = vec4<f32>(0.0, 0.0, 0.0, 0.0);

            fn main() -> vec4<f32> {
                return v_f32_zero;
            }
        """.trimIndent())

        module.globalVariables.toList() shouldHaveSize 1
        val globalVar = module.globalVariables.toList().first()
        globalVar.name shouldBe "v_f32_zero"
        globalVar.init shouldNotBe null

        val globalType = module.types[globalVar.type].inner.shouldBeInstanceOf<TypeInner.Vector>()
        globalType.size shouldBe VectorSize.Quad

        val function = module.functions.toList().single { it.name == "main" }
        val returnStatement = function.blocks[function.body].statements.single().shouldBeInstanceOf<Statement.Return>()
        val returnExpression = function.expressions[returnStatement.value!!]
        returnExpression.kind.shouldBeInstanceOf<ExpressionKind.GlobalVar>()
    }
})
