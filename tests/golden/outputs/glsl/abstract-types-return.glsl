#version 450 core
precision highp float;
precision highp int;

 int global_0 = 1;

int return_i32_ai() {
    return 1;
}

uint return_u32_ai() {
    return 1;
}

float return_f32_ai() {
    return 1;
}

float return_f32_af() {
    return 1.0f;
}

vec2 return_vec2f32_ai() {
    return vec2(1);
}

float[4] return_arrf32_ai() {
    return float[4](1, 1, 1, 1);
}

vec2 return_vec2f32_const_ai() {
    vec2 vec_one = vec2(1);
    return vec_one;
}

float return_const_f32_const_ai() {
    return global_0;
}

void wgsl_main() {
}

void main() {
    wgsl_main();
}
