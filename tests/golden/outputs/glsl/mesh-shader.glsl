#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec4 colorMask;
    bool visible;
};
struct Struct_4 {
    vec4 position;
    vec4 color;
};
struct Struct_7 {
    uvec3 indices;
    bool cull;
    vec4 colorMask;
};
struct Struct_8 {
    vec4 colorMask;
};
struct Struct_11 {
    Struct_4[3] vertices;
    Struct_7[1] primitives;
    uint vertex_count;
    uint primitive_count;
};
 vec4[3] global_0 = vec4[3](vec4(0.0f, 1.0f, 0.0f, 1.0f), vec4(-(1.0f), -(1.0f), 0.0f, 1.0f), vec4(1.0f, -(1.0f), 0.0f, 1.0f));
 vec4[3] global_1 = vec4[3](vec4(0.0f, 1.0f, 0.0f, 1.0f), vec4(0.0f, 0.0f, 1.0f, 1.0f), vec4(1.0f, 0.0f, 0.0f, 1.0f));
shared float global_2;
 Struct_3 global_3;
shared Struct_11 global_4;

vec4 wgsl_fs_main(Struct_4 vertex, Struct_8 primitive) {
    return (vertex.color * primitive.colorMask);
}

bool helper_reader() {
    return global_3.visible;
}

void helper_writer(bool value) {
    global_3.visible = value;
}

uvec3 ts_divergent(uvec3 thread_id) {
    if ((thread_id[0] == 0)) {
        global_3.colorMask = vec4(1.0f, 1.0f, 0.0f, 1.0f);
        global_3.visible = true;
        return vec3(1, 1, 1);
    }
    return vec3(2, 2, 2);
}

uvec3 ts_main() {
    global_2 = 1.0f;
    global_3.colorMask = vec4(1.0f, 1.0f, 0.0f, 1.0f);
    helper_writer(true);
    global_3.visible = helper_reader();
    return vec3(1, 1, 1);
}

void ms_main() {
    global_4.vertex_count = 3;
    global_4.primitive_count = 1;
    global_2 = 2.0f;
    global_4.vertices[0].position = global_0[0];
    global_4.vertices[0].color = (global_1[0] * global_3.colorMask);
    global_4.vertices[1].position = global_0[1];
    global_4.vertices[1].color = (global_1[1] * global_3.colorMask);
    global_4.vertices[2].position = global_0[2];
    global_4.vertices[2].color = (global_1[2] * global_3.colorMask);
    global_4.primitives[0].indices = uvec3(0, 1, 2);
    global_4.primitives[0].cull = !(helper_reader());
    global_4.primitives[0].colorMask = vec4(1.0f, 0.0f, 1.0f, 1.0f);
}

void ms_no_ts() {
    global_4.vertex_count = 3;
    global_4.primitive_count = 1;
    global_2 = 2.0f;
    global_4.vertices[0].position = global_0[0];
    global_4.vertices[0].color = global_1[0];
    global_4.vertices[1].position = global_0[1];
    global_4.vertices[1].color = global_1[1];
    global_4.vertices[2].position = global_0[2];
    global_4.vertices[2].color = global_1[2];
    global_4.primitives[0].indices = uvec3(0, 1, 2);
    global_4.primitives[0].cull = false;
    global_4.primitives[0].colorMask = vec4(1.0f, 0.0f, 1.0f, 1.0f);
}

void ms_divergent(uvec3 thread_id) {
    if ((thread_id[0] == 0)) {
        global_4.vertex_count = 3;
        global_4.primitive_count = 1;
        global_2 = 2.0f;
        global_4.vertices[0].position = global_0[0];
        global_4.vertices[0].color = global_1[0];
        global_4.vertices[1].position = global_0[1];
        global_4.vertices[1].color = global_1[1];
        global_4.vertices[2].position = global_0[2];
        global_4.vertices[2].color = global_1[2];
        global_4.primitives[0].indices = uvec3(0, 1, 2);
        global_4.primitives[0].cull = false;
        global_4.primitives[0].colorMask = vec4(1.0f, 0.0f, 1.0f, 1.0f);
        return;
    }
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(vertex, primitive);
}
