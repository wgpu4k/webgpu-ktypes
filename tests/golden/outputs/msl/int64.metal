#include <metal_stdlib>
using namespace metal;
struct Struct_11 {
    uint val_u32;
    int val_i32;
    float val_f32;
    ulong val_u64;
    ulong2 val_u64_2;
    ulong3 val_u64_3;
    ulong4 val_u64_4;
    long val_i64;
    long2 val_i64_2;
    long3 val_i64_3;
    long4 val_i64_4;
    ulong final_value;
};
struct Struct_14 {
    array<ulong, 2> val_u64_array_2;
    array<long, 2> val_i64_array_2;
};
long global_0 = 1;
ulong global_1 = 20u;

long int64_function(long x) {
    global_0;
    long local_0 = long(global_1);
    local_0 = ((31 - 1002003004005006) + -(7));
    local_0 = (local_0 + long(5));
    local_0 = long((global_2.val_u32 + uint(local_0)));
    local_0 = long((global_2.val_i32 + int(local_0)));
    local_0 = long((global_2.val_f32 + float(local_0)));
    local_0 = long3(global_2.val_i64)[2];
    local_0 = as_type<long>(global_2.val_u64);
    local_0 = as_type<long2>(global_2.val_u64_2)[1];
    local_0 = as_type<long3>(global_2.val_u64_3)[2];
    local_0 = as_type<long4>(global_2.val_u64_4)[3];
    local_0 = long((-(-1) - 1));
    global_4.val_i64 = (global_2.val_i64 + global_3.val_i64);
    global_4.val_i64_2 = (global_2.val_i64_2 + global_3.val_i64_2);
    global_4.val_i64_3 = (global_2.val_i64_3 + global_3.val_i64_3);
    global_4.val_i64_4 = (global_2.val_i64_4 + global_3.val_i64_4);
    global_6.val_i64_array_2 = global_5.val_i64_array_2;
    local_0 = abs(local_0);
    local_0 = clamp(local_0, local_0, local_0);
    local_0 = dot(float2(local_0), float2(local_0));
    local_0 = max(local_0, local_0);
    local_0 = min(local_0, local_0);
    local_0 = sign(local_0);
    return local_0;
}

long abs(long arg_0) {
}

long clamp(long arg_0, long arg_1, long arg_2) {
}

float2 dot(float2 arg_0, float2 arg_1) {
}

long max(long arg_0, long arg_1) {
}

long min(long arg_0, long arg_1) {
}

long sign(long arg_0) {
}

ulong uint64_function(ulong x) {
    ulong local_0 = ulong(global_1);
    local_0 = ((31u + 0u) - 0);
    local_0 = (local_0 + ulong(5));
    local_0 = ulong((global_2.val_u32 + uint(local_0)));
    local_0 = ulong((global_2.val_i32 + int(local_0)));
    local_0 = ulong((global_2.val_f32 + float(local_0)));
    local_0 = ulong3(global_2.val_u64)[2];
    local_0 = as_type<ulong>(global_2.val_i64);
    local_0 = as_type<ulong2>(global_2.val_i64_2)[1];
    local_0 = as_type<ulong3>(global_2.val_i64_3)[2];
    local_0 = as_type<ulong4>(global_2.val_i64_4)[3];
    global_4.val_u64 = (global_2.val_u64 + global_3.val_u64);
    global_4.val_u64_2 = (global_2.val_u64_2 + global_3.val_u64_2);
    global_4.val_u64_3 = (global_2.val_u64_3 + global_3.val_u64_3);
    global_4.val_u64_4 = (global_2.val_u64_4 + global_3.val_u64_4);
    global_6.val_u64_array_2 = global_5.val_u64_array_2;
    local_0 = abs(local_0);
    local_0 = clamp(local_0, local_0, local_0);
    local_0 = dot(float2(local_0), float2(local_0));
    local_0 = max(local_0, local_0);
    local_0 = min(local_0, local_0);
    return local_0;
}

ulong abs(ulong arg_0) {
}

ulong clamp(ulong arg_0, ulong arg_1, ulong arg_2) {
}

float2 dot(float2 arg_0, float2 arg_1) {
}

ulong max(ulong arg_0, ulong arg_1) {
}

ulong min(ulong arg_0, ulong arg_1) {
}

[[kernel]]
void main(Struct_11 global_2 [[buffer(0)]], Struct_11 global_3 [[buffer(1)]], Struct_11 global_4 [[buffer(3)]], Struct_14 global_5 [[buffer(2)]], Struct_14 global_6 [[buffer(4)]]) {
    global_4.final_value = (uint64_function(67u) + as_type<ulong>(int64_function(60)));
}
