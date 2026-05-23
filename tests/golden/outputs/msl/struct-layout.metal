#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float3 v3;
    float f3;
};
struct Struct_3 {
    float f3_forces_padding;
    float3 v3_needs_padding;
    float f3;
};

[[fragment]]
float4 no_padding_frag(Struct_2 global_0 [[buffer(0)]], Struct_2 global_1 [[buffer(1)]], Struct_3 global_2 [[buffer(2)]], Struct_3 global_3 [[buffer(3)]]) {
}

struct no_padding_vert_Output {
    float4 position [[position]];
};
[[vertex]]
no_padding_vert_Output no_padding_vert(Struct_2 global_0 [[buffer(0)]], Struct_2 global_1 [[buffer(1)]], Struct_3 global_2 [[buffer(2)]], Struct_3 global_3 [[buffer(3)]]) {
}

[[fragment]]
float4 needs_padding_frag(Struct_2 global_0 [[buffer(0)]], Struct_2 global_1 [[buffer(1)]], Struct_3 global_2 [[buffer(2)]], Struct_3 global_3 [[buffer(3)]]) {
}

struct needs_padding_vert_Output {
    float4 position [[position]];
};
[[vertex]]
needs_padding_vert_Output needs_padding_vert(Struct_2 global_0 [[buffer(0)]], Struct_2 global_1 [[buffer(1)]], Struct_3 global_2 [[buffer(2)]], Struct_3 global_3 [[buffer(3)]]) {
}

[[kernel]]
void no_padding_comp(Struct_2 global_0 [[buffer(0)]], Struct_2 global_1 [[buffer(1)]], Struct_3 global_2 [[buffer(2)]], Struct_3 global_3 [[buffer(3)]]) {
}

[[kernel]]
void needs_padding_comp(Struct_2 global_0 [[buffer(0)]], Struct_2 global_1 [[buffer(1)]], Struct_3 global_2 [[buffer(2)]], Struct_3 global_3 [[buffer(3)]]) {
}
