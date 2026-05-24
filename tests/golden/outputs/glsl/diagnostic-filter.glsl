#version 450 core
precision highp float;
precision highp int;


void thing() {
}

void with_diagnostic() {
}

void wgsl_main() {
    thing();
    with_diagnostic();
}

void main() {
    wgsl_main();
}
