#include <metal_stdlib>
using namespace metal;

bool test_any_and_all_for_bool() {
    bool local_0 = any(true);
    return all(local_0);
}

void any() {
}

void all() {
}

[[fragment]]
float4 derivatives(float4 foo [[position]]) {
}

void dpdxCoarse() {
}

void dpdyCoarse() {
}

void fwidthCoarse() {
}

void dpdxFine() {
}

void dpdyFine() {
}

void fwidthFine() {
}

void dpdx() {
}

void dpdy() {
}

void fwidth() {
}
