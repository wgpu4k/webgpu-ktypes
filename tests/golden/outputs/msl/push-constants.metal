#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    float multiplier;
};
struct Struct_3 {
    float4 color;
};
Struct_1 global_0;

struct vert_main_Input {
    float2 pos [[attribute(0)]];
};
struct vert_main_Output {
    float4 position [[position]];
};
[[vertex]]
vert_main_Output vert_main(vert_main_Input in [[stage_in]]) {
    float2 pos = in.pos;
    return float4((((float(ii) * float(vi)) * global_0.multiplier) * pos), 0.0f, 1.0f);
}

[[fragment]]
float4 main() {
    return (in.color * global_0.multiplier);
}
