#version 450 core
precision highp float;
precision highp int;

struct Struct_4 {
    mat4x4 view_proj;
    uvec4 num_lights;
};
struct Struct_6 {
    mat4x4 world;
    vec4 color;
};
struct Struct_8 {
    vec4 proj_position;
    vec3 world_normal;
    vec4 world_position;
};
struct Struct_9 {
    mat4x4 proj;
    vec4 pos;
    vec4 color;
};
layout(set = 0, binding = 2) uniform texture2D global_0;
layout(set = 0, binding = 3) uniform sampler_comparison global_1;
 vec3 global_2 = vec3(0.05f, 0.05f, 0.05f);
 uint global_3 = 10u;
layout(set = 0, binding = 0) uniform Struct_4 global_4;
layout(set = 1, binding = 0) uniform Struct_6 global_5;
layout(set = 0, binding = 1) buffer Struct_9[] global_6;
layout(set = 0, binding = 1) uniform Struct_9[10] global_7;

float fetch_shadow(uint light_id, vec4 homogeneous_coords) {
    if ((homogeneous_coords[3] <= 0.0f)) {
        return 1.0f;
    }
    vec2 flip_correction = vec2(0.5f, -(0.5f));
    float proj_correction = (1.0f / homogeneous_coords[3]);
    vec2 light_local = (((homogeneous_coords.xy * flip_correction) * proj_correction) + vec2(0.5f, 0.5f));
    return textureSampleCompareLevel(global_0, global_1, light_local, int(light_id), (homogeneous_coords[2] * proj_correction));
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, int arg_3, float arg_4) {
}

Struct_8 wgsl_vs_main(ivec4 position, ivec4 normal) {
    mat4x4 w = global_5.world;
    vec4 world_pos = (global_5.world * vec4(position));
    Struct_8 out;
    out.world_normal = (mat3x3(w[0].xyz, w[1].xyz, w[2].xyz) * vec3(normal.xyz));
    out.world_position = world_pos;
    out.proj_position = (global_4.view_proj * world_pos);
    return out;
}

vec4 wgsl_fs_main(Struct_8 in) {
    vec3 normal = normalize(in.world_normal);
    vec3 color = global_2;
    {
        uint i = 0u;
        while (true) {
            if ((i < min(global_4.num_lights[0], global_3))) {
                {
                    Struct_9 light = global_6[i];
                    float shadow = fetch_shadow(i, (light.proj * in.world_position));
                    vec3 light_dir = normalize((light.pos.xyz - in.world_position.xyz));
                    float diffuse = max(0.0f, dot(normal, light_dir));
                    color = ((shadow * diffuse) * light.color.xyz);
                }
                i = (i + 1u);
            } else {
                break;
            }
        }
    }
    return (vec4(color, 1.0f) * global_5.color);
}

vec3 normalize(vec3 arg_0) {
}

vec3 normalize(vec3 arg_0) {
}

vec3 dot(vec3 arg_0, vec3 arg_1) {
}

float max(float arg_0, vec3 arg_1) {
}

uint min(uint arg_0, uint arg_1) {
}

vec4 wgsl_fs_main_without_storage(Struct_8 in) {
    vec3 normal = normalize(in.world_normal);
    vec3 color = global_2;
    {
        uint i = 0u;
        while (true) {
            if ((i < min(global_4.num_lights[0], global_3))) {
                {
                    Struct_9 light = global_7[i];
                    float shadow = fetch_shadow(i, (light.proj * in.world_position));
                    vec3 light_dir = normalize((light.pos.xyz - in.world_position.xyz));
                    float diffuse = max(0.0f, dot(normal, light_dir));
                    color = ((shadow * diffuse) * light.color.xyz);
                }
                i = (i + 1u);
            } else {
                break;
            }
        }
    }
    return (vec4(color, 1.0f) * global_5.color);
}

vec3 normalize(vec3 arg_0) {
}

vec3 normalize(vec3 arg_0) {
}

vec3 dot(vec3 arg_0, vec3 arg_1) {
}

float max(float arg_0, vec3 arg_1) {
}

uint min(uint arg_0, uint arg_1) {
}

layout(location = 0) in ivec4 position;
layout(location = 1) in ivec4 normal;
layout(location = 0) out vec3 out_world_normal;
layout(location = 1) out vec4 out_world_position;
void main() {
    Struct_8 res = wgsl_vs_main(position, normal);
    gl_Position = res.proj_position;
    out_world_normal = res.world_normal;
    out_world_position = res.world_position;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(in);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main_without_storage(in);
}
