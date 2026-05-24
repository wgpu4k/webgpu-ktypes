#version 450 core
precision highp float;
precision highp int;

struct Struct_11 {
    uint val_u32;
    int val_i32;
    float val_f32;
    uint val_u16;
    uvec2 val_u16_2;
    uvec3 val_u16_3;
    uvec4 val_u16_4;
    int val_i16;
    ivec2 val_i16_2;
    ivec3 val_i16_3;
    ivec4 val_i16_4;
    uint final_value;
};
struct Struct_14 {
    uint[2] val_u16_array_2;
    int[2] val_i16_array_2;
};
 int global_0 = int(1);
 uint global_1 = uint(20);
 int global_2 = int(float16_t(33000.0f));
shared uint global_3;
layout(set = 0, binding = 0) uniform Struct_11 global_4;
layout(set = 0, binding = 1) buffer Struct_11 global_5;
layout(set = 0, binding = 3) buffer Struct_11 global_6;
layout(set = 0, binding = 2) buffer Struct_14 global_7;
layout(set = 0, binding = 4) buffer Struct_14 global_8;

int int16_function(int x) {
    global_0;
    int val = int(global_1);
    val = (val + int(5));
    val = (val + int(global_4.val_u32));
    val = (val + int(global_4.val_i32));
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
    val = (val - int(1));
    val = (val * int(2));
    val = (val / int(3));
    val = mod(val, int(4));
    val = (val & int(0));
    val = (val | int(0));
    val = (val ^ int(0));
    val = (val << 2u);
    val = (val >> 1u);
    val = -(val);
    int cmp_lt = (val < int(0));
    int cmp_le = (val <= int(0));
    int cmp_gt = (val > int(0));
    int cmp_ge = (val >= int(0));
    int cmp_eq = (val == int(0));
    int cmp_ne = (val != int(0));
    val = select(int(1), int(2), cmp_lt);
    int[4] arr = int[4](int(1), int(2), int(3), int(4));
    arr[0] = val;
    val = arr[1];
    uint u16_idx = uint(1);
    val = arr[u16_idx];
    global_6.val_u32 = uint(val);
    global_6.val_i32 = int(val);
    global_6.val_f32 = float(val);
    val = int(global_6.val_u32);
    uint as_unsigned = uint(val);
    val = int(as_unsigned);
    ivec2 v = (global_4.val_i16_2 + global_4.val_i16_2);
    ivec2 v2 = (v * ivec2(int(2)));
    global_6.val_i16_2 = v2;
    return val;
}

int abs(int arg_0) {
}

int max(int arg_0, int arg_1) {
}

int min(int arg_0, int arg_1) {
}

int clamp(int arg_0, int arg_1, int arg_2) {
}

int sign(int arg_0) {
}

int select(int arg_0, int arg_1, int arg_2) {
}

uint uint16_function(uint x) {
    uint val = uint(global_1);
    val = (val + uint(5));
    val = (val + uint(global_4.val_u32));
    val = (val + uint(global_4.val_i32));
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
    val = (val - uint(1));
    val = (val * uint(2));
    val = (val / uint(3));
    val = mod(val, uint(4));
    val = (val & uint(0));
    val = (val | uint(0));
    val = (val ^ uint(0));
    global_6.val_u32 = uint(val);
    global_6.val_i32 = int(val);
    global_6.val_f32 = float(val);
    val = uint(global_6.val_u32);
    return val;
}

uint abs(uint arg_0) {
}

uint max(uint arg_0, uint arg_1) {
}

uint min(uint arg_0, uint arg_1) {
}

uint clamp(uint arg_0, uint arg_1, uint arg_2) {
}

void wgsl_main(uint subgroup_invocation_id) {
    global_3 = uint(0);
    global_6.final_value = (uint16_function(uint(67)) + uint(int16_function(int(60))));
    int sg_val = int(subgroup_invocation_id);
    sg_val = subgroupAdd(sg_val);
    sg_val = subgroupMul(sg_val);
    sg_val = subgroupMin(sg_val);
    sg_val = subgroupMax(sg_val);
    sg_val = subgroupExclusiveAdd(sg_val);
    sg_val = subgroupInclusiveAdd(sg_val);
    sg_val = subgroupBroadcastFirst(sg_val);
    sg_val = subgroupBroadcast(sg_val, 4u);
    uint sg_uval = uint(subgroup_invocation_id);
    sg_uval = subgroupAdd(sg_uval);
    sg_uval = subgroupMin(sg_uval);
    sg_uval = subgroupMax(sg_uval);
    global_6.val_i16 = sg_val;
    global_6.val_u16 = sg_uval;
}

int subgroupAdd(int arg_0) {
}

int subgroupMul(int arg_0) {
}

int subgroupMin(int arg_0) {
}

int subgroupMax(int arg_0) {
}

int subgroupExclusiveAdd(int arg_0) {
}

int subgroupInclusiveAdd(int arg_0) {
}

int subgroupBroadcastFirst(int arg_0) {
}

int subgroupBroadcast(int arg_0, uint arg_1) {
}

uint subgroupAdd(uint arg_0) {
}

uint subgroupMin(uint arg_0) {
}

uint subgroupMax(uint arg_0) {
}

void main() {
    wgsl_main(subgroup_invocation_id);
}
