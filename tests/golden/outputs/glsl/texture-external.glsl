#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture_external global_0;
layout(set = 0, binding = 1) uniform sampler global_1;

vec4 test(texture_external t) {
    texture_external a = textureSampleBaseClampToEdge(t, global_1, vec2(0.0f));
    texture_external b = textureLoad(t, vec2(0));
    texture_external c = textureLoad(t, vec2(0u));
    texture_external d = textureDimensions(t);
    return (((a + b) + c) + vec2(d).xyxy);
}

texture_external textureSampleBaseClampToEdge(texture_external arg_0, sampler arg_1, vec2 arg_2) {
}

texture_external textureLoad(texture_external arg_0, vec2 arg_1) {
}

texture_external textureLoad(texture_external arg_0, vec2 arg_1) {
}

texture_external textureDimensions(texture_external arg_0) {
}

vec4 wgsl_fragment_main() {
    return test(global_0);
}

vec4 wgsl_vertex_main() {
    return test(global_0);
}

void wgsl_compute_main() {
    test(global_0);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fragment_main();
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_vertex_main();
}

void main() {
    wgsl_compute_main();
}
