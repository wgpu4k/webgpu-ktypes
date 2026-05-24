#include <metal_stdlib>
using namespace metal;

struct main_Input {
    float2 uv [[user(loc0)]];
};
[[fragment]]
float4 main(main_Input in [[stage_in]], texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
    float2 uv = in.uv;
    return textureSample(global_0, global_1, uv);
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}
