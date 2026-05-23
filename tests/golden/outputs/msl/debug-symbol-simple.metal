#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float3 position;
    float3 color;
};
struct Struct_4 {
    float4 clip_position;
    float3 color;
};

struct vs_main_Output {
    float4 clip_position [[position]];
    float3 color [[user(loc0)]];
};
[[vertex]]
vs_main_Output vs_main() {
}

[[fragment]]
float4 fs_main() {
}
