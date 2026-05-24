#include <metal_stdlib>
using namespace metal;

[[kernel]]
void f() {
    array<float3, 2> local_0 = array<float3, 2>();
    float4 local_1 = float4(0);
    int local_2 = 0;
    int local_3 = 0;
    local_1[0] = (local_0[local_3][1] * local_0[local_2][2]);
}
