#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    float2 uv;
    float4 position;
};
float global_0 = 1.2f;

[[fragment]]
float4 fs_extra(texture2d<float> global_1 [[texture(0)]], sampler global_2 [[sampler(1)]]) {
    return float4(0.0f, 0.5f, 0.0f, 0.5f);
}

struct vert_main_Input {
    float2 pos [[attribute(0)]];
    float2 uv [[attribute(1)]];
};
struct vert_main_Output {
    float2 uv [[user(loc0)]];
    float4 position [[position]];
};
[[vertex]]
vert_main_Output vert_main(vert_main_Input in [[stage_in]], texture2d<float> global_1 [[texture(0)]], sampler global_2 [[sampler(1)]]) {
    float2 pos = in.pos;
    float2 uv = in.uv;
    return Struct_3(uv, float4((global_0 * pos), 0.0f, 1.0f));
}

struct frag_main_Input {
    float2 uv [[user(loc0)]];
};
[[fragment]]
float4 frag_main(frag_main_Input in [[stage_in]], texture2d<float> global_1 [[texture(0)]], sampler global_2 [[sampler(1)]]) {
    float2 uv = in.uv;
    float4 local_0 = textureSample(global_1, global_2, uv);
    if ((local_0[3] == 0.0f)) {
        discard;
    }
    float4 local_1 = (local_0[3] * local_0);
    return local_1;
}

float4 textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}
