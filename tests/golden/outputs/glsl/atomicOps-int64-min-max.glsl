#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    uint64_t atomic_scalar;
    uint64_t[2] atomic_arr;
};
layout(set = 0, binding = 0) buffer uint64_t global_0;
layout(set = 0, binding = 1) buffer uint64_t[2] global_1;
layout(set = 0, binding = 3) uniform uint64_t global_2;
layout(set = 0, binding = 2) buffer Struct_2 global_3;

void wgsl_cs_main(uvec3 id) {
    atomicMax(global_0, global_2);
    atomicMax(global_1[1], (1 + global_2));
    atomicMax(global_3.atomic_scalar, 1u);
    atomicMax(global_3.atomic_arr[1], uint64_t(id[0]));
    workgroupBarrier();
    atomicMin(global_0, global_2);
    atomicMin(global_1[1], (1 + global_2));
    atomicMin(global_3.atomic_scalar, 1u);
    atomicMin(global_3.atomic_arr[1], uint64_t(id[0]));
}

uint64_t atomicMax(uint64_t arg_0, uint64_t arg_1) {
}

uint64_t atomicMax(uint64_t arg_0, int arg_1) {
}

uint64_t atomicMax(uint64_t arg_0, uint64_t arg_1) {
}

uint64_t atomicMax(uint64_t arg_0, uint64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicMin(uint64_t arg_0, uint64_t arg_1) {
}

uint64_t atomicMin(uint64_t arg_0, int arg_1) {
}

uint64_t atomicMin(uint64_t arg_0, uint64_t arg_1) {
}

uint64_t atomicMin(uint64_t arg_0, uint64_t arg_1) {
}

void main() {
    wgsl_cs_main(id);
}
