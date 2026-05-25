#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    uint index;
};
struct Struct_2 {
    uint index;
};
layout(set = 0, binding = 0) uniform texture2D[] global_0;
layout(set = 0, binding = 1) uniform texture2D[5] global_1;
layout(set = 0, binding = 2) uniform texture2DArray[5] global_2;
layout(set = 0, binding = 3) uniform texture2DMS[5] global_3;
layout(set = 0, binding = 4) uniform texture2D[5] global_4;
layout(set = 0, binding = 5) uniform texture_storage_2d<rgba32float, write>[5] global_5;
layout(set = 0, binding = 6) uniform sampler[5] global_6;
layout(set = 0, binding = 7) uniform sampler_comparison[5] global_7;
layout(set = 0, binding = 8) uniform Struct_1 global_8;

vec4 wgsl_main(Struct_2 fragment_in) {
    uint uniform_index = global_8.index;
    uint non_uniform_index = fragment_in.index;
    uint u1 = 0u;
    uvec2 u2 = uvec2(0u);
    float v1 = 0.0f;
    vec4 v4 = vec4(0.0f);
    vec2 uv = vec2(0.0f);
    ivec2 pix = ivec2(0);
    u2 = textureDimensions(global_0[0]);
    u2 = textureDimensions(global_0[uniform_index]);
    u2 = textureDimensions(global_0[non_uniform_index]);
    v4 = textureGather(0, global_1[0], global_6[0], uv);
    v4 = textureGather(0, global_1[uniform_index], global_6[uniform_index], uv);
    v4 = textureGather(0, global_1[non_uniform_index], global_6[non_uniform_index], uv);
    v4 = textureGatherCompare(global_4[0], global_7[0], uv, 0.0f);
    v4 = textureGatherCompare(global_4[uniform_index], global_7[uniform_index], uv, 0.0f);
    v4 = textureGatherCompare(global_4[non_uniform_index], global_7[non_uniform_index], uv, 0.0f);
    v4 = textureLoad(global_0[0], pix, 0);
    v4 = textureLoad(global_0[uniform_index], pix, 0);
    v4 = textureLoad(global_0[non_uniform_index], pix, 0);
    u1 = textureNumLayers(global_2[0]);
    u1 = textureNumLayers(global_2[uniform_index]);
    u1 = textureNumLayers(global_2[non_uniform_index]);
    u1 = textureNumLevels(global_1[0]);
    u1 = textureNumLevels(global_1[uniform_index]);
    u1 = textureNumLevels(global_1[non_uniform_index]);
    u1 = textureNumSamples(global_3[0]);
    u1 = textureNumSamples(global_3[uniform_index]);
    u1 = textureNumSamples(global_3[non_uniform_index]);
    v4 = textureSample(global_1[0], global_6[0], uv);
    v4 = textureSample(global_1[uniform_index], global_6[uniform_index], uv);
    v4 = textureSample(global_1[non_uniform_index], global_6[non_uniform_index], uv);
    v4 = textureSampleBias(global_1[0], global_6[0], uv, 0.0f);
    v4 = textureSampleBias(global_1[uniform_index], global_6[uniform_index], uv, 0.0f);
    v4 = textureSampleBias(global_1[non_uniform_index], global_6[non_uniform_index], uv, 0.0f);
    v1 = textureSampleCompare(global_4[0], global_7[0], uv, 0.0f);
    v1 = textureSampleCompare(global_4[uniform_index], global_7[uniform_index], uv, 0.0f);
    v1 = textureSampleCompare(global_4[non_uniform_index], global_7[non_uniform_index], uv, 0.0f);
    v1 = textureSampleCompareLevel(global_4[0], global_7[0], uv, 0.0f);
    v1 = textureSampleCompareLevel(global_4[uniform_index], global_7[uniform_index], uv, 0.0f);
    v1 = textureSampleCompareLevel(global_4[non_uniform_index], global_7[non_uniform_index], uv, 0.0f);
    v4 = textureSampleGrad(global_1[0], global_6[0], uv, uv, uv);
    v4 = textureSampleGrad(global_1[uniform_index], global_6[uniform_index], uv, uv, uv);
    v4 = textureSampleGrad(global_1[non_uniform_index], global_6[non_uniform_index], uv, uv, uv);
    v4 = textureSampleLevel(global_1[0], global_6[0], uv, 0.0f);
    v4 = textureSampleLevel(global_1[uniform_index], global_6[uniform_index], uv, 0.0f);
    v4 = textureSampleLevel(global_1[non_uniform_index], global_6[non_uniform_index], uv, 0.0f);
    textureStore(global_5[0], pix, v4);
    textureStore(global_5[uniform_index], pix, v4);
    textureStore(global_5[non_uniform_index], pix, v4);
    vec2 v2 = vec2((u2 + uvec2(u1)));
    return ((v4 + vec4(v2[0], v2[1], v2[0], v2[1])) + v1);
}

uvec2 textureDimensions(texture2D arg_0) {
}

uvec2 textureDimensions(texture2D arg_0) {
}

uvec2 textureDimensions(texture2D arg_0) {
}

int textureGather(int arg_0, texture2D arg_1, sampler arg_2, vec2 arg_3) {
}

int textureGather(int arg_0, texture2D arg_1, sampler arg_2, vec2 arg_3) {
}

int textureGather(int arg_0, texture2D arg_1, sampler arg_2, vec2 arg_3) {
}

texture2D textureGatherCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

texture2D textureGatherCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

texture2D textureGatherCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

texture2D textureLoad(texture2D arg_0, ivec2 arg_1, int arg_2) {
}

texture2D textureLoad(texture2D arg_0, ivec2 arg_1, int arg_2) {
}

texture2D textureLoad(texture2D arg_0, ivec2 arg_1, int arg_2) {
}

uint textureNumLayers(texture2DArray arg_0) {
}

uint textureNumLayers(texture2DArray arg_0) {
}

uint textureNumLayers(texture2DArray arg_0) {
}

uint textureNumLevels(texture2D arg_0) {
}

uint textureNumLevels(texture2D arg_0) {
}

uint textureNumLevels(texture2D arg_0) {
}

uint textureNumSamples(texture2DMS arg_0) {
}

uint textureNumSamples(texture2DMS arg_0) {
}

uint textureNumSamples(texture2DMS arg_0) {
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSampleBias(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

vec4 textureSampleBias(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

vec4 textureSampleBias(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

vec4 textureSampleGrad(texture2D arg_0, sampler arg_1, vec2 arg_2, vec2 arg_3, vec2 arg_4) {
}

vec4 textureSampleGrad(texture2D arg_0, sampler arg_1, vec2 arg_2, vec2 arg_3, vec2 arg_4) {
}

vec4 textureSampleGrad(texture2D arg_0, sampler arg_1, vec2 arg_2, vec2 arg_3, vec2 arg_4) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

texture_storage_2d<rgba32float, write> textureStore(texture_storage_2d<rgba32float, write> arg_0, ivec2 arg_1, vec4 arg_2) {
}

texture_storage_2d<rgba32float, write> textureStore(texture_storage_2d<rgba32float, write> arg_0, ivec2 arg_1, vec4 arg_2) {
}

texture_storage_2d<rgba32float, write> textureStore(texture_storage_2d<rgba32float, write> arg_0, ivec2 arg_1, vec4 arg_2) {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_main(fragment_in);
}
