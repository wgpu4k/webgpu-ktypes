#include <metal_stdlib>
using namespace metal;
struct Struct_6 {
    uint val_u32;
    int val_i32;
    float val_f32;
    float val_u16;
    float2 val_u16_2;
    float3 val_u16_3;
    float4 val_u16_4;
    float val_i16;
    float2 val_i16_2;
    float3 val_i16_3;
    float4 val_i16_4;
    float final_value;
};
struct Struct_9 {
    /* unknown type */ void val_u16_array_2;
    /* unknown type */ void val_i16_array_2;
};
float global_0 = i16(1);
float global_1 = u16(20);
float global_2 = i16(f16(33000.0f));
float global_3;

void i16() {
}

void u16() {
}

void i16() {
}

void f16() {
}

float int16_function(float x) {
    float local_0 = i16(global_1);
    local_0 = (local_0 + i16(5));
    local_0 = (local_0 + i16(global_4.val_u32));
    local_0 = (local_0 + i16(global_4.val_i32));
    local_0 = (local_0 + float3(global_4.val_i16)[2]);
    global_6.val_i16 = (global_4.val_i16 + global_5.val_i16);
    global_6.val_i16_2 = (global_4.val_i16_2 + global_5.val_i16_2);
    global_6.val_i16_3 = (global_4.val_i16_3 + global_5.val_i16_3);
    global_6.val_i16_4 = (global_4.val_i16_4 + global_5.val_i16_4);
    global_8.val_i16_array_2 = global_7.val_i16_array_2;
    local_0 = abs(local_0);
    local_0 = max(local_0, local_0);
    local_0 = min(local_0, local_0);
    local_0 = clamp(local_0, local_0, local_0);
    local_0 = sign(local_0);
    local_0 = (local_0 - i16(1));
    local_0 = (local_0 * i16(2));
    local_0 = (local_0 / i16(3));
    local_0 = (local_0 % i16(4));
    local_0 = (local_0 & i16(0));
    local_0 = (local_0 | i16(0));
    local_0 = (local_0 ^ i16(0));
    local_0 = (local_0 << 0u);
    local_0 = (local_0 >> 0u);
    local_0 = -(local_0);
    float local_1 = (local_0 < i16(0));
    float local_2 = (local_0 <= i16(0));
    float local_3 = (local_0 > i16(0));
    float local_4 = (local_0 >= i16(0));
    float local_5 = (local_0 == i16(0));
    float local_6 = (local_0 != i16(0));
    local_0 = select(i16(1), i16(2), local_1);
    /* unknown type */ void local_7 = /* unknown type */ void(i16(1), i16(2), i16(3), i16(4));
    local_7[0] = local_0;
    local_0 = local_7[1];
    int local_8 = u16(1);
    local_0 = local_7[local_8];
    global_6.val_u32 = uint(local_0);
    global_6.val_i32 = int(local_0);
    global_6.val_f32 = float(local_0);
    local_0 = i16(global_6.val_u32);
    float local_9 = as_type<float>(local_0);
    local_0 = as_type<float>(local_9);
    float2 local_10 = (global_4.val_i16_2 + global_4.val_i16_2);
    float2 local_11 = (local_10 * float2(i16(2)));
    global_6.val_i16_2 = local_11;
    return local_0;
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void abs() {
}

void max() {
}

void min() {
}

void clamp() {
}

void sign() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void select() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void i16() {
}

void u16() {
}

void i16() {
}

void i16() {
}

float uint16_function(float x) {
    float local_0 = u16(global_1);
    local_0 = (local_0 + u16(5));
    local_0 = (local_0 + u16(global_4.val_u32));
    local_0 = (local_0 + u16(global_4.val_i32));
    local_0 = (local_0 + float3(global_4.val_u16)[2]);
    global_6.val_u16 = (global_4.val_u16 + global_5.val_u16);
    global_6.val_u16_2 = (global_4.val_u16_2 + global_5.val_u16_2);
    global_6.val_u16_3 = (global_4.val_u16_3 + global_5.val_u16_3);
    global_6.val_u16_4 = (global_4.val_u16_4 + global_5.val_u16_4);
    global_8.val_u16_array_2 = global_7.val_u16_array_2;
    local_0 = abs(local_0);
    local_0 = max(local_0, local_0);
    local_0 = min(local_0, local_0);
    local_0 = clamp(local_0, local_0, local_0);
    local_0 = (local_0 - u16(1));
    local_0 = (local_0 * u16(2));
    local_0 = (local_0 / u16(3));
    local_0 = (local_0 % u16(4));
    local_0 = (local_0 & u16(0));
    local_0 = (local_0 | u16(0));
    local_0 = (local_0 ^ u16(0));
    global_6.val_u32 = uint(local_0);
    global_6.val_i32 = int(local_0);
    global_6.val_f32 = float(local_0);
    local_0 = u16(global_6.val_u32);
    return local_0;
}

void u16() {
}

void u16() {
}

void u16() {
}

void u16() {
}

void abs() {
}

void max() {
}

void min() {
}

void clamp() {
}

void u16() {
}

void u16() {
}

void u16() {
}

void u16() {
}

void u16() {
}

void u16() {
}

void u16() {
}

void u16() {
}

[[kernel]]
void main(Struct_6 global_4 [[buffer(0)]], Struct_6 global_5 [[buffer(1)]], Struct_6 global_6 [[buffer(3)]], Struct_9 global_7 [[buffer(2)]], Struct_9 global_8 [[buffer(4)]]) {
}

void u16() {
}

void u16() {
}

void u16() {
}

void i16() {
}

void i16() {
}

void subgroupAdd() {
}

void subgroupMul() {
}

void subgroupMin() {
}

void subgroupMax() {
}

void subgroupExclusiveAdd() {
}

void subgroupInclusiveAdd() {
}

void subgroupBroadcastFirst() {
}

void subgroupBroadcast() {
}

void u16() {
}

void subgroupAdd() {
}

void subgroupMin() {
}

void subgroupMax() {
}
