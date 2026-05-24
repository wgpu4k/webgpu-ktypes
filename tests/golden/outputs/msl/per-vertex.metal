#include <metal_stdlib>
using namespace metal;

struct fs_main_Input {
    array<float, 3> v [[user(loc0)]];
};
[[fragment]]
float4 fs_main(fs_main_Input in [[stage_in]]) {
    array<float, 3> v = in.v;
    return float4(v[0], v[1], v[2], 1.0f);
}
