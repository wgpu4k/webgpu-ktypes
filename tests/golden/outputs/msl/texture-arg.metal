#include <metal_stdlib>
using namespace metal;

float4 test(float Passed_Texture, float Passed_Sampler) {
    return textureSample(Passed_Texture, Passed_Sampler, float2(0.0f, 0.0f));
}

void textureSample() {
}

[[fragment]]
float4 main(float global_0 [[buffer(0)]], float global_1 [[buffer(1)]]) {
}
