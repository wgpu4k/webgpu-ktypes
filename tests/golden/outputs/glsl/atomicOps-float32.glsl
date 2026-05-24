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
    atomicStore(global_0, 1.5f);
    atomicStore(global_1[1], 1.5f);
    atomicStore(global_2.atomic_scalar, 1.5f);
    atomicStore(global_2.atomic_arr[1], 1.5f);
    workgroupBarrier();
    float l0 = atomicLoad(global_0);
    float l1 = atomicLoad(global_1[1]);
    float l2 = atomicLoad(global_2.atomic_scalar);
    float l3 = atomicLoad(global_2.atomic_arr[1]);
    workgroupBarrier();
    atomicAdd(global_0, 1.5f);
    atomicAdd(global_1[1], 1.5f);
    atomicAdd(global_2.atomic_scalar, 1.5f);
    atomicAdd(global_2.atomic_arr[1], 1.5f);
    workgroupBarrier();
    atomicExchange(global_0, 1.5f);
    atomicExchange(global_1[1], 1.5f);
    atomicExchange(global_2.atomic_scalar, 1.5f);
    atomicExchange(global_2.atomic_arr[1], 1.5f);
}

float atomicStore(float arg_0, float arg_1) {
}

float atomicStore(float arg_0, float arg_1) {
}

float atomicStore(float arg_0, float arg_1) {
}

float atomicStore(float arg_0, float arg_1) {
}

void workgroupBarrier() {
}

float atomicLoad(float arg_0) {
}

float atomicLoad(float arg_0) {
}

float atomicLoad(float arg_0) {
}

float atomicLoad(float arg_0) {
}

void workgroupBarrier() {
}

float atomicAdd(float arg_0, float arg_1) {
}

float atomicAdd(float arg_0, float arg_1) {
}

float atomicAdd(float arg_0, float arg_1) {
}

float atomicAdd(float arg_0, float arg_1) {
}

void workgroupBarrier() {
}

float atomicExchange(float arg_0, float arg_1) {
}

float atomicExchange(float arg_0, float arg_1) {
}

float atomicExchange(float arg_0, float arg_1) {
}

float atomicExchange(float arg_0, float arg_1) {
}

void main() {
    wgsl_cs_main(id);
}
