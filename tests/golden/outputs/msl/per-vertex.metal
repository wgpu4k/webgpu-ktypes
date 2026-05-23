#include <metal_stdlib>
using namespace metal;

struct fs_main_Input {
    /* unknown type */ void v [[user(loc0)]];
};
[[fragment]]
float4 fs_main(fs_main_Input in [[stage_in]]) {
    /* unknown type */ void v = in.v;
}
