#version 450 core
precision highp float;
precision highp int;


void wgsl_main() {
    vec3 a = cross(vec3(1.0f, 0.0f, 0.0f), vec3(0.0f, 1.0f, 0.0f));
}

void cross() {
}

void main() {
    wgsl_main();
}
