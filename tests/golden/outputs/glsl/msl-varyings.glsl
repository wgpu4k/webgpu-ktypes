#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec2 position;
};
struct Struct_3 {
    vec2 position;
};
struct Struct_5 {
    vec4 position;
};

Struct_5 wgsl_vs_main(Struct_2 vertex, Struct_3 note) {
    Struct_5 out;
    return out;
}

vec4 wgsl_fs_main(Struct_5 in, Struct_3 note) {
    vec3 position = vec3(1.0f);
    return in.position;
}

void main() {
    Struct_5 res = wgsl_vs_main(vertex, note);
    gl_Position = res.position;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(in, note);
}
