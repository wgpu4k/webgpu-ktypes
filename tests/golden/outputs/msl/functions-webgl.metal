#include <metal_stdlib>
using namespace metal;

float2 test_fma() {
    float2 local_0 = float2(2.0f, 2.0f);
    float2 local_1 = float2(0.5f, 0.5f);
    float2 local_2 = float2(0.5f, 0.5f);
    return fma(local_0, local_1, local_2);
}

float2 fma(float2 arg_0, float2 arg_1, float2 arg_2) {
}

[[fragment]]
void main() {
    float2 local_0 = test_fma();
}
