#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec4 position;
    vec3 uv;
};
struct Struct_5 {
    mat4x4 proj_inv;
    mat4x4 view;
};
layout(set = 0, binding = 1) uniform textureCube global_0;
layout(set = 0, binding = 2) uniform sampler global_1;
layout(set = 0, binding = 0) uniform Struct_5 global_2;

vec4 wgsl_fs_main(Struct_3 in) {
    return textureSample(global_0, global_1, in.uv);
}

vec4 textureSample(textureCube arg_0, sampler arg_1, vec3 arg_2) {
}

Struct_3 wgsl_vs_main(uint vertex_index) {
    int tmp1 = (int(vertex_index) / 2);
    int tmp2 = (int(vertex_index) & 1);
    vec4 pos = vec4(((float(tmp1) * 4.0f) - 1.0f), ((float(tmp2) * 4.0f) - 1.0f), 0.0f, 1.0f);
    mat3x3 inv_model_view = transpose(mat3x3(global_2.view[0].xyz, global_2.view[1].xyz, global_2.view[2].xyz));
    vec4 unprojected = (global_2.proj_inv * pos);
    return Struct_3(pos, (inv_model_view * unprojected.xyz));
}

mat3x3 transpose(mat3x3 arg_0) {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(in);
}

layout(location = 0) out vec3 out_uv;
void main() {
    Struct_3 res = wgsl_vs_main(vertex_index);
    gl_Position = res.position;
    out_uv = res.uv;
}
