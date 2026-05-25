#version 450 core
precision highp float;
precision highp int;

 coop_mat8x8<f32, A> global_0;
 coop_mat8x8<f32, B> global_1;
layout(set = 0, binding = 0) buffer float[] global_2;

void wgsl_main() {
    coop_mat8x8<f32, C> c = coopLoad(global_2[4]);
    coop_mat8x8<f32, C> d = coopMultiplyAdd(global_0, global_1, c);
    coopStore(d, global_2[0]);
    c = d;
}

coop_mat8x8<f32, C> coopLoad(float arg_0) {
}

coop_mat8x8<f32, C> coopMultiplyAdd(coop_mat8x8<f32, A> arg_0, coop_mat8x8<f32, B> arg_1, coop_mat8x8<f32, C> arg_2) {
}

void coopStore(coop_mat8x8<f32, C> arg_0, float arg_1) {
}

void main() {
    wgsl_main();
}
