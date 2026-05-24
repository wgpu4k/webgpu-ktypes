#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture2D global_0;
layout(set = 0, binding = 1) uniform texture2D global_1;
layout(set = 0, binding = 2) uniform sampler global_2;
layout(set = 0, binding = 3) uniform sampler global_3;
layout(set = 0, binding = 4) uniform vec2 global_4;
layout(set = 1, binding = 0) uniform vec2 global_5;

vec4 wgsl_entry_point_one(vec4 pos) {
    return textureSample(global_0, global_2, pos.xy);
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 wgsl_entry_point_two() {
    return textureSample(global_0, global_2, global_4);
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 wgsl_entry_point_three() {
    return (textureSample(global_0, global_2, (global_5 + global_4)) + textureSample(global_1, global_3, global_4));
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_entry_point_one(pos);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_entry_point_two();
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_entry_point_three();
}
