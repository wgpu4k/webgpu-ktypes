#include <metal_stdlib>
using namespace metal;

struct vs_Output {
    float4 position [[position]];
};
[[vertex]]
vs_Output vs() {
    return float4(0.0f);
}

[[fragment]]
void fs(float4 position [[position]]) {
}
