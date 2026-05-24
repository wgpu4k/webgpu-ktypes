#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    uvec3 local_invocation_id;
    uint local_invocation_index;
};
shared uint global_0;

void wgsl_compute1(Struct_2 input) {
    global_0 = (input.local_invocation_index * 2);
    global_0 = input.local_invocation_id[0];
}

void main() {
    wgsl_compute1(input);
}
