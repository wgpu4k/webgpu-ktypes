#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture2D global_0;
layout(set = 0, binding = 1) uniform sampler_comparison global_1;

float wgsl_main() {
    vec2 pos = vec2(0.0f);
    int array_index = 0;
    float depth = 0.0f;
    return textureSampleCompareLevel(global_0, global_1, pos, array_index, depth);
}

texture2D textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, int arg_3, float arg_4) {
}

layout(location = 0) out float outColor;
void main() {
    outColor = wgsl_main();
}
