#version 450 core
#extension GL_EXT_shader_explicit_arithmetic_types_float16 : require
precision highp float;
precision highp int;

struct Struct_2 {
    mat4x2 m;
};
struct Struct_4 {
    Struct_2[4] a;
};
layout(set = 0, binding = 0) buffer mat4x2 global_0;
layout(set = 0, binding = 1) uniform mat4x2 global_1;
layout(set = 2, binding = 0) buffer mat4x2[4] global_2;
layout(set = 2, binding = 1) uniform mat4x2[4] global_3;
layout(set = 1, binding = 0) buffer Struct_2 global_4;
layout(set = 1, binding = 1) uniform Struct_2 global_5;
layout(set = 3, binding = 0) buffer Struct_4 global_6;
layout(set = 3, binding = 1) uniform Struct_4 global_7;

void access_m() {
    int idx = 1;
    idx = (idx - 1);
    mat4x2 l_s_m = global_0;
    vec2 l_s_c_c = global_0[0];
    vec2 l_s_c_v = global_0[idx];
    float16_t l_s_e_cc = global_0[0][0];
    float16_t l_s_e_cv = global_0[0][idx];
    float16_t l_s_e_vc = global_0[idx][0];
    float16_t l_s_e_vv = global_0[idx][idx];
    mat4x2 l_u_m = global_1;
    vec2 l_u_c_c = global_1[0];
    vec2 l_u_c_v = global_1[idx];
    float16_t l_u_e_cc = global_1[0][0];
    float16_t l_u_e_cv = global_1[0][idx];
    float16_t l_u_e_vc = global_1[idx][0];
    float16_t l_u_e_vv = global_1[idx][idx];
    global_0 = l_u_m;
    global_0[0] = l_u_c_c;
    global_0[idx] = l_u_c_v;
    global_0[0][0] = l_u_e_cc;
    global_0[0][idx] = l_u_e_cv;
    global_0[idx][0] = l_u_e_vc;
    global_0[idx][idx] = l_u_e_vv;
}

void access_am() {
    int idx = 1;
    idx = (idx - 1);
    mat4x2[4] l_s_a = global_2;
    mat4x2 l_s_m_c = global_2[0];
    mat4x2 l_s_m_v = global_2[idx];
    vec2 l_s_c_cc = global_2[0][0];
    vec2 l_s_c_cv = global_2[0][idx];
    vec2 l_s_c_vc = global_2[idx][0];
    vec2 l_s_c_vv = global_2[idx][idx];
    float16_t l_s_e_ccc = global_2[0][0][0];
    float16_t l_s_e_ccv = global_2[0][0][idx];
    float16_t l_s_e_cvc = global_2[0][idx][0];
    float16_t l_s_e_cvv = global_2[0][idx][idx];
    float16_t l_s_e_vcc = global_2[idx][0][0];
    float16_t l_s_e_vcv = global_2[idx][0][idx];
    float16_t l_s_e_vvc = global_2[idx][idx][0];
    float16_t l_s_e_vvv = global_2[idx][idx][idx];
    mat4x2[4] l_u_a = global_3;
    mat4x2 l_u_m_c = global_3[0];
    mat4x2 l_u_m_v = global_3[idx];
    vec2 l_u_c_cc = global_3[0][0];
    vec2 l_u_c_cv = global_3[0][idx];
    vec2 l_u_c_vc = global_3[idx][0];
    vec2 l_u_c_vv = global_3[idx][idx];
    float16_t l_u_e_ccc = global_3[0][0][0];
    float16_t l_u_e_ccv = global_3[0][0][idx];
    float16_t l_u_e_cvc = global_3[0][idx][0];
    float16_t l_u_e_cvv = global_3[0][idx][idx];
    float16_t l_u_e_vcc = global_3[idx][0][0];
    float16_t l_u_e_vcv = global_3[idx][0][idx];
    float16_t l_u_e_vvc = global_3[idx][idx][0];
    float16_t l_u_e_vvv = global_3[idx][idx][idx];
    global_2 = l_u_a;
    global_2[0] = l_u_m_c;
    global_2[idx] = l_u_m_v;
    global_2[0][0] = l_u_c_cc;
    global_2[0][idx] = l_u_c_cv;
    global_2[idx][0] = l_u_c_vc;
    global_2[idx][idx] = l_u_c_vv;
    global_2[0][0][0] = l_u_e_ccc;
    global_2[0][0][idx] = l_u_e_ccv;
    global_2[0][idx][0] = l_u_e_cvc;
    global_2[0][idx][idx] = l_u_e_cvv;
    global_2[idx][0][0] = l_u_e_vcc;
    global_2[idx][0][idx] = l_u_e_vcv;
    global_2[idx][idx][0] = l_u_e_vvc;
    global_2[idx][idx][idx] = l_u_e_vvv;
}

void access_sm() {
    int idx = 1;
    idx = (idx - 1);
    Struct_2 l_s_s = global_4;
    mat4x2 l_s_m = global_4.m;
    vec2 l_s_c_c = global_4.m[0];
    vec2 l_s_c_v = global_4.m[idx];
    float16_t l_s_e_cc = global_4.m[0][0];
    float16_t l_s_e_cv = global_4.m[0][idx];
    float16_t l_s_e_vc = global_4.m[idx][0];
    float16_t l_s_e_vv = global_4.m[idx][idx];
    Struct_2 l_u_s = global_5;
    mat4x2 l_u_m = global_5.m;
    vec2 l_u_c_c = global_5.m[0];
    vec2 l_u_c_v = global_5.m[idx];
    float16_t l_u_e_cc = global_5.m[0][0];
    float16_t l_u_e_cv = global_5.m[0][idx];
    float16_t l_u_e_vc = global_5.m[idx][0];
    float16_t l_u_e_vv = global_5.m[idx][idx];
    global_4 = l_u_s;
    global_4.m = l_u_m;
    global_4.m[0] = l_u_c_c;
    global_4.m[idx] = l_u_c_v;
    global_4.m[0][0] = l_u_e_cc;
    global_4.m[0][idx] = l_u_e_cv;
    global_4.m[idx][0] = l_u_e_vc;
    global_4.m[idx][idx] = l_u_e_vv;
}

void access_sasm() {
    int idx = 1;
    idx = (idx - 1);
    Struct_4 l_s_s = global_6;
    Struct_2[4] l_s_a = global_6.a;
    mat4x2 l_s_m_c = global_6.a[0].m;
    mat4x2 l_s_m_v = global_6.a[idx].m;
    vec2 l_s_c_cc = global_6.a[0].m[0];
    vec2 l_s_c_cv = global_6.a[0].m[idx];
    vec2 l_s_c_vc = global_6.a[idx].m[0];
    vec2 l_s_c_vv = global_6.a[idx].m[idx];
    float16_t l_s_e_ccc = global_6.a[0].m[0][0];
    float16_t l_s_e_ccv = global_6.a[0].m[0][idx];
    float16_t l_s_e_cvc = global_6.a[0].m[idx][0];
    float16_t l_s_e_cvv = global_6.a[0].m[idx][idx];
    float16_t l_s_e_vcc = global_6.a[idx].m[0][0];
    float16_t l_s_e_vcv = global_6.a[idx].m[0][idx];
    float16_t l_s_e_vvc = global_6.a[idx].m[idx][0];
    float16_t l_s_e_vvv = global_6.a[idx].m[idx][idx];
    Struct_4 l_u_s = global_7;
    Struct_2[4] l_u_a = global_7.a;
    mat4x2 l_u_m_c = global_7.a[0].m;
    mat4x2 l_u_m_v = global_7.a[idx].m;
    vec2 l_u_c_cc = global_7.a[0].m[0];
    vec2 l_u_c_cv = global_7.a[0].m[idx];
    vec2 l_u_c_vc = global_7.a[idx].m[0];
    vec2 l_u_c_vv = global_7.a[idx].m[idx];
    float16_t l_u_e_ccc = global_7.a[0].m[0][0];
    float16_t l_u_e_ccv = global_7.a[0].m[0][idx];
    float16_t l_u_e_cvc = global_7.a[0].m[idx][0];
    float16_t l_u_e_cvv = global_7.a[0].m[idx][idx];
    float16_t l_u_e_vcc = global_7.a[idx].m[0][0];
    float16_t l_u_e_vcv = global_7.a[idx].m[0][idx];
    float16_t l_u_e_vvc = global_7.a[idx].m[idx][0];
    float16_t l_u_e_vvv = global_7.a[idx].m[idx][idx];
    global_6 = l_u_s;
    global_6.a = l_u_a;
    global_6.a[0].m = l_u_m_c;
    global_6.a[idx].m = l_u_m_v;
    global_6.a[0].m[0] = l_u_c_cc;
    global_6.a[0].m[idx] = l_u_c_cv;
    global_6.a[idx].m[0] = l_u_c_vc;
    global_6.a[idx].m[idx] = l_u_c_vv;
    global_6.a[0].m[0][0] = l_u_e_ccc;
    global_6.a[0].m[0][idx] = l_u_e_ccv;
    global_6.a[0].m[idx][0] = l_u_e_cvc;
    global_6.a[0].m[idx][idx] = l_u_e_cvv;
    global_6.a[idx].m[0][0] = l_u_e_vcc;
    global_6.a[idx].m[0][idx] = l_u_e_vcv;
    global_6.a[idx].m[idx][0] = l_u_e_vvc;
    global_6.a[idx].m[idx][idx] = l_u_e_vvv;
}

void wgsl_main() {
    access_m();
    access_sm();
    access_am();
    access_sasm();
}

void main() {
    wgsl_main();
}
