#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    ivec3 chunk;
    uint texture_index;
};
struct Struct_6 {
    vec4 clip_position;
};

Struct_6 wgsl_vs_main(Struct_3 in) {
    Struct_6 out;
    vec3 position = vec3((in.chunk - ivec3(5)));
    return out;
}

void main() {
    Struct_6 res = wgsl_vs_main(in);
    gl_Position = res.clip_position;
}
