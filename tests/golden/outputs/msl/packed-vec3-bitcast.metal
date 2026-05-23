#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    int3 chunk;
    uint texture_index;
};
struct Struct_6 {
    float4 clip_position;
};

struct vs_main_Output {
    float4 clip_position [[position]];
};
[[vertex]]
vs_main_Output vs_main() {
}
