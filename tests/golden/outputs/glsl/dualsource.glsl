#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec4 output0;
    vec4 output1;
};

Struct_2 wgsl_main() {
    return Struct_2(vec4(0.4f, 0.3f, 0.2f, 0.1f), vec4(0.9f, 0.8f, 0.7f, 0.6f));
}

layout(location = 0) out Struct_2 outColor;
void main() {
    outColor = wgsl_main();
}
