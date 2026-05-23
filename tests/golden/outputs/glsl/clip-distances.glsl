#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec4 position;
    void clip_distances;
};

Struct_3 main() {
    Struct_3 out;
    out.clip_distances[0] = 0.5f;
    return out;
}

void main() {
    Struct_3 res = main();
    gl_Position = res.position;
}
