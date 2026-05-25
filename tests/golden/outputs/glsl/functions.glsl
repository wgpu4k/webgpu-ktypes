#version 450 core
precision highp float;
precision highp int;


vec2 test_fma() {
    vec2 a = vec2(2.0f, 2.0f);
    vec2 b = vec2(0.5f, 0.5f);
    vec2 c = vec2(0.5f, 0.5f);
    return fma(a, b, c);
}

int test_integer_dot_product() {
    ivec2 a_2 = ivec2(1);
    ivec2 b_2 = ivec2(1);
    int c_2 = dot(a_2, b_2);
    uvec3 a_3 = uvec3(1u);
    uvec3 b_3 = uvec3(1u);
    uint c_3 = dot(a_3, b_3);
    int c_4 = dot(ivec4(4), ivec4(2));
    return c_4;
}

ivec2 dot(ivec2 arg_0, ivec2 arg_1) {
}

uvec3 dot(uvec3 arg_0, uvec3 arg_1) {
}

ivec4 dot(ivec4 arg_0, ivec4 arg_1) {
}

uint test_packed_integer_dot_product() {
    uint a_5 = 1u;
    uint b_5 = 2u;
    int c_5 = dot4I8Packed(a_5, b_5);
    uint a_6 = 3u;
    uint b_6 = 4u;
    uint c_6 = dot4U8Packed(a_6, b_6);
    int c_7 = dot4I8Packed((5u + c_6), (6u + c_6));
    uint c_8 = dot4U8Packed((7u + c_6), (8u + c_6));
    return c_8;
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

uint dot4U8Packed(uint arg_0, uint arg_1) {
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

uint dot4U8Packed(uint arg_0, uint arg_1) {
}

void wgsl_main() {
    vec2 a = test_fma();
    int b = test_integer_dot_product();
    uint c = test_packed_integer_dot_product();
}

void main() {
    wgsl_main();
}
