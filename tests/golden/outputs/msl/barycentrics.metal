#include <metal_stdlib>
using namespace metal;

[[fragment]]
float4 fs_main() {
    return float4(bary, 1.0f);
}

[[fragment]]
float4 fs_main_no_perspective() {
    return float4(bary, 1.0f);
}
