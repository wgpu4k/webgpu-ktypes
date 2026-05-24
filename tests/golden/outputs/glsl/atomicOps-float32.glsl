#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    float atomic_scalar;
    float[2] atomic_arr;
};
layout(set = 0, binding = 0) buffer float global_0;
layout(set = 0, binding = 1) buffer float[2] global_1;
layout(set = 0, binding = 2) buffer Struct_2 global_2;

void wgsl_cs_main(uvec3 id) {
    float l0 = atomicLoad(global_0);
    float l1 = atomicLoad(global_1[1]);
    float l2 = atomicLoad(global_2.atomic_scalar);
    float l3 = atomicLoad(global_2.atomic_arr[1]);
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void workgroupBarrier() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void workgroupBarrier() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void workgroupBarrier() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void main() {
    wgsl_cs_main(id);
}
