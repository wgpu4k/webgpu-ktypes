#include <metal_stdlib>
using namespace metal;

[[fragment]]
float main(float4 pos [[position]]) {
    return (pos[2] - 0.1f);
}
