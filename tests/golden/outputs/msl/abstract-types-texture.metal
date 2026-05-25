#include <metal_stdlib>
using namespace metal;

void color() {
    textureSample(global_0, global_1, float2(1, 2));
    textureSample(global_0, global_1, float2(1, 2), float2(3, 4));
    textureSampleLevel(global_0, global_1, float2(1, 2), 0);
    textureSampleLevel(global_0, global_1, float2(1, 2), 0.0f);
    textureSampleGrad(global_0, global_1, float2(1, 2), float2(3, 4), float2(5, 6));
    textureSampleBias(global_0, global_1, float2(1, 2), 1);
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float2 arg_3) {
}

float4 textureSampleLevel(texture2d<float> arg_0, sampler arg_1, float2 arg_2, int arg_3) {
}

float4 textureSampleLevel(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

float4 textureSampleGrad(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float2 arg_3, float2 arg_4) {
}

float4 textureSampleBias(texture2d<float> arg_0, sampler arg_1, float2 arg_2, int arg_3) {
}

void depth() {
    textureSampleLevel(global_2, global_1, float2(1, 2), 1);
    textureSampleCompare(global_2, global_3, float2(1, 2), 0);
    textureGatherCompare(global_2, global_3, float2(1, 2), 0);
}

float4 textureSampleLevel(depth2d<float> arg_0, sampler arg_1, float2 arg_2, int arg_3) {
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, int arg_3) {
}

depth2d<float> textureGatherCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, int arg_3) {
}

void storage() {
    textureStore(global_4, float2(0, 1), float4(2, 3, 4, 5));
}

texture2d<float> textureStore(texture2d<float> arg_0, float2 arg_1, float4 arg_2) {
}

[[fragment]]
void main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]], depth2d<float> global_2 [[texture(2)]], sampler_comparison global_3 [[sampler(3)]], texture2d<float> global_4 [[texture(4)]]) {
    color();
    depth();
    storage();
}
