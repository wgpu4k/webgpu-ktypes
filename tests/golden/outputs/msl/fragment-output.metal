#include <metal_stdlib>
using namespace metal;
struct Struct_9 {
    float4 vec4f;
    int4 vec4i;
    uint4 vec4u;
    float3 vec3f;
    int3 vec3i;
    uint3 vec3u;
};
struct Struct_13 {
    float2 vec2f;
    int2 vec2i;
    uint2 vec2u;
    float scalarf;
    int scalari;
    uint scalaru;
};

struct main_vec4vec3_Output {
    float4 vec4f [[user(loc0)]];
    int4 vec4i [[user(loc1)]];
    uint4 vec4u [[user(loc2)]];
    float3 vec3f [[user(loc3)]];
    int3 vec3i [[user(loc4)]];
    uint3 vec3u [[user(loc5)]];
};
[[fragment]]
main_vec4vec3_Output main_vec4vec3() {
    Struct_9 local_0;
    local_0.vec4f = float4(0.0f);
    local_0.vec4i = int4(0);
    local_0.vec4u = uint4(0u);
    local_0.vec3f = float3(0.0f);
    local_0.vec3i = int3(0);
    local_0.vec3u = uint3(0u);
    return local_0;
}

struct main_vec2scalar_Output {
    float2 vec2f [[user(loc0)]];
    int2 vec2i [[user(loc1)]];
    uint2 vec2u [[user(loc2)]];
    float scalarf [[user(loc3)]];
    int scalari [[user(loc4)]];
    uint scalaru [[user(loc5)]];
};
[[fragment]]
main_vec2scalar_Output main_vec2scalar() {
    Struct_13 local_0;
    local_0.vec2f = float2(0.0f);
    local_0.vec2i = int2(0);
    local_0.vec2u = uint2(0u);
    local_0.scalarf = 0.0f;
    local_0.scalari = 0;
    local_0.scalaru = 0u;
    return local_0;
}
