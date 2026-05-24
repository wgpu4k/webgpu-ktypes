#include <metal_stdlib>
using namespace metal;

[[fragment]]
float main(depth2d<float> global_0 [[texture(0)]], sampler_comparison global_1 [[sampler(1)]]) {
    float3 local_0 = float3(0.0f);
    int local_1 = 0;
    float local_2 = 0.0f;
    return textureSampleCompareLevel(global_0, global_1, local_0, local_1, local_2);
}

depth2d<float> textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float3 arg_2, int arg_3, float arg_4) {
}
