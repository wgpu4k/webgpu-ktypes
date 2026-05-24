#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec3 v3;
    float f3;
};
struct Struct_3 {
    float f3_forces_padding;
    vec3 v3_needs_padding;
    float f3;
};
layout(set = 0, binding = 0) uniform Struct_2 global_0;
layout(set = 0, binding = 1) buffer Struct_2 global_1;
layout(set = 0, binding = 2) uniform Struct_3 global_2;
layout(set = 0, binding = 3) buffer Struct_3 global_3;

vec4 wgsl_no_padding_frag(Struct_2 input) {
    input;
    return vec4(0.0f);
}

vec4 wgsl_no_padding_vert(Struct_2 input) {
    input;
    return vec4(0.0f);
}

vec4 wgsl_needs_padding_frag(Struct_3 input) {
    input;
    return vec4(0.0f);
}

vec4 wgsl_needs_padding_vert(Struct_3 input) {
    input;
    return vec4(0.0f);
}

void wgsl_no_padding_comp() {
    Struct_2 x;
    x = global_0;
    x = global_1;
}

void wgsl_needs_padding_comp() {
    Struct_3 x;
    x = global_2;
    x = global_3;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_no_padding_frag(input);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_no_padding_vert(input);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_needs_padding_frag(input);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_needs_padding_vert(input);
}

void main() {
    wgsl_no_padding_comp();
}

void main() {
    wgsl_needs_padding_comp();
}
