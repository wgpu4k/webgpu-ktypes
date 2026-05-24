#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float3 v3;
    float v1;
};
bool global_0 = true;
/* unknown type */ void global_1;
uint global_2;

void test_msl_packed_vec3_as_arg(float3 arg) {
}

void test_msl_packed_vec3() {
    global_9.v3 = float3(1.0f);
    int local_0 = 1;
    global_9.v3[0] = 1.0f;
    global_9.v3[0] = 2.0f;
    global_9.v3[local_0] = 3.0f;
    Struct_2 local_1 = global_9;
    float3 local_2 = local_1.v3;
    float2 local_3 = local_1.v3.zx;
    float3 local_4 = (local_1.v3 * float3x3());
    float3 local_5 = (float3x3() * local_1.v3);
    float3 local_6 = (local_1.v3 * 2.0f);
    float3 local_7 = (2.0f * local_1.v3);
}

[[kernel]]
void main(/* unknown type */ void global_3 [[buffer(2)]], /* unknown type */ void global_4 [[buffer(3)]], float3 global_5 [[buffer(4)]], float3x2 global_6 [[buffer(5)]], /* unknown type */ void global_7 [[buffer(6)]], /* unknown type */ void global_8 [[buffer(7)]], Struct_2 global_9 [[buffer(1)]]) {
}

void atomicStore() {
}
