#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_fs_main(float[3] v) {
    return vec4(v[0], v[1], v[2], 1.0f);
}

layout(location = 0) in float[3] v;
layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(v);
}
