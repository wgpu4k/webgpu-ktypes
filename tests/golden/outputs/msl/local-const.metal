#include <metal_stdlib>
using namespace metal;
int global_0 = 4;
int global_1 = 4;
uint global_2 = 4;
float global_3 = 4;
float global_4 = 2.0f;
float3 global_5 = float3(global_0, global_0, global_0);

void const_in_fn() {
    int local_0 = 4;
    int local_1 = 4;
    uint local_2 = 4;
    float local_3 = 4;
    float3 local_4 = float3(local_0, local_0, local_0);
    float local_5 = 2.0f;
    int local_6 = global_0;
    int local_7 = global_1;
    uint local_8 = global_2;
    float local_9 = global_3;
    float3 local_10 = global_5;
    float local_11 = global_4;
}

[[kernel]]
void main() {
}
