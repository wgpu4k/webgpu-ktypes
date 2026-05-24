#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    uint a;
    uint[10] b;
    uint[] c;
};
layout(set = 0, binding = 0) buffer Struct_3 global_0;

uint fetch_add_atomic() {
    return atomicAdd(global_0.a, 0u);
}

void atomicAdd() {
}

uint fetch_add_atomic_static_sized_array(int i) {
    return atomicAdd(global_0.b[i], 0u);
}

void atomicAdd() {
}

uint fetch_add_atomic_dynamic_sized_array(int i) {
    return atomicAdd(global_0.c[i], 0u);
}

void atomicAdd() {
}

uint exchange_atomic() {
    return atomicExchange(global_0.a, 0u);
}

void atomicExchange() {
}

uint exchange_atomic_static_sized_array(int i) {
    return atomicExchange(global_0.b[i], 0u);
}

void atomicExchange() {
}

uint exchange_atomic_dynamic_sized_array(int i) {
    return atomicExchange(global_0.c[i], 0u);
}

void atomicExchange() {
}

uint fetch_add_atomic_dynamic_sized_array_static_index() {
    return atomicAdd(global_0.c[1000], 0u);
}

void atomicAdd() {
}

uint exchange_atomic_dynamic_sized_array_static_index() {
    return atomicExchange(global_0.c[1000], 0u);
}

void atomicExchange() {
}

void wgsl_main() {
}

void main() {
    wgsl_main();
}
