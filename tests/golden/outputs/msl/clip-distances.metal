#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    float4 position;
    /* unknown type */ void clip_distances;
};

struct main_Output {
    float4 position [[position]];
    /* unknown type */ void clip_distances;
};
[[vertex]]
main_Output main() {
}
