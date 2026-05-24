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

texture1D textureLoad(texture1D arg_0, int arg_1, int arg_2) {
}

vec4 test_textureLoad_2d(ivec2 coords, int level) {
    return textureLoad(global_1, coords, level);
}

texture2D textureLoad(texture2D arg_0, ivec2 arg_1, int arg_2) {
}

vec4 test_textureLoad_2d_array_u(ivec2 coords, uint index, int level) {
    return textureLoad(global_2, coords, index, level);
}

texture2DArray textureLoad(texture2DArray arg_0, ivec2 arg_1, uint arg_2, int arg_3) {
}

vec4 test_textureLoad_2d_array_s(ivec2 coords, int index, int level) {
    return textureLoad(global_2, coords, index, level);
}

texture2DArray textureLoad(texture2DArray arg_0, ivec2 arg_1, int arg_2, int arg_3) {
}

vec4 test_textureLoad_3d(ivec3 coords, int level) {
    return textureLoad(global_3, coords, level);
}

texture3D textureLoad(texture3D arg_0, ivec3 arg_1, int arg_2) {
}

vec4 test_textureLoad_multisampled_2d(ivec2 coords, int _sample) {
    return textureLoad(global_4, coords, _sample);
}

texture2DMS textureLoad(texture2DMS arg_0, ivec2 arg_1, int arg_2) {
}

void test_textureStore_1d(int coords, vec4 value) {
    textureStore(global_5, coords, value);
}

texture_storage_1d<rgba8unorm, write> textureStore(texture_storage_1d<rgba8unorm, write> arg_0, int arg_1, vec4 arg_2) {
}

void test_textureStore_2d(ivec2 coords, vec4 value) {
    textureStore(global_6, coords, value);
}

texture_storage_2d<rgba8unorm, write> textureStore(texture_storage_2d<rgba8unorm, write> arg_0, ivec2 arg_1, vec4 arg_2) {
}

void test_textureStore_2d_array_u(ivec2 coords, uint array_index, vec4 value) {
    textureStore(global_7, coords, array_index, value);
}

texture_storage_2d_array<rgba8unorm, write> textureStore(texture_storage_2d_array<rgba8unorm, write> arg_0, ivec2 arg_1, uint arg_2, vec4 arg_3) {
}

void test_textureStore_2d_array_s(ivec2 coords, int array_index, vec4 value) {
    textureStore(global_7, coords, array_index, value);
}

texture_storage_2d_array<rgba8unorm, write> textureStore(texture_storage_2d_array<rgba8unorm, write> arg_0, ivec2 arg_1, int arg_2, vec4 arg_3) {
}

void test_textureStore_3d(ivec3 coords, vec4 value) {
    textureStore(global_8, coords, value);
}

texture_storage_3d<rgba8unorm, write> textureStore(texture_storage_3d<rgba8unorm, write> arg_0, ivec3 arg_1, vec4 arg_2) {
}

vec4 wgsl_fragment_shader() {
    test_textureLoad_1d(0, 0);
    test_textureLoad_2d(ivec2(0), 0);
    test_textureLoad_2d_array_u(ivec2(0), 0u, 0);
    test_textureLoad_2d_array_s(ivec2(0), 0, 0);
    test_textureLoad_3d(ivec3(0), 0);
    test_textureLoad_multisampled_2d(ivec2(0), 0);
    test_textureStore_1d(0, vec4(0.0f));
    test_textureStore_2d(ivec2(0), vec4(0.0f));
    test_textureStore_2d_array_u(ivec2(0), 0u, vec4(0.0f));
    test_textureStore_2d_array_s(ivec2(0), 0, vec4(0.0f));
    test_textureStore_3d(ivec3(0), vec4(0.0f));
    return vec4(0.0f, 0.0f, 0.0f, 0.0f);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fragment_shader();
}
