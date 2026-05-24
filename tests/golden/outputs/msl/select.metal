#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main() {
    select(1, 2.0f, false);
    float2 local_0 = float2(1, 2);
    float2 local_1 = select(float2(1.0f, 0.0f), float2(0.0f, 1.0f), (local_0[0] < local_0[1]));
}

int select(int arg_0, float arg_1, bool arg_2) {
}

float2 select(float2 arg_0, float2 arg_1, float arg_2) {
}
