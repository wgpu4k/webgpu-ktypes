#include <metal_stdlib>
using namespace metal;

float compute() {
    float local_0 = (global_0 + 9001.0f);
    return local_0;
}

[[kernel]]
void main(float global_0 [[buffer(0)]]) {
    compute();
}
