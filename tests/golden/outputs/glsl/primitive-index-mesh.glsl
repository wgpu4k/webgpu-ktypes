#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_func(uint index) {
    return vec4(float(index), 1.0f, 1.0f, 1.0f);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_func(index);
}
