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
}

[[fragment]]
float4 main() {
}
