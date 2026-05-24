#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    array<float, 2> inner;
};

[[kernel]]
void cs_main(Struct_2 global_0 [[buffer(0)]]) {
    Struct_2 local_0 = global_0;
}
