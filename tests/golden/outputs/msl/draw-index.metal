#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint draw_index;
};

struct vertex_Output {
    float4 position [[position]];
};
[[vertex]]
vertex_Output vertex() {
    return float4(float(input.draw_index), 1.0f, 1.0f, 1.0f);
}
