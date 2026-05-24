#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    uint[] arr;
};
layout(set = 0, binding = 0) buffer Struct_2 global_0;

void f() {
    mat2x2 v;
    vec2 px = v[0];
    px = vec2(10.0f);
}

void index_unsized(int i, uint v) {
    Struct_2 p = global_0;
    uint val = p.arr[i];
    p.arr[i] = (val + v);
}

void index_dynamic_array(int i, uint v) {
    uint[] p = global_0.arr;
    uint val = p[i];
    p[i] = (val + v);
}

void wgsl_main() {
    f();
    index_unsized(1, 1);
    index_dynamic_array(1, 1);
}

void main() {
    wgsl_main();
}
