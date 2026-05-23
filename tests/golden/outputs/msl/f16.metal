#include <metal_stdlib>
using namespace metal;
struct Struct_15 {
    uint val_u32;
    int val_i32;
    float val_f32;
    float val_f16;
    float2 val_f16_2;
    float3 val_f16_3;
    float4 val_f16_4;
    float final_value;
    float2x2 val_mat2x2;
    float2x3 val_mat2x3;
    float2x4 val_mat2x4;
    float3x2 val_mat3x2;
    float3x3 val_mat3x3;
    float3x4 val_mat3x4;
    float4x2 val_mat4x2;
    float4x3 val_mat4x3;
    float4x4 val_mat4x4;
};
struct Struct_17 {
    /* unknown type */ void val_f16_array_2;
};
struct Struct_18 {
    float scalar1;
    float scalar2;
    float3 v3;
    float tuck_in;
    float scalar4;
    uint larger;
};
float global_0 = 0.0f;
float global_1 = f16(15.2f);

void f16() {
}

float f16_function(float x) {
    Struct_18 local_0;
    float local_1 = f16(global_1);
    local_1 = (0.0f - 0.0f);
    local_1 = (local_1 + f16(5.0f));
    local_1 = f16((global_2.val_f32 + float(local_1)));
    local_1 = float3(global_2.val_f16)[2];
    global_4.val_i32 = int(0.0f);
    global_4.val_i32 = int(-(0.0f));
    global_4.val_u32 = uint(0.0f);
    global_4.val_u32 = uint(-(0.0f));
    global_4.val_f32 = float(0.0f);
    global_4.val_f32 = float(-(0.0f));
    global_4.val_f16 = (global_2.val_f16 + global_3.val_f16);
    global_4.val_f16_2 = (global_2.val_f16_2 + global_3.val_f16_2);
    global_4.val_f16_3 = (global_2.val_f16_3 + global_3.val_f16_3);
    global_4.val_f16_4 = (global_2.val_f16_4 + global_3.val_f16_4);
    global_4.val_mat2x2 = (global_2.val_mat2x2 + global_3.val_mat2x2);
    global_4.val_mat2x3 = (global_2.val_mat2x3 + global_3.val_mat2x3);
    global_4.val_mat2x4 = (global_2.val_mat2x4 + global_3.val_mat2x4);
    global_4.val_mat3x2 = (global_2.val_mat3x2 + global_3.val_mat3x2);
    global_4.val_mat3x3 = (global_2.val_mat3x3 + global_3.val_mat3x3);
    global_4.val_mat3x4 = (global_2.val_mat3x4 + global_3.val_mat3x4);
    global_4.val_mat4x2 = (global_2.val_mat4x2 + global_3.val_mat4x2);
    global_4.val_mat4x3 = (global_2.val_mat4x3 + global_3.val_mat4x3);
    global_4.val_mat4x4 = (global_2.val_mat4x4 + global_3.val_mat4x4);
    global_6.val_f16_array_2 = global_5.val_f16_array_2;
    local_1 = abs(local_1);
    local_1 = clamp(local_1, local_1, local_1);
    local_1 = dot(float2(local_1), float2(local_1));
    local_1 = max(local_1, local_1);
    local_1 = min(local_1, local_1);
    local_1 = sign(local_1);
    local_1 = f16(1.0f);
    float2 local_2 = float2(global_2.val_f16_2);
    global_4.val_f16_2 = float2(local_2);
    float3 local_3 = float3(global_2.val_f16_3);
    global_4.val_f16_3 = float3(local_3);
    float4 local_4 = float4(global_2.val_f16_4);
    global_4.val_f16_4 = float4(local_4);
    global_4.val_mat2x2 = float2x2(float2x2(global_2.val_mat2x2));
    global_4.val_mat2x3 = float2x3(float2x3(global_2.val_mat2x3));
    global_4.val_mat2x4 = float2x4(float2x4(global_2.val_mat2x4));
    global_4.val_mat3x2 = float3x2(float3x2(global_2.val_mat3x2));
    global_4.val_mat3x3 = float3x3(float3x3(global_2.val_mat3x3));
    global_4.val_mat3x4 = float3x4(float3x4(global_2.val_mat3x4));
    global_4.val_mat4x2 = float4x2(float4x2(global_2.val_mat4x2));
    global_4.val_mat4x3 = float4x3(float4x3(global_2.val_mat4x3));
    global_4.val_mat4x4 = float4x4(float4x4(global_2.val_mat4x4));
    return local_1;
}

void f16() {
}

void f16() {
}

void f16() {
}

void abs() {
}

void clamp() {
}

void dot() {
}

void max() {
}

void min() {
}

void sign() {
}

void f16() {
}

[[kernel]]
void main(Struct_15 global_2 [[buffer(0)]], Struct_15 global_3 [[buffer(1)]], Struct_15 global_4 [[buffer(3)]], Struct_17 global_5 [[buffer(2)]], Struct_17 global_6 [[buffer(4)]]) {
}
