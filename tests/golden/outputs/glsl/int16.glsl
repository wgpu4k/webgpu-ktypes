#version 450 core
precision highp float;
precision highp int;

struct Struct_6 {
    uint val_u32;
    int val_i32;
    float val_f32;
    float val_u16;
    vec2 val_u16_2;
    vec3 val_u16_3;
    vec4 val_u16_4;
    float val_i16;
    vec2 val_i16_2;
    vec3 val_i16_3;
    vec4 val_i16_4;
    float final_value;
};
struct Struct_9 {
    float[2] val_u16_array_2;
    float[2] val_i16_array_2;
};
 float global_0 = i16(1);
 float global_1 = u16(20);
 float global_2 = i16(f16(33000.0f));
shared float global_3;
layout(set = 0, binding = 0) uniform Struct_6 global_4;
layout(set = 0, binding = 1) buffer Struct_6 global_5;
layout(set = 0, binding = 3) buffer Struct_6 global_6;
layout(set = 0, binding = 2) buffer Struct_9 global_7;
layout(set = 0, binding = 4) buffer Struct_9 global_8;

void i16() {
}

void u16() {
}

void i16() {
}

void f16() {
}

float int16_function(float x) {
    float val = i16(global_1);
    val = (val + i16(5));
    val = (val + i16(global_4.val_u32));
    val = (val + i16(global_4.val_i32));
    val = (val + vec3(global_4.val_i16)[2]);
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
    val = (val - i16(1));
    val = (val * i16(2));
    val = (val / i16(3));
    val = mod(val, i16(4));
    val = (val & i16(0));
    val = (val | i16(0));
    val = (val ^ i16(0));
    val = (val << 0u);
    val = (val >> 0u);
    val = -(val);
    float cmp_lt = (val < i16(0));
    float cmp_le = (val <= i16(0));
    float cmp_gt = (val > i16(0));
    float cmp_ge = (val >= i16(0));
    float cmp_eq = (val == i16(0));
    float cmp_ne = (val != i16(0));
    val = select(i16(1), i16(2), cmp_lt);
    float[4] arr = float[4](i16(1), i16(2), i16(3), i16(4));
    arr[0] = val;
    val = arr[1];
    int u16_idx = u16(1);
    val = arr[u16_idx];
    global_6.val_u32 = uint(val);
    global_6.val_i32 = int(val);
    global_6.val_f32 = float(val);
    val = i16(global_6.val_u32);
    float as_unsigned = intBitsToFloat(val);
    val = intBitsToFloat(as_unsigned);
    vec2 v = (global_4.val_i16_2 + global_4.val_i16_2);
    vec2 v2 = (v * vec2(i16(2)));
    global_6.val_i16_2 = v2;
    return val;
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
    float val = u16(global_1);
    val = (val + u16(5));
    val = (val + u16(global_4.val_u32));
    val = (val + u16(global_4.val_i32));
    val = (val + vec3(global_4.val_u16)[2]);
    global_6.val_u16 = (global_4.val_u16 + global_5.val_u16);
    global_6.val_u16_2 = (global_4.val_u16_2 + global_5.val_u16_2);
    global_6.val_u16_3 = (global_4.val_u16_3 + global_5.val_u16_3);
    global_6.val_u16_4 = (global_4.val_u16_4 + global_5.val_u16_4);
    global_8.val_u16_array_2 = global_7.val_u16_array_2;
    val = abs(val);
    val = max(val, val);
    val = min(val, val);
    val = clamp(val, val, val);
    val = (val - u16(1));
    val = (val * u16(2));
    val = (val / u16(3));
    val = mod(val, u16(4));
    val = (val & u16(0));
    val = (val | u16(0));
    val = (val ^ u16(0));
    global_6.val_u32 = uint(val);
    global_6.val_i32 = int(val);
    global_6.val_f32 = float(val);
    val = u16(global_6.val_u32);
    return val;
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

void wgsl_main(uint subgroup_invocation_id) {
    global_3 = u16(0);
    global_6.final_value = (uint16_function(u16(67)) + u16(int16_function(i16(60))));
    float sg_val = i16(subgroup_invocation_id);
    sg_val = subgroupAdd(sg_val);
    sg_val = subgroupMul(sg_val);
    sg_val = subgroupMin(sg_val);
    sg_val = subgroupMax(sg_val);
    sg_val = subgroupExclusiveAdd(sg_val);
    sg_val = subgroupInclusiveAdd(sg_val);
    sg_val = subgroupBroadcastFirst(sg_val);
    sg_val = subgroupBroadcast(sg_val, 0u);
    float sg_uval = u16(subgroup_invocation_id);
    sg_uval = subgroupAdd(sg_uval);
    sg_uval = subgroupMin(sg_uval);
    sg_uval = subgroupMax(sg_uval);
    global_6.val_i16 = sg_val;
    global_6.val_u16 = sg_uval;
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

void main() {
    wgsl_main(subgroup_invocation_id);
}
