#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture2D global_0;
layout(set = 0, binding = 1) uniform sampler global_1;

vec4 test(texture2D Passed_Texture, sampler Passed_Sampler) {
    return textureSample(Passed_Texture, Passed_Sampler, vec2(0.0f, 0.0f));
}

void textureSample() {
}

vec4 wgsl_main() {
    return test(global_0, global_1);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_main();
}
