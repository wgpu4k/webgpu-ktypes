#include <metal_stdlib>
using namespace metal;

int five() {
    return 5;
}

[[kernel]]
void main(float global_0 [[buffer(0)]]) {
    global_0;
    global_0;
    int local_0 = 5;
    local_0;
    five();
    int local_1 = five();
    float local_2 = global_0;
}
