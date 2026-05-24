#include <metal_stdlib>
using namespace metal;
int global_0 = 1;
int global_1 = 2;
bool global_2 = false;

[[kernel]]
void foo() {
    int local_0 = ((global_0 + global_1) - 2);
    bool local_1 = false;
}

float3 any(float3 arg_0) {
}

float3 any(float3 arg_0) {
}
