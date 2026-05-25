#version 450 core
#extension GL_EXT_shader_explicit_arithmetic_types_float16 : require
#extension GL_EXT_shader_explicit_arithmetic_types_int16 : require
precision highp float;
precision highp int;

struct Struct_11 {
    uint val_u32;
    int val_i32;
    float val_f32;
    uint16_t val_u16;
    uvec2 val_u16_2;
    uvec3 val_u16_3;
    uvec4 val_u16_4;
    int16_t val_i16;
    ivec2 val_i16_2;
    ivec3 val_i16_3;
    ivec4 val_i16_4;
    uint16_t final_value;
};
struct Struct_14 {
    uint16_t[2] val_u16_array_2;
    int16_t[2] val_i16_array_2;
};
 int16_t global_0 = int16_t(1);
 uint16_t global_1 = uint16_t(20);
 int16_t global_2 = int16_t(float16_t(33000.0f));
shared uint16_t global_3;
layout(set = 0, binding = 0) uniform Struct_11 global_4;
layout(set = 0, binding = 1) buffer Struct_11 global_5;
layout(set = 0, binding = 3) buffer Struct_11 global_6;
layout(set = 0, binding = 2) buffer Struct_14 global_7;
layout(set = 0, binding = 4) buffer Struct_14 global_8;

int16_t int16_function(int16_t x) {
    global_0;
    int16_t val = int16_t(global_1);
    val = (val + int16_t(5));
    val = (val + int16_t(global_4.val_u32));
    val = (val + int16_t(global_4.val_i32));
    val = (val + ivec3(global_4.val_i16)[2]);
    global_6.val_i16 = (global_4.val_i16 + global_5.val_i16);
    global_6.val_i16_2 = (global_4.val_i16_2 + global_5.val_i16_2);
    global_6.val_i16_3 = (global_4.val_i16_3 + global_5.val_i16_3);
    global_6.val_i16_4 = (global_4.val_i16_4 + global_5.val_i16_4);
    global_8.val_i16_array_2 = global_7.val_i16_array_2;
    val = abs(val);
    val = max(val, val);
    val = min(val, val);
    val = clamp(val, val, val);
    val = sign(val);
    val = (val - int16_t(1));
    val = (val * int16_t(2));
    val = (val / int16_t(3));
    val = mod(val, int16_t(4));
    val = (val & int16_t(0));
    val = (val | int16_t(16));
    val = (val ^ int16_t(1));
    val = (val << 2u);
    val = (val >> 1u);
    val = -(val);
    bool cmp_lt = (val < int16_t(0));
    bool cmp_le = (val <= int16_t(0));
    bool cmp_gt = (val > int16_t(0));
    bool cmp_ge = (val >= int16_t(0));
    bool cmp_eq = (val == int16_t(0));
    bool cmp_ne = (val != int16_t(0));
    val = select(int16_t(1), int16_t(2), cmp_lt);
    int16_t[4] arr = int16_t[4](int16_t(1), int16_t(2), int16_t(3), int16_t(4));
    arr[0] = val;
    val = arr[1];
    uint16_t u16_idx = uint16_t(1);
    val = arr[u16_idx];
    global_6.val_u32 = uint(val);
    global_6.val_i32 = int(val);
    global_6.val_f32 = float(val);
    val = int16_t(global_6.val_u32);
    uint16_t as_unsigned = uint16_t(val);
    val = int16_t(as_unsigned);
    ivec2 v = (global_4.val_i16_2 + global_4.val_i16_2);
    ivec2 v2 = (v * ivec2(int16_t(2)));
    global_6.val_i16_2 = v2;
    return val;
}

int16_t abs(int16_t arg_0) {
}

int16_t max(int16_t arg_0, int16_t arg_1) {
}

int16_t min(int16_t arg_0, int16_t arg_1) {
}

int16_t clamp(int16_t arg_0, int16_t arg_1, int16_t arg_2) {
}

int16_t sign(int16_t arg_0) {
}

int16_t select(int16_t arg_0, int16_t arg_1, bool arg_2) {
}

uint16_t uint16_function(uint16_t x) {
    uint16_t val = uint16_t(global_1);
    val = (val + uint16_t(5));
    val = (val + uint16_t(global_4.val_u32));
    val = (val + uint16_t(global_4.val_i32));
    val = (val + uvec3(global_4.val_u16)[2]);
    global_6.val_u16 = (global_4.val_u16 + global_5.val_u16);
    global_6.val_u16_2 = (global_4.val_u16_2 + global_5.val_u16_2);
    global_6.val_u16_3 = (global_4.val_u16_3 + global_5.val_u16_3);
    global_6.val_u16_4 = (global_4.val_u16_4 + global_5.val_u16_4);
    global_8.val_u16_array_2 = global_7.val_u16_array_2;
    val = abs(val);
    val = max(val, val);
    val = min(val, val);
    val = clamp(val, val, val);
    val = (val - uint16_t(1));
    val = (val * uint16_t(2));
    val = (val / uint16_t(3));
    val = mod(val, uint16_t(4));
    val = (val & uint16_t(0));
    val = (val | uint16_t(16));
    val = (val ^ uint16_t(1));
    global_6.val_u32 = uint(val);
    global_6.val_i32 = int(val);
    global_6.val_f32 = float(val);
    val = uint16_t(global_6.val_u32);
    return val;
}

uint16_t abs(uint16_t arg_0) {
}

uint16_t max(uint16_t arg_0, uint16_t arg_1) {
}

uint16_t min(uint16_t arg_0, uint16_t arg_1) {
}

uint16_t clamp(uint16_t arg_0, uint16_t arg_1, uint16_t arg_2) {
}

void wgsl_main(uint subgroup_invocation_id) {
    global_3 = uint16_t(0);
    global_6.final_value = (uint16_function(uint16_t(67)) + uint16_t(int16_function(int16_t(60))));
    int16_t sg_val = int16_t(subgroup_invocation_id);
    sg_val = subgroupAdd(sg_val);
    sg_val = subgroupMul(sg_val);
    sg_val = subgroupMin(sg_val);
    sg_val = subgroupMax(sg_val);
    sg_val = subgroupExclusiveAdd(sg_val);
    sg_val = subgroupInclusiveAdd(sg_val);
    sg_val = subgroupBroadcastFirst(sg_val);
    sg_val = subgroupBroadcast(sg_val, 4u);
    uint16_t sg_uval = uint16_t(subgroup_invocation_id);
    sg_uval = subgroupAdd(sg_uval);
    sg_uval = subgroupMin(sg_uval);
    sg_uval = subgroupMax(sg_uval);
    global_6.val_i16 = sg_val;
    global_6.val_u16 = sg_uval;
}

int16_t subgroupAdd(int16_t arg_0) {
}

int16_t subgroupMul(int16_t arg_0) {
}

int16_t subgroupMin(int16_t arg_0) {
}

int16_t subgroupMax(int16_t arg_0) {
}

int16_t subgroupExclusiveAdd(int16_t arg_0) {
}

int16_t subgroupInclusiveAdd(int16_t arg_0) {
}

int16_t subgroupBroadcastFirst(int16_t arg_0) {
}

int16_t subgroupBroadcast(int16_t arg_0, uint arg_1) {
}

uint16_t subgroupAdd(uint16_t arg_0) {
}

uint16_t subgroupMin(uint16_t arg_0) {
}

uint16_t subgroupMax(uint16_t arg_0) {
}

void main() {
    wgsl_main(subgroup_invocation_id);
}
