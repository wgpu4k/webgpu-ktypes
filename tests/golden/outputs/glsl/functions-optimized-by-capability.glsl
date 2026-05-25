#version 450 core
precision highp float;
precision highp int;


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
    uint c = test_packed_integer_dot_product();
}

void main() {
    wgsl_main();
}
