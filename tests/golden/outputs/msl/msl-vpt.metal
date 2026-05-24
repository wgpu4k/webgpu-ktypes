#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    float4 position;
    float4 color;
    float2 texcoord;
};
struct Struct_5 {
    float4 position;
    float3 normal;
    float2 texcoord;
};

float4 do_lighting(float4 position, float3 normal) {
    return float4(0);
}

struct render_vertex_Output {
    float4 position [[position]];
    float4 color [[user(loc0)]];
    float2 texcoord [[user(loc1)]];
};
[[vertex]]
render_vertex_Output render_vertex(float4x4 global_0 [[buffer(0)]]) {
    Struct_3 local_0;
    local_0.position = (v_in.position * global_0);
    local_0.color = do_lighting(v_in.position, v_in.normal);
    local_0.texcoord = v_in.texcoord;
    return local_0;
}
