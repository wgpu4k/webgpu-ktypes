#version 450 core
precision highp float;
precision highp int;


void wgsl_main() {
    int i = 0;
    ivec2 i2 = ivec2(0);
    ivec3 i3 = ivec3(0);
    ivec4 i4 = ivec4(0);
    uint u = 0u;
    uvec2 u2 = uvec2(0u);
    uvec3 u3 = uvec3(0u);
    uvec4 u4 = uvec4(0u);
    vec2 f2 = vec2(0.0f);
    vec4 f4 = vec4(0.0f);
    u = pack4x8snorm(f4);
    u = pack4x8unorm(f4);
    u = pack2x16snorm(f2);
    u = pack2x16unorm(f2);
    u = pack2x16float(f2);
    u = pack4xI8(i4);
    u = pack4xU8(u4);
    u = pack4xI8Clamp(i4);
    u = pack4xU8Clamp(u4);
    f4 = unpack4x8snorm(u);
    f4 = unpack4x8unorm(u);
    f2 = unpack2x16snorm(u);
    f2 = unpack2x16unorm(u);
    f2 = unpack2x16float(u);
    i4 = unpack4xI8(u);
    u4 = unpack4xU8(u);
    i = insertBits(i, i, 5u, 10u);
    i2 = insertBits(i2, i2, 5u, 10u);
    i3 = insertBits(i3, i3, 5u, 10u);
    i4 = insertBits(i4, i4, 5u, 10u);
    u = insertBits(u, u, 5u, 10u);
    u2 = insertBits(u2, u2, 5u, 10u);
    u3 = insertBits(u3, u3, 5u, 10u);
    u4 = insertBits(u4, u4, 5u, 10u);
    i = extractBits(i, 5u, 10u);
    i2 = extractBits(i2, 5u, 10u);
    i3 = extractBits(i3, 5u, 10u);
    i4 = extractBits(i4, 5u, 10u);
    u = extractBits(u, 5u, 10u);
    u2 = extractBits(u2, 5u, 10u);
    u3 = extractBits(u3, 5u, 10u);
    u4 = extractBits(u4, 5u, 10u);
    i = firstTrailingBit(i);
    u2 = firstTrailingBit(u2);
    i3 = firstLeadingBit(i3);
    u3 = firstLeadingBit(u3);
    i = firstLeadingBit(i);
    u = firstLeadingBit(u);
    i = countOneBits(i);
    i2 = countOneBits(i2);
    i3 = countOneBits(i3);
    i4 = countOneBits(i4);
    u = countOneBits(u);
    u2 = countOneBits(u2);
    u3 = countOneBits(u3);
    u4 = countOneBits(u4);
    i = reverseBits(i);
    i2 = reverseBits(i2);
    i3 = reverseBits(i3);
    i4 = reverseBits(i4);
    u = reverseBits(u);
    u2 = reverseBits(u2);
    u3 = reverseBits(u3);
    u4 = reverseBits(u4);
}

vec4 pack4x8snorm(vec4 arg_0) {
}

vec4 pack4x8unorm(vec4 arg_0) {
}

vec2 pack2x16snorm(vec2 arg_0) {
}

vec2 pack2x16unorm(vec2 arg_0) {
}

vec2 pack2x16float(vec2 arg_0) {
}

ivec4 pack4xI8(ivec4 arg_0) {
}

uvec4 pack4xU8(uvec4 arg_0) {
}

ivec4 pack4xI8Clamp(ivec4 arg_0) {
}

uvec4 pack4xU8Clamp(uvec4 arg_0) {
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

ivec2 insertBits(ivec2 arg_0, ivec2 arg_1, uint arg_2, uint arg_3) {
}

ivec3 insertBits(ivec3 arg_0, ivec3 arg_1, uint arg_2, uint arg_3) {
}

ivec4 insertBits(ivec4 arg_0, ivec4 arg_1, uint arg_2, uint arg_3) {
}

uint insertBits(uint arg_0, uint arg_1, uint arg_2, uint arg_3) {
}

uvec2 insertBits(uvec2 arg_0, uvec2 arg_1, uint arg_2, uint arg_3) {
}

uvec3 insertBits(uvec3 arg_0, uvec3 arg_1, uint arg_2, uint arg_3) {
}

uvec4 insertBits(uvec4 arg_0, uvec4 arg_1, uint arg_2, uint arg_3) {
}

int extractBits(int arg_0, uint arg_1, uint arg_2) {
}

ivec2 extractBits(ivec2 arg_0, uint arg_1, uint arg_2) {
}

ivec3 extractBits(ivec3 arg_0, uint arg_1, uint arg_2) {
}

ivec4 extractBits(ivec4 arg_0, uint arg_1, uint arg_2) {
}

uint extractBits(uint arg_0, uint arg_1, uint arg_2) {
}

uvec2 extractBits(uvec2 arg_0, uint arg_1, uint arg_2) {
}

uvec3 extractBits(uvec3 arg_0, uint arg_1, uint arg_2) {
}

uvec4 extractBits(uvec4 arg_0, uint arg_1, uint arg_2) {
}

int firstTrailingBit(int arg_0) {
}

uvec2 firstTrailingBit(uvec2 arg_0) {
}

ivec3 firstLeadingBit(ivec3 arg_0) {
}

uvec3 firstLeadingBit(uvec3 arg_0) {
}

int firstLeadingBit(int arg_0) {
}

uint firstLeadingBit(uint arg_0) {
}

int countOneBits(int arg_0) {
}

ivec2 countOneBits(ivec2 arg_0) {
}

ivec3 countOneBits(ivec3 arg_0) {
}

ivec4 countOneBits(ivec4 arg_0) {
}

uint countOneBits(uint arg_0) {
}

uvec2 countOneBits(uvec2 arg_0) {
}

uvec3 countOneBits(uvec3 arg_0) {
}

uvec4 countOneBits(uvec4 arg_0) {
}

int reverseBits(int arg_0) {
}

ivec2 reverseBits(ivec2 arg_0) {
}

ivec3 reverseBits(ivec3 arg_0) {
}

ivec4 reverseBits(ivec4 arg_0) {
}

uint reverseBits(uint arg_0) {
}

uvec2 reverseBits(uvec2 arg_0) {
}

uvec3 reverseBits(uvec3 arg_0) {
}

uvec4 reverseBits(uvec4 arg_0) {
}

void main() {
    wgsl_main();
}
