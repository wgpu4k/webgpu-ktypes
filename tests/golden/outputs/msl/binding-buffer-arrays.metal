#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint index;
};
struct Struct_4 {
    uint x;
    array<int> far;
};
struct Struct_5 {
    uint index;
};

[[fragment]]
uint main(Struct_1 global_0 [[buffer(10)]], array<Struct_4, 1> global_1 [[buffer(0)]]) {
    uint local_0 = global_0.index;
    uint local_1 = fragment_in.index;
    uint local_2 = 0u;
    local_2 = global_1[0].x;
    local_2 = global_1[local_0].x;
    local_2 = global_1[local_1].x;
    local_2 = arrayLength(&global_1[0].far);
    local_2 = arrayLength(&global_1[local_0].far);
    local_2 = arrayLength(&global_1[local_1].far);
    return local_2;
}

/* unknown type */ void arrayLength(/* unknown type */ void arg_0) {
}

/* unknown type */ void arrayLength(/* unknown type */ void arg_0) {
}

/* unknown type */ void arrayLength(/* unknown type */ void arg_0) {
}
