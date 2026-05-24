#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    uint index;
    float2 double;
};
struct Struct_5 {
    float4 color;
    uint primitive_index;
};
Struct_3 global_0;

[[fragment]]
float4 main() {
    if ((in.primitive_index == global_0.index)) {
        return in.color;
    } else {
        return float4((float3(1.0f) - in.color.xyz), in.color[3]);
    }
}
