#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    array<uint> data;
};

uint collatz_iterations(uint n_base) {
    uint local_0 = n_base;
    uint local_1 = 0u;
    while (true) {
        if ((local_0 > 1u)) {
            if (((local_0 % 2u) == 0u)) {
                local_0 = (local_0 / 2u);
            } else {
                local_0 = ((3u * local_0) + 1u);
            }
            local_1 = (local_1 + 1u);
        } else {
            break;
        }
    }
    return local_1;
}

[[kernel]]
void main(Struct_2 global_0 [[buffer(0)]]) {
    global_0.data[global_id[0]] = collatz_iterations(global_0.data[global_id[0]]);
}
