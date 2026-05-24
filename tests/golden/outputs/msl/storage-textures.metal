#include <metal_stdlib>
using namespace metal;

[[kernel]]
void csLoad(texture2d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]], texture2d<float> global_2 [[texture(2)]], texture2d<float> global_3 [[texture(0)]], texture2d<float> global_4 [[texture(1)]], texture2d<float> global_5 [[texture(2)]]) {
    textureLoad(global_0, uint2(0));
    textureLoad(global_1, uint2(0));
    textureLoad(global_2, uint2(0));
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1) {
}

[[kernel]]
void csStore(texture2d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]], texture2d<float> global_2 [[texture(2)]], texture2d<float> global_3 [[texture(0)]], texture2d<float> global_4 [[texture(1)]], texture2d<float> global_5 [[texture(2)]]) {
    textureStore(global_3, uint2(0), float4(0.0f));
    textureStore(global_4, uint2(0), float4(0.0f));
    textureStore(global_5, uint2(0), float4(0.0f));
}

texture2d<float> textureStore(texture2d<float> arg_0, uint2 arg_1, float4 arg_2) {
}

texture2d<float> textureStore(texture2d<float> arg_0, uint2 arg_1, float4 arg_2) {
}

texture2d<float> textureStore(texture2d<float> arg_0, uint2 arg_1, float4 arg_2) {
}
