#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float3 a;
};
struct Struct_4 {
    /* unknown type */ void a;
    float b;
};
struct Struct_6 {
    float4x3 a;
    float b;
};
struct Struct_7 {
    Struct_2 a;
    float b;
};

struct vertex_Output {
    float4 position [[position]];
};
[[vertex]]
vertex_Output vertex(Struct_4 global_0 [[buffer(1)]], Struct_6 global_1 [[buffer(2)]], Struct_7 global_2 [[buffer(0)]]) {
}
