#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    uint a;
    array<uint, 10> b;
    array<uint> c;
};

uint fetch_add_atomic() {
    return atomicAdd(&global_0.a, 1u);
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

uint fetch_add_atomic_static_sized_array(int i) {
    return atomicAdd(&global_0.b[i], 1u);
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

uint fetch_add_atomic_dynamic_sized_array(int i) {
    return atomicAdd(&global_0.c[i], 1u);
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

uint exchange_atomic() {
    return atomicExchange(&global_0.a, 1u);
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

uint exchange_atomic_static_sized_array(int i) {
    return atomicExchange(&global_0.b[i], 1u);
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

uint exchange_atomic_dynamic_sized_array(int i) {
    return atomicExchange(&global_0.c[i], 1u);
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

uint fetch_add_atomic_dynamic_sized_array_static_index() {
    return atomicAdd(&global_0.c[1000], 1u);
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

uint exchange_atomic_dynamic_sized_array_static_index() {
    return atomicExchange(&global_0.c[1000], 1u);
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

[[kernel]]
void main(Struct_3 global_0 [[buffer(0)]]) {
    fetch_add_atomic();
    fetch_add_atomic_static_sized_array(1);
    fetch_add_atomic_dynamic_sized_array(1);
    exchange_atomic();
    exchange_atomic_static_sized_array(1);
    exchange_atomic_dynamic_sized_array(1);
    fetch_add_atomic_dynamic_sized_array_static_index();
    exchange_atomic_dynamic_sized_array_static_index();
}
