#version 450 core
precision highp float;
precision highp int;

struct Struct_11 {
    uint val_u32;
    int val_i32;
    float val_f32;
    uint64_t val_u64;
    uvec2 val_u64_2;
    uvec3 val_u64_3;
    uvec4 val_u64_4;
    int64_t val_i64;
    ivec2 val_i64_2;
    ivec3 val_i64_3;
    ivec4 val_i64_4;
    uint64_t final_value;
};
struct Struct_14 {
    uint64_t[2] val_u64_array_2;
    int64_t[2] val_i64_array_2;
};
 int64_t global_0 = 1;
 uint64_t global_1 = 20u;
layout(set = 0, binding = 0) uniform Struct_11 global_2;
layout(set = 0, binding = 1) buffer Struct_11 global_3;
layout(set = 0, binding = 3) buffer Struct_11 global_4;
layout(set = 0, binding = 2) buffer Struct_14 global_5;
layout(set = 0, binding = 4) buffer Struct_14 global_6;

int64_t int64_function(int64_t x) {
    global_0;
    int64_t val = int64_t(global_1);
    val = ((31 - 1002003004005006) + -(7));
    val = (val + int64_t(5));
    val = int64_t((global_2.val_u32 + uint(val)));
    val = int64_t((global_2.val_i32 + int(val)));
    val = int64_t((global_2.val_f32 + float(val)));
    val = ivec3(global_2.val_i64)[2];
    val = int64_t(global_2.val_u64);
    val = ivec2(global_2.val_u64_2)[1];
    val = ivec3(global_2.val_u64_3)[2];
    val = ivec4(global_2.val_u64_4)[3];
    val = int64_t((-(-1) - 1));
    global_4.val_i64 = (global_2.val_i64 + global_3.val_i64);
    global_4.val_i64_2 = (global_2.val_i64_2 + global_3.val_i64_2);
    global_4.val_i64_3 = (global_2.val_i64_3 + global_3.val_i64_3);
    global_4.val_i64_4 = (global_2.val_i64_4 + global_3.val_i64_4);
    global_6.val_i64_array_2 = global_5.val_i64_array_2;
    val = abs(val);
    val = clamp(val, val, val);
    val = dot(vec2(val), vec2(val));
    val = max(val, val);
    val = min(val, val);
    val = sign(val);
    return val;
}

int64_t abs(int64_t arg_0) {
}

int64_t clamp(int64_t arg_0, int64_t arg_1, int64_t arg_2) {
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

int64_t max(int64_t arg_0, int64_t arg_1) {
}

int64_t min(int64_t arg_0, int64_t arg_1) {
}

int64_t sign(int64_t arg_0) {
}

uint64_t uint64_function(uint64_t x) {
    uint64_t val = uint64_t(global_1);
    val = ((31u + 0u) - 0);
    val = (val + uint64_t(5));
    val = uint64_t((global_2.val_u32 + uint(val)));
    val = uint64_t((global_2.val_i32 + int(val)));
    val = uint64_t((global_2.val_f32 + float(val)));
    val = uvec3(global_2.val_u64)[2];
    val = uint64_t(global_2.val_i64);
    val = uvec2(global_2.val_i64_2)[1];
    val = uvec3(global_2.val_i64_3)[2];
    val = uvec4(global_2.val_i64_4)[3];
    global_4.val_u64 = (global_2.val_u64 + global_3.val_u64);
    global_4.val_u64_2 = (global_2.val_u64_2 + global_3.val_u64_2);
    global_4.val_u64_3 = (global_2.val_u64_3 + global_3.val_u64_3);
    global_4.val_u64_4 = (global_2.val_u64_4 + global_3.val_u64_4);
    global_6.val_u64_array_2 = global_5.val_u64_array_2;
    val = abs(val);
    val = clamp(val, val, val);
    val = dot(vec2(val), vec2(val));
    val = max(val, val);
    val = min(val, val);
    return val;
}

uint64_t abs(uint64_t arg_0) {
}

uint64_t clamp(uint64_t arg_0, uint64_t arg_1, uint64_t arg_2) {
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

uint64_t max(uint64_t arg_0, uint64_t arg_1) {
}

uint64_t min(uint64_t arg_0, uint64_t arg_1) {
}

void wgsl_main() {
    global_4.final_value = (uint64_function(67u) + /* unsupported bitcast */ int64_function(60));
}

void main() {
    wgsl_main();
}
