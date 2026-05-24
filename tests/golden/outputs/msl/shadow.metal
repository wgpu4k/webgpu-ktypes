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
uint global_3 = 10u;

float fetch_shadow(uint light_id, float4 homogeneous_coords) {
    if ((homogeneous_coords[3] <= 0.0f)) {
        return 1.0f;
    }
    float2 local_0 = float2(0.5f, -(0.5f));
    float local_1 = (1.0f / homogeneous_coords[3]);
    float2 local_2 = (((homogeneous_coords.xy * local_0) * local_1) + float2(0.5f, 0.5f));
    return textureSampleCompareLevel(global_0, global_1, local_2, int(light_id), (homogeneous_coords[2] * local_1));
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, int arg_3, float arg_4) {
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
vs_main_Output vs_main(vs_main_Input in [[stage_in]], depth2d<float> global_0 [[texture(2)]], sampler_comparison global_1 [[sampler(3)]], Struct_4 global_4 [[buffer(0)]], Struct_6 global_5 [[buffer(0)]], array<Struct_9> global_6 [[buffer(1)]], array<Struct_9, 10> global_7 [[buffer(1)]]) {
    int4 position = in.position;
    int4 normal = in.normal;
    float4x4 local_0 = global_5.world;
    float4 local_1 = (global_5.world * float4(position));
    Struct_8 local_2;
    local_2.world_normal = (float3x3(local_0[0].xyz, local_0[1].xyz, local_0[2].xyz) * float3(normal.xyz));
    local_2.world_position = local_1;
    local_2.proj_position = (global_4.view_proj * local_1);
    return local_2;
}

[[fragment]]
float4 fs_main(depth2d<float> global_0 [[texture(2)]], sampler_comparison global_1 [[sampler(3)]], Struct_4 global_4 [[buffer(0)]], Struct_6 global_5 [[buffer(0)]], array<Struct_9> global_6 [[buffer(1)]], array<Struct_9, 10> global_7 [[buffer(1)]]) {
    float3 local_0 = normalize(in.world_normal);
    float3 local_1 = global_2;
    {
        uint local_2 = 0u;
        while (true) {
            if ((local_2 < min(global_4.num_lights[0], global_3))) {
                {
                    Struct_9 local_3 = global_6[local_2];
                    float local_4 = fetch_shadow(local_2, (local_3.proj * in.world_position));
                    float3 local_5 = normalize((local_3.pos.xyz - in.world_position.xyz));
                    float local_6 = max(0.0f, dot(local_0, local_5));
                    local_1 = ((local_4 * local_6) * local_3.color.xyz);
                }
                local_2 = (local_2 + 1u);
            } else {
                break;
            }
        }
    }
    return (float4(local_1, 1.0f) * global_5.color);
}

float3 normalize(float3 arg_0) {
}

float3 normalize(float3 arg_0) {
}

float3 dot(float3 arg_0, float3 arg_1) {
}

float max(float arg_0, float3 arg_1) {
}

uint min(uint arg_0, uint arg_1) {
}

[[fragment]]
float4 fs_main_without_storage(depth2d<float> global_0 [[texture(2)]], sampler_comparison global_1 [[sampler(3)]], Struct_4 global_4 [[buffer(0)]], Struct_6 global_5 [[buffer(0)]], array<Struct_9> global_6 [[buffer(1)]], array<Struct_9, 10> global_7 [[buffer(1)]]) {
    float3 local_0 = normalize(in.world_normal);
    float3 local_1 = global_2;
    {
        uint local_2 = 0u;
        while (true) {
            if ((local_2 < min(global_4.num_lights[0], global_3))) {
                {
                    Struct_9 local_3 = global_7[local_2];
                    float local_4 = fetch_shadow(local_2, (local_3.proj * in.world_position));
                    float3 local_5 = normalize((local_3.pos.xyz - in.world_position.xyz));
                    float local_6 = max(0.0f, dot(local_0, local_5));
                    local_1 = ((local_4 * local_6) * local_3.color.xyz);
                }
                local_2 = (local_2 + 1u);
            } else {
                break;
            }
        }
    }
    return (float4(local_1, 1.0f) * global_5.color);
}

float3 normalize(float3 arg_0) {
}

float3 normalize(float3 arg_0) {
}

float3 dot(float3 arg_0, float3 arg_1) {
}

float max(float arg_0, float3 arg_1) {
}

uint min(uint arg_0, uint arg_1) {
}
