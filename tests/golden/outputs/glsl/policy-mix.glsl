#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec4[10] a;
};
struct Struct_5 {
    vec4[20] a;
};
layout(set = 0, binding = 2) uniform texture2DArray global_0;
shared float[30] global_1;
 float[40] global_2;
layout(set = 0, binding = 0) buffer Struct_3 global_3;
layout(set = 0, binding = 1) uniform Struct_5 global_4;

vec4 mock_function(ivec2 c, int i, int l) {
    vec4[2] in_function = vec4[2](vec4(0.707f, 0.0f, 0.0f, 1.0f), vec4(0.0f, 0.707f, 0.0f, 1.0f));
    return (((((global_3.a[i] + global_4.a[i]) + textureLoad(global_0, c, i, l)) + global_1[i]) + global_2[i]) + in_function[i]);
}

void textureLoad() {
}

void wgsl_main() {
}

void main() {
    wgsl_main();
}
