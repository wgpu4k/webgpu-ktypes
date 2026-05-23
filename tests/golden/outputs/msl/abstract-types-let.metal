#include <metal_stdlib>
using namespace metal;

void all_constant_arguments() {
    int2 local_0 = float2(42, 43);
    uint2 local_1 = float2(44, 45);
    float2 local_2 = float2(46, 47);
    float2 local_3 = float2(48.0f, 49.0f);
    float2 local_4 = float2(48, 49.0f);
    uint2 local_5 = float2(0u, 43);
    uint2 local_6 = float2(42, 0u);
    uint2 local_7 = uint2(0u, 43);
    uint2 local_8 = uint2(42, 0u);
    int2 local_9 = float2();
    uint2 local_10 = float2();
    float2 local_11 = float2();
    float2x2 local_12 = float2x2(float2(), float2());
    float2x2 local_13 = float2x2(1, 2, 3, 4);
    float2x2 local_14 = float2x2(1.0f, 2, 3, 4);
    float2x2 local_15 = float2x2(1, 2.0f, 3, 4);
    float2x2 local_16 = float2x2(1, 2, 3.0f, 4);
    float2x2 local_17 = float2x2(1, 2, 3, 4.0f);
    float2x2 local_18 = float2x2(1.0f, 2, 3, 4);
    float2x2 local_19 = float2x2(1, 2.0f, 3, 4);
    float2x2 local_20 = float2x2(1, 2, 3.0f, 4);
    float2x2 local_21 = float2x2(1, 2, 3, 4.0f);
    int2 local_22 = float2(1);
    float2 local_23 = float2(1.0f);
    int2 local_24 = int2(1);
    uint2 local_25 = uint2(1);
    float2 local_26 = float2(1);
    float2 local_27 = float2(1.0f);
    /* unknown type */ void local_28 = /* unknown type */ void(1.0f, 2.0f);
    /* unknown type */ void local_29 = /* unknown type */ void(1.0f, 2.0f);
    /* unknown type */ void local_30 = /* unknown type */ void(1.0f, 2.0f);
    /* unknown type */ void local_31 = /* unknown type */ void(1, 2);
    /* unknown type */ void local_32 = /* unknown type */ void(0, 2);
    /* unknown type */ void local_33 = /* unknown type */ void(1, 0);
    /* unknown type */ void local_34 = float(1, 2);
    /* unknown type */ void local_35 = float(1, 2);
    /* unknown type */ void local_36 = float(1, 2.0f);
    /* unknown type */ void local_37 = float(1.0f, 2);
    /* unknown type */ void local_38 = float(1.0f, 2.0f);
    /* unknown type */ void local_39 = float(float3(1));
    /* unknown type */ void local_40 = float(float3(1));
    /* unknown type */ void local_41 = float(float3(1.0f));
    int2 local_42 = float2(1);
    uint2 local_43 = float2(1);
    float2 local_44 = float2(1);
    float2 local_45 = float2(1.0f);
    float local_46 = float(1, 2);
    float local_47 = float(1, 2.0f);
    float local_48 = float(1.0f, 2);
    float local_49 = float(1.0f, 2.0f);
}

void mixed_constant_and_runtime_arguments() {
    uint local_0;
    int local_1;
    float local_2;
    uint2 local_3 = float2(local_0, 43);
    uint2 local_4 = float2(42, local_0);
    float2 local_5 = float2(local_2, 47);
    float2 local_6 = float2(local_2, 49.0f);
    uint2 local_7 = uint2(local_0, 43);
    uint2 local_8 = uint2(42, local_0);
    float2x2 local_9 = float2x2(local_2, 2, 3, 4);
    float2x2 local_10 = float2x2(1, local_2, 3, 4);
    float2x2 local_11 = float2x2(1, 2, local_2, 4);
    float2x2 local_12 = float2x2(1, 2, 3, local_2);
    /* unknown type */ void local_13 = /* unknown type */ void(local_2, 2.0f);
    /* unknown type */ void local_14 = /* unknown type */ void(1.0f, local_2);
    /* unknown type */ void local_15 = /* unknown type */ void(local_2, 2);
    /* unknown type */ void local_16 = /* unknown type */ void(1, local_2);
    /* unknown type */ void local_17 = /* unknown type */ void(local_1, 2);
    /* unknown type */ void local_18 = /* unknown type */ void(1, local_1);
    /* unknown type */ void local_19 = float(local_2, 2.0f);
    /* unknown type */ void local_20 = float(1.0f, local_2);
    /* unknown type */ void local_21 = float(local_2, 2);
    /* unknown type */ void local_22 = float(1, local_2);
    /* unknown type */ void local_23 = float(local_1, 2);
    /* unknown type */ void local_24 = float(1, local_1);
    int2 local_25 = float2(local_1);
    uint2 local_26 = float2(local_0);
    float2 local_27 = float2(local_2);
}

[[kernel]]
void main() {
}
