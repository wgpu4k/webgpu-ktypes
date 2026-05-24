#version 450 core
precision highp float;
precision highp int;

 uint global_0 = 0u;
shared int[] global_1;

void wgsl_test_workgroupUniformLoad(uvec3 workgroup_id) {
    int x = global_1[workgroup_id[0]];
    int val = workgroupUniformLoad(x);
    if ((val > 10)) {
    }
}

void workgroupUniformLoad() {
}

void workgroupBarrier() {
}

void main() {
    wgsl_test_workgroupUniformLoad(workgroup_id);
}
