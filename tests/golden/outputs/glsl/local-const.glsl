#version 450 core
precision highp float;
precision highp int;

 int global_0 = 4;
 int global_1 = 4;
 uint global_2 = 4;
 float global_3 = 4;
 float global_4 = 2.0f;
 vec3 global_5 = vec3(global_0, global_0, global_0);

void const_in_fn() {
    int a = 4;
    int b = 4;
    uint c = 4;
    float d = 4;
    vec3 e = vec3(a, a, a);
    float f = 2.0f;
    int ag = global_0;
    int bg = global_1;
    uint cg = global_2;
    float dg = global_3;
    vec3 eg = global_5;
    float fg = global_4;
}

void wgsl_main() {
    const_in_fn();
}

void main() {
    wgsl_main();
}
