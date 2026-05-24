#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float value;
    float value2;
    float4 position;
};

[[fragment]]
float4 fs_main() {
    return float4(v_out.value, v_out.value, v_out.value2, v_out.value2);
}
