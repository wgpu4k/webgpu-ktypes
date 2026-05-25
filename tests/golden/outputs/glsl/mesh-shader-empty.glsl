#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    uint dummy;
};
struct Struct_4 {
    vec4 position;
};
struct Struct_6 {
    uvec3 indices;
};
struct Struct_9 {
    Struct_4[3] vertices;
    Struct_6[1] primitives;
    uint vertex_count;
    uint primitive_count;
};
 Struct_1 global_0;
shared Struct_9 global_1;

uvec3 ts_main() {
    return vec3(1, 1, 1);
}

void ms_main() {
}
