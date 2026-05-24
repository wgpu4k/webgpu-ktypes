#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec4 position;
};
struct Struct_5 {
    uint v_uint8;
    uint v_uint8x2;
    uint v_uint8x4;
    int v_sint8;
    int v_sint8x2;
    int v_sint8x4;
    float v_unorm8;
    float v_unorm8x2;
    float v_unorm8x4;
    float v_snorm8;
    float v_snorm8x2;
    float v_snorm8x4;
    uint v_uint16;
    uint v_uint16x2;
    uint v_uint16x4;
    int v_sint16;
    int v_sint16x2;
    int v_sint16x4;
    float v_unorm16;
    float v_unorm16x2;
    float v_unorm16x4;
    float v_snorm16;
    float v_snorm16x2;
    float v_snorm16x4;
    float v_float16;
    float v_float16x2;
    float v_float16x4;
    float v_float32;
    float v_float32x2;
    float v_float32x3;
    float v_float32x4;
    uint v_uint32;
    uint v_uint32x2;
    uint v_uint32x3;
    uint v_uint32x4;
    int v_sint32;
    int v_sint32x2;
    int v_sint32x3;
    int v_sint32x4;
    float v_unorm10_10_10_2;
    float v_unorm8x4_bgra;
    float v_float16_as_f16;
    float v_float16x2_as_f16;
    float v_float16x4_as_f16;
};

Struct_2 wgsl_render_vertex(Struct_5 v_in) {
    return Struct_2(vec4(v_in.v_float32));
}

void main() {
    Struct_2 res = wgsl_render_vertex(v_in);
    gl_Position = res.position;
}
