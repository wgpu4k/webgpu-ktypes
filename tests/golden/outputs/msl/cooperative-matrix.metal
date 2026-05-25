#include <metal_stdlib>
using namespace metal;
coop_mat8x8<f32, A> global_0;
coop_mat8x8<f32, B> global_1;

[[kernel]]
void main(array<float> global_2 [[buffer(0)]]) {
    coop_mat8x8<f32, C> local_0 = coopLoad(&global_2[4]);
    coop_mat8x8<f32, C> local_1 = coopMultiplyAdd(global_0, global_1, local_0);
    coopStore(local_1, &global_2[0]);
    local_0 = local_1;
}

coop_mat8x8<f32, C> coopLoad(/* unknown type */ void arg_0) {
}

coop_mat8x8<f32, C> coopMultiplyAdd(coop_mat8x8<f32, A> arg_0, coop_mat8x8<f32, B> arg_1, coop_mat8x8<f32, C> arg_2) {
}

void coopStore(coop_mat8x8<f32, C> arg_0, /* unknown type */ void arg_1) {
}
