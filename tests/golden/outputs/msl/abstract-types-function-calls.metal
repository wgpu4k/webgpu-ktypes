#include <metal_stdlib>
using namespace metal;
int global_0 = 0;
int global_1 = 0;
float2 global_2 = float2(0.0f);
float2 global_3 = float2(0);
float2x2 global_4 = float2x2(float2(0.0f), float2(0.0f));
array<float, 2> global_5 = array<float, 2>(0.0f, 0.0f);
array<int, 2> global_6 = array<int, 2>(0, 0);

void func_f(float a) {
}

void func_i(int a) {
}

void func_u(uint a) {
}

void func_vf(float2 a) {
}

void func_vi(int2 a) {
}

void func_vu(uint2 a) {
}

void func_mf(float2x2 a) {
}

void func_af(array<float, 2> a) {
}

void func_ai(array<int, 2> a) {
}

void func_au(array<uint, 2> a) {
}

void func_f_i(float a, int b) {
}

[[kernel]]
void main() {
    func_f(0.0f);
    func_f(0);
    func_i(0);
    func_u(0);
    func_f(global_0);
    func_f(global_1);
    func_i(global_1);
    func_u(global_1);
    func_vf(float2(0.0f));
    func_vf(float2(0));
    func_vi(float2(0));
    func_vu(float2(0));
    func_vf(global_2);
    func_vf(global_3);
    func_vi(global_3);
    func_vu(global_3);
    func_mf(float2x2(float2(0.0f), float2(0.0f)));
    func_mf(float2x2(float2(0), float2(0)));
    func_mf(global_4);
    func_af(array<float, 2>(0.0f, 0.0f));
    func_af(array<float, 2>(0, 0));
    func_ai(array<int, 2>(0, 0));
    func_au(array<uint, 2>(0, 0));
    func_af(global_5);
    func_af(global_6);
    func_ai(global_6);
    func_au(global_6);
    func_f_i(0.0f, 0);
    func_f_i(0, 0);
    func_f_i(global_0, global_1);
    func_f_i(global_1, global_1);
}
