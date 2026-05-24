#include <metal_stdlib>
using namespace metal;

float test_textureLoad_depth_2d(int2 coords, int level) {
    return textureLoad(global_0, coords, level);
}

void textureLoad() {
}

float test_textureLoad_depth_2d_array_u(int2 coords, uint index, int level) {
    return textureLoad(global_1, coords, index, level);
}

void textureLoad() {
}

float test_textureLoad_depth_2d_array_s(int2 coords, int index, int level) {
    return textureLoad(global_1, coords, index, level);
}

void textureLoad() {
}

float test_textureLoad_depth_multisampled_2d(int2 coords, int _sample) {
    return textureLoad(global_2, coords, _sample);
}

void textureLoad() {
}

[[fragment]]
float4 fragment_shader(depth2d<float> global_0 [[texture(0)]], depth2d<float> global_1 [[texture(1)]], depth2d<float> global_2 [[texture(2)]]) {
}
