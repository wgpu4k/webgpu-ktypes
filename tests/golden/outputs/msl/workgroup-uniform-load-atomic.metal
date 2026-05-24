#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    uint atomic_scalar;
    array<int, 2> atomic_arr;
};
uint global_0;
int global_1;
Struct_3 global_2;

[[kernel]]
void test_atomic_workgroup_uniform_load() {
    uint local_0 = (workgroup_id[0] + (workgroup_id[1] * 32768));
    atomicOr(&global_0, uint((local_0 >= 64)));
    atomicAdd(&global_1, 1);
    atomicStore(&global_2.atomic_scalar, 1u);
    atomicAdd(&global_2.atomic_arr[0], 1);
    workgroupBarrier();
    uint local_1 = workgroupUniformLoad(&global_0);
    int local_2 = workgroupUniformLoad(&global_1);
    uint local_3 = workgroupUniformLoad(&global_2.atomic_scalar);
    int local_4 = workgroupUniformLoad(&global_2.atomic_arr[0]);
    if (((((local_1 == 0u) && (local_2 > 0)) && (local_3 > 0u)) && (local_4 > 0))) {
        return;
    }
}

/* unknown type */ void atomicOr(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, int arg_1) {
}

/* unknown type */ void atomicStore(/* unknown type */ void arg_0, uint arg_1) {
}

/* unknown type */ void atomicAdd(/* unknown type */ void arg_0, int arg_1) {
}

void workgroupBarrier() {
}

/* unknown type */ void workgroupUniformLoad(/* unknown type */ void arg_0) {
}

/* unknown type */ void workgroupUniformLoad(/* unknown type */ void arg_0) {
}

/* unknown type */ void workgroupUniformLoad(/* unknown type */ void arg_0) {
}

/* unknown type */ void workgroupUniformLoad(/* unknown type */ void arg_0) {
}
