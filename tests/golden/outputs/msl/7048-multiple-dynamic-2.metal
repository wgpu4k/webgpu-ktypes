#include <metal_stdlib>
using namespace metal;

[[fragment]]
float4 fs_main() {
    array<float2, 2> local_0 = array<float2, 2>(float2(0.0f, 0.0f), float2(0.0f, 0.0f));
    int local_1 = 0;
    float2 local_2 = local_0[local_1];
    float2 local_3 = local_0[local_1];
    return (local_2 * local_3).xxyy;
}
