#include <metal_stdlib>
using namespace metal;
half global_0 = -(65504.0h);
half global_1 = 65504.0h;
float global_2 = -(3.4028235E38f);
float global_3 = 3.4028235E38f;
double global_4 = -(1.7976931348623157E308);
double global_5 = 1.7976931348623157E308;
float global_6 = -(Infinity.0f);
float global_7 = Infinity.0f;

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

int test_f64_to_i32(double f) {
    return int(f);
}

uint test_f64_to_u32(double f) {
    return uint(f);
}

long test_f64_to_i64(double f) {
    return long(f);
}

ulong test_f64_to_u64(double f) {
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

int2 test_f64_to_i32_vec(double2 f) {
    return int2(f);
}

uint2 test_f64_to_u32_vec(double2 f) {
    return uint2(f);
}

long2 test_f64_to_i64_vec(double2 f) {
    return long2(f);
}

ulong2 test_f64_to_u64_vec(double2 f) {
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
    long local_16 = long(global_4);
    long local_17 = long(global_5);
    ulong local_18 = ulong(global_4);
    ulong local_19 = ulong(global_5);
    int local_20 = int(global_6);
    int local_21 = int(global_7);
    uint local_22 = uint(global_6);
    uint local_23 = uint(global_7);
    long local_24 = long(global_6);
    long local_25 = long(global_7);
    ulong local_26 = ulong(global_6);
    ulong local_27 = ulong(global_7);
}

[[kernel]]
void main() {
    test_const_eval();
    test_f16_to_i32(1.0h);
    test_f16_to_u32(1.0h);
    test_f16_to_i64(1.0h);
    test_f16_to_u64(1.0h);
    test_f32_to_i32(1.0f);
    test_f32_to_u32(1.0f);
    test_f32_to_i64(1.0f);
    test_f32_to_u64(1.0f);
    test_f64_to_i32(1.0);
    test_f64_to_u32(1.0);
    test_f64_to_i64(1.0);
    test_f64_to_u64(1.0);
    test_f16_to_i32_vec(float2(1.0f, 2.0f));
    test_f16_to_u32_vec(float2(1.0f, 2.0f));
    test_f16_to_i64_vec(float2(1.0f, 2.0f));
    test_f16_to_u64_vec(float2(1.0f, 2.0f));
    test_f32_to_i32_vec(float2(1.0f, 2.0f));
    test_f32_to_u32_vec(float2(1.0f, 2.0f));
    test_f32_to_i64_vec(float2(1.0f, 2.0f));
    test_f32_to_u64_vec(float2(1.0f, 2.0f));
    test_f64_to_i32_vec(float2(1.0f, 2.0f));
    test_f64_to_u32_vec(float2(1.0f, 2.0f));
    test_f64_to_i64_vec(float2(1.0f, 2.0f));
    test_f64_to_u64_vec(float2(1.0f, 2.0f));
}
