#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec4 position;
    vec4 color;
    vec2 texcoord;
};
struct Struct_5 {
    vec4 position;
    vec3 normal;
    vec2 texcoord;
};
layout(set = 0, binding = 0) uniform mat4x4 global_0;

vec4 do_lighting(vec4 position, vec3 normal) {
    return vec4(0);
}

Struct_3 wgsl_render_vertex(Struct_5 v_in, uint v_existing_id) {
    Struct_3 v_out;
    v_out.position = (v_in.position * global_0);
    v_out.color = do_lighting(v_in.position, v_in.normal);
    v_out.texcoord = v_in.texcoord;
    return v_out;
}

layout(location = 0) out vec4 out_color;
layout(location = 1) out vec2 out_texcoord;
void main() {
    Struct_3 res = wgsl_render_vertex(v_in, v_existing_id);
    gl_Position = res.position;
    out_color = res.color;
    out_texcoord = res.texcoord;
}
