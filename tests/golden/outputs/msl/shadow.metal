#include <metal_stdlib>
using namespace metal;
struct Struct_4 {
    float4x4 view_proj;
    uint4 num_lights;
};
struct Struct_6 {
    float4x4 world;
    float4 color;
};
struct Struct_8 {
    float4 proj_position;
    float3 world_normal;
    float4 world_position;
};
struct Struct_9 {
    float4x4 proj;
    float4 pos;
    float4 color;
};
float3 global_2 = float3(0.05f, 0.05f, 0.05f);
uint global_3 = 0u;

float fetch_shadow(uint light_id, float4 homogeneous_coords) {
    if ((homogeneous_coords[3] <= 0.0f)) {
        return 1.0f;
    }
    float2 local_0 = float2(0.5f, -(0.5f));
    float local_1 = (1.0f / homogeneous_coords[3]);
    float2 local_2 = (((homogeneous_coords.xy * local_0) * local_1) + float2(0.5f, 0.5f));
    return textureSampleCompareLevel(global_0, global_1, local_2, int(light_id), (homogeneous_coords[2] * local_1));
}

void textureSampleCompareLevel() {
}

struct vs_main_Input {
    int4 position [[attribute(0)]];
    int4 normal [[attribute(1)]];
};
struct vs_main_Output {
    float4 proj_position [[position]];
    float3 world_normal [[user(loc0)]];
    float4 world_position [[user(loc1)]];
};
[[vertex]]
vs_main_Output vs_main(vs_main_Input in [[stage_in]], depth2d<float> global_0 [[texture(2)]], sampler_comparison global_1 [[sampler(3)]], Struct_4 global_4 [[buffer(0)]], Struct_6 global_5 [[buffer(0)]], /* unknown type */ void global_6 [[buffer(1)]], /* unknown type */ void global_7 [[buffer(1)]]) {
    int4 position = in.position;
    int4 normal = in.normal;
}

[[fragment]]
float4 fs_main(depth2d<float> global_0 [[texture(2)]], sampler_comparison global_1 [[sampler(3)]], Struct_4 global_4 [[buffer(0)]], Struct_6 global_5 [[buffer(0)]], /* unknown type */ void global_6 [[buffer(1)]], /* unknown type */ void global_7 [[buffer(1)]]) {
}

void normalize() {
}

void normalize() {
}

void max() {
}

void dot() {
}

void min() {
}

[[fragment]]
float4 fs_main_without_storage(depth2d<float> global_0 [[texture(2)]], sampler_comparison global_1 [[sampler(3)]], Struct_4 global_4 [[buffer(0)]], Struct_6 global_5 [[buffer(0)]], /* unknown type */ void global_6 [[buffer(1)]], /* unknown type */ void global_7 [[buffer(1)]]) {
}

void normalize() {
}

void normalize() {
}

void max() {
}

void dot() {
}

void min() {
}
