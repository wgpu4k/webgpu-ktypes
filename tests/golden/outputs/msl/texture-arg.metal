#include <metal_stdlib>
using namespace metal;

float4 test(texture2d<float> Passed_Texture, sampler Passed_Sampler) {
    return textureSample(Passed_Texture, Passed_Sampler, float2(0.0f, 0.0f));
}

texture2d<float> textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

[[fragment]]
float4 main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
    return test(global_0, global_1);
}
