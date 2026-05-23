#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    float4 position;
    float2 texcoord;
};

[[fragment]]
float4 fs() {
}

struct vs_Input {
    float2 xy [[attribute(0)]];
};
struct vs_Output {
    float4 position [[position]];
    float2 texcoord [[user(loc0)]];
};
[[vertex]]
vs_Output vs(vs_Input in [[stage_in]]) {
    float2 xy = in.xy;
}
