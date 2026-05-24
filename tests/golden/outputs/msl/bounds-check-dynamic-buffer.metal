#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint t;
};

[[kernel]]
void main(uint global_0 [[buffer(0)]], array<uint> global_1 [[buffer(1)]], array<Struct_1, 1> global_2 [[buffer(2)]], array<Struct_1, 1> global_3 [[buffer(3)]], array<Struct_1, 1> global_4 [[buffer(4)]], array<Struct_1, 1> global_5 [[buffer(0)]]) {
    uint local_0 = global_0;
    global_1[0] = global_2[local_0].t;
    global_1[1] = global_3[local_0].t;
    global_1[2] = global_4[local_0].t;
    global_1[3] = global_5[local_0].t;
}
