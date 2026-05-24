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
}
