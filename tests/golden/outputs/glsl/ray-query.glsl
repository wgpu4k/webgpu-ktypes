#version 460 core
#extension GL_EXT_ray_query : enable
precision highp float;
precision highp int;

struct Struct_3 {
    uint visible;
    vec3 normal;
};
struct Struct_8 {
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
layout(set = 0, binding = 1) buffer Struct_3 global_1;

Struct_8 query_loop(vec3 pos, vec3 dir, accelerationStructureEXT acs) {
    rayQueryEXT rq;
    rayQueryInitialize(rq, acs, RayDesc(4u, 0, 0.1f, 100.0f, pos, dir));
    while (true) {
        if (rayQueryProceed(rq)) {
        } else {
            break;
        }
    }
    return rayQueryGetCommittedIntersection(rq);
}

uint RayDesc(uint arg_0, int arg_1, float arg_2, float arg_3, vec3 arg_4, vec3 arg_5) {
}

rayQueryEXT rayQueryInitialize(rayQueryEXT arg_0, accelerationStructureEXT arg_1, uint arg_2) {
}

bool rayQueryProceed(rayQueryEXT arg_0) {
}

Struct_8 rayQueryGetCommittedIntersection(rayQueryEXT arg_0) {
}

vec3 get_torus_normal(vec3 world_point, Struct_8 intersection) {
    vec4 local_point = (intersection.world_to_object * vec4(world_point, 1.0f));
    vec2 point_on_guiding_line = (normalize(local_point.xy) * 2.4f);
    vec4 world_point_on_guiding_line = (intersection.object_to_world * vec4(point_on_guiding_line, 0.0f, 1.0f));
    return normalize((world_point - world_point_on_guiding_line));
}

vec2 normalize(vec2 arg_0) {
}

vec3 normalize(vec3 arg_0) {
}

void wgsl_main_candidate() {
    vec3 pos = vec3(0.0f);
    vec3 dir = vec3(0.0f, 1.0f, 0.0f);
    rayQueryEXT rq;
    rayQueryInitialize(rq, global_0, RayDesc(4u, 0, 0.1f, 100.0f, pos, dir));
    Struct_8 intersection = rayQueryGetCandidateIntersection(rq);
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

Struct_8 rayQueryGetCandidateIntersection(rayQueryEXT arg_0) {
}

void rayQueryGenerateIntersection(rayQueryEXT arg_0, float arg_1) {
}

void rayQueryConfirmIntersection(rayQueryEXT arg_0) {
}

void rayQueryTerminate(rayQueryEXT arg_0) {
}

void wgsl_main() {
    vec3 pos = vec3(0.0f);
    vec3 dir = vec3(0.0f, 1.0f, 0.0f);
    Struct_8 intersection = query_loop(pos, dir, global_0);
    global_1.visible = uint((intersection.kind == 0u));
    global_1.normal = get_torus_normal((dir * intersection.t), intersection);
}

void main() {
    wgsl_main_candidate();
}

void main() {
    wgsl_main();
}
