#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture2D global_0;
layout(set = 0, binding = 1) uniform sampler global_1;
layout(set = 0, binding = 2) uniform texture2D global_2;
layout(set = 0, binding = 3) uniform sampler_comparison global_3;
layout(set = 0, binding = 4) uniform texture_storage_2d<rgba8unorm, read_write> global_4;

void color() {
    textureSample(global_0, global_1, vec2(1, 2));
    textureSample(global_0, global_1, vec2(1, 2), vec2(3, 4));
    textureSampleLevel(global_0, global_1, vec2(1, 2), 0);
    textureSampleLevel(global_0, global_1, vec2(1, 2), 0.0f);
    textureSampleGrad(global_0, global_1, vec2(1, 2), vec2(3, 4), vec2(5, 6));
    textureSampleBias(global_0, global_1, vec2(1, 2), 1);
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2, vec2 arg_3) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, int arg_3) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

vec4 textureSampleGrad(texture2D arg_0, sampler arg_1, vec2 arg_2, vec2 arg_3, vec2 arg_4) {
}

vec4 textureSampleBias(texture2D arg_0, sampler arg_1, vec2 arg_2, int arg_3) {
}

void depth() {
    textureSampleLevel(global_2, global_1, vec2(1, 2), 1);
    textureSampleCompare(global_2, global_3, vec2(1, 2), 0);
    textureGatherCompare(global_2, global_3, vec2(1, 2), 0);
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, int arg_3) {
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, int arg_3) {
}

texture2D textureGatherCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, int arg_3) {
}

void storage() {
    textureStore(global_4, vec2(0, 1), vec4(2, 3, 4, 5));
}

texture_storage_2d<rgba8unorm, read_write> textureStore(texture_storage_2d<rgba8unorm, read_write> arg_0, vec2 arg_1, vec4 arg_2) {
}

void wgsl_main() {
    color();
    depth();
    storage();
}

void main() {
    wgsl_main();
}
