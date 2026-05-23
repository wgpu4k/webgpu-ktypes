#include <metal_stdlib>
using namespace metal;

struct vs_Output {
    float4 position [[position]];
};
[[vertex]]
vs_Output vs() {
}

[[fragment]]
void fs(float4 position [[position]]) {
}
