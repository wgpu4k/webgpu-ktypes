#include <metal_stdlib>
using namespace metal;

void all_constant_arguments() {
    int2 local_0 = float2(42, 43);
    uint2 local_1 = float2(44, 45);
    float2 local_2 = float2(46, 47);
    float2 local_3 = float2(48.0f, 49.0f);
    float2 local_4 = float2(48, 49.0f);
    uint2 local_5 = float2(42u, 43);
    uint2 local_6 = float2(42, 43u);
    uint2 local_7 = uint2(42u, 43);
    uint2 local_8 = uint2(42, 43u);
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
    array<float, 2> local_28 = array<float, 2>(1.0f, 2.0f);
    array<float, 2> local_29 = array<float, 2>(1.0f, 2.0f);
    array<float, 2> local_30 = array<float, 2>(1.0f, 2.0f);
    array<float, 2> local_31 = array<float, 2>(1, 2);
    array<int, 2> local_32 = array<int, 2>(1, 2);
    array<int, 2> local_33 = array<int, 2>(1, 2);
    array<int, 2> local_34 = array<int, 2>(1, 2);
    array<float, 2> local_35 = array<float, 2>(1, 2);
    array<float, 2> local_36 = array<float, 2>(1, 2.0f);
    array<float, 2> local_37 = array<float, 2>(1.0f, 2);
    array<float, 2> local_38 = array<float, 2>(1.0f, 2.0f);
    array<int3, 1> local_39 = array<int3, 1>(float3(1));
    array<float3, 1> local_40 = array<float3, 1>(float3(1));
    array<float3, 1> local_41 = array<float3, 1>(float3(1.0f));
    int2 local_42 = float2(1);
    uint2 local_43 = float2(1);
    float2 local_44 = float2(1);
    float2 local_45 = float2(1.0f);
    array<int, 2> local_46 = array<int, 2>(1, 2);
    array<int, 2> local_47 = array<int, 2>(1, 2.0f);
    array<float, 2> local_48 = array<float, 2>(1.0f, 2);
    array<float, 2> local_49 = array<float, 2>(1.0f, 2.0f);
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
    array<float, 2> local_13 = array<float, 2>(local_2, 2.0f);
    array<float, 2> local_14 = array<float, 2>(1.0f, local_2);
    array<float, 2> local_15 = array<float, 2>(local_2, 2);
    array<float, 2> local_16 = array<float, 2>(1, local_2);
    array<int, 2> local_17 = array<int, 2>(local_1, 2);
    array<int, 2> local_18 = array<int, 2>(1, local_1);
    array<float, 2> local_19 = array<float, 2>(local_2, 2.0f);
    array<float, 2> local_20 = array<float, 2>(1.0f, local_2);
    array<float, 2> local_21 = array<float, 2>(local_2, 2);
    array<float, 2> local_22 = array<float, 2>(1, local_2);
    array<int, 2> local_23 = array<int, 2>(local_1, 2);
    array<int, 2> local_24 = array<int, 2>(1, local_1);
    int2 local_25 = float2(local_1);
    uint2 local_26 = float2(local_0);
    float2 local_27 = float2(local_2);
}

[[kernel]]
void main() {
    all_constant_arguments();
    mixed_constant_and_runtime_arguments();
}
