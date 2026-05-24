#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float3 position;
    float3 color;
};
struct Struct_4 {
    float4 clip_position;
    float3 color;
};

struct vs_main_Output {
    float4 clip_position [[position]];
    float3 color [[user(loc0)]];
};
[[vertex]]
vs_main_Output vs_main() {
    Struct_4 local_0;
    local_0.color = model.color;
    local_0.clip_position = float4(model.position, 1.0f);
    return local_0;
}

[[fragment]]
float4 fs_main() {
    float3 local_0 = in.color;
    {
        int local_1 = 0;
        while (true) {
            if ((local_1 < 10)) {
                {
                    float local_2 = float(local_1);
                    local_0[0] = (local_2 * 0.001f);
                    local_0[1] = (local_2 * 0.002f);
                }
                local_1 = 1;
            } else {
                break;
            }
        }
    }
    return float4(local_0, 1.0f);
}
