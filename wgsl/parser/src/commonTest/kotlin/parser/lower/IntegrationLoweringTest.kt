package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.parser.lowerWgsl

class IntegrationLoweringTest : FunSpec({
    test("T023: should lower complete vertex shader") {
        val module = lowerWgsl("""
            struct VertexInput {
                @location(0) position: vec3<f32>,
                @location(1) color: vec3<f32>
            };
            
            struct VertexOutput {
                @builtin(position) position: vec4<f32>,
                @location(0) color: vec3<f32>
            };
            
            @vertex
            fn vs_main(input: VertexInput) -> VertexOutput {
                var output: VertexOutput;
                output.position = vec4(input.position, 1.0);
                output.color = input.color;
                return output;
            }
        """)
        
        // Verify types: f32, vec3<f32>, VertexInput, vec4<f32>, VertexOutput
        module.types.toList() shouldHaveSize 5
        
        // Verify entry points
        module.entryPoints shouldHaveSize 1
        module.entryPoints[0].name shouldBe "vs_main"
        
        // Verify functions
        module.functions.toList() shouldHaveSize 1
        module.functions.toList()[0].name shouldBe "vs_main"
    }
})
