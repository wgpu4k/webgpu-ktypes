#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    /* unknown type */ void data;
};

uint collatz_iterations(uint n_base) {
    uint local_0 = n_base;
    uint local_1 = 0u;
    while (true) {
        if ((local_0 > 0u)) {
            if (((local_0 % 0u) == 0u)) {
                local_0 = (local_0 / 0u);
            } else {
                local_0 = ((0u * local_0) + 0u);
            }
            local_1 = (local_1 + 0u);
        } else {
            break;
        }
    }
    return local_1;
}

[[kernel]]
void main(Struct_2 global_0 [[buffer(0)]]) {
}
