#version 450 core
precision highp float;
precision highp int;

shared uint[1] global_0;
shared uint[1] global_1;
layout(set = 0, binding = 0) buffer uint[] global_2;
layout(set = 0, binding = 1) buffer uint[] global_3;

void wgsl_main() {
    global_0[0] = global_2[0];
    global_1[0] = global_3[0];
    global_3[0] = (global_0[0] + global_1[0]);
}

void main() {
    wgsl_main();
}
