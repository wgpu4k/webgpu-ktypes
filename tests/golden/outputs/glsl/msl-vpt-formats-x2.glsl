#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec4 position;
};
struct Struct_8 {
    uvec2 v_uint8;
    uvec2 v_uint8x2;
    uvec2 v_uint8x4;
    ivec2 v_sint8;
    ivec2 v_sint8x2;
    ivec2 v_sint8x4;
    vec2 v_unorm8;
    vec2 v_unorm8x2;
    vec2 v_unorm8x4;
    vec2 v_snorm8;
    vec2 v_snorm8x2;
    vec2 v_snorm8x4;
    uvec2 v_uint16;
    uvec2 v_uint16x2;
    uvec2 v_uint16x4;
    ivec2 v_sint16;
    ivec2 v_sint16x2;
    ivec2 v_sint16x4;
    vec2 v_unorm16;
    vec2 v_unorm16x2;
    vec2 v_unorm16x4;
    vec2 v_snorm16;
    vec2 v_snorm16x2;
    vec2 v_snorm16x4;
    vec2 v_float16;
    vec2 v_float16x2;
    vec2 v_float16x4;
    vec2 v_float32;
    vec2 v_float32x2;
    vec2 v_float32x3;
    vec2 v_float32x4;
    uvec2 v_uint32;
    uvec2 v_uint32x2;
    uvec2 v_uint32x3;
    uvec2 v_uint32x4;
    ivec2 v_sint32;
    ivec2 v_sint32x2;
    ivec2 v_sint32x3;
    ivec2 v_sint32x4;
    vec2 v_unorm10_10_10_2;
    vec2 v_unorm8x4_bgra;
    vec2 v_float16_as_f16;
    vec2 v_float16x2_as_f16;
    vec2 v_float16x4_as_f16;
};

Struct_2 wgsl_render_vertex(Struct_8 v_in) {
    return Struct_2(vec4(v_in.v_float32[0]));
}

void main() {
    Struct_2 res = wgsl_render_vertex(v_in);
    gl_Position = res.position;
}
