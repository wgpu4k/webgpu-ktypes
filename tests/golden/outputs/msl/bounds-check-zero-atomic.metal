#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    uint a;
    /* unknown type */ void b;
    /* unknown type */ void c;
};

uint fetch_add_atomic() {
    return atomicAdd(&global_0.a, 0u);
}

void atomicAdd() {
}

uint fetch_add_atomic_static_sized_array(int i) {
    return atomicAdd(&global_0.b[i], 0u);
}

void atomicAdd() {
}

uint fetch_add_atomic_dynamic_sized_array(int i) {
    return atomicAdd(&global_0.c[i], 0u);
}

void atomicAdd() {
}

uint exchange_atomic() {
    return atomicExchange(&global_0.a, 0u);
}

void atomicExchange() {
}

uint exchange_atomic_static_sized_array(int i) {
    return atomicExchange(&global_0.b[i], 0u);
}

void atomicExchange() {
}

uint exchange_atomic_dynamic_sized_array(int i) {
    return atomicExchange(&global_0.c[i], 0u);
}

void atomicExchange() {
}

uint fetch_add_atomic_dynamic_sized_array_static_index() {
    return atomicAdd(&global_0.c[1000], 0u);
}

void atomicAdd() {
}

uint exchange_atomic_dynamic_sized_array_static_index() {
    return atomicExchange(&global_0.c[1000], 0u);
}

void atomicExchange() {
}

[[kernel]]
void main(Struct_3 global_0 [[buffer(0)]]) {
}
