#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    uint atomic_scalar;
    int[2] atomic_arr;
};
struct Struct_10 {
    uint old_value;
    bool exchanged;
};
struct Struct_11 {
    int old_value;
    bool exchanged;
};
layout(set = 0, binding = 0) buffer uint global_0;
layout(set = 0, binding = 1) buffer int[2] global_1;
shared uint global_2;
shared int[2] global_3;
layout(set = 0, binding = 2) buffer Struct_3 global_4;
shared Struct_3 global_5;

void wgsl_cs_main(uvec3 id) {
    atomicStore(global_0, 1u);
    atomicStore(global_1[1], 1);
    atomicStore(global_4.atomic_scalar, 1u);
    atomicStore(global_4.atomic_arr[1], 1);
    atomicStore(global_2, 1u);
    atomicStore(global_3[1], 1);
    atomicStore(global_5.atomic_scalar, 1u);
    atomicStore(global_5.atomic_arr[1], 1);
    workgroupBarrier();
    uint l0 = atomicLoad(global_0);
    int l1 = atomicLoad(global_1[1]);
    uint l2 = atomicLoad(global_4.atomic_scalar);
    int l3 = atomicLoad(global_4.atomic_arr[1]);
    uint l4 = atomicLoad(global_2);
    int l5 = atomicLoad(global_3[1]);
    uint l6 = atomicLoad(global_5.atomic_scalar);
    int l7 = atomicLoad(global_5.atomic_arr[1]);
    workgroupBarrier();
    atomicAdd(global_0, 1u);
    atomicAdd(global_1[1], 1);
    atomicAdd(global_4.atomic_scalar, 1u);
    atomicAdd(global_4.atomic_arr[1], 1);
    atomicAdd(global_2, 1u);
    atomicAdd(global_3[1], 1);
    atomicAdd(global_5.atomic_scalar, 1u);
    atomicAdd(global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicSub(global_0, 1u);
    atomicSub(global_1[1], 1);
    atomicSub(global_4.atomic_scalar, 1u);
    atomicSub(global_4.atomic_arr[1], 1);
    atomicSub(global_2, 1u);
    atomicSub(global_3[1], 1);
    atomicSub(global_5.atomic_scalar, 1u);
    atomicSub(global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicMax(global_0, 1u);
    atomicMax(global_1[1], 1);
    atomicMax(global_4.atomic_scalar, 1u);
    atomicMax(global_4.atomic_arr[1], 1);
    atomicMax(global_2, 1u);
    atomicMax(global_3[1], 1);
    atomicMax(global_5.atomic_scalar, 1u);
    atomicMax(global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicMin(global_0, 1u);
    atomicMin(global_1[1], 1);
    atomicMin(global_4.atomic_scalar, 1u);
    atomicMin(global_4.atomic_arr[1], 1);
    atomicMin(global_2, 1u);
    atomicMin(global_3[1], 1);
    atomicMin(global_5.atomic_scalar, 1u);
    atomicMin(global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicAnd(global_0, 1u);
    atomicAnd(global_1[1], 1);
    atomicAnd(global_4.atomic_scalar, 1u);
    atomicAnd(global_4.atomic_arr[1], 1);
    atomicAnd(global_2, 1u);
    atomicAnd(global_3[1], 1);
    atomicAnd(global_5.atomic_scalar, 1u);
    atomicAnd(global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicOr(global_0, 1u);
    atomicOr(global_1[1], 1);
    atomicOr(global_4.atomic_scalar, 1u);
    atomicOr(global_4.atomic_arr[1], 1);
    atomicOr(global_2, 1u);
    atomicOr(global_3[1], 1);
    atomicOr(global_5.atomic_scalar, 1u);
    atomicOr(global_5.atomic_arr[1], 1);
    workgroupBarrier();
    atomicXor(global_0, 1u);
    atomicXor(global_1[1], 1);
    atomicXor(global_4.atomic_scalar, 1u);
    atomicXor(global_4.atomic_arr[1], 1);
    atomicXor(global_2, 1u);
    atomicXor(global_3[1], 1);
    atomicXor(global_5.atomic_scalar, 1u);
    atomicXor(global_5.atomic_arr[1], 1);
    atomicExchange(global_0, 1u);
    atomicExchange(global_1[1], 1);
    atomicExchange(global_4.atomic_scalar, 1u);
    atomicExchange(global_4.atomic_arr[1], 1);
    atomicExchange(global_2, 1u);
    atomicExchange(global_3[1], 1);
    atomicExchange(global_5.atomic_scalar, 1u);
    atomicExchange(global_5.atomic_arr[1], 1);
    Struct_10 cas_res_0 = atomicCompareExchangeWeak(global_0, 1u, 2u);
    Struct_11 cas_res_1 = atomicCompareExchangeWeak(global_1[1], 1, 2);
    Struct_10 cas_res_2 = atomicCompareExchangeWeak(global_4.atomic_scalar, 1u, 2u);
    Struct_11 cas_res_3 = atomicCompareExchangeWeak(global_4.atomic_arr[1], 1, 2);
    Struct_10 cas_res_4 = atomicCompareExchangeWeak(global_2, 1u, 2u);
    Struct_11 cas_res_5 = atomicCompareExchangeWeak(global_3[1], 1, 2);
    Struct_10 cas_res_6 = atomicCompareExchangeWeak(global_5.atomic_scalar, 1u, 2u);
    Struct_11 cas_res_7 = atomicCompareExchangeWeak(global_5.atomic_arr[1], 1, 2);
}

uint atomicStore(uint arg_0, uint arg_1) {
}

int atomicStore(int arg_0, int arg_1) {
}

uint atomicStore(uint arg_0, uint arg_1) {
}

int atomicStore(int arg_0, int arg_1) {
}

uint atomicStore(uint arg_0, uint arg_1) {
}

int atomicStore(int arg_0, int arg_1) {
}

uint atomicStore(uint arg_0, uint arg_1) {
}

int atomicStore(int arg_0, int arg_1) {
}

void workgroupBarrier() {
}

uint atomicLoad(uint arg_0) {
}

int atomicLoad(int arg_0) {
}

uint atomicLoad(uint arg_0) {
}

int atomicLoad(int arg_0) {
}

uint atomicLoad(uint arg_0) {
}

int atomicLoad(int arg_0) {
}

uint atomicLoad(uint arg_0) {
}

int atomicLoad(int arg_0) {
}

void workgroupBarrier() {
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

int atomicAdd(int arg_0, int arg_1) {
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

int atomicAdd(int arg_0, int arg_1) {
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

int atomicAdd(int arg_0, int arg_1) {
}

uint atomicAdd(uint arg_0, uint arg_1) {
}

int atomicAdd(int arg_0, int arg_1) {
}

void workgroupBarrier() {
}

uint atomicSub(uint arg_0, uint arg_1) {
}

int atomicSub(int arg_0, int arg_1) {
}

uint atomicSub(uint arg_0, uint arg_1) {
}

int atomicSub(int arg_0, int arg_1) {
}

uint atomicSub(uint arg_0, uint arg_1) {
}

int atomicSub(int arg_0, int arg_1) {
}

uint atomicSub(uint arg_0, uint arg_1) {
}

int atomicSub(int arg_0, int arg_1) {
}

void workgroupBarrier() {
}

uint atomicMax(uint arg_0, uint arg_1) {
}

int atomicMax(int arg_0, int arg_1) {
}

uint atomicMax(uint arg_0, uint arg_1) {
}

int atomicMax(int arg_0, int arg_1) {
}

uint atomicMax(uint arg_0, uint arg_1) {
}

int atomicMax(int arg_0, int arg_1) {
}

uint atomicMax(uint arg_0, uint arg_1) {
}

int atomicMax(int arg_0, int arg_1) {
}

void workgroupBarrier() {
}

uint atomicMin(uint arg_0, uint arg_1) {
}

int atomicMin(int arg_0, int arg_1) {
}

uint atomicMin(uint arg_0, uint arg_1) {
}

int atomicMin(int arg_0, int arg_1) {
}

uint atomicMin(uint arg_0, uint arg_1) {
}

int atomicMin(int arg_0, int arg_1) {
}

uint atomicMin(uint arg_0, uint arg_1) {
}

int atomicMin(int arg_0, int arg_1) {
}

void workgroupBarrier() {
}

uint atomicAnd(uint arg_0, uint arg_1) {
}

int atomicAnd(int arg_0, int arg_1) {
}

uint atomicAnd(uint arg_0, uint arg_1) {
}

int atomicAnd(int arg_0, int arg_1) {
}

uint atomicAnd(uint arg_0, uint arg_1) {
}

int atomicAnd(int arg_0, int arg_1) {
}

uint atomicAnd(uint arg_0, uint arg_1) {
}

int atomicAnd(int arg_0, int arg_1) {
}

void workgroupBarrier() {
}

uint atomicOr(uint arg_0, uint arg_1) {
}

int atomicOr(int arg_0, int arg_1) {
}

uint atomicOr(uint arg_0, uint arg_1) {
}

int atomicOr(int arg_0, int arg_1) {
}

uint atomicOr(uint arg_0, uint arg_1) {
}

int atomicOr(int arg_0, int arg_1) {
}

uint atomicOr(uint arg_0, uint arg_1) {
}

int atomicOr(int arg_0, int arg_1) {
}

void workgroupBarrier() {
}

uint atomicXor(uint arg_0, uint arg_1) {
}

int atomicXor(int arg_0, int arg_1) {
}

uint atomicXor(uint arg_0, uint arg_1) {
}

int atomicXor(int arg_0, int arg_1) {
}

uint atomicXor(uint arg_0, uint arg_1) {
}

int atomicXor(int arg_0, int arg_1) {
}

uint atomicXor(uint arg_0, uint arg_1) {
}

int atomicXor(int arg_0, int arg_1) {
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

int atomicExchange(int arg_0, int arg_1) {
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

int atomicExchange(int arg_0, int arg_1) {
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

int atomicExchange(int arg_0, int arg_1) {
}

uint atomicExchange(uint arg_0, uint arg_1) {
}

int atomicExchange(int arg_0, int arg_1) {
}

Struct_10 atomicCompareExchangeWeak(uint arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(int arg_0, int arg_1, int arg_2) {
}

Struct_10 atomicCompareExchangeWeak(uint arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(int arg_0, int arg_1, int arg_2) {
}

Struct_10 atomicCompareExchangeWeak(uint arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(int arg_0, int arg_1, int arg_2) {
}

Struct_10 atomicCompareExchangeWeak(uint arg_0, uint arg_1, uint arg_2) {
}

Struct_11 atomicCompareExchangeWeak(int arg_0, int arg_1, int arg_2) {
}

void main() {
    wgsl_cs_main(id);
}
