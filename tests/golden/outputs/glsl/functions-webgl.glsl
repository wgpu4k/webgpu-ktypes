#version 450 core
precision highp float;
precision highp int;


vec2 test_fma() {
    vec2 a = vec2(2.0f, 2.0f);
    vec2 b = vec2(0.5f, 0.5f);
    vec2 c = vec2(0.5f, 0.5f);
    return fma(a, b, c);
}

vec2 fma(vec2 arg_0, vec2 arg_1, vec2 arg_2) {
}

void wgsl_main() {
    vec2 a = test_fma();
}

void main() {
    wgsl_main();
}
