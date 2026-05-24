#include <metal_stdlib>
using namespace metal;

float4 test(texture2d<float> t) {
    texture2d<float> local_0 = textureSampleBaseClampToEdge(t, global_1, float2(0.0f));
    texture2d<float> local_1 = textureLoad(t, float2(0));
    texture2d<float> local_2 = textureLoad(t, float2(0u));
    texture2d<float> local_3 = textureDimensions(t);
    return (((local_0 + local_1) + local_2) + float2(local_3).xyxy);
}

void textureSampleBaseClampToEdge() {
}

void textureLoad() {
}

void textureLoad() {
}

void textureDimensions() {
}

[[fragment]]
float4 fragment_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
}

struct vertex_main_Output {
    float4 position [[position]];
};
[[vertex]]
vertex_main_Output vertex_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
}

[[kernel]]
void compute_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]]) {
}
