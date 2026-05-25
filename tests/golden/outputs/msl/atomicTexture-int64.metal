#include <metal_stdlib>
using namespace metal;

[[kernel]]
void cs_main(texture2d<float> global_0 [[texture(0)]]) {
    textureAtomicMax(global_0, int2(0, 0), 1u);
    workgroupBarrier();
    textureAtomicMin(global_0, int2(0, 0), 1u);
}

texture2d<float> textureAtomicMax(texture2d<float> arg_0, int2 arg_1, ulong arg_2) {
}

void workgroupBarrier() {
}

texture2d<float> textureAtomicMin(texture2d<float> arg_0, int2 arg_1, ulong arg_2) {
}
