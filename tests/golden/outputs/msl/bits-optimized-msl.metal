#include <metal_stdlib>
using namespace metal;

[[kernel]]
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
    local_4 = pack4x8snorm(local_9);
    local_4 = pack4x8unorm(local_9);
    local_4 = pack2x16snorm(local_8);
    local_4 = pack2x16unorm(local_8);
    local_4 = pack2x16float(local_8);
    local_4 = pack4xI8(local_3);
    local_4 = pack4xU8(local_7);
    local_4 = pack4xI8Clamp(local_3);
    local_4 = pack4xU8Clamp(local_7);
    local_9 = unpack4x8snorm(local_4);
    local_9 = unpack4x8unorm(local_4);
    local_8 = unpack2x16snorm(local_4);
    local_8 = unpack2x16unorm(local_4);
    local_8 = unpack2x16float(local_4);
    local_3 = unpack4xI8(local_4);
    local_7 = unpack4xU8(local_4);
    local_0 = insertBits(local_0, local_0, 5u, 10u);
    local_1 = insertBits(local_1, local_1, 5u, 10u);
    local_2 = insertBits(local_2, local_2, 5u, 10u);
    local_3 = insertBits(local_3, local_3, 5u, 10u);
    local_4 = insertBits(local_4, local_4, 5u, 10u);
    local_5 = insertBits(local_5, local_5, 5u, 10u);
    local_6 = insertBits(local_6, local_6, 5u, 10u);
    local_7 = insertBits(local_7, local_7, 5u, 10u);
    local_0 = extractBits(local_0, 5u, 10u);
    local_1 = extractBits(local_1, 5u, 10u);
    local_2 = extractBits(local_2, 5u, 10u);
    local_3 = extractBits(local_3, 5u, 10u);
    local_4 = extractBits(local_4, 5u, 10u);
    local_5 = extractBits(local_5, 5u, 10u);
    local_6 = extractBits(local_6, 5u, 10u);
    local_7 = extractBits(local_7, 5u, 10u);
    local_0 = firstTrailingBit(local_0);
    local_5 = firstTrailingBit(local_5);
    local_2 = firstLeadingBit(local_2);
    local_6 = firstLeadingBit(local_6);
    local_0 = firstLeadingBit(local_0);
    local_4 = firstLeadingBit(local_4);
    local_0 = countOneBits(local_0);
    local_1 = countOneBits(local_1);
    local_2 = countOneBits(local_2);
    local_3 = countOneBits(local_3);
    local_4 = countOneBits(local_4);
    local_5 = countOneBits(local_5);
    local_6 = countOneBits(local_6);
    local_7 = countOneBits(local_7);
    local_0 = reverseBits(local_0);
    local_1 = reverseBits(local_1);
    local_2 = reverseBits(local_2);
    local_3 = reverseBits(local_3);
    local_4 = reverseBits(local_4);
    local_5 = reverseBits(local_5);
    local_6 = reverseBits(local_6);
    local_7 = reverseBits(local_7);
}

float4 pack4x8snorm(float4 arg_0) {
}

float4 pack4x8unorm(float4 arg_0) {
}

float2 pack2x16snorm(float2 arg_0) {
}

float2 pack2x16unorm(float2 arg_0) {
}

float2 pack2x16float(float2 arg_0) {
}

int4 pack4xI8(int4 arg_0) {
}

uint4 pack4xU8(uint4 arg_0) {
}

int4 pack4xI8Clamp(int4 arg_0) {
}

uint4 pack4xU8Clamp(uint4 arg_0) {
}

uint unpack4x8snorm(uint arg_0) {
}

uint unpack4x8unorm(uint arg_0) {
}

uint unpack2x16snorm(uint arg_0) {
}

uint unpack2x16unorm(uint arg_0) {
}

uint unpack2x16float(uint arg_0) {
}

uint unpack4xI8(uint arg_0) {
}

uint unpack4xU8(uint arg_0) {
}

int insertBits(int arg_0, int arg_1, uint arg_2, uint arg_3) {
}

int2 insertBits(int2 arg_0, int2 arg_1, uint arg_2, uint arg_3) {
}

int3 insertBits(int3 arg_0, int3 arg_1, uint arg_2, uint arg_3) {
}

int4 insertBits(int4 arg_0, int4 arg_1, uint arg_2, uint arg_3) {
}

uint insertBits(uint arg_0, uint arg_1, uint arg_2, uint arg_3) {
}

uint2 insertBits(uint2 arg_0, uint2 arg_1, uint arg_2, uint arg_3) {
}

uint3 insertBits(uint3 arg_0, uint3 arg_1, uint arg_2, uint arg_3) {
}

uint4 insertBits(uint4 arg_0, uint4 arg_1, uint arg_2, uint arg_3) {
}

int extractBits(int arg_0, uint arg_1, uint arg_2) {
}

int2 extractBits(int2 arg_0, uint arg_1, uint arg_2) {
}

int3 extractBits(int3 arg_0, uint arg_1, uint arg_2) {
}

int4 extractBits(int4 arg_0, uint arg_1, uint arg_2) {
}

uint extractBits(uint arg_0, uint arg_1, uint arg_2) {
}

uint2 extractBits(uint2 arg_0, uint arg_1, uint arg_2) {
}

uint3 extractBits(uint3 arg_0, uint arg_1, uint arg_2) {
}

uint4 extractBits(uint4 arg_0, uint arg_1, uint arg_2) {
}

int firstTrailingBit(int arg_0) {
}

uint2 firstTrailingBit(uint2 arg_0) {
}

int3 firstLeadingBit(int3 arg_0) {
}

uint3 firstLeadingBit(uint3 arg_0) {
}

int firstLeadingBit(int arg_0) {
}

uint firstLeadingBit(uint arg_0) {
}

int countOneBits(int arg_0) {
}

int2 countOneBits(int2 arg_0) {
}

int3 countOneBits(int3 arg_0) {
}

int4 countOneBits(int4 arg_0) {
}

uint countOneBits(uint arg_0) {
}

uint2 countOneBits(uint2 arg_0) {
}

uint3 countOneBits(uint3 arg_0) {
}

uint4 countOneBits(uint4 arg_0) {
}

int reverseBits(int arg_0) {
}

int2 reverseBits(int2 arg_0) {
}

int3 reverseBits(int3 arg_0) {
}

int4 reverseBits(int4 arg_0) {
}

uint reverseBits(uint arg_0) {
}

uint2 reverseBits(uint2 arg_0) {
}

uint3 reverseBits(uint3 arg_0) {
}

uint4 reverseBits(uint4 arg_0) {
}
