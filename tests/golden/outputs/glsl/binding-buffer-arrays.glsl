#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    uint index;
};
struct Struct_4 {
    uint x;
    int[] far;
};
struct Struct_5 {
    uint index;
};
layout(set = 0, binding = 10) uniform Struct_1 global_0;
layout(set = 0, binding = 0) buffer Struct_4[1] global_1;

uint wgsl_main(Struct_5 fragment_in) {
    uint uniform_index = global_0.index;
    uint non_uniform_index = fragment_in.index;
    uint u1 = 0u;
    u1 = global_1[0].x;
    u1 = global_1[uniform_index].x;
    u1 = global_1[non_uniform_index].x;
    u1 = arrayLength(global_1[0].far);
    u1 = arrayLength(global_1[uniform_index].far);
    u1 = arrayLength(global_1[non_uniform_index].far);
    return u1;
}

int[] arrayLength(int[] arg_0) {
}

int[] arrayLength(int[] arg_0) {
}

int[] arrayLength(int[] arg_0) {
}

layout(location = 0) out uint outColor;
void main() {
    outColor = wgsl_main(fragment_in);
}
