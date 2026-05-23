#include <metal_stdlib>
using namespace metal;

float2 test_fma() {
    float2 local_0 = float2(2.0f, 2.0f);
    float2 local_1 = float2(0.5f, 0.5f);
    float2 local_2 = float2(0.5f, 0.5f);
    return fma(local_0, local_1, local_2);
}

void fma() {
}

[[fragment]]
void main() {
}
