#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float4 position;
};
struct Struct_9 {
    uint4 v_uint8;
    uint4 v_uint8x2;
    uint4 v_uint8x4;
    int4 v_sint8;
    int4 v_sint8x2;
    int4 v_sint8x4;
    float4 v_unorm8;
    float4 v_unorm8x2;
    float4 v_unorm8x4;
    float4 v_snorm8;
    float4 v_snorm8x2;
    float4 v_snorm8x4;
    uint4 v_uint16;
    uint4 v_uint16x2;
    uint4 v_uint16x4;
    int4 v_sint16;
    int4 v_sint16x2;
    int4 v_sint16x4;
    float4 v_unorm16;
    float4 v_unorm16x2;
    float4 v_unorm16x4;
    float4 v_snorm16;
    float4 v_snorm16x2;
    float4 v_snorm16x4;
    float4 v_float16;
    float4 v_float16x2;
    float4 v_float16x4;
    float4 v_float32;
    float4 v_float32x2;
    float4 v_float32x3;
    float4 v_float32x4;
    uint4 v_uint32;
    uint4 v_uint32x2;
    uint4 v_uint32x3;
    uint4 v_uint32x4;
    int4 v_sint32;
    int4 v_sint32x2;
    int4 v_sint32x3;
    int4 v_sint32x4;
    float4 v_unorm10_10_10_2;
    float4 v_unorm8x4_bgra;
    half4 v_float16_as_f16;
    half4 v_float16x2_as_f16;
    half4 v_float16x4_as_f16;
};

struct render_vertex_Output {
    float4 position [[position]];
};
[[vertex]]
render_vertex_Output render_vertex() {
    return Struct_2(float4(v_in.v_float32[0]));
}
