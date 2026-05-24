#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float2 position;
};
struct Struct_3 {
    float2 position;
};
struct Struct_5 {
    float4 position;
};

struct vs_main_Output {
    float4 position [[position]];
};
[[vertex]]
vs_main_Output vs_main() {
}

[[fragment]]
float4 fs_main() {
}
