#version 450 core
precision highp float;
precision highp int;


void wgsl_main() {
    vec2 x0 = vec2(1, 2);
    vec2 i1 = select(vec2(1.0f, 0.0f), vec2(0.0f, 1.0f), (x0[0] < x0[1]));
}

void select() {
}

void select() {
}

void main() {
    wgsl_main();
}
