#include <metal_stdlib>
using namespace metal;

void takes_ptr(thread int* p) {
}

void takes_array_ptr(thread /* unknown type */ void* p) {
}

void takes_vec_ptr(thread int2* p) {
}

void takes_mat_ptr(thread float2x2* p) {
}

void argument(thread /* unknown type */ void* v, uint i) {
}

void argument_nested_x3(thread /* unknown type */ void* v, uint i, uint j) {
}

void index_from_self(thread /* unknown type */ void* v, uint i) {
}

void local_var_from_arg(/* unknown type */ void a, uint i) {
    /* unknown type */ void local_0 = a;
}

void let_binding(thread /* unknown type */ void* a, uint i) {
    /* unknown type */ void local_0 = &a[i];
    /* unknown type */ void local_1 = &a[0];
}

void local_var(uint i) {
    /* unknown type */ void local_0 = /* unknown type */ void(1, 2, 3, 4);
}

void argument_nested_x2(thread /* unknown type */ void* v, uint i, uint j) {
}

void mat_vec_ptrs(thread /* unknown type */ void* pv, thread /* unknown type */ void* pm, uint i) {
}

[[kernel]]
void main() {
}
