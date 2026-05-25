#include <metal_stdlib>
using namespace metal;

[[kernel]]
void cs_main(texture2d<float> global_0 [[texture(0)]], texture2d<float> global_1 [[texture(1)]]) {
    textureAtomicMax(global_0, int2(0, 0), 1u);
    textureAtomicMin(global_0, int2(0, 0), 1u);
    textureAtomicAdd(global_0, int2(0, 0), 1u);
    textureAtomicAnd(global_0, int2(0, 0), 1u);
    textureAtomicOr(global_0, int2(0, 0), 1u);
    textureAtomicXor(global_0, int2(0, 0), 1u);
    textureAtomicMax(global_1, int2(0, 0), 1);
    textureAtomicMin(global_1, int2(0, 0), 1);
    textureAtomicAdd(global_1, int2(0, 0), 1);
    textureAtomicAnd(global_1, int2(0, 0), 1);
    textureAtomicOr(global_1, int2(0, 0), 1);
    textureAtomicXor(global_1, int2(0, 0), 1);
}

texture2d<float> textureAtomicMax(texture2d<float> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureAtomicMin(texture2d<float> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureAtomicAdd(texture2d<float> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureAtomicAnd(texture2d<float> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureAtomicOr(texture2d<float> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureAtomicXor(texture2d<float> arg_0, int2 arg_1, uint arg_2) {
}

texture2d<float> textureAtomicMax(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureAtomicMin(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureAtomicAdd(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureAtomicAnd(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureAtomicOr(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}

texture2d<float> textureAtomicXor(texture2d<float> arg_0, int2 arg_1, int arg_2) {
}
