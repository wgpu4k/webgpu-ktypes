#version 450 core
#extension GL_EXT_shader_explicit_arithmetic_types_int64 : require
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture_storage_2d<r64uint> global_0;

void wgsl_cs_main(uvec3 id) {
    textureAtomicMax(global_0, ivec2(0, 0), 1u);
    workgroupBarrier();
    textureAtomicMin(global_0, ivec2(0, 0), 1u);
}

texture_storage_2d<r64uint> textureAtomicMax(texture_storage_2d<r64uint> arg_0, ivec2 arg_1, uint64_t arg_2) {
}

void workgroupBarrier() {
}

texture_storage_2d<r64uint> textureAtomicMin(texture_storage_2d<r64uint> arg_0, ivec2 arg_1, uint64_t arg_2) {
}

void main() {
    wgsl_cs_main(id);
}
