#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    uint2 local_0 = textureDimensions(global_3);
    int2 local_1 = (int2((local_0 * local_id.xy)) % int2(10, 20));
    texture2d<uint> local_2 = textureLoad(global_0, local_1, int(local_id[2]));
    texture2d<uint> local_3 = textureLoad(global_0, local_1, uint(local_id[2]));
    texture2d<float> local_4 = textureLoad(global_1, local_1, int(local_id[2]));
    texture2d<float> local_5 = textureLoad(global_1, local_1, uint(local_id[2]));
    texture2d<float> local_6 = textureLoad(global_3, local_1);
    texture2d<float> local_7 = textureLoad(global_4, local_1, local_id[2], (int(local_id[2]) + 1));
    texture2d<float> local_8 = textureLoad(global_4, local_1, int(local_id[2]), (int(local_id[2]) + 1));
    texture2d<float> local_9 = textureLoad(global_6, int(local_id[0]), int(local_id[2]));
    texture2d<float> local_10 = textureLoad(global_5, int(local_id[0]));
    texture2d<uint> local_11 = textureLoad(global_0, uint2(local_1), int(local_id[2]));
    texture2d<float> local_12 = textureLoad(global_1, uint2(local_1), int(local_id[2]));
    texture2d<float> local_13 = textureLoad(global_1, uint2(local_1), uint(local_id[2]));
    texture2d<float> local_14 = textureLoad(global_3, uint2(local_1));
    texture2d<float> local_15 = textureLoad(global_4, uint2(local_1), local_id[2], (int(local_id[2]) + 1));
    texture2d<float> local_16 = textureLoad(global_4, uint2(local_1), int(local_id[2]), (int(local_id[2]) + 1));
    texture2d<float> local_17 = textureLoad(global_6, uint(local_id[0]), int(local_id[2]));
    textureStore(global_7, local_1[0], ((((local_2 + local_4) + local_6) + local_7) + local_8));
    textureStore(global_7, uint(local_1[0]), ((((local_11 + local_12) + local_14) + local_15) + local_16));
}

uint2 textureDimensions(texture2d<float> arg_0) {
}

texture2d<uint> textureLoad(texture2d<uint> arg_0, int2 arg_1, int arg_2) {
}

texture2d<uint> textureLoad(texture2d<uint> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, uint arg_2, int arg_3) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int2 arg_1, int arg_2, int arg_3) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int arg_1, int arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, int arg_1) {
}

texture2d<uint> textureLoad(texture2d<uint> arg_0, uint2 arg_1, int arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1, int arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1, uint arg_2) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1, uint arg_2, int arg_3) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint2 arg_1, int arg_2, int arg_3) {
}

texture2d<float> textureLoad(texture2d<float> arg_0, uint arg_1, int arg_2) {
}

texture2d<float> textureStore(texture2d<float> arg_0, int arg_1, texture2d<uint> arg_2) {
}

texture2d<float> textureStore(texture2d<float> arg_0, uint arg_1, texture2d<uint> arg_2) {
}

[[kernel]]
void depth_load(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    uint2 local_0 = textureDimensions(global_3);
    int2 local_1 = (int2((local_0 * local_id.xy)) % int2(10, 20));
    float local_2 = textureLoad(global_2, local_1, int(local_id[2]));
    textureStore(global_7, local_1[0], uint4(uint(local_2)));
    return;
}

uint2 textureDimensions(texture2d<float> arg_0) {
}

depth2d<float> textureLoad(depth2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureStore(texture2d<float> arg_0, int arg_1, uint4 arg_2) {
}

struct queries_Output {
    float4 position [[position]];
};
[[vertex]]
queries_Output queries(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    uint local_0 = textureDimensions(global_8);
    uint local_1 = textureDimensions(global_8, int(local_0));
    uint2 local_2 = textureDimensions(global_9);
    uint2 local_3 = textureDimensions(global_9, 1);
    uint2 local_4 = textureDimensions(global_12);
    uint2 local_5 = textureDimensions(global_12, 1);
    uint2 local_6 = textureDimensions(global_13);
    uint2 local_7 = textureDimensions(global_13, 1);
    uint2 local_8 = textureDimensions(global_14);
    uint2 local_9 = textureDimensions(global_14, 1);
    uint3 local_10 = textureDimensions(global_15);
    uint3 local_11 = textureDimensions(global_15, 1);
    uint2 local_12 = textureDimensions(global_16);
    uint local_13 = ((((((((((local_0 + local_2[1]) + local_3[1]) + local_4[1]) + local_5[1]) + local_6[1]) + local_7[1]) + local_8[1]) + local_9[1]) + local_10[2]) + local_11[2]);
    return float4(float(local_13));
}

uint textureDimensions(texture1d<float> arg_0) {
}

uint textureDimensions(texture1d<float> arg_0, int arg_1) {
}

uint2 textureDimensions(texture2d<float> arg_0) {
}

uint2 textureDimensions(texture2d<float> arg_0, int arg_1) {
}

uint2 textureDimensions(texture2d_array<float> arg_0) {
}

uint2 textureDimensions(texture2d_array<float> arg_0, int arg_1) {
}

uint2 textureDimensions(texturecube<float> arg_0) {
}

uint2 textureDimensions(texturecube<float> arg_0, int arg_1) {
}

uint2 textureDimensions(texturecube_array<float> arg_0) {
}

uint2 textureDimensions(texturecube_array<float> arg_0, int arg_1) {
}

uint3 textureDimensions(texture3d<float> arg_0) {
}

uint3 textureDimensions(texture3d<float> arg_0, int arg_1) {
}

uint2 textureDimensions(texture2d_ms<float> arg_0) {
}

struct levels_queries_Output {
    float4 position [[position]];
};
[[vertex]]
levels_queries_Output levels_queries(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    uint local_0 = textureNumLevels(global_9);
    uint local_1 = textureNumLayers(global_12);
    uint local_2 = textureNumLevels(global_12);
    uint local_3 = textureNumLayers(global_12);
    uint local_4 = textureNumLevels(global_13);
    uint local_5 = textureNumLevels(global_14);
    uint local_6 = textureNumLayers(global_14);
    uint local_7 = textureNumLevels(global_15);
    uint local_8 = textureNumSamples(global_16);
    uint local_9 = (((((((local_1 + local_6) + local_8) + local_0) + local_2) + local_7) + local_4) + local_5);
    return float4(float(local_9));
}

uint textureNumLevels(texture2d<float> arg_0) {
}

uint textureNumLayers(texture2d_array<float> arg_0) {
}

uint textureNumLevels(texture2d_array<float> arg_0) {
}

uint textureNumLayers(texture2d_array<float> arg_0) {
}

uint textureNumLevels(texturecube<float> arg_0) {
}

uint textureNumLevels(texturecube_array<float> arg_0) {
}

uint textureNumLayers(texturecube_array<float> arg_0) {
}

uint textureNumLevels(texture3d<float> arg_0) {
}

uint textureNumSamples(texture2d_ms<float> arg_0) {
}

[[fragment]]
float4 texture_sample(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    float2 local_0 = float2(0.5f);
    float3 local_1 = float3(0.5f);
    int2 local_2 = int2(3, 1);
    float local_3 = 2.3f;
    float4 local_4;
    local_4 = textureSample(global_8, global_17, local_0[0]);
    local_4 = textureSample(global_9, global_17, local_0);
    local_4 = textureSample(global_9, global_17, local_0, int2(3, 1));
    local_4 = textureSampleLevel(global_9, global_17, local_0, local_3);
    local_4 = textureSampleLevel(global_9, global_17, local_0, local_3, local_2);
    local_4 = textureSampleBias(global_9, global_17, local_0, 2.0f, local_2);
    local_4 = textureSampleBaseClampToEdge(global_9, global_17, local_0);
    local_4 = textureSample(global_12, global_17, local_0, 0u);
    local_4 = textureSample(global_12, global_17, local_0, 0u, local_2);
    local_4 = textureSampleLevel(global_12, global_17, local_0, 0u, local_3);
    local_4 = textureSampleLevel(global_12, global_17, local_0, 0u, local_3, local_2);
    local_4 = textureSampleBias(global_12, global_17, local_0, 0u, 2.0f, local_2);
    local_4 = textureSample(global_12, global_17, local_0, 0);
    local_4 = textureSample(global_12, global_17, local_0, 0, local_2);
    local_4 = textureSampleLevel(global_12, global_17, local_0, 0, local_3);
    local_4 = textureSampleLevel(global_12, global_17, local_0, 0, local_3, local_2);
    local_4 = textureSampleBias(global_12, global_17, local_0, 0, 2.0f, local_2);
    local_4 = textureSample(global_14, global_17, local_1, 0u);
    local_4 = textureSampleLevel(global_14, global_17, local_1, 0u, local_3);
    local_4 = textureSampleBias(global_14, global_17, local_1, 0u, 2.0f);
    local_4 = textureSample(global_14, global_17, local_1, 0);
    local_4 = textureSampleLevel(global_14, global_17, local_1, 0, local_3);
    local_4 = textureSampleBias(global_14, global_17, local_1, 0, 2.0f);
    return local_4;
}

float4 textureSample(texture1d<float> arg_0, sampler arg_1, float arg_2) {
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2, int2 arg_3) {
}

float4 textureSampleLevel(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3) {
}

float4 textureSampleLevel(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3, int2 arg_4) {
}

float4 textureSampleBias(texture2d<float> arg_0, sampler arg_1, float2 arg_2, float arg_3, int2 arg_4) {
}

float4 textureSampleBaseClampToEdge(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

float4 textureSample(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, uint arg_3) {
}

float4 textureSample(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, uint arg_3, int2 arg_4) {
}

float4 textureSampleLevel(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, uint arg_3, float arg_4) {
}

float4 textureSampleLevel(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, uint arg_3, float arg_4, int2 arg_5) {
}

float4 textureSampleBias(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, uint arg_3, float arg_4, int2 arg_5) {
}

float4 textureSample(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, int arg_3) {
}

float4 textureSample(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, int arg_3, int2 arg_4) {
}

float4 textureSampleLevel(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, int arg_3, float arg_4) {
}

float4 textureSampleLevel(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, int arg_3, float arg_4, int2 arg_5) {
}

float4 textureSampleBias(texture2d_array<float> arg_0, sampler arg_1, float2 arg_2, int arg_3, float arg_4, int2 arg_5) {
}

float4 textureSample(texturecube_array<float> arg_0, sampler arg_1, float3 arg_2, uint arg_3) {
}

float4 textureSampleLevel(texturecube_array<float> arg_0, sampler arg_1, float3 arg_2, uint arg_3, float arg_4) {
}

float4 textureSampleBias(texturecube_array<float> arg_0, sampler arg_1, float3 arg_2, uint arg_3, float arg_4) {
}

float4 textureSample(texturecube_array<float> arg_0, sampler arg_1, float3 arg_2, int arg_3) {
}

float4 textureSampleLevel(texturecube_array<float> arg_0, sampler arg_1, float3 arg_2, int arg_3, float arg_4) {
}

float4 textureSampleBias(texturecube_array<float> arg_0, sampler arg_1, float3 arg_2, int arg_3, float arg_4) {
}

[[fragment]]
float4 gather(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    float2 local_0 = float2(0.5f);
    float local_1 = 0.5f;
    int local_2 = textureGather(1, global_9, global_17, local_0);
    int local_3 = textureGather(3, global_9, global_17, local_0, int2(3, 1));
    depth2d<float> local_4 = textureGatherCompare(global_19, global_18, local_0, local_1);
    depth2d<float> local_5 = textureGatherCompare(global_19, global_18, local_0, local_1, int2(3, 1));
    int local_6 = textureGather(0, global_10, global_17, local_0);
    int local_7 = textureGather(0, global_11, global_17, local_0);
    float4 local_8 = (float4(local_6) + float4(local_7));
    return ((((local_2 + local_3) + local_4) + local_5) + local_8);
}

int textureGather(int arg_0, texture2d<float> arg_1, sampler arg_2, float2 arg_3) {
}

int textureGather(int arg_0, texture2d<float> arg_1, sampler arg_2, float2 arg_3, int2 arg_4) {
}

depth2d<float> textureGatherCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

depth2d<float> textureGatherCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3, int2 arg_4) {
}

int textureGather(int arg_0, texture2d<uint> arg_1, sampler arg_2, float2 arg_3) {
}

int textureGather(int arg_0, texture2d<int> arg_1, sampler arg_2, float2 arg_3) {
}

[[fragment]]
float4 depth_no_comparison(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    float2 local_0 = float2(0.5f);
    int local_1 = 1;
    float4 local_2 = textureSample(global_19, global_17, local_0);
    depth2d<float> local_3 = textureGather(global_19, global_17, local_0);
    float4 local_4 = textureSampleLevel(global_19, global_17, local_0, local_1);
    return ((local_2 + local_3) + local_4);
}

float4 textureSample(depth2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

depth2d<float> textureGather(depth2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

float4 textureSampleLevel(depth2d<float> arg_0, sampler arg_1, float2 arg_2, int arg_3) {
}

[[fragment]]
float texture_sample_comparison(texture2d<uint> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(3)]], depth2d<float> global_2 [[texture(4)]], texture2d<float> global_3 [[texture(1)]], texture2d<float> global_4 [[texture(5)]], texture2d<float> global_5 [[texture(6)]], texture2d<float> global_6 [[texture(7)]], texture2d<float> global_7 [[texture(2)]], texture1d<float> global_8 [[texture(0)]], texture2d<float> global_9 [[texture(1)]], texture2d<uint> global_10 [[texture(2)]], texture2d<int> global_11 [[texture(3)]], texture2d_array<float> global_12 [[texture(4)]], texturecube<float> global_13 [[texture(5)]], texturecube_array<float> global_14 [[texture(6)]], texture3d<float> global_15 [[texture(7)]], texture2d_ms<float> global_16 [[texture(8)]], sampler global_17 [[sampler(0)]], sampler_comparison global_18 [[sampler(1)]], depth2d<float> global_19 [[texture(2)]], depth2d<float> global_20 [[texture(3)]], depth2d<float> global_21 [[texture(4)]]) {
    float2 local_0 = float2(0.5f);
    float3 local_1 = float3(0.5f);
    float local_2 = 0.5f;
    float local_3;
    local_3 = textureSampleCompare(global_19, global_18, local_0, local_2);
    local_3 = textureSampleCompare(global_20, global_18, local_0, 0u, local_2);
    local_3 = textureSampleCompare(global_20, global_18, local_0, 0, local_2);
    local_3 = textureSampleCompare(global_21, global_18, local_1, local_2);
    local_3 = textureSampleCompareLevel(global_19, global_18, local_0, local_2);
    local_3 = textureSampleCompareLevel(global_20, global_18, local_0, 0u, local_2);
    local_3 = textureSampleCompareLevel(global_20, global_18, local_0, 0, local_2);
    local_3 = textureSampleCompareLevel(global_21, global_18, local_1, local_2);
    return local_3;
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, uint arg_3, float arg_4) {
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, int arg_3, float arg_4) {
}

float textureSampleCompare(depth2d<float> arg_0, sampler_comparison arg_1, float3 arg_2, float arg_3) {
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, uint arg_3, float arg_4) {
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float2 arg_2, int arg_3, float arg_4) {
}

float textureSampleCompareLevel(depth2d<float> arg_0, sampler_comparison arg_1, float3 arg_2, float arg_3) {
}
