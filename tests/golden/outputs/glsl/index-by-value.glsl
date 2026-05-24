#version 450 core
precision highp float;
precision highp int;


int index_arg_array(int[5] a, int i) {
    return a[i];
}

int index_let_array(int i, int j) {
    int[2][2] a = int[2][2](int[2](1, 2), int[2](3, 4));
    return a[i][j];
}

float index_let_matrix(int i, int j) {
    mat2x2 a = mat2x2(1, 2, 3, 4);
    return a[i][j];
}

vec4 index_let_array_1d(uint vi) {
    int[5] arr = int[5](1, 2, 3, 4, 5);
    int value = arr[vi];
    return vec4(ivec4(value));
}

vec4 wgsl_main(uint vi) {
    index_arg_array(int[5](1, 2, 3, 4, 5), 6);
    index_let_array(1, 2);
    index_let_matrix(1, 2);
    return index_let_array_1d(vi);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_main(vi);
}
