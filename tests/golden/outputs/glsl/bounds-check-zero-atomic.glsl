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
    return atomicAdd(global_0.a, 1u);
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

uint fetch_add_atomic_static_sized_array(int i) {
    return atomicAdd(global_0.b[i], 1u);
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

uint fetch_add_atomic_dynamic_sized_array(int i) {
    return atomicAdd(global_0.c[i], 1u);
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

uint exchange_atomic() {
    return atomicExchange(global_0.a, 1u);
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

uint exchange_atomic_static_sized_array(int i) {
    return atomicExchange(global_0.b[i], 1u);
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

uint exchange_atomic_dynamic_sized_array(int i) {
    return atomicExchange(global_0.c[i], 1u);
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

uint fetch_add_atomic_dynamic_sized_array_static_index() {
    return atomicAdd(global_0.c[1000], 1u);
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

uint exchange_atomic_dynamic_sized_array_static_index() {
    return atomicExchange(global_0.c[1000], 1u);
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

void wgsl_main() {
    fetch_add_atomic();
    fetch_add_atomic_static_sized_array(1);
    fetch_add_atomic_dynamic_sized_array(1);
    exchange_atomic();
    exchange_atomic_static_sized_array(1);
    exchange_atomic_dynamic_sized_array(1);
    fetch_add_atomic_dynamic_sized_array_static_index();
    exchange_atomic_dynamic_sized_array_static_index();
}

void main() {
    wgsl_main();
}
