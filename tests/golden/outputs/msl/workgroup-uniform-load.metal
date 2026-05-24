#include <metal_stdlib>
using namespace metal;
uint global_0 = 128u;
array<int> global_1;

[[kernel]]
void test_workgroupUniformLoad() {
    /* unknown type */ void local_0 = &global_1[workgroup_id[0]];
    /* unknown type */ void local_1 = workgroupUniformLoad(local_0);
    if ((local_1 > 10)) {
        workgroupBarrier();
    }
}

/* unknown type */ void workgroupUniformLoad(/* unknown type */ void arg_0) {
}

void workgroupBarrier() {
}
