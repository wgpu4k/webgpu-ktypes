#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture1D global_0;
layout(set = 0, binding = 1) uniform texture2D global_1;
layout(set = 0, binding = 2) uniform texture2DArray global_2;
layout(set = 0, binding = 3) uniform texture3D global_3;
layout(set = 0, binding = 4) uniform texture2DMS global_4;
layout(set = 0, binding = 5) uniform texture_storage_1d<rgba8unorm, write> global_5;
layout(set = 0, binding = 6) uniform texture_storage_2d<rgba8unorm, write> global_6;
layout(set = 0, binding = 7) uniform texture_storage_2d_array<rgba8unorm, write> global_7;
layout(set = 0, binding = 8) uniform texture_storage_3d<rgba8unorm, write> global_8;

vec4 test_textureLoad_1d(int coords, int level) {
    return textureLoad(global_0, coords, level);
}

void textureLoad() {
}

vec4 test_textureLoad_2d(ivec2 coords, int level) {
    return textureLoad(global_1, coords, level);
}

void textureLoad() {
}

vec4 test_textureLoad_2d_array_u(ivec2 coords, uint index, int level) {
    return textureLoad(global_2, coords, index, level);
}

void textureLoad() {
}

vec4 test_textureLoad_2d_array_s(ivec2 coords, int index, int level) {
    return textureLoad(global_2, coords, index, level);
}

void textureLoad() {
}

vec4 test_textureLoad_3d(ivec3 coords, int level) {
    return textureLoad(global_3, coords, level);
}

void textureLoad() {
}

vec4 test_textureLoad_multisampled_2d(ivec2 coords, int _sample) {
    return textureLoad(global_4, coords, _sample);
}

void textureLoad() {
}

void test_textureStore_1d(int coords, vec4 value) {
}

void textureStore() {
}

void test_textureStore_2d(ivec2 coords, vec4 value) {
}

void textureStore() {
}

void test_textureStore_2d_array_u(ivec2 coords, uint array_index, vec4 value) {
}

void textureStore() {
}

void test_textureStore_2d_array_s(ivec2 coords, int array_index, vec4 value) {
}

void textureStore() {
}

void test_textureStore_3d(ivec3 coords, vec4 value) {
}

void textureStore() {
}

vec4 wgsl_fragment_shader() {
    return vec4(0.0f, 0.0f, 0.0f, 0.0f);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fragment_shader();
}
