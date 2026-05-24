#include <metal_stdlib>
using namespace metal;

float4 test_textureLoad_1d(int coords, int level) {
    return textureLoad(global_0, coords, level);
}

texture1d<float> textureLoad(texture1d<float> arg_0, int arg_1, int arg_2) {
}

float4 test_textureLoad_2d(int2 coords, int level) {
    return textureLoad(global_1, coords, level);
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

float4 test_textureLoad_2d_array_u(int2 coords, uint index, int level) {
    return textureLoad(global_2, coords, index, level);
}

texture2d_array<float> textureLoad(texture2d_array<float> arg_0, int2 arg_1, uint arg_2, int arg_3) {
}

float4 test_textureLoad_2d_array_s(int2 coords, int index, int level) {
    return textureLoad(global_2, coords, index, level);
}

texture2d_array<float> textureLoad(texture2d_array<float> arg_0, int2 arg_1, int arg_2, int arg_3) {
}

float4 test_textureLoad_3d(int3 coords, int level) {
    return textureLoad(global_3, coords, level);
}

texture3d<float> textureLoad(texture3d<float> arg_0, int3 arg_1, int arg_2) {
}

float4 test_textureLoad_multisampled_2d(int2 coords, int _sample) {
    return textureLoad(global_4, coords, _sample);
}

texture2d_ms<float> textureLoad(texture2d_ms<float> arg_0, int2 arg_1, int arg_2) {
}

void test_textureStore_1d(int coords, float4 value) {
    textureStore(global_5, coords, value);
}

texture2d<float> textureStore(texture2d<float> arg_0, int arg_1, float4 arg_2) {
}

void test_textureStore_2d(int2 coords, float4 value) {
    textureStore(global_6, coords, value);
}

texture2d<float> textureStore(texture2d<float> arg_0, int2 arg_1, float4 arg_2) {
}

void test_textureStore_2d_array_u(int2 coords, uint array_index, float4 value) {
    textureStore(global_7, coords, array_index, value);
}

texture2d<float> textureStore(texture2d<float> arg_0, int2 arg_1, uint arg_2, float4 arg_3) {
}

void test_textureStore_2d_array_s(int2 coords, int array_index, float4 value) {
    textureStore(global_7, coords, array_index, value);
}

texture2d<float> textureStore(texture2d<float> arg_0, int2 arg_1, int arg_2, float4 arg_3) {
}

void test_textureStore_3d(int3 coords, float4 value) {
    textureStore(global_8, coords, value);
}

texture2d<float> textureStore(texture2d<float> arg_0, int3 arg_1, float4 arg_2) {
}

[[fragment]]
float4 fragment_shader(texture1d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]], texture2d_array<float> global_2 [[texture(2)]], texture3d<float> global_3 [[texture(3)]], texture2d_ms<float> global_4 [[texture(4)]], texture2d<float> global_5 [[texture(5)]], texture2d<float> global_6 [[texture(6)]], texture2d<float> global_7 [[texture(7)]], texture2d<float> global_8 [[texture(8)]]) {
    test_textureLoad_1d(0, 0);
    test_textureLoad_2d(int2(), 0);
    test_textureLoad_2d_array_u(int2(), 0u, 0);
    test_textureLoad_2d_array_s(int2(), 0, 0);
    test_textureLoad_3d(int3(), 0);
    test_textureLoad_multisampled_2d(int2(), 0);
    test_textureStore_1d(0, float4());
    test_textureStore_2d(int2(), float4());
    test_textureStore_2d_array_u(int2(), 0u, float4());
    test_textureStore_2d_array_s(int2(), 0, float4());
    test_textureStore_3d(int3(), float4());
    return float4(0.0f, 0.0f, 0.0f, 0.0f);
}
