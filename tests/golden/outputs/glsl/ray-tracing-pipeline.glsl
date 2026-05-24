#version 460 core
#extension GL_EXT_ray_query : enable
precision highp float;
precision highp int;

struct Struct_1 {
    uint hit_num;
    uint selected_hit;
};
layout(set = 0, binding = 0) uniform accelerationStructureEXT global_0;
 Struct_1 global_1;
 Struct_1 global_2;

void miss(vec3 origin, vec3 dir, float t_min) {
}

void closest_hit_main(vec3 origin, vec3 dir, mat4x3 obj_to_world, mat4x3 world_to_obj) {
}

void ray_gen_main(uvec3 id, uvec3 num_invocations) {
    global_1 = Struct_1();
    vec3 shift = (vec3(id) / vec3(num_invocations));
    vec3 ray_shift = ((vec3(shift[0], 0.0f, shift[1]) * 2.0f) - 1.0f);
    traceRay(global_0, RayDesc(0u, 0, 0.01f, 100.0f, vec3(0.0f), (vec3(0.0f, 1.0f, 0.0f) + ray_shift)), global_1);
}

uint RayDesc(uint arg_0, int arg_1, float arg_2, float arg_3, vec3 arg_4, vec3 arg_5) {
}

accelerationStructureEXT traceRay(accelerationStructureEXT arg_0, uint arg_1, Struct_1 arg_2) {
}

void any_hit_main(uint data, uint geo_idx, float max, uint kind) {
    global_2.hit_num = (global_2.hit_num + 1);
    global_2.selected_hit = data;
}
