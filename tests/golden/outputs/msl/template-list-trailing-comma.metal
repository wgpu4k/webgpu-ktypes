#include <metal_stdlib>
using namespace metal;
array<uint, 1> global_0;
array<uint, 1> global_1;

[[kernel]]
void main(array<uint> global_2 [[buffer(0)]], array<uint> global_3 [[buffer(1)]]) {
    global_0[0] = global_2[0];
    global_1[0] = global_3[0];
    global_3[0] = (global_0[0] + global_1[0]);
}
