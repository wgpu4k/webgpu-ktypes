#version 450 core
precision highp float;
precision highp int;

struct Struct_1 {
    uint num_subgroups;
    uint subgroup_size;
};

void wgsl_main(Struct_1 sizes, uint subgroup_id, uint subgroup_invocation_id) {
}

void subgroupBallot() {
}

void subgroupBallot() {
}

void subgroupAll() {
}

void subgroupAny() {
}

void subgroupAdd() {
}

void subgroupMul() {
}

void subgroupMin() {
}

void subgroupMax() {
}

void subgroupAnd() {
}

void subgroupOr() {
}

void subgroupXor() {
}

void subgroupExclusiveAdd() {
}

void subgroupExclusiveMul() {
}

void subgroupInclusiveAdd() {
}

void subgroupInclusiveMul() {
}

void subgroupBroadcastFirst() {
}

void subgroupBroadcast() {
}

void subgroupShuffle() {
}

void subgroupShuffleDown() {
}

void subgroupShuffleUp() {
}

void subgroupShuffleXor() {
}

void quadBroadcast() {
}

void quadSwapX() {
}

void quadSwapY() {
}

void quadSwapDiagonal() {
}

void main() {
    wgsl_main(sizes, subgroup_id, subgroup_invocation_id);
}
