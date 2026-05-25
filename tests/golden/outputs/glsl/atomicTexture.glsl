#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture_storage_2d<r32uint> global_0;
layout(set = 0, binding = 1) uniform texture_storage_2d<r32sint> global_1;

void wgsl_cs_main(uvec3 id) {
    textureAtomicMax(global_0, ivec2(0, 0), 1u);
    textureAtomicMin(global_0, ivec2(0, 0), 1u);
    textureAtomicAdd(global_0, ivec2(0, 0), 1u);
    textureAtomicAnd(global_0, ivec2(0, 0), 1u);
    textureAtomicOr(global_0, ivec2(0, 0), 1u);
    textureAtomicXor(global_0, ivec2(0, 0), 1u);
    textureAtomicMax(global_1, ivec2(0, 0), 1);
    textureAtomicMin(global_1, ivec2(0, 0), 1);
    textureAtomicAdd(global_1, ivec2(0, 0), 1);
    textureAtomicAnd(global_1, ivec2(0, 0), 1);
    textureAtomicOr(global_1, ivec2(0, 0), 1);
    textureAtomicXor(global_1, ivec2(0, 0), 1);
}

texture_storage_2d<r32uint> textureAtomicMax(texture_storage_2d<r32uint> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_storage_2d<r32uint> textureAtomicMin(texture_storage_2d<r32uint> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_storage_2d<r32uint> textureAtomicAdd(texture_storage_2d<r32uint> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_storage_2d<r32uint> textureAtomicAnd(texture_storage_2d<r32uint> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_storage_2d<r32uint> textureAtomicOr(texture_storage_2d<r32uint> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_storage_2d<r32uint> textureAtomicXor(texture_storage_2d<r32uint> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_storage_2d<r32sint> textureAtomicMax(texture_storage_2d<r32sint> arg_0, ivec2 arg_1, int arg_2) {
}

texture_storage_2d<r32sint> textureAtomicMin(texture_storage_2d<r32sint> arg_0, ivec2 arg_1, int arg_2) {
}

texture_storage_2d<r32sint> textureAtomicAdd(texture_storage_2d<r32sint> arg_0, ivec2 arg_1, int arg_2) {
}

texture_storage_2d<r32sint> textureAtomicAnd(texture_storage_2d<r32sint> arg_0, ivec2 arg_1, int arg_2) {
}

texture_storage_2d<r32sint> textureAtomicOr(texture_storage_2d<r32sint> arg_0, ivec2 arg_1, int arg_2) {
}

texture_storage_2d<r32sint> textureAtomicXor(texture_storage_2d<r32sint> arg_0, ivec2 arg_1, int arg_2) {
}

void main() {
    wgsl_cs_main(id);
}
