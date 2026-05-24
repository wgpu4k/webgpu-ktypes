#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec3 a;
};
struct Struct_4 {
    vec3[2] a;
    float b;
};
struct Struct_6 {
    mat4x3 a;
    float b;
};
struct Struct_7 {
    Struct_2 a;
    float b;
};
layout(set = 0, binding = 1) uniform Struct_4 global_0;
layout(set = 0, binding = 2) uniform Struct_6 global_1;
layout(set = 0, binding = 0) uniform Struct_7 global_2;

vec4 wgsl_vertex() {
    return (((vec4(1.0f) * global_2.b) * global_0.b) * global_1.b);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_vertex();
}
