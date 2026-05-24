#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float2 position;
};
struct Struct_3 {
    float2 position;
};
struct Struct_5 {
    float4 position;
};

struct vs_main_Output {
    float4 position [[position]];
};
[[vertex]]
vs_main_Output vs_main() {
    Struct_5 local_0;
    return local_0;
}

[[fragment]]
float4 fs_main() {
    float3 local_0 = float3(1.0f);
    return in.position;
}
