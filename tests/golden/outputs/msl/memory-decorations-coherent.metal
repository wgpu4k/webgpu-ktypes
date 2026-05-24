#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    array<uint> values;
};

[[kernel]]
void main(Struct_2 global_0 [[buffer(0)]], Struct_2 global_1 [[buffer(1)]]) {
    global_0.values[0] = global_1.values[0];
}
