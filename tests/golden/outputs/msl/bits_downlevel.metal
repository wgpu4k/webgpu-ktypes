#include <metal_stdlib>
using namespace metal;

[[fragment]]
void main() {
    int local_0 = 0;
    int2 local_1 = int2(0);
    int3 local_2 = int3(0);
    int4 local_3 = int4(0);
    uint local_4 = 0u;
    uint2 local_5 = uint2(0u);
    uint3 local_6 = uint3(0u);
    uint4 local_7 = uint4(0u);
    float2 local_8 = float2(0.0f);
    float4 local_9 = float4(0.0f);
    local_4 = pack4xI8(local_3);
    local_4 = pack4xU8(local_7);
    local_9 = unpack4x8snorm(local_4);
    local_9 = unpack4x8unorm(local_4);
    local_8 = unpack2x16snorm(local_4);
    local_8 = unpack2x16unorm(local_4);
}

int4 pack4xI8(int4 arg_0) {
}

uint4 pack4xU8(uint4 arg_0) {
}

uint unpack4x8snorm(uint arg_0) {
}

uint unpack4x8unorm(uint arg_0) {
}

uint unpack2x16snorm(uint arg_0) {
}

uint unpack2x16unorm(uint arg_0) {
}
