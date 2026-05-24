#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    uint atomic_scalar;
    array<int, 2> atomic_arr;
};
struct Struct_10 {
    uint old_value;
    char _pad0[4];
    bool exchanged;
};
struct Struct_11 {
    int old_value;
    char _pad0[4];
    bool exchanged;
};
uint global_2;
array<int, 2> global_3;
Struct_3 global_5;

[[kernel]]
void cs_main(uint global_0 [[buffer(0)]], array<int, 2> global_1 [[buffer(1)]], Struct_3 global_4 [[buffer(2)]]) {
    atomicStore(&global_0, 1u);
    atomicStore(&global_1[1], 1);
    atomicStore(&global_4.atomic_scalar, 1u);
    atomicStore(&global_4.atomic_arr[1], 1);
    atomicStore(&global_2, 1u);
    atomicStore(&global_3[1], 1);
    atomicStore(&global_5.atomic_scalar, 1u);
    atomicStore(&global_5.atomic_arr[1], 1);
    workgroupBarrier();
    /* unknown type */ void local_0 = atomicLoad(&global_0);
    /* unknown type */ void local_1 = atomicLoad(&global_1[1]);
    /* unknown type */ void local_2 = atomicLoad(&global_4.atomic_scalar);
    /* unknown type */ void local_3 = atomicLoad(&global_4.atomic_arr[1]);
    /* unknown type */ void local_4 = atomicLoad(&global_2);
    /* unknown type */ void local_5 = atomicLoad(&global_3[1]);
    /* unknown type */ void local_6 = atomicLoad(&global_5.atomic_scalar);
    /* unknown type */ void local_7 = atomicLoad(&global_5.atomic_arr[1]);
    workgroupBarrier();
    atomicAdd(&global_0, 1u);
    atomicAdd(&global_1[1], 1);
    atomicAdd(&global_4.atomic_scalar, 1u);
    atomicAdd(&global_4.atomic_arr[1], 1);
    atomicAdd(&global_2, 1u);
    atomicAdd(&global_3[1], 1);
    atomicAdd(&global_5.atomic_scalar, 1u);
    atomicAdd(&global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicSub(&global_0, 1u);
    atomicSub(&global_1[1], 1);
    atomicSub(&global_4.atomic_scalar, 1u);
    atomicSub(&global_4.atomic_arr[1], 1);
    atomicSub(&global_2, 1u);
    atomicSub(&global_3[1], 1);
    atomicSub(&global_5.atomic_scalar, 1u);
    atomicSub(&global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicMax(&global_0, 1u);
    atomicMax(&global_1[1], 1);
    atomicMax(&global_4.atomic_scalar, 1u);
    atomicMax(&global_4.atomic_arr[1], 1);
    atomicMax(&global_2, 1u);
    atomicMax(&global_3[1], 1);
    atomicMax(&global_5.atomic_scalar, 1u);
    atomicMax(&global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicMin(&global_0, 1u);
    atomicMin(&global_1[1], 1);
    atomicMin(&global_4.atomic_scalar, 1u);
    atomicMin(&global_4.atomic_arr[1], 1);
    atomicMin(&global_2, 1u);
    atomicMin(&global_3[1], 1);
    atomicMin(&global_5.atomic_scalar, 1u);
    atomicMin(&global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicAnd(&global_0, 1u);
    atomicAnd(&global_1[1], 1);
    atomicAnd(&global_4.atomic_scalar, 1u);
    atomicAnd(&global_4.atomic_arr[1], 1);
    atomicAnd(&global_2, 1u);
    atomicAnd(&global_3[1], 1);
    atomicAnd(&global_5.atomic_scalar, 1u);
    atomicAnd(&global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicOr(&global_0, 1u);
    atomicOr(&global_1[1], 1);
    atomicOr(&global_4.atomic_scalar, 1u);
    atomicOr(&global_4.atomic_arr[1], 1);
    atomicOr(&global_2, 1u);
    atomicOr(&global_3[1], 1);
    atomicOr(&global_5.atomic_scalar, 1u);
    atomicOr(&global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicXor(&global_0, 1u);
    atomicXor(&global_1[1], 1);
    atomicXor(&global_4.atomic_scalar, 1u);
    atomicXor(&global_4.atomic_arr[1], 1);
    atomicXor(&global_2, 1u);
    atomicXor(&global_3[1], 1);
    atomicXor(&global_5.atomic_scalar, 1u);
    atomicXor(&global_5.atomic_arr[1], 1);
    atomicExchange(&global_0, 1u);
    atomicExchange(&global_1[1], 1);
    atomicExchange(&global_4.atomic_scalar, 1u);
    atomicExchange(&global_4.atomic_arr[1], 1);
    atomicExchange(&global_2, 1u);
    atomicExchange(&global_3[1], 1);
    atomicExchange(&global_5.atomic_scalar, 1u);
    atomicExchange(&global_5.atomic_arr[1], 1);
    Struct_10 local_8 = atomicCompareExchangeWeak(&global_0, 1u, 2u);
    Struct_11 local_9 = atomicCompareExchangeWeak(&global_1[1], 1, 2);
    Struct_10 local_10 = atomicCompareExchangeWeak(&global_4.atomic_scalar, 1u, 2u);
    Struct_11 local_11 = atomicCompareExchangeWeak(&global_4.atomic_arr[1], 1, 2);
    Struct_10 local_12 = atomicCompareExchangeWeak(&global_2, 1u, 2u);
    Struct_11 local_13 = atomicCompareExchangeWeak(&global_3[1], 1, 2);
    Struct_10 local_14 = atomicCompareExchangeWeak(&global_5.atomic_scalar, 1u, 2u);
    Struct_11 local_15 = atomicCompareExchangeWeak(&global_5.atomic_arr[1], 1, 2);
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, int arg_1) {
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

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, int arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicSub(/* unknown type */ void arg_0, int arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMax(/* unknown type */ void arg_0, int arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicMin(/* unknown type */ void arg_0, int arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAnd(/* unknown type */ void arg_0, int arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, int arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicXor(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicExchange(/* unknown type */ void arg_0, int arg_1) {
}

Struct_10 atomicCompareExchangeWeak(/* unknown type */ void arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(/* unknown type */ void arg_0, int arg_1, int arg_2) {
}

Struct_10 atomicCompareExchangeWeak(/* unknown type */ void arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(/* unknown type */ void arg_0, int arg_1, int arg_2) {
}

Struct_10 atomicCompareExchangeWeak(/* unknown type */ void arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(/* unknown type */ void arg_0, int arg_1, int arg_2) {
}

Struct_10 atomicCompareExchangeWeak(/* unknown type */ void arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(/* unknown type */ void arg_0, int arg_1, int arg_2) {
}
