#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint num_subgroups;
    uint subgroup_size;
};

[[kernel]]
void main() {
    subgroupBallot(((subgroup_invocation_id & 1u) == 1u));
    subgroupBallot();
    subgroupAll((subgroup_invocation_id != 0u));
    subgroupAny((subgroup_invocation_id == 0u));
    subgroupAdd(subgroup_invocation_id);
    subgroupMul(subgroup_invocation_id);
    subgroupMin(subgroup_invocation_id);
    subgroupMax(subgroup_invocation_id);
    subgroupAnd(subgroup_invocation_id);
    subgroupOr(subgroup_invocation_id);
    subgroupXor(subgroup_invocation_id);
    subgroupExclusiveAdd(subgroup_invocation_id);
    subgroupExclusiveMul(subgroup_invocation_id);
    subgroupInclusiveAdd(subgroup_invocation_id);
    subgroupInclusiveMul(subgroup_invocation_id);
    subgroupBroadcastFirst(subgroup_invocation_id);
    subgroupBroadcast(subgroup_invocation_id, 4u);
    subgroupShuffle(subgroup_invocation_id, ((sizes.subgroup_size - 1u) - subgroup_invocation_id));
    subgroupShuffleDown(subgroup_invocation_id, 1u);
    subgroupShuffleUp(subgroup_invocation_id, 1u);
    subgroupShuffleXor(subgroup_invocation_id, (sizes.subgroup_size - 1u));
    quadBroadcast(subgroup_invocation_id, 4u);
    quadSwapX(subgroup_invocation_id);
    quadSwapY(subgroup_invocation_id);
    quadSwapDiagonal(subgroup_invocation_id);
}

bool subgroupBallot(bool arg_0) {
}

void subgroupBallot() {
}

bool subgroupAll(bool arg_0) {
}

bool subgroupAny(bool arg_0) {
}

uint subgroupAdd(uint arg_0) {
}

uint subgroupMul(uint arg_0) {
}

uint subgroupMin(uint arg_0) {
}

uint subgroupMax(uint arg_0) {
}

uint subgroupAnd(uint arg_0) {
}

uint subgroupOr(uint arg_0) {
}

uint subgroupXor(uint arg_0) {
}

uint subgroupExclusiveAdd(uint arg_0) {
}

uint subgroupExclusiveMul(uint arg_0) {
}

uint subgroupInclusiveAdd(uint arg_0) {
}

uint subgroupInclusiveMul(uint arg_0) {
}

uint subgroupBroadcastFirst(uint arg_0) {
}

uint subgroupBroadcast(uint arg_0, uint arg_1) {
}

uint subgroupShuffle(uint arg_0, uint arg_1) {
}

uint subgroupShuffleDown(uint arg_0, uint arg_1) {
}

uint subgroupShuffleUp(uint arg_0, uint arg_1) {
}

uint subgroupShuffleXor(uint arg_0, uint arg_1) {
}

uint quadBroadcast(uint arg_0, uint arg_1) {
}

uint quadSwapX(uint arg_0) {
}

uint quadSwapY(uint arg_0) {
}

uint quadSwapDiagonal(uint arg_0) {
}
