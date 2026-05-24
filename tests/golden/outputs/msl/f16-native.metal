#include <metal_stdlib>
using namespace metal;
struct Struct_8 {
    half scalar_f16;
    float scalar_f32;
    half2 vec2_f16;
    float2 vec2_f32;
    half3 vec3_f16;
    float3 vec3_f32;
    half4 vec4_f16;
    float4 vec4_f32;
};

struct test_direct_Input {
    half scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    half2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    half3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    half4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
struct test_direct_Output {
    half scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    half2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    half3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    half4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_direct_Output test_direct(test_direct_Input in [[stage_in]]) {
    half scalar_f16 = in.scalar_f16;
    float scalar_f32 = in.scalar_f32;
    half2 vec2_f16 = in.vec2_f16;
    float2 vec2_f32 = in.vec2_f32;
    half3 vec3_f16 = in.vec3_f16;
    float3 vec3_f32 = in.vec3_f32;
    half4 vec4_f16 = in.vec4_f16;
    float4 vec4_f32 = in.vec4_f32;
    Struct_8 local_0;
    local_0.scalar_f16 = (scalar_f16 + 1.0f);
    local_0.scalar_f32 = (scalar_f32 + 1.0f);
    local_0.vec2_f16 = (vec2_f16 + float2(1.0f));
    local_0.vec2_f32 = (vec2_f32 + float2(1.0f));
    local_0.vec3_f16 = (vec3_f16 + float3(1.0f));
    local_0.vec3_f32 = (vec3_f32 + float3(1.0f));
    local_0.vec4_f16 = (vec4_f16 + float4(1.0f));
    local_0.vec4_f32 = (vec4_f32 + float4(1.0f));
    return local_0;
}

struct test_struct_Output {
    half scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    half2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    half3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    half4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_struct_Output test_struct() {
    Struct_8 local_0;
    local_0.scalar_f16 = (input.scalar_f16 + 1.0f);
    local_0.scalar_f32 = (input.scalar_f32 + 1.0f);
    local_0.vec2_f16 = (input.vec2_f16 + float2(1.0f));
    local_0.vec2_f32 = (input.vec2_f32 + float2(1.0f));
    local_0.vec3_f16 = (input.vec3_f16 + float3(1.0f));
    local_0.vec3_f32 = (input.vec3_f32 + float3(1.0f));
    local_0.vec4_f16 = (input.vec4_f16 + float4(1.0f));
    local_0.vec4_f32 = (input.vec4_f32 + float4(1.0f));
    return local_0;
}

struct test_copy_input_Output {
    half scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    half2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    half3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    half4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_copy_input_Output test_copy_input() {
    Struct_8 local_0 = input_original;
    Struct_8 local_1;
    local_1.scalar_f16 = (local_0.scalar_f16 + 1.0f);
    local_1.scalar_f32 = (local_0.scalar_f32 + 1.0f);
    local_1.vec2_f16 = (local_0.vec2_f16 + float2(1.0f));
    local_1.vec2_f32 = (local_0.vec2_f32 + float2(1.0f));
    local_1.vec3_f16 = (local_0.vec3_f16 + float3(1.0f));
    local_1.vec3_f32 = (local_0.vec3_f32 + float3(1.0f));
    local_1.vec4_f16 = (local_0.vec4_f16 + float4(1.0f));
    local_1.vec4_f32 = (local_0.vec4_f32 + float4(1.0f));
    return local_1;
}

[[fragment]]
half test_return_partial() {
    Struct_8 local_0 = input_original;
    local_0.scalar_f16 = 0.0f;
    return local_0.scalar_f16;
}

struct test_component_access_Output {
    half scalar_f16 [[user(loc0)]];
    float scalar_f32 [[user(loc1)]];
    half2 vec2_f16 [[user(loc2)]];
    float2 vec2_f32 [[user(loc3)]];
    half3 vec3_f16 [[user(loc4)]];
    float3 vec3_f32 [[user(loc5)]];
    half4 vec4_f16 [[user(loc6)]];
    float4 vec4_f32 [[user(loc7)]];
};
[[fragment]]
test_component_access_Output test_component_access() {
    Struct_8 local_0;
    local_0.vec2_f16[0] = input.vec2_f16[1];
    local_0.vec2_f16[1] = input.vec2_f16[0];
    return local_0;
}
