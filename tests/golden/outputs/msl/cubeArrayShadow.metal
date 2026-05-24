#include <metal_stdlib>
using namespace metal;

[[fragment]]
float4 fragment(depth2d<float> global_0 [[texture(4)]], sampler_comparison global_1 [[sampler(5)]]) {
    float3 local_0 = float4(1.0f, 1.0f, 2.0f, 1.0f).xyz;
    float local_1 = textureSampleCompare(global_0, global_1, local_0, int(1), 1.0f);
    return float4(local_1, 1.0f, 1.0f, 1.0f);
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float3 arg_2, int arg_3, float arg_4) {
}
