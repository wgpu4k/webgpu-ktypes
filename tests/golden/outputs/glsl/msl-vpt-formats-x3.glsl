#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec4 position;
};
struct Struct_10 {
    uvec3 v_uint8;
    uvec3 v_uint8x2;
    uvec3 v_uint8x4;
    ivec3 v_sint8;
    ivec3 v_sint8x2;
    ivec3 v_sint8x4;
    vec3 v_unorm8;
    vec3 v_unorm8x2;
    vec3 v_unorm8x4;
    vec3 v_snorm8;
    vec3 v_snorm8x2;
    vec3 v_snorm8x4;
    uvec3 v_uint16;
    uvec3 v_uint16x2;
    uvec3 v_uint16x4;
    ivec3 v_sint16;
    ivec3 v_sint16x2;
    ivec3 v_sint16x4;
    vec3 v_unorm16;
    vec3 v_unorm16x2;
    vec3 v_unorm16x4;
    vec3 v_snorm16;
    vec3 v_snorm16x2;
    vec3 v_snorm16x4;
    vec3 v_float16;
    vec3 v_float16x2;
    vec3 v_float16x4;
    vec3 v_float32;
    vec3 v_float32x2;
    vec3 v_float32x3;
    vec3 v_float32x4;
    uvec3 v_uint32;
    uvec3 v_uint32x2;
    uvec3 v_uint32x3;
    uvec3 v_uint32x4;
    ivec3 v_sint32;
    ivec3 v_sint32x2;
    ivec3 v_sint32x3;
    ivec3 v_sint32x4;
    vec3 v_unorm10_10_10_2;
    vec3 v_unorm8x4_bgra;
    vec3 v_float16_as_f16;
    vec3 v_float16x2_as_f16;
    vec3 v_float16x4_as_f16;
};

Struct_2 wgsl_render_vertex(Struct_10 v_in) {
    return Struct_2(vec4(v_in.v_float32[0]));
}

void main() {
    Struct_2 res = wgsl_render_vertex(v_in);
    gl_Position = res.position;
}
