#include <metal_stdlib>
using namespace metal;
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

float2 return_vec2f32_ai() {
    return float2(1);
}

array<float, 4> return_arrf32_ai() {
    return array<float, 4>(1, 1, 1, 1);
}

float2 return_vec2f32_const_ai() {
    float2 local_0 = float2(1);
    return local_0;
}

float return_const_f32_const_ai() {
    return global_0;
}

[[kernel]]
void main() {
}
