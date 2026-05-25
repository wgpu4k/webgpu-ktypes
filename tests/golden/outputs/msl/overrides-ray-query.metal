#include <metal_stdlib>
using namespace metal;
float global_0;

[[kernel]]
void main(raytracing::acceleration_structure global_1 [[buffer(0)]]) {
    raytracing::ray_query local_0;
    uint local_1 = RayDesc(4u, 0, (global_0 * 17.0f), (global_0 * 19.0f), float3((global_0 * 23.0f)), float3((global_0 * 29.0f), (global_0 * 31.0f), (global_0 * 37.0f)));
    rayQueryInitialize(&local_0, global_1, local_1);
    while (true) {
        if (rayQueryProceed(&local_0)) {
        } else {
            break;
        }
    }
}

uint RayDesc(uint arg_0, int arg_1, float arg_2, float arg_3, float3 arg_4, float3 arg_5) {
}

/* unknown type */ void rayQueryInitialize(/* unknown type */ void arg_0, raytracing::acceleration_structure arg_1, uint arg_2) {
}

bool rayQueryProceed(/* unknown type */ void arg_0) {
}
