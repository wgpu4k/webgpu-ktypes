#include <metal_stdlib>
using namespace metal;
struct Struct_4 {
    float scalar_f16;
    float scalar_f32;
    float2 vec2_f16;
    float2 vec2_f32;
    float3 vec3_f16;
    float3 vec3_f32;
    float4 vec4_f16;
    float4 vec4_f32;
};

struct test_direct_Input {
    float scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    float2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    float3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    float4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
struct test_direct_Output {
    float scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    float2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    float3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    float4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_direct_Output test_direct(test_direct_Input in [[stage_in]]) {
    float scalar_f16 = in.scalar_f16;
    float scalar_f32 = in.scalar_f32;
    float2 vec2_f16 = in.vec2_f16;
    float2 vec2_f32 = in.vec2_f32;
    float3 vec3_f16 = in.vec3_f16;
    float3 vec3_f32 = in.vec3_f32;
    float4 vec4_f16 = in.vec4_f16;
    float4 vec4_f32 = in.vec4_f32;
}

struct test_struct_Output {
    float scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    float2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    float3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    float4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_struct_Output test_struct() {
}

struct test_copy_input_Output {
    float scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    float2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    float3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    float4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_copy_input_Output test_copy_input() {
}

[[fragment]]
float test_return_partial() {
}

struct test_component_access_Output {
    float scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    float2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    float3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    float4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_component_access_Output test_component_access() {
}
