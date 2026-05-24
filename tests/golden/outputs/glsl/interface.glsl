#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec4 position;
    float _varying;
};
struct Struct_4 {
    float depth;
    uint sample_mask;
    float color;
};
struct Struct_5 {
    uint index;
};
shared uint[1] global_0;

Struct_2 wgsl_vertex(uint vertex_index, uint instance_index, uint color) {
    uint tmp = ((vertex_index + instance_index) + color);
    return Struct_2(vec4(1.0f), float(tmp));
}

Struct_4 wgsl_fragment(Struct_2 in, bool front_facing, uint sample_index, uint sample_mask) {
    uint mask = (sample_mask & (1u << sample_index));
    float color = select(0.0f, 1.0f, front_facing);
    return Struct_4(in._varying, mask, color);
}

float select(float arg_0, float arg_1, bool arg_2) {
}

void wgsl_compute(uvec3 global_id, uvec3 local_id, uint local_index, uvec3 wg_id, uvec3 num_wgs) {
    global_0[0] = ((((global_id[0] + local_id[0]) + local_index) + wg_id[0]) + num_wgs[0]);
}

vec4 wgsl_vertex_two_structs(Struct_5 in1, Struct_5 in2) {
    uint index = 2u;
    return vec4(float(in1.index), float(in2.index), float(index), 0.0f);
}

layout(location = 10) in uint color;
layout(location = 1) out float out__varying;
void main() {
    Struct_2 res = wgsl_vertex(vertex_index, instance_index, color);
    gl_Position = res.position;
    out__varying = res._varying;
}

layout(location = 0) out Struct_4 outColor;
void main() {
    outColor = wgsl_fragment(in, front_facing, sample_index, sample_mask);
}

void main() {
    wgsl_compute(global_id, local_id, local_index, wg_id, num_wgs);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_vertex_two_structs(in1, in2);
}
