#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    int x;
};
 int global_0 = 1;
layout(set = 0, binding = 0) uniform texture2D global_1;
layout(set = 0, binding = 1) uniform sampler global_2;

void statement() {
}

Struct_1 returns() {
    return Struct_1(global_0);
}

void call() {
    statement();
    Struct_1 x = returns();
    float vf = float(global_0);
    texture2D s = textureSample(global_1, global_2, vec2(vf));
}

texture2D textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

void wgsl_main() {
    call();
    statement();
    returns();
}

void main() {
    wgsl_main();
}
