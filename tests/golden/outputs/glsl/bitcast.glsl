#version 450 core
precision highp float;
precision highp int;


void wgsl_main() {
    ivec2 i2 = ivec2(0);
    ivec3 i3 = ivec3(0);
    ivec4 i4 = ivec4(0);
    uvec2 u2 = uvec2(0u);
    uvec3 u3 = uvec3(0u);
    uvec4 u4 = uvec4(0u);
    vec2 f2 = vec2(0.0f);
    vec3 f3 = vec3(0.0f);
    vec4 f4 = vec4(0.0f);
    u2 = uvec2(i2);
    u3 = uvec3(i3);
    u4 = uvec4(i4);
    i2 = ivec2(u2);
    i3 = ivec3(u3);
    i4 = ivec4(u4);
    f2 = intBitsToFloat(i2);
    f3 = intBitsToFloat(i3);
    f4 = intBitsToFloat(i4);
}

void main() {
    wgsl_main();
}
