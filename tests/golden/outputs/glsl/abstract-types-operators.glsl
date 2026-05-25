#version 450 core
precision highp float;
precision highp int;

 float global_0 = (1.0f + 2.0f);
 float global_1 = (1.0f + 2);
 float global_2 = (1.0f + 2.0f);
 float global_3 = (1 + 2.0f);
 float global_4 = (1 + 2);
 float global_5 = (1 + 2.0f);
 float global_6 = (1.0f + 2.0f);
 float global_7 = (1.0f + 2);
 float global_8 = (1.0f + 2.0f);
 int global_9 = (1 + 2);
 int global_10 = (1 + 2);
 int global_11 = (1 + 2);
 int global_12 = (1 + 2);
 uint global_13 = (1 + 2);
 uint global_14 = (1 + 2u);
 uint global_15 = (1u + 2);
 uint global_16 = (1u + 2u);
 uint global_17 = ~(0);
 uint global_18 = (~(0) & (0 - 1));
 int global_19 = -(-2147483648);
 float global_20 = -(3.4028235E38f);
 int global_21 = (1 << 2);
 int global_22 = (1 << 2u);
 uint global_23 = (1 << 2);
 uint global_24 = (1 << 2u);
 int global_25 = (1 << 2);
 int global_26 = (1 << 2u);
 int global_27 = (1 >> 2);
 int global_28 = (1 >> 2u);
 uint global_29 = (1 >> 2);
 uint global_30 = (1 >> 2u);
 int global_31 = (1 >> 2);
 int global_32 = (1 >> 2u);
 int global_33 = int(-(-2147483648));
 int global_34 = -(-2147483648);
shared uint[64] global_35;

void runtime_values() {
    float f = 42;
    int i = 43;
    uint u = 44;
    float plus_fafaf = (1.0f + 2.0f);
    float plus_fafai = (1.0f + 2);
    float plus_faf_f = (1.0f + f);
    float plus_faiaf = (1 + 2.0f);
    float plus_faiai = (1 + 2);
    float plus_fai_f = (1 + f);
    float plus_f_faf = (f + 2.0f);
    float plus_f_fai = (f + 2);
    float plus_f_f_f = (f + f);
    int plus_iaiai = (1 + 2);
    int plus_iai_i = (1 + i);
    int plus_i_iai = (i + 2);
    int plus_i_i_i = (i + i);
    uint plus_uaiai = (1 + 2);
    uint plus_uai_u = (1 + u);
    uint plus_u_uai = (u + 2);
    uint plus_u_u_u = (u + u);
    int shl_iai_u = (1 << u);
    int shr_iai_u = (1 << u);
}

void wgpu_4445() {
    float a = (((3.0f * 2.0f) - 1.0f) * 1.0f);
    float b = (((3.0f * 2.0f) + 1.0f) * 1.0f);
    float c = (((3.0f * 2.0f) - 1.0f) * 1.0f);
}

void wgpu_4435() {
    int x = 1;
    uint y = global_35[(x - 1)];
}

void wgsl_main() {
    runtime_values();
    wgpu_4445();
    wgpu_4435();
}

void main() {
    wgsl_main();
}
