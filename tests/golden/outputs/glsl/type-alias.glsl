#version 450 core
precision highp float;
precision highp int;


void wgsl_main() {
    vec3 a = vec3(0.0f, 0.0f, 0.0f);
    vec3 c = vec3(0.0f);
    vec3 b = vec3(vec2(0.0f), 0.0f);
    vec3 d = vec3(vec2(0.0f), 0.0f);
    ivec3 e = ivec3(d);
    mat2x2 f = mat2x2(1.0f, 2.0f, 3.0f, 4.0f);
    mat3x3 g = mat3x3(a, a, a);
    ivec2 h = ivec2();
    mat2x2 i = mat2x2();
}

void main() {
    wgsl_main();
}
