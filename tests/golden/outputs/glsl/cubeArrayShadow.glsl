#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 4) uniform texture2D global_0;
layout(set = 0, binding = 5) uniform sampler_comparison global_1;

vec4 wgsl_fragment() {
    vec3 frag_ls = vec4(1.0f, 1.0f, 2.0f, 1.0f).xyz;
    float a = textureSampleCompare(global_0, global_1, frag_ls, int(1), 1.0f);
    return vec4(a, 1.0f, 1.0f, 1.0f);
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec3 arg_2, int arg_3, float arg_4) {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fragment();
}
