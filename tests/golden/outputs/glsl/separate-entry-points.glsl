#version 450 core
precision highp float;
precision highp int;


void derivatives() {
    float x = dpdx(0.0f);
    float y = dpdy(0.0f);
    float width = fwidth(0.0f);
}

float dpdx(float arg_0) {
}

float dpdy(float arg_0) {
}

float fwidth(float arg_0) {
}

void barriers() {
    storageBarrier();
    workgroupBarrier();
    textureBarrier();
}

void storageBarrier() {
}

void workgroupBarrier() {
}

void textureBarrier() {
}

vec4 wgsl_fragment() {
    derivatives();
    return vec4(0.0f);
}

void wgsl_compute() {
    barriers();
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fragment();
}

void main() {
    wgsl_compute();
}
