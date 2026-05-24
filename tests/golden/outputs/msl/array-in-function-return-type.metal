#include <metal_stdlib>
using namespace metal;

array<float, 2> ret_array() {
    return array<float, 2>(1.0f, 2.0f);
}

array<array<float, 2>, 3> ret_array_array() {
    return array<array<float, 2>, 3>(ret_array(), ret_array(), ret_array());
}

[[fragment]]
float4 main() {
    array<array<float, 2>, 3> local_0 = ret_array_array();
    return float4(local_0[0][0], local_0[0][1], 0.0f, 1.0f);
}
