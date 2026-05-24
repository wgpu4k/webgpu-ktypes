#include <metal_stdlib>
using namespace metal;

float4 test(texture2d<float> Passed_Texture, sampler Passed_Sampler) {
    return textureSample(Passed_Texture, Passed_Sampler, float2(0.0f, 0.0f));
}

void textureSample() {
}

[[fragment]]
float4 main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
}
