#version 450 core
precision highp float;
precision highp int;


void takes_ptr(int p) {
}

void takes_array_ptr(int[4] p) {
}

void takes_vec_ptr(ivec2 p) {
}

void takes_mat_ptr(mat2x2 p) {
}

void argument(int[4] v, uint i) {
    takes_ptr(v[i]);
}

void argument_nested_x3(int[4][4][4] v, uint i, uint j) {
    takes_ptr(v[i][0][j]);
    takes_ptr(v[i][j][0]);
    takes_ptr(v[0][i][j]);
}

void index_from_self(int[4] v, uint i) {
    takes_ptr(v[v[i]]);
}

void local_var_from_arg(int[4] a, uint i) {
    int[4] b = a;
    takes_ptr(b[i]);
}

void let_binding(int[4] a, uint i) {
    int p0 = a[i];
    takes_ptr(p0);
    int p1 = a[0];
    takes_ptr(p1);
}

void local_var(uint i) {
    int[4] arr = int[4](1, 2, 3, 4);
    takes_ptr(arr[i]);
    takes_array_ptr(arr);
}

void argument_nested_x2(int[4][4] v, uint i, uint j) {
    takes_ptr(v[i][j]);
    takes_ptr(v[i][0]);
    takes_ptr(v[0][j]);
    takes_array_ptr(v[i]);
}

void mat_vec_ptrs(ivec2[4] pv, mat2x2[4] pm, uint i) {
    takes_vec_ptr(pv[i]);
    takes_mat_ptr(pm[i]);
}

void wgsl_main() {
    ivec2[4] vec;
    mat2x2[4] mat;
    int[4] arr1d;
    int[4][4] arr2d;
    int[4][4][4] arr3d;
    local_var(1);
    mat_vec_ptrs(vec, mat, 1);
    argument(arr1d, 1);
    argument_nested_x2(arr2d, 1, 2);
    argument_nested_x3(arr3d, 1, 2);
    index_from_self(arr1d, 1);
    local_var_from_arg(int[4](1, 2, 3, 4), 5);
    let_binding(arr1d, 1);
}

void main() {
    wgsl_main();
}
