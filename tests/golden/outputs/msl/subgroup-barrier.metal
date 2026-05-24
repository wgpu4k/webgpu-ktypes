#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main() {
    subgroupBarrier();
}

void subgroupBarrier() {
}
