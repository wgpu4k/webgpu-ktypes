package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ygdrasil.wgsl.ir.ShaderStage
import io.ygdrasil.wgsl.parser.TestUtils.lowerWgsl

class FunctionLoweringTest : FunSpec({
    test("T017: should lower simple function") {
        val module = lowerWgsl("fn add(a: i32, b: i32) -> i32 { return a + b; }")
        
        module.functions.toList() shouldHaveSize 1
        val addFunc = module.functions.toList()[0]
        
        addFunc.name shouldBe "add"
        addFunc.parameters shouldHaveSize 2
        addFunc.parameters[0].name shouldBe "a"
        addFunc.parameters[1].name shouldBe "b"
        addFunc.body shouldNotBe null
    }

    test("T018: should lower vertex shader entry point") {
        val module = lowerWgsl("""
            @vertex
            fn main() -> @builtin(position) vec4<f32> {
                return vec4(0.0);
            }
        """)
        
        module.entryPoints shouldHaveSize 1
        val entryPoint = module.entryPoints[0]
        
        entryPoint.name shouldBe "main"
        entryPoint.stage shouldBe ShaderStage.Vertex
    }

    test("T019: should lower fragment shader entry point") {
        val module = lowerWgsl("""
            @fragment
            fn main() -> @location(0) vec4<f32> {
                return vec4(1.0);
            }
        """)
        
        module.entryPoints shouldHaveSize 1
        module.entryPoints[0].stage shouldBe ShaderStage.Fragment
    }

    test("T020: should lower compute shader entry point") {
        val module = lowerWgsl("""
            @compute @workgroup_size(1)
            fn main() {}
        """)
        
        module.entryPoints shouldHaveSize 1
        module.entryPoints[0].stage shouldBe ShaderStage.Compute
    }
})
