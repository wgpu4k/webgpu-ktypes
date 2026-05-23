#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float4 position;
    float _varying;
};
struct Struct_4 {
    float depth;
    uint sample_mask;
    float color;
};
struct Struct_5 {
    uint index;
};
/* unknown type */ void global_0;

struct vertex_two_structs_Output {
    float4 position [[position]];
};
[[vertex]]
vertex_two_structs_Output vertex_two_structs() {
}
