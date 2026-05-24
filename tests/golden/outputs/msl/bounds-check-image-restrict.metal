#include <metal_stdlib>
using namespace metal;

float4 test_textureLoad_1d(int coords, int level) {
    return textureLoad(global_0, coords, level);
}

void textureLoad() {
}

float4 test_textureLoad_2d(int2 coords, int level) {
    return textureLoad(global_1, coords, level);
}

void textureLoad() {
}

float4 test_textureLoad_2d_array_u(int2 coords, uint index, int level) {
    return textureLoad(global_2, coords, index, level);
}

void textureLoad() {
}

float4 test_textureLoad_2d_array_s(int2 coords, int index, int level) {
    return textureLoad(global_2, coords, index, level);
}

void textureLoad() {
}

float4 test_textureLoad_3d(int3 coords, int level) {
    return textureLoad(global_3, coords, level);
}

void textureLoad() {
}

float4 test_textureLoad_multisampled_2d(int2 coords, int _sample) {
    return textureLoad(global_4, coords, _sample);
}

void textureLoad() {
}

void test_textureStore_1d(int coords, float4 value) {
}

void textureStore() {
}

void test_textureStore_2d(int2 coords, float4 value) {
}

void textureStore() {
}

void test_textureStore_2d_array_u(int2 coords, uint array_index, float4 value) {
}

void textureStore() {
}

void test_textureStore_2d_array_s(int2 coords, int array_index, float4 value) {
}

void textureStore() {
}

void test_textureStore_3d(int3 coords, float4 value) {
}

void textureStore() {
}

[[fragment]]
float4 fragment_shader(texture1d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]], texture2d_array<float> global_2 [[texture(2)]], texture3d<float> global_3 [[texture(3)]], texture2d_ms<float> global_4 [[texture(4)]], texture2d<float> global_5 [[texture(5)]], texture2d<float> global_6 [[texture(6)]], texture2d<float> global_7 [[texture(7)]], texture2d<float> global_8 [[texture(8)]]) {
}
