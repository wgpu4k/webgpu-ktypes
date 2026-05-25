#include <metal_stdlib>
using namespace metal;
struct Struct_10 {
    uint kind;
    char _pad0[4];
    float t;
    char _pad4[4];
    uint instance_custom_data;
    char _pad8[4];
    uint instance_index;
    char _pad12[4];
    uint sbt_record_offset;
    char _pad16[4];
    uint geometry_index;
    char _pad20[4];
    uint primitive_index;
    char _pad24[4];
    float2 barycentrics;
    char _pad28[8];
    bool front_face;
    char _pad36[4];
    float4x3 object_to_world;
    char _pad40[48];
    float4x3 world_to_object;
};

[[kernel]]
void main_candidate(raytracing::acceleration_structure global_0 [[buffer(0)]]) {
    float3 local_0 = float3(0.0f);
    float3 local_1 = float3(0.0f, 1.0f, 0.0f);
    raytracing::ray_query local_2;
    rayQueryInitialize(&local_2, global_0, RayDesc(4u, 0, 0.1f, 100.0f, local_0, local_1));
    Struct_10 local_3 = rayQueryGetCandidateIntersection(&local_2);
    if ((local_3.kind == 3u)) {
        rayQueryGenerateIntersection(&local_2, 10.0f);
    } else {
        if ((local_3.kind == 1u)) {
            rayQueryConfirmIntersection(&local_2);
        } else {
            rayQueryTerminate(&local_2);
        }
    }
}

uint RayDesc(uint arg_0, int arg_1, float arg_2, float arg_3, float3 arg_4, float3 arg_5) {
}

/* unknown type */ void rayQueryInitialize(/* unknown type */ void arg_0, raytracing::acceleration_structure arg_1, uint arg_2) {
}

Struct_10 rayQueryGetCandidateIntersection(/* unknown type */ void arg_0) {
}

void rayQueryGenerateIntersection(/* unknown type */ void arg_0, float arg_1) {
}

void rayQueryConfirmIntersection(/* unknown type */ void arg_0) {
}

void rayQueryTerminate(/* unknown type */ void arg_0) {
}
