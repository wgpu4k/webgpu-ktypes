#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    uint draw_index;
};

vec4 wgsl_vertex(Struct_1 input) {
    return vec4(float(input.draw_index), 1.0f, 1.0f, 1.0f);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_vertex(input);
}
