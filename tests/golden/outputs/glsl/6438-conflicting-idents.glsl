#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec4 position;
    vec2 texcoord;
};

vec4 wgsl_fs() {
    return vec4(1.0f, 0.0f, 0.0f, 1.0f);
}

Struct_3 wgsl_vs(vec2 xy) {
    Struct_3 vsOutput;
    vsOutput.position = vec4(xy, 0.0f, 1.0f);
    return vsOutput;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs();
}

layout(location = 0) in vec2 xy;
layout(location = 0) out vec2 out_texcoord;
void main() {
    Struct_3 res = wgsl_vs(xy);
    gl_Position = res.position;
    out_texcoord = res.texcoord;
}
