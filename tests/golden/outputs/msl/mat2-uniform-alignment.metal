#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    int a;
    float2x2 b;
};

[[kernel]]
void main(Struct_3 global_0 [[buffer(0)]]) {
}
