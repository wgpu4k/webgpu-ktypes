#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    void inner;
};
layout(set = 0, binding = 0) buffer Struct_2 global_0;

void wgsl_cs_main() {
    Struct_2 ah = global_0;
}

void main() {
    wgsl_cs_main();
}
