#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    uint[] data;
};
layout(set = 0, binding = 0) buffer Struct_2 global_0;

uint collatz_iterations(uint n_base) {
    uint n = n_base;
    uint i = 0u;
    while (true) {
        if ((n > 1u)) {
            if ((mod(n, 2u) == 0u)) {
                n = (n / 2u);
            } else {
                n = ((3u * n) + 1u);
            }
            i = (i + 1u);
        } else {
            break;
        }
    }
    return i;
}

void wgsl_main(uvec3 global_id) {
    global_0.data[global_id[0]] = collatz_iterations(global_0.data[global_id[0]]);
}

void main() {
    wgsl_main(global_id);
}
