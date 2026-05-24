#version 450 core
precision highp float;
precision highp int;


void wgsl_main() {
    int idx = 2;
    unpack4xI8(12u)[idx];
    unpack4xU8(12u)[1];
}

uint unpack4xI8(uint arg_0) {
}

uint unpack4xU8(uint arg_0) {
}

void main() {
    wgsl_main();
}
