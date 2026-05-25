#version 450 core
precision highp float;
precision highp int;

struct Struct_5 {
    vec4 position;
    uint _flat;
    uint flat_first;
    uint flat_either;
    float _linear;
    vec2 linear_centroid;
    vec3 linear_sample;
    vec3 linear_center;
    vec4 perspective;
    float perspective_centroid;
    float perspective_sample;
    float perspective_center;
};

Struct_5 wgsl_vert_main() {
    Struct_5 out;
    out.position = vec4(2.0f, 4.0f, 5.0f, 6.0f);
    out._flat = 8u;
    out.flat_first = 9u;
    out.flat_either = 10u;
    out._linear = 27.0f;
    out.linear_centroid = vec2(64.0f, 125.0f);
    out.linear_sample = vec3(216.0f, 343.0f, 512.0f);
    out.linear_center = vec3(255.0f, 511.0f, 1024.0f);
    out.perspective = vec4(729.0f, 1000.0f, 1331.0f, 1728.0f);
    out.perspective_centroid = 2197.0f;
    out.perspective_sample = 2744.0f;
    out.perspective_center = 2812.0f;
    return out;
}

void wgsl_frag_main(Struct_5 val) {
}

layout(location = 0) out uint out__flat;
layout(location = 1) out uint out_flat_first;
layout(location = 2) out uint out_flat_either;
layout(location = 3) out float out__linear;
layout(location = 4) out vec2 out_linear_centroid;
layout(location = 6) out vec3 out_linear_sample;
layout(location = 7) out vec3 out_linear_center;
layout(location = 8) out vec4 out_perspective;
layout(location = 9) out float out_perspective_centroid;
layout(location = 10) out float out_perspective_sample;
layout(location = 11) out float out_perspective_center;
void main() {
    Struct_5 res = wgsl_vert_main();
    gl_Position = res.position;
    out__flat = res._flat;
    out_flat_first = res.flat_first;
    out_flat_either = res.flat_either;
    out__linear = res._linear;
    out_linear_centroid = res.linear_centroid;
    out_linear_sample = res.linear_sample;
    out_linear_center = res.linear_center;
    out_perspective = res.perspective;
    out_perspective_centroid = res.perspective_centroid;
    out_perspective_sample = res.perspective_sample;
    out_perspective_center = res.perspective_center;
}

void main() {
    wgsl_frag_main(val);
}
