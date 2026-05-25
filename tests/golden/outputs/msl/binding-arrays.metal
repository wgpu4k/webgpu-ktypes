#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint index;
};
struct Struct_2 {
    uint index;
};

[[fragment]]
float4 main(array<texture2d<float>> global_0 [[buffer(0)]], array<texture2d<float>, 5> global_1 [[buffer(1)]], array<texture2d_array<float>, 5> global_2 [[buffer(2)]], array<texture2d_ms<float>, 5> global_3 [[buffer(3)]], array<depth2d<float>, 5> global_4 [[buffer(4)]], array<texture2d<float>, 5> global_5 [[buffer(5)]], array<sampler, 5> global_6 [[buffer(6)]], array<sampler_comparison, 5> global_7 [[buffer(7)]], Struct_1 global_8 [[buffer(8)]]) {
    uint local_0 = global_8.index;
    uint local_1 = fragment_in.index;
    uint local_2 = 0u;
    uint2 local_3 = uint2(0u);
    float local_4 = 0.0f;
    float4 local_5 = float4(0.0f);
    float2 local_6 = float2(0.0f);
    int2 local_7 = int2(0);
    local_3 = textureDimensions(global_0[0]);
    local_3 = textureDimensions(global_0[local_0]);
    local_3 = textureDimensions(global_0[local_1]);
    local_5 = textureGather(0, global_1[0], global_6[0], local_6);
    local_5 = textureGather(0, global_1[local_0], global_6[local_0], local_6);
    local_5 = textureGather(0, global_1[local_1], global_6[local_1], local_6);
    local_5 = textureGatherCompare(global_4[0], global_7[0], local_6, 0.0f);
    local_5 = textureGatherCompare(global_4[local_0], global_7[local_0], local_6, 0.0f);
    local_5 = textureGatherCompare(global_4[local_1], global_7[local_1], local_6, 0.0f);
    local_5 = textureLoad(global_0[0], local_7, 0);
    local_5 = textureLoad(global_0[local_0], local_7, 0);
    local_5 = textureLoad(global_0[local_1], local_7, 0);
    local_2 = textureNumLayers(global_2[0]);
    local_2 = textureNumLayers(global_2[local_0]);
    local_2 = textureNumLayers(global_2[local_1]);
    local_2 = textureNumLevels(global_1[0]);
    local_2 = textureNumLevels(global_1[local_0]);
    local_2 = textureNumLevels(global_1[local_1]);
    local_2 = textureNumSamples(global_3[0]);
    local_2 = textureNumSamples(global_3[local_0]);
    local_2 = textureNumSamples(global_3[local_1]);
    local_5 = textureSample(global_1[0], global_6[0], local_6);
    local_5 = textureSample(global_1[local_0], global_6[local_0], local_6);
    local_5 = textureSample(global_1[local_1], global_6[local_1], local_6);
    local_5 = textureSampleBias(global_1[0], global_6[0], local_6, 0.0f);
    local_5 = textureSampleBias(global_1[local_0], global_6[local_0], local_6, 0.0f);
    local_5 = textureSampleBias(global_1[local_1], global_6[local_1], local_6, 0.0f);
    local_4 = textureSampleCompare(global_4[0], global_7[0], local_6, 0.0f);
    local_4 = textureSampleCompare(global_4[local_0], global_7[local_0], local_6, 0.0f);
    local_4 = textureSampleCompare(global_4[local_1], global_7[local_1], local_6, 0.0f);
    local_4 = textureSampleCompareLevel(global_4[0], global_7[0], local_6, 0.0f);
    local_4 = textureSampleCompareLevel(global_4[local_0], global_7[local_0], local_6, 0.0f);
    local_4 = textureSampleCompareLevel(global_4[local_1], global_7[local_1], local_6, 0.0f);
    local_5 = textureSampleGrad(global_1[0], global_6[0], local_6, local_6, local_6);
    local_5 = textureSampleGrad(global_1[local_0], global_6[local_0], local_6, local_6, local_6);
    local_5 = textureSampleGrad(global_1[local_1], global_6[local_1], local_6, local_6, local_6);
    local_5 = textureSampleLevel(global_1[0], global_6[0], local_6, 0.0f);
    local_5 = textureSampleLevel(global_1[local_0], global_6[local_0], local_6, 0.0f);
    local_5 = textureSampleLevel(global_1[local_1], global_6[local_1], local_6, 0.0f);
    textureStore(global_5[0], local_7, local_5);
    textureStore(global_5[local_0], local_7, local_5);
    textureStore(global_5[local_1], local_7, local_5);
    float2 local_8 = float2((local_3 + uint2(local_2)));
    return ((local_5 + float4(local_8[0], local_8[1], local_8[0], local_8[1])) + local_4);
}

uint2 textureDimensions(texture2d<float> arg_0) {
}

uint2 textureDimensions(texture2d<float> arg_0) {
}

uint2 textureDimensions(texture2d<float> arg_0) {
}

int textureGather(int arg_0, texture2d<float> arg_1, sampler arg_2, float2 arg_3) {
}

int textureGather(int arg_0, texture2d<float> arg_1, sampler arg_2, float2 arg_3) {
}

int textureGather(int arg_0, texture2d<float> arg_1, sampler arg_2, float2 arg_3) {
}

depth2d<float> textureGatherCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

depth2d<float> textureGatherCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

depth2d<float> textureGatherCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

uint textureNumLayers(texture2d_array<float> arg_0) {
}

uint textureNumLayers(texture2d_array<float> arg_0) {
}

uint textureNumLayers(texture2d_array<float> arg_0) {
}

uint textureNumLevels(texture2d<float> arg_0) {
}

uint textureNumLevels(texture2d<float> arg_0) {
}

uint textureNumLevels(texture2d<float> arg_0) {
}

uint textureNumSamples(texture2d_ms<float> arg_0) {
}

uint textureNumSamples(texture2d_ms<float> arg_0) {
}

uint textureNumSamples(texture2d_ms<float> arg_0) {
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

float4 textureSampleBias(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

float4 textureSampleBias(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

float4 textureSampleBias(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float4 textureSampleGrad(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float2 arg_3, float2 arg_4) {
}

float4 textureSampleGrad(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float2 arg_3, float2 arg_4) {
}

float4 textureSampleGrad(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float2 arg_3, float2 arg_4) {
}

float4 textureSampleLevel(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

float4 textureSampleLevel(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

float4 textureSampleLevel(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

texture2d<float> textureStore(texture2d<float> arg_0, int2 arg_1, float4 arg_2) {
}

texture2d<float> textureStore(texture2d<float> arg_0, int2 arg_1, float4 arg_2) {
}

texture2d<float> textureStore(texture2d<float> arg_0, int2 arg_1, float4 arg_2) {
}
