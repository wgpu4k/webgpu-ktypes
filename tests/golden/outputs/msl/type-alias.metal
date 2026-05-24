#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main() {
    float3 local_0 = float3(0.0f, 0.0f, 0.0f);
    float3 local_1 = float3(0.0f);
    float3 local_2 = float3(float2(0.0f), 0.0f);
    float3 local_3 = float3(float2(0.0f), 0.0f);
    int3 local_4 = int3(local_3);
    float2x2 local_5 = float2x2(1.0f, 2.0f, 3.0f, 4.0f);
    float3x3 local_6 = float3x3(local_0, local_0, local_0);
    int2 local_7 = int2();
    float2x2 local_8 = float2x2();
}
