#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float4 position;
    float value;
    float4 unused_value2;
    float unused_value;
    float value2;
};

struct vs_main_Output {
    float4 position [[position]];
    float value [[user(loc1)]];
    float4 unused_value2 [[user(loc2)]];
    float unused_value [[user(loc0)]];
    float value2 [[user(loc3)]];
};
[[vertex]]
vs_main_Output vs_main() {
    return Struct_2(float4(1.0f), 1.0f, float4(2.0f), 1.0f, 0.5f);
}
