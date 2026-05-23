#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    /* unknown type */ void arr;
};

void f() {
    float2x2 local_0;
    /* unknown type */ void local_1 = /* unsupported expression: ValuePointer */;
    local_1 = float2(10.0f);
}

void index_unsized(int i, uint v) {
    device Struct_2* local_0 = /* unsupported expression: ValuePointer */;
    uint local_1 = local_0.arr[i];
    local_0.arr[i] = (local_1 + v);
}

void index_dynamic_array(int i, uint v) {
    device /* unknown type */ void* local_0 = /* unsupported expression: ValuePointer */;
    uint local_1 = local_0[i];
    local_0[i] = (local_1 + v);
}

[[kernel]]
void main(Struct_2 global_0 [[buffer(0)]]) {
}
