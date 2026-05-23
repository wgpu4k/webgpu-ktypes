#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_vs() {
    return vec4(0.0f);
}

void wgsl_fs(vec4 position) {
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_vs();
}

void main() {
    wgsl_fs(position);
}
