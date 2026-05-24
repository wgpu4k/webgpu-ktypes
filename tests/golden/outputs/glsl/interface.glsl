#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec4 position;
    float _varying;
};
struct Struct_4 {
    float depth;
    uint sample_mask;
    float color;
};
struct Struct_5 {
    uint index;
};
shared void global_0;

vec4 wgsl_vertex_two_structs(Struct_5 in1, Struct_5 in2) {
    uint index = 0u;
    return vec4(float(in1.index), float(in2.index), float(index), 0.0f);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_vertex_two_structs(in1, in2);
}
