#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float atomic_scalar;
    array<float, 2> atomic_arr;
};

[[kernel]]
void cs_main(float global_0 [[buffer(0)]], array<float, 2> global_1 [[buffer(1)]], Struct_2 global_2 [[buffer(2)]]) {
    atomicStore(&global_0, 1.5f);
    atomicStore(&global_1[1], 1.5f);
    atomicStore(&global_2.atomic_scalar, 1.5f);
    atomicStore(&global_2.atomic_arr[1], 1.5f);
    workgroupBarrier();
    /* unknown type */ void local_0 = atomicLoad(&global_0);
    /* unknown type */ void local_1 = atomicLoad(&global_1[1]);
    /* unknown type */ void local_2 = atomicLoad(&global_2.atomic_scalar);
    /* unknown type */ void local_3 = atomicLoad(&global_2.atomic_arr[1]);
    workgroupBarrier();
    atomicAdd(&global_0, 1.5f);
    atomicAdd(&global_1[1], 1.5f);
    atomicAdd(&global_2.atomic_scalar, 1.5f);
    atomicAdd(&global_2.atomic_arr[1], 1.5f);
    workgroupBarrier();
    atomicExchange(&global_0, 1.5f);
    atomicExchange(&global_1[1], 1.5f);
    atomicExchange(&global_2.atomic_scalar, 1.5f);
    atomicExchange(&global_2.atomic_arr[1], 1.5f);
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, float arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicLoad(/* unknown type */ void arg_0) {
}

/* unknown type */ void atomicLoad(/* unknown type */ void arg_0) {
}

/* unknown type */ void atomicLoad(/* unknown type */ void arg_0) {
}

/* unknown type */ void atomicLoad(/* unknown type */ void arg_0) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, float arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, float arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, float arg_1) {
}
