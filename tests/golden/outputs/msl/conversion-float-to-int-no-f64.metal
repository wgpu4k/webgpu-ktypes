#include <metal_stdlib>
using namespace metal;
float global_0 = -(65504.0f);
float global_1 = 65504.0f;
float global_2 = -(3.4028235E38f);
float global_3 = 3.4028235E38f;
float global_4 = -(Infinity.0f);
float global_5 = Infinity.0f;

int test_f16_to_i32(half f) {
    return int(f);
}

uint test_f16_to_u32(half f) {
    return uint(f);
}

long test_f16_to_i64(half f) {
    return long(f);
}

ulong test_f16_to_u64(half f) {
    return ulong(f);
}

int test_f32_to_i32(float f) {
    return int(f);
}

uint test_f32_to_u32(float f) {
    return uint(f);
}

long test_f32_to_i64(float f) {
    return long(f);
}

ulong test_f32_to_u64(float f) {
    return ulong(f);
}

int2 test_f16_to_i32_vec(half2 f) {
    return int2(f);
}

uint2 test_f16_to_u32_vec(half2 f) {
    return uint2(f);
}

long2 test_f16_to_i64_vec(half2 f) {
    return long2(f);
}

ulong2 test_f16_to_u64_vec(half2 f) {
    return ulong2(f);
}

int2 test_f32_to_i32_vec(float2 f) {
    return int2(f);
}

uint2 test_f32_to_u32_vec(float2 f) {
    return uint2(f);
}

long2 test_f32_to_i64_vec(float2 f) {
    return long2(f);
}

ulong2 test_f32_to_u64_vec(float2 f) {
    return ulong2(f);
}

void test_const_eval() {
    int local_0 = int(global_0);
    int local_1 = int(global_1);
    uint local_2 = uint(global_0);
    uint local_3 = uint(global_1);
    long local_4 = long(global_0);
    long local_5 = long(global_1);
    ulong local_6 = ulong(global_0);
    ulong local_7 = ulong(global_1);
    int local_8 = int(global_2);
    int local_9 = int(global_3);
    uint local_10 = uint(global_2);
    uint local_11 = uint(global_3);
    long local_12 = long(global_2);
    long local_13 = long(global_3);
    ulong local_14 = ulong(global_2);
    ulong local_15 = ulong(global_3);
    int local_16 = int(global_4);
    int local_17 = int(global_5);
    uint local_18 = uint(global_4);
    uint local_19 = uint(global_5);
    long local_20 = long(global_4);
    long local_21 = long(global_5);
    ulong local_22 = ulong(global_4);
    ulong local_23 = ulong(global_5);
}

[[kernel]]
void main() {
    test_const_eval();
    test_f16_to_i32(1.0f);
    test_f16_to_u32(1.0f);
    test_f16_to_i64(1.0f);
    test_f16_to_u64(1.0f);
    test_f32_to_i32(1.0f);
    test_f32_to_u32(1.0f);
    test_f32_to_i64(1.0f);
    test_f32_to_u64(1.0f);
    test_f16_to_i32_vec(float2(1, 2));
    test_f16_to_u32_vec(float2(1, 2));
    test_f16_to_i64_vec(float2(1, 2));
    test_f16_to_u64_vec(float2(1, 2));
    test_f32_to_i32_vec(float2(1, 2));
    test_f32_to_u32_vec(float2(1, 2));
    test_f32_to_i64_vec(float2(1, 2));
    test_f32_to_u64_vec(float2(1, 2));
}
