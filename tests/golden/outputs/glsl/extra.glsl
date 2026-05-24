#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    uint index;
    vec2 double;
};
struct Struct_5 {
    vec4 color;
    uint primitive_index;
};
 Struct_3 global_0;

vec4 wgsl_main(Struct_5 in) {
    if ((in.primitive_index == global_0.index)) {
        return in.color;
    } else {
        return vec4((vec3(1.0f) - in.color.xyz), in.color[3]);
    }
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_main(in);
}
