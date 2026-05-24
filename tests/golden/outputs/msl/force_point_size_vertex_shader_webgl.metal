#include <metal_stdlib>
using namespace metal;

struct vs_main_Output {
    float4 position [[position]];
};
[[vertex]]
vs_main_Output vs_main() {
    float local_0 = float((int(in_vertex_index) - 1));
    float local_1 = float(((int((in_vertex_index & 1u)) * 2) - 1));
    return float4(local_0, local_1, 0.0f, 1.0f);
}

[[fragment]]
float4 fs_main() {
    return float4(1.0f, 0.0f, 0.0f, 1.0f);
}
