#version 450 core
#extension GL_EXT_shader_explicit_arithmetic_types_int64 : require
precision highp float;
precision highp int;

struct Struct_3 {
    uint64_t atomic_scalar;
    int64_t[2] atomic_arr;
};
struct Struct_11 {
    uint64_t old_value;
    bool exchanged;
};
struct Struct_12 {
    int64_t old_value;
    bool exchanged;
};
layout(set = 0, binding = 0) buffer uint64_t global_0;
layout(set = 0, binding = 1) buffer int64_t[2] global_1;
shared uint64_t global_2;
shared int64_t[2] global_3;
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
    uint64_t l0 = atomicLoad(global_0);
    int64_t l1 = atomicLoad(global_1[1]);
    uint64_t l2 = atomicLoad(global_4.atomic_scalar);
    int64_t l3 = atomicLoad(global_4.atomic_arr[1]);
    uint64_t l4 = atomicLoad(global_2);
    int64_t l5 = atomicLoad(global_3[1]);
    uint64_t l6 = atomicLoad(global_5.atomic_scalar);
    int64_t l7 = atomicLoad(global_5.atomic_arr[1]);
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
    Struct_11 cas_res_0 = atomicCompareExchangeWeak(global_0, 1u, 2u);
    Struct_12 cas_res_1 = atomicCompareExchangeWeak(global_1[1], 1, 2);
    Struct_11 cas_res_2 = atomicCompareExchangeWeak(global_4.atomic_scalar, 1u, 2u);
    Struct_12 cas_res_3 = atomicCompareExchangeWeak(global_4.atomic_arr[1], 1, 2);
    Struct_11 cas_res_4 = atomicCompareExchangeWeak(global_2, 1u, 2u);
    Struct_12 cas_res_5 = atomicCompareExchangeWeak(global_3[1], 1, 2);
    Struct_11 cas_res_6 = atomicCompareExchangeWeak(global_5.atomic_scalar, 1u, 2u);
    Struct_12 cas_res_7 = atomicCompareExchangeWeak(global_5.atomic_arr[1], 1, 2);
}

uint64_t atomicStore(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicStore(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicStore(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicStore(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicStore(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicStore(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicStore(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicStore(int64_t arg_0, int64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicLoad(uint64_t arg_0) {
}

int64_t atomicLoad(int64_t arg_0) {
}

uint64_t atomicLoad(uint64_t arg_0) {
}

int64_t atomicLoad(int64_t arg_0) {
}

uint64_t atomicLoad(uint64_t arg_0) {
}

int64_t atomicLoad(int64_t arg_0) {
}

uint64_t atomicLoad(uint64_t arg_0) {
}

int64_t atomicLoad(int64_t arg_0) {
}

void workgroupBarrier() {
}

uint64_t atomicAdd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAdd(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicAdd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAdd(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicAdd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAdd(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicAdd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAdd(int64_t arg_0, int64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicSub(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicSub(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicSub(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicSub(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicSub(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicSub(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicSub(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicSub(int64_t arg_0, int64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicMax(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMax(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicMax(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMax(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicMax(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMax(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicMax(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMax(int64_t arg_0, int64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicMin(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMin(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicMin(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMin(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicMin(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMin(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicMin(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicMin(int64_t arg_0, int64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicAnd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAnd(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicAnd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAnd(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicAnd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAnd(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicAnd(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicAnd(int64_t arg_0, int64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicOr(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicOr(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicOr(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicOr(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicOr(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicOr(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicOr(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicOr(int64_t arg_0, int64_t arg_1) {
}

void workgroupBarrier() {
}

uint64_t atomicXor(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicXor(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicXor(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicXor(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicXor(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicXor(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicXor(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicXor(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicExchange(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicExchange(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicExchange(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicExchange(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicExchange(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicExchange(int64_t arg_0, int64_t arg_1) {
}

uint64_t atomicExchange(uint64_t arg_0, uint64_t arg_1) {
}

int64_t atomicExchange(int64_t arg_0, int64_t arg_1) {
}

Struct_11 atomicCompareExchangeWeak(uint64_t arg_0, uint64_t arg_1, uint64_t arg_2) {
}

Struct_12 atomicCompareExchangeWeak(int64_t arg_0, int64_t arg_1, int64_t arg_2) {
}

Struct_11 atomicCompareExchangeWeak(uint64_t arg_0, uint64_t arg_1, uint64_t arg_2) {
}

Struct_12 atomicCompareExchangeWeak(int64_t arg_0, int64_t arg_1, int64_t arg_2) {
}

Struct_11 atomicCompareExchangeWeak(uint64_t arg_0, uint64_t arg_1, uint64_t arg_2) {
}

Struct_12 atomicCompareExchangeWeak(int64_t arg_0, int64_t arg_1, int64_t arg_2) {
}

Struct_11 atomicCompareExchangeWeak(uint64_t arg_0, uint64_t arg_1, uint64_t arg_2) {
}

Struct_12 atomicCompareExchangeWeak(int64_t arg_0, int64_t arg_1, int64_t arg_2) {
}

void main() {
    wgsl_cs_main(id);
}
