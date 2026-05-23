#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_main() {
    return vec4(0.4f, 0.3f, 0.2f, 0.1f);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_main();
}
