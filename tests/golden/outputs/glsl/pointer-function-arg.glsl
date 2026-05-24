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
}

void argument_nested_x3(int[4][4][4] v, uint i, uint j) {
}

void index_from_self(int[4] v, uint i) {
}

void local_var_from_arg(int[4] a, uint i) {
    int[4] b = a;
}

void let_binding(int[4] a, uint i) {
    int p0 = a[i];
    int p1 = a[0];
}

void local_var(uint i) {
    int[4] arr = int[4](1, 2, 3, 4);
}

void argument_nested_x2(int[4][4] v, uint i, uint j) {
}

void mat_vec_ptrs(ivec2[4] pv, mat2x2[4] pm, uint i) {
}

void wgsl_main() {
    ivec2[4] vec;
    mat2x2[4] mat;
    int[4] arr1d;
    int[4][4] arr2d;
    int[4][4][4] arr3d;
}

void main() {
    wgsl_main();
}
