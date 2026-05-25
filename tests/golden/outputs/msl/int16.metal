#include <metal_stdlib>
using namespace metal;
struct Struct_11 {
    uint val_u32;
    int val_i32;
    float val_f32;
    ushort val_u16;
    ushort2 val_u16_2;
    ushort3 val_u16_3;
    ushort4 val_u16_4;
    short val_i16;
    short2 val_i16_2;
    short3 val_i16_3;
    short4 val_i16_4;
    ushort final_value;
};
struct Struct_14 {
    array<ushort, 2> val_u16_array_2;
    array<short, 2> val_i16_array_2;
};
short global_0 = short(1);
ushort global_1 = ushort(20);
short global_2 = short(half(33000.0f));
ushort global_3;

short int16_function(short x) {
    global_0;
    short local_0 = short(global_1);
    local_0 = (local_0 + short(5));
    local_0 = (local_0 + short(global_4.val_u32));
    local_0 = (local_0 + short(global_4.val_i32));
    local_0 = (local_0 + short3(global_4.val_i16)[2]);
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
    local_0 = (local_0 - short(1));
    local_0 = (local_0 * short(2));
    local_0 = (local_0 / short(3));
    local_0 = (local_0 % short(4));
    local_0 = (local_0 & short(0));
    local_0 = (local_0 | short(16));
    local_0 = (local_0 ^ short(1));
    local_0 = (local_0 << 2u);
    local_0 = (local_0 >> 1u);
    local_0 = -(local_0);
    bool local_1 = (local_0 < short(0));
    bool local_2 = (local_0 <= short(0));
    bool local_3 = (local_0 > short(0));
    bool local_4 = (local_0 >= short(0));
    bool local_5 = (local_0 == short(0));
    bool local_6 = (local_0 != short(0));
    local_0 = select(short(1), short(2), local_1);
    array<short, 4> local_7 = array<short, 4>(short(1), short(2), short(3), short(4));
    local_7[0] = local_0;
    local_0 = local_7[1];
    ushort local_8 = ushort(1);
    local_0 = local_7[local_8];
    global_6.val_u32 = uint(local_0);
    global_6.val_i32 = int(local_0);
    global_6.val_f32 = float(local_0);
    local_0 = short(global_6.val_u32);
    ushort local_9 = as_type<ushort>(local_0);
    local_0 = as_type<short>(local_9);
    short2 local_10 = (global_4.val_i16_2 + global_4.val_i16_2);
    short2 local_11 = (local_10 * short2(short(2)));
    global_6.val_i16_2 = local_11;
    return local_0;
}

short abs(short arg_0) {
}

short max(short arg_0, short arg_1) {
}

short min(short arg_0, short arg_1) {
}

short clamp(short arg_0, short arg_1, short arg_2) {
}

short sign(short arg_0) {
}

short select(short arg_0, short arg_1, bool arg_2) {
}

ushort uint16_function(ushort x) {
    ushort local_0 = ushort(global_1);
    local_0 = (local_0 + ushort(5));
    local_0 = (local_0 + ushort(global_4.val_u32));
    local_0 = (local_0 + ushort(global_4.val_i32));
    local_0 = (local_0 + ushort3(global_4.val_u16)[2]);
    global_6.val_u16 = (global_4.val_u16 + global_5.val_u16);
    global_6.val_u16_2 = (global_4.val_u16_2 + global_5.val_u16_2);
    global_6.val_u16_3 = (global_4.val_u16_3 + global_5.val_u16_3);
    global_6.val_u16_4 = (global_4.val_u16_4 + global_5.val_u16_4);
    global_8.val_u16_array_2 = global_7.val_u16_array_2;
    local_0 = abs(local_0);
    local_0 = max(local_0, local_0);
    local_0 = min(local_0, local_0);
    local_0 = clamp(local_0, local_0, local_0);
    local_0 = (local_0 - ushort(1));
    local_0 = (local_0 * ushort(2));
    local_0 = (local_0 / ushort(3));
    local_0 = (local_0 % ushort(4));
    local_0 = (local_0 & ushort(0));
    local_0 = (local_0 | ushort(16));
    local_0 = (local_0 ^ ushort(1));
    global_6.val_u32 = uint(local_0);
    global_6.val_i32 = int(local_0);
    global_6.val_f32 = float(local_0);
    local_0 = ushort(global_6.val_u32);
    return local_0;
}

ushort abs(ushort arg_0) {
}

ushort max(ushort arg_0, ushort arg_1) {
}

ushort min(ushort arg_0, ushort arg_1) {
}

ushort clamp(ushort arg_0, ushort arg_1, ushort arg_2) {
}

[[kernel]]
void main(Struct_11 global_4 [[buffer(0)]], Struct_11 global_5 [[buffer(1)]], Struct_11 global_6 [[buffer(3)]], Struct_14 global_7 [[buffer(2)]], Struct_14 global_8 [[buffer(4)]]) {
    global_3 = ushort(0);
    global_6.final_value = (uint16_function(ushort(67)) + ushort(int16_function(short(60))));
    short local_0 = short(subgroup_invocation_id);
    local_0 = subgroupAdd(local_0);
    local_0 = subgroupMul(local_0);
    local_0 = subgroupMin(local_0);
    local_0 = subgroupMax(local_0);
    local_0 = subgroupExclusiveAdd(local_0);
    local_0 = subgroupInclusiveAdd(local_0);
    local_0 = subgroupBroadcastFirst(local_0);
    local_0 = subgroupBroadcast(local_0, 4u);
    ushort local_1 = ushort(subgroup_invocation_id);
    local_1 = subgroupAdd(local_1);
    local_1 = subgroupMin(local_1);
    local_1 = subgroupMax(local_1);
    global_6.val_i16 = local_0;
    global_6.val_u16 = local_1;
}

short subgroupAdd(short arg_0) {
}

short subgroupMul(short arg_0) {
}

short subgroupMin(short arg_0) {
}

short subgroupMax(short arg_0) {
}

short subgroupExclusiveAdd(short arg_0) {
}

short subgroupInclusiveAdd(short arg_0) {
}

short subgroupBroadcastFirst(short arg_0) {
}

short subgroupBroadcast(short arg_0, uint arg_1) {
}

ushort subgroupAdd(ushort arg_0) {
}

ushort subgroupMin(ushort arg_0) {
}

ushort subgroupMax(ushort arg_0) {
}
