#version 450 core
precision highp float;
precision highp int;


void derivatives() {
    float x = dpdx(0.0f);
    float y = dpdy(0.0f);
    float width = fwidth(0.0f);
}

void dpdx() {
}

void dpdy() {
}

void fwidth() {
}

void barriers() {
}

void storageBarrier() {
}

void workgroupBarrier() {
}

void textureBarrier() {
}

vec4 wgsl_fragment() {
    return vec4();
}

void wgsl_compute() {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fragment();
}

void main() {
    wgsl_compute();
}
