#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main(array<int, 2> global_0 [[buffer(0)]]) {
    array<int> local_0;
    {
        int local_1 = 0;
        while (true) {
            if ((local_1 < 2)) {
                {
                    global_0[local_1] = local_0[local_1];
                }
                local_1 = (local_1 + 1);
            } else {
                break;
            }
        }
    }
}
