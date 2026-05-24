#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    float multiplier;
};
struct Struct_3 {
    vec4 color;
};
 Struct_1 global_0;

vec4 wgsl_vert_main(vec2 pos, uint ii, uint vi) {
    return vec4((((float(ii) * float(vi)) * global_0.multiplier) * pos), 0.0f, 1.0f);
}

vec4 wgsl_main(Struct_3 in) {
    return (in.color * global_0.multiplier);
}

layout(location = 0) in vec2 pos;
out vec4 outValue;
void main() {
    gl_Position = wgsl_vert_main(pos, ii, vi);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_main(in);
}
