#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) buffer float global_0;

float compute() {
    float θ2 = (global_0 + 9001.0f);
    return θ2;
}

void wgsl_main() {
    compute();
}

void main() {
    wgsl_main();
}
