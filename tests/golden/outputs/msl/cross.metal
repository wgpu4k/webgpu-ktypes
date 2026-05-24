#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main() {
    float3 local_0 = cross(float3(1.0f, 0.0f, 0.0f), float3(0.0f, 1.0f, 0.0f));
}

float3 cross(float3 arg_0, float3 arg_1) {
}
