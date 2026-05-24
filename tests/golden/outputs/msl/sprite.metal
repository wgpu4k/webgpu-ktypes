#include <metal_stdlib>
using namespace metal;

struct main_Input {
    float2 uv [[user(loc0)]];
};
[[fragment]]
float4 main(main_Input in [[stage_in]], texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
    float2 uv = in.uv;
}

void textureSample() {
}
