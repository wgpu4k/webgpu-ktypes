#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture_storage_2d<r32float, read> global_0;
layout(set = 0, binding = 1) uniform texture_storage_2d<rg32float, read> global_1;
layout(set = 0, binding = 2) uniform texture_storage_2d<rgba32float, read> global_2;
layout(set = 1, binding = 0) uniform texture_storage_2d<r32float, write> global_3;
layout(set = 1, binding = 1) uniform texture_storage_2d<rg32float, write> global_4;
layout(set = 1, binding = 2) uniform texture_storage_2d<rgba32float, write> global_5;

void wgsl_csLoad() {
    textureLoad(global_0, uvec2(0));
    textureLoad(global_1, uvec2(0));
    textureLoad(global_2, uvec2(0));
}

texture_storage_2d<r32float, read> textureLoad(texture_storage_2d<r32float, read> arg_0, uvec2 arg_1) {
}

texture_storage_2d<rg32float, read> textureLoad(texture_storage_2d<rg32float, read> arg_0, uvec2 arg_1) {
}

texture_storage_2d<rgba32float, read> textureLoad(texture_storage_2d<rgba32float, read> arg_0, uvec2 arg_1) {
}

void wgsl_csStore() {
    textureStore(global_3, uvec2(0), vec4(0.0f));
    textureStore(global_4, uvec2(0), vec4(0.0f));
    textureStore(global_5, uvec2(0), vec4(0.0f));
}

texture_storage_2d<r32float, write> textureStore(texture_storage_2d<r32float, write> arg_0, uvec2 arg_1, vec4 arg_2) {
}

texture_storage_2d<rg32float, write> textureStore(texture_storage_2d<rg32float, write> arg_0, uvec2 arg_1, vec4 arg_2) {
}

texture_storage_2d<rgba32float, write> textureStore(texture_storage_2d<rgba32float, write> arg_0, uvec2 arg_1, vec4 arg_2) {
}

void main() {
    wgsl_csLoad();
}

void main() {
    wgsl_csStore();
}
