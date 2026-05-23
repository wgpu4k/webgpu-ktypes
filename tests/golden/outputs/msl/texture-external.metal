#include <metal_stdlib>
using namespace metal;

float4 test(float t) {
    float local_0 = textureSampleBaseClampToEdge(t, global_1, float2(0.0f));
    float local_1 = textureLoad(t, float2(0));
    float local_2 = textureLoad(t, float2(0u));
    float local_3 = textureDimensions(t);
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
float4 fragment_main(float global_0 [[buffer(0)]], float global_1 [[buffer(1)]]) {
}

struct vertex_main_Output {
    float4 position [[position]];
};
[[vertex]]
vertex_main_Output vertex_main(float global_0 [[buffer(0)]], float global_1 [[buffer(1)]]) {
}

[[kernel]]
void compute_main(float global_0 [[buffer(0)]], float global_1 [[buffer(1)]]) {
}
