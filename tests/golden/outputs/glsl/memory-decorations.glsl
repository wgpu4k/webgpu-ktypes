#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    uint[] values;
};
layout(set = 0, binding = 0) buffer Struct_2 global_0;
layout(set = 0, binding = 1) buffer Struct_2 global_1;
layout(set = 0, binding = 2) buffer Struct_2 global_2;
layout(set = 0, binding = 3) buffer Struct_2 global_3;

void wgsl_main() {
    global_0.values[0] = global_1.values[0];
    global_2.values[0] = global_3.values[0];
}

void main() {
    wgsl_main();
}
