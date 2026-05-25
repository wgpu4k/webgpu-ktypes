#version 460 core
#extension GL_EXT_ray_query : enable
precision highp float;
precision highp int;

struct Struct_10 {
    uint kind;
    float t;
    uint instance_custom_data;
    uint instance_index;
    uint sbt_record_offset;
    uint geometry_index;
    uint primitive_index;
    vec2 barycentrics;
    bool front_face;
    mat4x3 object_to_world;
    mat4x3 world_to_object;
};
layout(set = 0, binding = 0) uniform accelerationStructureEXT global_0;

void wgsl_main_candidate() {
    vec3 pos = vec3(0.0f);
    vec3 dir = vec3(0.0f, 1.0f, 0.0f);
    rayQueryEXT rq;
    rayQueryInitialize(rq, global_0, RayDesc(4u, 0, 0.1f, 100.0f, pos, dir));
    Struct_10 intersection = rayQueryGetCandidateIntersection(rq);
    if ((intersection.kind == 3u)) {
        rayQueryGenerateIntersection(rq, 10.0f);
    } else {
        if ((intersection.kind == 1u)) {
            rayQueryConfirmIntersection(rq);
        } else {
            rayQueryTerminate(rq);
        }
    }
}

uint RayDesc(uint arg_0, int arg_1, float arg_2, float arg_3, vec3 arg_4, vec3 arg_5) {
}

rayQueryEXT rayQueryInitialize(rayQueryEXT arg_0, accelerationStructureEXT arg_1, uint arg_2) {
}

Struct_10 rayQueryGetCandidateIntersection(rayQueryEXT arg_0) {
}

void rayQueryGenerateIntersection(rayQueryEXT arg_0, float arg_1) {
}

void rayQueryConfirmIntersection(rayQueryEXT arg_0) {
}

void rayQueryTerminate(rayQueryEXT arg_0) {
}

void main() {
    wgsl_main_candidate();
}
