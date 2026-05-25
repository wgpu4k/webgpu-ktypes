#version 450 core
precision highp float;
precision highp int;

 int global_0 = 0;
 int global_1 = 0;
 vec2 global_2 = vec2(0.0f);
 vec2 global_3 = vec2(0);
 mat2x2 global_4 = mat2x2(vec2(0.0f), vec2(0.0f));
 float[2] global_5 = float[2](0.0f, 0.0f);
 int[2] global_6 = int[2](0, 0);

void func_f(float a) {
}

void func_i(int a) {
}

void func_u(uint a) {
}

void func_vf(vec2 a) {
}

void func_vi(ivec2 a) {
}

void func_vu(uvec2 a) {
}

void func_mf(mat2x2 a) {
}

void func_af(float[2] a) {
}

void func_ai(int[2] a) {
}

void func_au(uint[2] a) {
}

void func_f_i(float a, int b) {
}

void wgsl_main() {
    func_f(0.0f);
    func_f(0);
    func_i(0);
    func_u(0);
    func_f(global_0);
    func_f(global_1);
    func_i(global_1);
    func_u(global_1);
    func_vf(vec2(0.0f));
    func_vf(vec2(0));
    func_vi(vec2(0));
    func_vu(vec2(0));
    func_vf(global_2);
    func_vf(global_3);
    func_vi(global_3);
    func_vu(global_3);
    func_mf(mat2x2(vec2(0.0f), vec2(0.0f)));
    func_mf(mat2x2(vec2(0), vec2(0)));
    func_mf(global_4);
    func_af(float[2](0.0f, 0.0f));
    func_af(float[2](0, 0));
    func_ai(int[2](0, 0));
    func_au(uint[2](0, 0));
    func_af(global_5);
    func_af(global_6);
    func_ai(global_6);
    func_au(global_6);
    func_f_i(0.0f, 0);
    func_f_i(0, 0);
    func_f_i(global_0, global_1);
    func_f_i(global_1, global_1);
}

void main() {
    wgsl_main();
}
