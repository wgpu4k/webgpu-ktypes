#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint hit_num;
    uint selected_hit;
};
Struct_1 global_1;
Struct_1 global_2;

void miss(float3 origin, float3 dir, float t_min) {
}

void closest_hit_main(float3 origin, float3 dir, float4x3 obj_to_world, float4x3 world_to_obj) {
}

void ray_gen_main(uint3 id, uint3 num_invocations) {
    global_1 = Struct_1();
    float3 local_0 = (float3(id) / float3(num_invocations));
    float3 local_1 = ((float3(local_0[0], 0.0f, local_0[1]) * 2.0f) - 1.0f);
}

void traceRay() {
}

void RayDesc() {
}

void any_hit_main(uint data, uint geo_idx, float max, uint kind) {
    global_2.hit_num = (global_2.hit_num + 1);
    global_2.selected_hit = data;
}
