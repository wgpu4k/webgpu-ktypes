#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main() {
    int2 local_0 = int2(0);
    int3 local_1 = int3(0);
    int4 local_2 = int4(0);
    uint2 local_3 = uint2(0u);
    uint3 local_4 = uint3(0u);
    uint4 local_5 = uint4(0u);
    float2 local_6 = float2(0.0f);
    float3 local_7 = float3(0.0f);
    float4 local_8 = float4(0.0f);
    local_3 = as_type<uint2>(local_0);
    local_4 = as_type<uint3>(local_1);
    local_5 = as_type<uint4>(local_2);
    local_0 = as_type<int2>(local_3);
    local_1 = as_type<int3>(local_4);
    local_2 = as_type<int4>(local_5);
    local_6 = as_type<float2>(local_0);
    local_7 = as_type<float3>(local_1);
    local_8 = as_type<float4>(local_2);
}
