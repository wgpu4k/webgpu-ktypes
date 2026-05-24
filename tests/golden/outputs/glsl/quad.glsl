#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec2 uv;
    vec4 position;
};
 float global_0 = 1.2f;
layout(set = 0, binding = 0) uniform texture2D global_1;
layout(set = 0, binding = 1) uniform sampler global_2;

vec4 wgsl_fs_extra() {
    return vec4(0.0f, 0.5f, 0.0f, 0.5f);
}

Struct_3 wgsl_vert_main(vec2 pos, vec2 uv) {
    return Struct_3(uv, vec4((global_0 * pos), 0.0f, 1.0f));
}

vec4 wgsl_frag_main(vec2 uv) {
    vec4 color = textureSample(global_1, global_2, uv);
    if ((color[3] == 0.0f)) {
        discard;
    }
    vec4 premultiplied = (color[3] * color);
    return premultiplied;
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_extra();
}

layout(location = 0) in vec2 pos;
layout(location = 1) in vec2 uv;
layout(location = 0) out vec2 out_uv;
void main() {
    Struct_3 res = wgsl_vert_main(pos, uv);
    out_uv = res.uv;
    gl_Position = res.position;
}

layout(location = 0) in vec2 uv;
layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_frag_main(uv);
}
