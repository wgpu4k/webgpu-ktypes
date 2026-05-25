#include <metal_stdlib>
using namespace metal;

float4 test(texture2d<float> t) {
    float4 local_0 = textureSampleBaseClampToEdge(t, global_1, float2(0.0f));
    texture2d<float> local_1 = textureLoad(t, float2(0));
    texture2d<float> local_2 = textureLoad(t, float2(0u));
    uint2 local_3 = textureDimensions(t);
    return (((local_0 + local_1) + local_2) + float2(local_3).xyxy);
}

float4 textureSampleBaseClampToEdge(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, float2 arg_1) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, float2 arg_1) {
}

uint2 textureDimensions(texture2d<float> arg_0) {
}

[[fragment]]
float4 fragment_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
    return test(global_0);
}

struct vertex_main_Output {
    float4 position [[position]];
};
[[vertex]]
vertex_main_Output vertex_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
    return test(global_0);
}

[[kernel]]
void compute_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
    test(global_0);
}
