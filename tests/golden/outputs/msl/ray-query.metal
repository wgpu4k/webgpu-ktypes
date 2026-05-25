#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    uint visible;
    float3 normal;
};
struct Struct_8 {
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

Struct_8 query_loop(float3 pos, float3 dir, raytracing::acceleration_structure acs) {
    raytracing::ray_query local_0;
    rayQueryInitialize(&local_0, acs, RayDesc(4u, 0, 0.1f, 100.0f, pos, dir));
    while (true) {
        if (rayQueryProceed(&local_0)) {
        } else {
            break;
        }
    }
    return rayQueryGetCommittedIntersection(&local_0);
}

uint RayDesc(uint arg_0, int arg_1, float arg_2, float arg_3, float3 arg_4, float3 arg_5) {
}

/* unknown type */ void rayQueryInitialize(/* unknown type */ void arg_0, raytracing::acceleration_structure arg_1, uint arg_2) {
}

bool rayQueryProceed(/* unknown type */ void arg_0) {
}

Struct_8 rayQueryGetCommittedIntersection(/* unknown type */ void arg_0) {
}

float3 get_torus_normal(float3 world_point, Struct_8 intersection) {
    float4 local_0 = (intersection.world_to_object * float4(world_point, 1.0f));
    float2 local_1 = (normalize(local_0.xy) * 2.4f);
    float4 local_2 = (intersection.object_to_world * float4(local_1, 0.0f, 1.0f));
    return normalize((world_point - local_2));
}

float2 normalize(float2 arg_0) {
}

float3 normalize(float3 arg_0) {
}

[[kernel]]
void main_candidate(raytracing::acceleration_structure global_0 [[buffer(0)]], Struct_3 global_1 [[buffer(1)]]) {
    float3 local_0 = float3(0.0f);
    float3 local_1 = float3(0.0f, 1.0f, 0.0f);
    raytracing::ray_query local_2;
    rayQueryInitialize(&local_2, global_0, RayDesc(4u, 0, 0.1f, 100.0f, local_0, local_1));
    Struct_8 local_3 = rayQueryGetCandidateIntersection(&local_2);
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

Struct_8 rayQueryGetCandidateIntersection(/* unknown type */ void arg_0) {
}

void rayQueryGenerateIntersection(/* unknown type */ void arg_0, float arg_1) {
}

void rayQueryConfirmIntersection(/* unknown type */ void arg_0) {
}

void rayQueryTerminate(/* unknown type */ void arg_0) {
}

[[kernel]]
void main(raytracing::acceleration_structure global_0 [[buffer(0)]], Struct_3 global_1 [[buffer(1)]]) {
    float3 local_0 = float3(0.0f);
    float3 local_1 = float3(0.0f, 1.0f, 0.0f);
    Struct_8 local_2 = query_loop(local_0, local_1, global_0);
    global_1.visible = uint((local_2.kind == 0u));
    global_1.normal = get_torus_normal((local_1 * local_2.t), local_2);
}
