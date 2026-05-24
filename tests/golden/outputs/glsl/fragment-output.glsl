#version 450 core
precision highp float;
precision highp int;

struct Struct_9 {
    vec4 vec4f;
    ivec4 vec4i;
    uvec4 vec4u;
    vec3 vec3f;
    ivec3 vec3i;
    uvec3 vec3u;
};
struct Struct_13 {
    vec2 vec2f;
    ivec2 vec2i;
    uvec2 vec2u;
    float scalarf;
    int scalari;
    uint scalaru;
};

Struct_9 wgsl_main_vec4vec3() {
    Struct_9 output;
    output.vec4f = vec4(0.0f);
    output.vec4i = ivec4(0);
    output.vec4u = uvec4(0u);
    output.vec3f = vec3(0.0f);
    output.vec3i = ivec3(0);
    output.vec3u = uvec3(0u);
    return output;
}

Struct_13 wgsl_main_vec2scalar() {
    Struct_13 output;
    output.vec2f = vec2(0.0f);
    output.vec2i = ivec2(0);
    output.vec2u = uvec2(0u);
    output.scalarf = 0.0f;
    output.scalari = 0;
    output.scalaru = 0u;
    return output;
}

layout(location = 0) out Struct_9 outColor;
void main() {
    outColor = wgsl_main_vec4vec3();
}

layout(location = 0) out Struct_13 outColor;
void main() {
    outColor = wgsl_main_vec2scalar();
}
