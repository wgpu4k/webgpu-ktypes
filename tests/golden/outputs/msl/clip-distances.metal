#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    float4 position;
    array<float, 1> clip_distances;
};

struct main_Output {
    float4 position [[position]];
    array<float, 1> clip_distances;
};
[[vertex]]
main_Output main() {
    Struct_3 local_0;
    local_0.clip_distances[0] = 0.5f;
    return local_0;
}
