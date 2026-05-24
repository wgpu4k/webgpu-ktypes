#include <metal_stdlib>
using namespace metal;

bool test_any_and_all_for_bool() {
    bool local_0 = any(true);
    return all(local_0);
}

bool any(bool arg_0) {
}

bool all(bool arg_0) {
}

[[fragment]]
float4 derivatives(float4 foo [[position]]) {
    float4 local_0 = dpdxCoarse(foo);
    float4 local_1 = dpdyCoarse(foo);
    float4 local_2 = fwidthCoarse(foo);
    local_0 = dpdxFine(foo);
    local_1 = dpdyFine(foo);
    local_2 = fwidthFine(foo);
    local_0 = dpdx(foo);
    local_1 = dpdy(foo);
    local_2 = fwidth(foo);
    bool local_3 = test_any_and_all_for_bool();
    return ((local_0 + local_1) * local_2);
}

float4 dpdxCoarse(float4 arg_0) {
}

float4 dpdyCoarse(float4 arg_0) {
}

float4 fwidthCoarse(float4 arg_0) {
}

float4 dpdxFine(float4 arg_0) {
}

float4 dpdyFine(float4 arg_0) {
}

float4 fwidthFine(float4 arg_0) {
}

float4 dpdx(float4 arg_0) {
}

float4 dpdy(float4 arg_0) {
}

float4 fwidth(float4 arg_0) {
}
