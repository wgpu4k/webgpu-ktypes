#include <metal_stdlib>
using namespace metal;

[[fragment]]
float4 entry_point_one(float4 pos [[position]], texture2d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]], sampler global_2 [[sampler(2)]], sampler global_3 [[sampler(3)]], float2 global_4 [[buffer(4)]], float2 global_5 [[buffer(0)]]) {
}

void textureSample() {
}

[[fragment]]
float4 entry_point_two(texture2d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]], sampler global_2 [[sampler(2)]], sampler global_3 [[sampler(3)]], float2 global_4 [[buffer(4)]], float2 global_5 [[buffer(0)]]) {
}

void textureSample() {
}

[[fragment]]
float4 entry_point_three(texture2d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]], sampler global_2 [[sampler(2)]], sampler global_3 [[sampler(3)]], float2 global_4 [[buffer(4)]], float2 global_5 [[buffer(0)]]) {
}

void textureSample() {
}

void textureSample() {
}
