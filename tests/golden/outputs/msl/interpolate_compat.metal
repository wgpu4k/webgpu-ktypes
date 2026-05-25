#include <metal_stdlib>
using namespace metal;
struct Struct_5 {
    float4 position;
    uint _flat;
    uint flat_either;
    float _linear;
    float2 linear_centroid;
    float3 linear_sample;
    float3 linear_center;
    float4 perspective;
    float perspective_centroid;
    float perspective_sample;
    float perspective_center;
};

struct vert_main_Output {
    float4 position [[position]];
    uint _flat [[user(loc0)]];
    uint flat_either [[user(loc2)]];
    float _linear [[user(loc3)]];
    float2 linear_centroid [[user(loc4)]];
    float3 linear_sample [[user(loc6)]];
    float3 linear_center [[user(loc7)]];
    float4 perspective [[user(loc8)]];
    float perspective_centroid [[user(loc9)]];
    float perspective_sample [[user(loc10)]];
    float perspective_center [[user(loc11)]];
};
[[vertex]]
vert_main_Output vert_main() {
    Struct_5 local_0;
    local_0.position = float4(2.0f, 4.0f, 5.0f, 6.0f);
    local_0._flat = 8u;
    local_0.flat_either = 10u;
    local_0._linear = 27.0f;
    local_0.linear_centroid = float2(64.0f, 125.0f);
    local_0.linear_sample = float3(216.0f, 343.0f, 512.0f);
    local_0.linear_center = float3(255.0f, 511.0f, 1024.0f);
    local_0.perspective = float4(729.0f, 1000.0f, 1331.0f, 1728.0f);
    local_0.perspective_centroid = 2197.0f;
    local_0.perspective_sample = 2744.0f;
    local_0.perspective_center = 2812.0f;
    return local_0;
}

[[fragment]]
void frag_main() {
}
