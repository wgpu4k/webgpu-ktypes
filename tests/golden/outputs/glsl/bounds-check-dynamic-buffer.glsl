#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    uint t;
};
layout(set = 0, binding = 0) buffer uint global_0;
layout(set = 0, binding = 1) buffer uint[] global_1;
layout(set = 0, binding = 2) uniform Struct_1[1] global_2;
layout(set = 0, binding = 3) buffer Struct_1[1] global_3;
layout(set = 0, binding = 4) buffer Struct_1[1] global_4;
layout(set = 1, binding = 0) buffer Struct_1[1] global_5;

void wgsl_main() {
    uint i = global_0;
    global_1[0] = global_2[i].t;
    global_1[1] = global_3[i].t;
    global_1[2] = global_4[i].t;
    global_1[3] = global_5[i].t;
}

void main() {
    wgsl_main();
}
