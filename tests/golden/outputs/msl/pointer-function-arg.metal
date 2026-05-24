#include <metal_stdlib>
using namespace metal;

void takes_ptr(thread int* p) {
}

void takes_array_ptr(thread array<int, 4>* p) {
}

void takes_vec_ptr(thread int2* p) {
}

void takes_mat_ptr(thread float2x2* p) {
}

void argument(thread array<int, 4>* v, uint i) {
    takes_ptr(&v[i]);
}

void argument_nested_x3(thread array<array<array<int, 4>, 4>, 4>* v, uint i, uint j) {
    takes_ptr(&v[i][0][j]);
    takes_ptr(&v[i][j][0]);
    takes_ptr(&v[0][i][j]);
}

void index_from_self(thread array<int, 4>* v, uint i) {
    takes_ptr(&v[v[i]]);
}

void local_var_from_arg(array<int, 4> a, uint i) {
    array<int, 4> local_0 = a;
    takes_ptr(&local_0[i]);
}

void let_binding(thread array<int, 4>* a, uint i) {
    /* unknown type */ void local_0 = &a[i];
    takes_ptr(local_0);
    /* unknown type */ void local_1 = &a[0];
    takes_ptr(local_1);
}

void local_var(uint i) {
    array<int, 4> local_0 = array<int, 4>(1, 2, 3, 4);
    takes_ptr(&local_0[i]);
    takes_array_ptr(&local_0);
}

void argument_nested_x2(thread array<array<int, 4>, 4>* v, uint i, uint j) {
    takes_ptr(&v[i][j]);
    takes_ptr(&v[i][0]);
    takes_ptr(&v[0][j]);
    takes_array_ptr(&v[i]);
}

void mat_vec_ptrs(thread array<int2, 4>* pv, thread array<float2x2, 4>* pm, uint i) {
    takes_vec_ptr(&pv[i]);
    takes_mat_ptr(&pm[i]);
}

[[kernel]]
void main() {
    array<int2, 4> local_0;
    array<float2x2, 4> local_1;
    array<int, 4> local_2;
    array<array<int, 4>, 4> local_3;
    array<array<array<int, 4>, 4>, 4> local_4;
    local_var(1);
    mat_vec_ptrs(&local_0, &local_1, 1);
    argument(&local_2, 1);
    argument_nested_x2(&local_3, 1, 2);
    argument_nested_x3(&local_4, 1, 2);
    index_from_self(&local_2, 1);
    local_var_from_arg(array<int, 4>(1, 2, 3, 4), 5);
    let_binding(&local_2, 1);
}
