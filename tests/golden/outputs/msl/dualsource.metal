#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float4 output0;
    float4 output1;
};

struct main_Output {
    float4 output0 [[user(loc0)]];
    float4 output1 [[user(loc0)]];
};
[[fragment]]
main_Output main() {
    return Struct_2(float4(0.4f, 0.3f, 0.2f, 0.1f), float4(0.9f, 0.8f, 0.7f, 0.6f));
}
