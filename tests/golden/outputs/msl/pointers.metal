#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    array<uint> arr;
};

void f() {
    float2x2 local_0;
    /* unknown type */ void local_1 = &local_0[0];
    local_1 = float2(10.0f);
}

void index_unsized(int i, uint v) {
    device Struct_2* local_0 = &global_0;
    uint local_1 = local_0.arr[i];
    local_0.arr[i] = (local_1 + v);
}

void index_dynamic_array(int i, uint v) {
    device array<uint>* local_0 = &global_0.arr;
    uint local_1 = local_0[i];
    local_0[i] = (local_1 + v);
}

[[kernel]]
void main(Struct_2 global_0 [[buffer(0)]]) {
}
