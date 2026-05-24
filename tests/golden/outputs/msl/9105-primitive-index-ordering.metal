#include <metal_stdlib>
using namespace metal;

struct func_Input {
    float input_location [[user(loc0)]];
};
[[fragment]]
float4 func(func_Input in [[stage_in]], float4 arbitrary_position [[position]]) {
    float input_location = in.input_location;
    return float4(arbitrary_position.xy, input_location, float(index));
}
