#version 460 core
#extension GL_EXT_ray_query : enable
precision highp float;
precision highp int;

 float global_0;
layout(set = 0, binding = 0) uniform accelerationStructureEXT global_1;

void wgsl_main() {
    rayQueryEXT rq;
    uint desc = RayDesc(4u, 0, (global_0 * 17.0f), (global_0 * 19.0f), vec3((global_0 * 23.0f)), vec3((global_0 * 29.0f), (global_0 * 31.0f), (global_0 * 37.0f)));
    rayQueryInitialize(rq, global_1, desc);
    while (true) {
        if (rayQueryProceed(rq)) {
        } else {
            break;
        }
    }
}

uint RayDesc(uint arg_0, int arg_1, float arg_2, float arg_3, vec3 arg_4, vec3 arg_5) {
}

rayQueryEXT rayQueryInitialize(rayQueryEXT arg_0, accelerationStructureEXT arg_1, uint arg_2) {
}

bool rayQueryProceed(rayQueryEXT arg_0) {
}

void main() {
    wgsl_main();
}
