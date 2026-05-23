#include <metal_stdlib>
using namespace metal;

struct main_Input {
    float2 uv [[user(loc0)]];
};
[[fragment]]
float4 main(main_Input in [[stage_in]], float global_0 [[buffer(0)]], float global_1 [[buffer(1)]]) {
    float2 uv = in.uv;
}

void textureSample() {
}
