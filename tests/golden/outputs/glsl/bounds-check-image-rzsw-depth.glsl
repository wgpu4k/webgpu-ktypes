#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture2D global_0;
layout(set = 0, binding = 1) uniform texture2D global_1;
layout(set = 0, binding = 2) uniform texture2D global_2;

float test_textureLoad_depth_2d(ivec2 coords, int level) {
    return textureLoad(global_0, coords, level);
}

void textureLoad() {
}

float test_textureLoad_depth_2d_array_u(ivec2 coords, uint index, int level) {
    return textureLoad(global_1, coords, index, level);
}

void textureLoad() {
}

float test_textureLoad_depth_2d_array_s(ivec2 coords, int index, int level) {
    return textureLoad(global_1, coords, index, level);
}

void textureLoad() {
}

float test_textureLoad_depth_multisampled_2d(ivec2 coords, int _sample) {
    return textureLoad(global_2, coords, _sample);
}

void textureLoad() {
}

vec4 wgsl_fragment_shader() {
    return vec4(0.0f, 0.0f, 0.0f, 0.0f);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fragment_shader();
}
