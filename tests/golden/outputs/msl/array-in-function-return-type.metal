#include <metal_stdlib>
using namespace metal;

/* unknown type */ void ret_array() {
    return /* unknown type */ void(1.0f, 2.0f);
}

/* unknown type */ void ret_array_array() {
    return /* unknown type */ void(ret_array(), ret_array(), ret_array());
}

[[fragment]]
float4 main() {
}
