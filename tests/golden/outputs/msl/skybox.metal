#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    float4 position;
    float3 uv;
};
struct Struct_5 {
    float4x4 proj_inv;
    float4x4 view;
};

[[fragment]]
float4 fs_main(texturecube<float> global_0 [[texture(1)]], sampler global_1 [[sampler(2)]], Struct_5 global_2 [[buffer(0)]]) {
}

void textureSample() {
}

struct vs_main_Output {
    float4 position [[position]];
    float3 uv [[user(loc0)]];
};
[[vertex]]
vs_main_Output vs_main(texturecube<float> global_0 [[texture(1)]], sampler global_1 [[sampler(2)]], Struct_5 global_2 [[buffer(0)]]) {
}

void transpose() {
}
