#version 450 core
precision highp float;
precision highp int;

struct Struct_5 {
    float[10] a;
    vec4 v;
    mat3x4 m;
    float[] d;
};
layout(set = 0, binding = 0) buffer Struct_5 global_0;

float index_vector_by_value(vec4 v, int i) {
    return v[i];
}

float index_array(int i) {
    return global_0.a[i];
}

float index_dynamic_array(int i) {
    return global_0.d[i];
}

float index_vector(int i) {
    return global_0.v[i];
}

vec4 index_matrix(int i) {
    return global_0.m[i];
}

float index_twice(int i, int j) {
    return global_0.m[i][j];
}

float index_expensive(int i) {
    return global_0.a[int((sin((float(i) / 100.0f)) * 100.0f))];
}

void sin() {
}

float index_in_bounds() {
    return ((global_0.a[9] + global_0.v[3]) + global_0.m[2][3]);
}

void set_array(int i, float v) {
    global_0.a[i] = v;
}

void set_dynamic_array(int i, float v) {
    global_0.d[i] = v;
}

void set_vector(int i, float v) {
    global_0.v[i] = v;
}

void set_matrix(int i, vec4 v) {
    global_0.m[i] = v;
}

void set_index_twice(int i, int j, float v) {
    global_0.m[i][j] = v;
}

void set_expensive(int i, float v) {
    global_0.a[int((sin((float(i) / 100.0f)) * 100.0f))] = v;
}

void sin() {
}

void set_in_bounds(float v) {
    global_0.a[9] = v;
    global_0.v[3] = v;
    global_0.m[2][3] = v;
}

float index_dynamic_array_constant_index() {
    return global_0.d[1000];
}

void set_dynamic_array_constant_index(float v) {
    global_0.d[1000] = v;
}

void wgsl_main() {
}

void main() {
    wgsl_main();
}
