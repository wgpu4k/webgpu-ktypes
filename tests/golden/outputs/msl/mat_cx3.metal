#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float3x3 m;
};
struct Struct_4 {
    /* unknown type */ void a;
};

void access_m() {
    int local_0 = 1;
    local_0 = (local_0 - 1);
    float3x3 local_1 = global_0;
    float3 local_2 = global_0[0];
    float3 local_3 = global_0[local_0];
    float local_4 = global_0[0][0];
    float local_5 = global_0[0][local_0];
    float local_6 = global_0[local_0][0];
    float local_7 = global_0[local_0][local_0];
    float3x3 local_8 = global_1;
    float3 local_9 = global_1[0];
    float3 local_10 = global_1[local_0];
    float local_11 = global_1[0][0];
    float local_12 = global_1[0][local_0];
    float local_13 = global_1[local_0][0];
    float local_14 = global_1[local_0][local_0];
    global_0 = local_8;
    global_0[0] = local_9;
    global_0[local_0] = local_10;
    global_0[0][0] = local_11;
    global_0[0][local_0] = local_12;
    global_0[local_0][0] = local_13;
    global_0[local_0][local_0] = local_14;
}

void access_am() {
    int local_0 = 1;
    local_0 = (local_0 - 1);
    /* unknown type */ void local_1 = global_2;
    float3x3 local_2 = global_2[0];
    float3x3 local_3 = global_2[local_0];
    float3 local_4 = global_2[0][0];
    float3 local_5 = global_2[0][local_0];
    float3 local_6 = global_2[local_0][0];
    float3 local_7 = global_2[local_0][local_0];
    float local_8 = global_2[0][0][0];
    float local_9 = global_2[0][0][local_0];
    float local_10 = global_2[0][local_0][0];
    float local_11 = global_2[0][local_0][local_0];
    float local_12 = global_2[local_0][0][0];
    float local_13 = global_2[local_0][0][local_0];
    float local_14 = global_2[local_0][local_0][0];
    float local_15 = global_2[local_0][local_0][local_0];
    /* unknown type */ void local_16 = global_3;
    float3x3 local_17 = global_3[0];
    float3x3 local_18 = global_3[local_0];
    float3 local_19 = global_3[0][0];
    float3 local_20 = global_3[0][local_0];
    float3 local_21 = global_3[local_0][0];
    float3 local_22 = global_3[local_0][local_0];
    float local_23 = global_3[0][0][0];
    float local_24 = global_3[0][0][local_0];
    float local_25 = global_3[0][local_0][0];
    float local_26 = global_3[0][local_0][local_0];
    float local_27 = global_3[local_0][0][0];
    float local_28 = global_3[local_0][0][local_0];
    float local_29 = global_3[local_0][local_0][0];
    float local_30 = global_3[local_0][local_0][local_0];
    global_2 = local_16;
    global_2[0] = local_17;
    global_2[local_0] = local_18;
    global_2[0][0] = local_19;
    global_2[0][local_0] = local_20;
    global_2[local_0][0] = local_21;
    global_2[local_0][local_0] = local_22;
    global_2[0][0][0] = local_23;
    global_2[0][0][local_0] = local_24;
    global_2[0][local_0][0] = local_25;
    global_2[0][local_0][local_0] = local_26;
    global_2[local_0][0][0] = local_27;
    global_2[local_0][0][local_0] = local_28;
    global_2[local_0][local_0][0] = local_29;
    global_2[local_0][local_0][local_0] = local_30;
}

void access_sm() {
    int local_0 = 1;
    local_0 = (local_0 - 1);
    Struct_2 local_1 = global_4;
    float3x3 local_2 = global_4.m;
    float3 local_3 = global_4.m[0];
    float3 local_4 = global_4.m[local_0];
    float local_5 = global_4.m[0][0];
    float local_6 = global_4.m[0][local_0];
    float local_7 = global_4.m[local_0][0];
    float local_8 = global_4.m[local_0][local_0];
    Struct_2 local_9 = global_5;
    float3x3 local_10 = global_5.m;
    float3 local_11 = global_5.m[0];
    float3 local_12 = global_5.m[local_0];
    float local_13 = global_5.m[0][0];
    float local_14 = global_5.m[0][local_0];
    float local_15 = global_5.m[local_0][0];
    float local_16 = global_5.m[local_0][local_0];
    global_4 = local_9;
    global_4.m = local_10;
    global_4.m[0] = local_11;
    global_4.m[local_0] = local_12;
    global_4.m[0][0] = local_13;
    global_4.m[0][local_0] = local_14;
    global_4.m[local_0][0] = local_15;
    global_4.m[local_0][local_0] = local_16;
}

void access_sasm() {
    int local_0 = 1;
    local_0 = (local_0 - 1);
    Struct_4 local_1 = global_6;
    /* unknown type */ void local_2 = global_6.a;
    float3x3 local_3 = global_6.a[0].m;
    float3x3 local_4 = global_6.a[local_0].m;
    float3 local_5 = global_6.a[0].m[0];
    float3 local_6 = global_6.a[0].m[local_0];
    float3 local_7 = global_6.a[local_0].m[0];
    float3 local_8 = global_6.a[local_0].m[local_0];
    float local_9 = global_6.a[0].m[0][0];
    float local_10 = global_6.a[0].m[0][local_0];
    float local_11 = global_6.a[0].m[local_0][0];
    float local_12 = global_6.a[0].m[local_0][local_0];
    float local_13 = global_6.a[local_0].m[0][0];
    float local_14 = global_6.a[local_0].m[0][local_0];
    float local_15 = global_6.a[local_0].m[local_0][0];
    float local_16 = global_6.a[local_0].m[local_0][local_0];
    Struct_4 local_17 = global_7;
    /* unknown type */ void local_18 = global_7.a;
    float3x3 local_19 = global_7.a[0].m;
    float3x3 local_20 = global_7.a[local_0].m;
    float3 local_21 = global_7.a[0].m[0];
    float3 local_22 = global_7.a[0].m[local_0];
    float3 local_23 = global_7.a[local_0].m[0];
    float3 local_24 = global_7.a[local_0].m[local_0];
    float local_25 = global_7.a[0].m[0][0];
    float local_26 = global_7.a[0].m[0][local_0];
    float local_27 = global_7.a[0].m[local_0][0];
    float local_28 = global_7.a[0].m[local_0][local_0];
    float local_29 = global_7.a[local_0].m[0][0];
    float local_30 = global_7.a[local_0].m[0][local_0];
    float local_31 = global_7.a[local_0].m[local_0][0];
    float local_32 = global_7.a[local_0].m[local_0][local_0];
    global_6 = local_17;
    global_6.a = local_18;
    global_6.a[0].m = local_19;
    global_6.a[local_0].m = local_20;
    global_6.a[0].m[0] = local_21;
    global_6.a[0].m[local_0] = local_22;
    global_6.a[local_0].m[0] = local_23;
    global_6.a[local_0].m[local_0] = local_24;
    global_6.a[0].m[0][0] = local_25;
    global_6.a[0].m[0][local_0] = local_26;
    global_6.a[0].m[local_0][0] = local_27;
    global_6.a[0].m[local_0][local_0] = local_28;
    global_6.a[local_0].m[0][0] = local_29;
    global_6.a[local_0].m[0][local_0] = local_30;
    global_6.a[local_0].m[local_0][0] = local_31;
    global_6.a[local_0].m[local_0][local_0] = local_32;
}

[[kernel]]
void main(float3x3 global_0 [[buffer(0)]], float3x3 global_1 [[buffer(1)]], /* unknown type */ void global_2 [[buffer(0)]], /* unknown type */ void global_3 [[buffer(1)]], Struct_2 global_4 [[buffer(0)]], Struct_2 global_5 [[buffer(1)]], Struct_4 global_6 [[buffer(0)]], Struct_4 global_7 [[buffer(1)]]) {
}
