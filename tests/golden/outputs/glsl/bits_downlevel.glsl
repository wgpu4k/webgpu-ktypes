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
    u = pack4xI8(i4);
    u = pack4xU8(u4);
    f4 = unpack4x8snorm(u);
    f4 = unpack4x8unorm(u);
    f2 = unpack2x16snorm(u);
    f2 = unpack2x16unorm(u);
}

void pack4xI8() {
}

void pack4xU8() {
}

void unpack4x8snorm() {
}

void unpack4x8unorm() {
}

void unpack2x16snorm() {
}

void unpack2x16unorm() {
}

void main() {
    wgsl_main();
}
