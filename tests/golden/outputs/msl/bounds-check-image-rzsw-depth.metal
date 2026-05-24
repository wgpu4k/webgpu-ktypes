#include <metal_stdlib>
using namespace metal;

float test_textureLoad_depth_2d(int2 coords, int level) {
    return textureLoad(global_0, coords, level);
}

depth2d<float> textureLoad(depth2d<float> arg_0, int2 arg_1, int arg_2) {
}

float test_textureLoad_depth_2d_array_u(int2 coords, uint index, int level) {
    return textureLoad(global_1, coords, index, level);
}

depth2d<float> textureLoad(depth2d<float> arg_0, int2 arg_1, uint arg_2, int arg_3) {
}

float test_textureLoad_depth_2d_array_s(int2 coords, int index, int level) {
    return textureLoad(global_1, coords, index, level);
}

depth2d<float> textureLoad(depth2d<float> arg_0, int2 arg_1, int arg_2, int arg_3) {
}

float test_textureLoad_depth_multisampled_2d(int2 coords, int _sample) {
    return textureLoad(global_2, coords, _sample);
}

depth2d<float> textureLoad(depth2d<float> arg_0, int2 arg_1, int arg_2) {
}

[[fragment]]
float4 fragment_shader(depth2d<float> global_0 [[texture(0)]], depth2d<float> global_1 [[texture(1)]], depth2d<float> global_2 [[texture(2)]]) {
    test_textureLoad_depth_2d(int2(), 0);
    test_textureLoad_depth_2d_array_u(int2(), 0u, 0);
    test_textureLoad_depth_2d_array_s(int2(), 0, 0);
    test_textureLoad_depth_multisampled_2d(int2(), 0);
    return float4(0.0f, 0.0f, 0.0f, 0.0f);
}
