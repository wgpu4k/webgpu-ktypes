#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    ulong atomic_scalar;
    array<ulong, 2> atomic_arr;
};

[[kernel]]
void cs_main(ulong global_0 [[buffer(0)]], array<ulong, 2> global_1 [[buffer(1)]], ulong global_2 [[buffer(3)]], Struct_2 global_3 [[buffer(2)]]) {
    atomicMax(&global_0, global_2);
    atomicMax(&global_1[1], (1 + global_2));
    atomicMax(&global_3.atomic_scalar, 1u);
    atomicMax(&global_3.atomic_arr[1], ulong(id[0]));
    workgroupBarrier();
    atomicMin(&global_0, global_2);
    atomicMin(&global_1[1], (1 + global_2));
    atomicMin(&global_3.atomic_scalar, 1u);
    atomicMin(&global_3.atomic_arr[1], ulong(id[0]));
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, ulong arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, ulong arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, ulong arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, ulong arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, ulong arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, ulong arg_1) {
}
