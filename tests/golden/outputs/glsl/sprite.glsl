#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture2D global_0;
layout(set = 0, binding = 1) uniform sampler global_1;

vec4 wgsl_main(vec2 uv) {
    return textureSample(global_0, global_1, uv);
}

void textureSample() {
}

layout(location = 0) in vec2 uv;
layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_main(uv);
}
