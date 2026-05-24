#include <metal_stdlib>
using namespace metal;

[[fragment]]
float4 func() {
    return float4(float(index), 1.0f, 1.0f, 1.0f);
}
