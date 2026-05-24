#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    uint atomic_scalar;
    int[2] atomic_arr;
};
layout(set = 0, binding = 0) buffer uint global_0;
layout(set = 0, binding = 1) buffer int[2] global_1;
shared uint global_2;
shared int[2] global_3;
layout(set = 0, binding = 2) buffer Struct_3 global_4;
shared Struct_3 global_5;

void wgsl_cs_main(uvec3 id) {
    uint l0 = atomicLoad(global_0);
    int l1 = atomicLoad(global_1[1]);
    uint l2 = atomicLoad(global_4.atomic_scalar);
    int l3 = atomicLoad(global_4.atomic_arr[1]);
    uint l4 = atomicLoad(global_2);
    int l5 = atomicLoad(global_3[1]);
    uint l6 = atomicLoad(global_5.atomic_scalar);
    int l7 = atomicLoad(global_5.atomic_arr[1]);
    uint cas_res_0 = atomicCompareExchangeWeak(global_0, 0u, 0u);
    int cas_res_1 = atomicCompareExchangeWeak(global_1[1], 0, 0);
    uint cas_res_2 = atomicCompareExchangeWeak(global_4.atomic_scalar, 0u, 0u);
    int cas_res_3 = atomicCompareExchangeWeak(global_4.atomic_arr[1], 0, 0);
    uint cas_res_4 = atomicCompareExchangeWeak(global_2, 0u, 0u);
    int cas_res_5 = atomicCompareExchangeWeak(global_3[1], 0, 0);
    uint cas_res_6 = atomicCompareExchangeWeak(global_5.atomic_scalar, 0u, 0u);
    int cas_res_7 = atomicCompareExchangeWeak(global_5.atomic_arr[1], 0, 0);
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void atomicStore() {
}

void workgroupBarrier() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void atomicLoad() {
}

void workgroupBarrier() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void atomicAdd() {
}

void workgroupBarrier() {
}

void atomicSub() {
}

void atomicSub() {
}

void atomicSub() {
}

void atomicSub() {
}

void atomicSub() {
}

void atomicSub() {
}

void atomicSub() {
}

void atomicSub() {
}

void workgroupBarrier() {
}

void atomicMax() {
}

void atomicMax() {
}

void atomicMax() {
}

void atomicMax() {
}

void atomicMax() {
}

void atomicMax() {
}

void atomicMax() {
}

void atomicMax() {
}

void workgroupBarrier() {
}

void atomicMin() {
}

void atomicMin() {
}

void atomicMin() {
}

void atomicMin() {
}

void atomicMin() {
}

void atomicMin() {
}

void atomicMin() {
}

void atomicMin() {
}

void workgroupBarrier() {
}

void atomicAnd() {
}

void atomicAnd() {
}

void atomicAnd() {
}

void atomicAnd() {
}

void atomicAnd() {
}

void atomicAnd() {
}

void atomicAnd() {
}

void atomicAnd() {
}

void workgroupBarrier() {
}

void atomicOr() {
}

void atomicOr() {
}

void atomicOr() {
}

void atomicOr() {
}

void atomicOr() {
}

void atomicOr() {
}

void atomicOr() {
}

void atomicOr() {
}

void workgroupBarrier() {
}

void atomicXor() {
}

void atomicXor() {
}

void atomicXor() {
}

void atomicXor() {
}

void atomicXor() {
}

void atomicXor() {
}

void atomicXor() {
}

void atomicXor() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicExchange() {
}

void atomicCompareExchangeWeak() {
}

void atomicCompareExchangeWeak() {
}

void atomicCompareExchangeWeak() {
}

void atomicCompareExchangeWeak() {
}

void atomicCompareExchangeWeak() {
}

void atomicCompareExchangeWeak() {
}

void atomicCompareExchangeWeak() {
}

void atomicCompareExchangeWeak() {
}

void main() {
    wgsl_cs_main(id);
}
