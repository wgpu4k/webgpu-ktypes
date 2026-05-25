#include <metal_stdlib>
using namespace metal;
struct Struct_6 {
    uint2 chunk_size;
    int2 chunk_corner;
    float2 min_max_height;
};
struct Struct_8 {
    float3 position;
    float3 normal;
};
struct Struct_10 {
    array<uint> data;
};
struct Struct_11 {
    uint2 chunk_size;
    int2 chunk_corner;
    float2 min_max_height;
    uint texture_size;
    uint start_index;
};
struct Struct_13 {
    uint index;
    float4 position;
    float2 uv;
};
struct Struct_14 {
    uint vert_component;
    uint index;
};
struct Struct_16 {
    float4 view_pos;
    float4x4 view_proj;
};
struct Struct_17 {
    float3 position;
    float3 color;
};
struct Struct_18 {
    float4 clip_position;
    float3 normal;
    float3 world_pos;
};
struct Struct_20 {
    array<Struct_8> data;
};

float3 permute3(float3 x) {
    return ((((x * 34.0f) + 1.0f) * x) % float3(289.0f));
}

float2 index_to_p(uint vert_index, uint2 chunk_size, int2 chunk_corner) {
    return (float2((float(vert_index) % float((chunk_size[0] + 1u))), float((vert_index / (chunk_size[0] + 1u)))) + float2(chunk_corner));
}

float snoise2(float2 v) {
    float4 local_0 = float4(0.21132487f, 0.36602542f, -(0.57735026f), 0.024390243f);
    float2 local_1 = floor((v + dot(v, local_0.yy)));
    float2 local_2 = ((v - local_1) + dot(local_1, local_0.xx));
    float2 local_3 = select(float2(1.0f, 0.0f), float2(0.0f, 1.0f), (local_2[0] < local_2[1]));
    float4 local_4 = ((local_2.xyxy + local_0.xxzz) - float4(local_3, 0.0f, 0.0f));
    local_1 = (local_1 % float2(289.0f));
    float3 local_5 = permute3(((permute3((local_1[1] + float3(0.0f, local_3[1], 1.0f))) + local_1[0]) + float3(0.0f, local_3[0], 1.0f)));
    float3 local_6 = max((0.5f - float3(dot(local_2, local_2), dot(local_4.xy, local_4.xy), dot(local_4.zw, local_4.zw))), float3(0.0f));
    local_6 = (local_6 * local_6);
    local_6 = (local_6 * local_6);
    float3 local_7 = ((2.0f * fract((local_5 * local_0.www))) - 1.0f);
    float3 local_8 = (abs(local_7) - 0.5f);
    float3 local_9 = floor((local_7 + 0.5f));
    float3 local_10 = (local_7 - local_9);
    local_6 = (local_6 * (1.7928429f - (0.85373473f * ((local_10 * local_10) + (local_8 * local_8)))));
    float3 local_11 = float3(((local_10[0] * local_2[0]) + (local_8[0] * local_2[1])), ((local_10.yz * local_4.xz) + (local_8.yz * local_4.yw)));
    return (130.0f * dot(local_6, local_11));
}

float2 dot(float2 arg_0, float2 arg_1) {
}

float2 floor(float2 arg_0) {
}

float2 dot(float2 arg_0, float2 arg_1) {
}

float2 select(float2 arg_0, float2 arg_1, bool arg_2) {
}

float2 dot(float2 arg_0, float2 arg_1) {
}

float2 dot(float2 arg_0, float2 arg_1) {
}

float2 dot(float2 arg_0, float2 arg_1) {
}

float3 max(float3 arg_0, float3 arg_1) {
}

float3 fract(float3 arg_0) {
}

float3 abs(float3 arg_0) {
}

float3 floor(float3 arg_0) {
}

float3 dot(float3 arg_0, float3 arg_1) {
}

float fbm(float2 p) {
    uint local_0 = 5u;
    float2 local_1 = (p * 0.01f);
    float local_2 = 0.0f;
    float local_3 = 0.5f;
    float2 local_4 = float2(100.0f);
    float2 local_5 = float2(cos(0.5f), sin(0.5f));
    float2x2 local_6 = float2x2(local_5[0], local_5[1], -(local_5[1]), local_5[0]);
    {
        uint local_7 = 0u;
        while (true) {
            if ((local_7 < local_0)) {
                {
                    local_2 = (local_2 + (local_3 * snoise2(local_1)));
                    local_1 = (((local_6 * local_1) * 2.0f) + local_4);
                    local_3 = (local_3 * 0.5f);
                }
                local_7 = (local_7 + 1u);
            } else {
                break;
            }
        }
    }
    return local_2;
}

float cos(float arg_0) {
}

float sin(float arg_0) {
}

float3 color23(float2 p) {
    return float3(((snoise2(p) * 0.5f) + 0.5f), ((snoise2((p + float2(23.0f, 32.0f))) * 0.5f) + 0.5f), ((snoise2((p + float2(-(43.0f), 3.0f))) * 0.5f) + 0.5f));
}

struct gen_terrain_vertex_Output {
    uint index [[user(loc0)]];
    float4 position [[position]];
    float2 uv [[user(loc1)]];
};
[[vertex]]
gen_terrain_vertex_Output gen_terrain_vertex(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]], texture2d<float> global_2 [[texture(2)]], sampler global_3 [[sampler(3)]], Struct_6 global_4 [[buffer(0)]], Struct_10 global_5 [[buffer(2)]], Struct_11 global_6 [[buffer(0)]], Struct_16 global_7 [[buffer(0)]], Struct_17 global_8 [[buffer(0)]], Struct_20 global_9 [[buffer(1)]]) {
    float local_0 = float((((vindex + 2u) / 3u) % 2u));
    float local_1 = float((((vindex + 1u) / 3u) % 2u));
    float2 local_2 = float2(local_0, local_1);
    float4 local_3 = float4((-(1.0f) + (local_2 * 2.0f)), 0.0f, 1.0f);
    uint local_4 = (uint(((local_2[0] * float(global_6.texture_size)) + (local_2[1] * float(global_6.texture_size)))) + global_6.start_index);
    return Struct_13(local_4, local_3, local_2);
}

struct vs_main_Output {
    float4 clip_position [[position]];
    float3 normal [[user(loc0)]];
    float3 world_pos [[user(loc1)]];
};
[[vertex]]
vs_main_Output vs_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]], texture2d<float> global_2 [[texture(2)]], sampler global_3 [[sampler(3)]], Struct_6 global_4 [[buffer(0)]], Struct_10 global_5 [[buffer(2)]], Struct_11 global_6 [[buffer(0)]], Struct_16 global_7 [[buffer(0)]], Struct_17 global_8 [[buffer(0)]], Struct_20 global_9 [[buffer(1)]]) {
    float4 local_0 = (global_7.view_proj * float4(vertex.position, 1.0f));
    float3 local_1 = vertex.normal;
    return Struct_18(local_0, local_1, vertex.position);
}

float3 terrain_point(float2 p, float2 min_max_height) {
    return float3(p[0], mix(min_max_height[0], min_max_height[1], fbm(p)), p[1]);
}

float mix(float arg_0, float arg_1, float arg_2) {
}

[[fragment]]
float4 fs_main(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]], texture2d<float> global_2 [[texture(2)]], sampler global_3 [[sampler(3)]], Struct_6 global_4 [[buffer(0)]], Struct_10 global_5 [[buffer(2)]], Struct_11 global_6 [[buffer(0)]], Struct_16 global_7 [[buffer(0)]], Struct_17 global_8 [[buffer(0)]], Struct_20 global_9 [[buffer(1)]]) {
    global_0;
    global_1;
    global_2;
    global_3;
    color23(float2(1, 2));
    float3 local_0 = smoothstep(float3(0.0f), float3(0.1f), fract(in.world_pos));
    local_0 = mix(float3(0.5f, 0.1f, 0.7f), float3(0.2f, 0.2f, 0.2f), float3(((local_0[0] * local_0[1]) * local_0[2])));
    float local_1 = 0.1f;
    float3 local_2 = (global_8.color * local_1);
    float3 local_3 = normalize((global_8.position - in.world_pos));
    float3 local_4 = normalize((global_7.view_pos.xyz - in.world_pos));
    float3 local_5 = normalize((local_4 + local_3));
    float3 local_6 = max(dot(in.normal, local_3), 0.0f);
    float3 local_7 = (local_6 * global_8.color);
    float3 local_8 = pow(max(dot(in.normal, local_5), 0.0f), 32.0f);
    float3 local_9 = (local_8 * global_8.color);
    float3 local_10 = (((local_2 + local_7) + local_9) * local_0);
    return float4(local_10, 1.0f);
}

float3 fract(float3 arg_0) {
}

float3 smoothstep(float3 arg_0, float3 arg_1, float3 arg_2) {
}

float3 mix(float3 arg_0, float3 arg_1, float3 arg_2) {
}

float3 normalize(float3 arg_0) {
}

float3 normalize(float3 arg_0) {
}

float3 normalize(float3 arg_0) {
}

float3 dot(float3 arg_0, float3 arg_1) {
}

float3 max(float3 arg_0, float arg_1) {
}

float3 dot(float3 arg_0, float3 arg_1) {
}

float3 max(float3 arg_0, float arg_1) {
}

float3 pow(float3 arg_0, float arg_1) {
}

Struct_8 terrain_vertex(float2 p, float2 min_max_height) {
    float3 local_0 = terrain_point(p, min_max_height);
    float3 local_1 = (terrain_point((p + float2(0.1f, 0.0f)), min_max_height) - local_0);
    float3 local_2 = (terrain_point((p + float2(0.0f, 0.1f)), min_max_height) - local_0);
    float3 local_3 = (terrain_point((p + float2(-(0.1f), 0.0f)), min_max_height) - local_0);
    float3 local_4 = (terrain_point((p + float2(0.0f, -(0.1f))), min_max_height) - local_0);
    float3 local_5 = normalize(cross(local_2, local_1));
    float3 local_6 = normalize(cross(local_4, local_3));
    float3 local_7 = ((local_5 + local_6) * 0.5f);
    return Struct_8(local_0, local_7);
}

float3 cross(float3 arg_0, float3 arg_1) {
}

float3 normalize(float3 arg_0) {
}

float3 cross(float3 arg_0, float3 arg_1) {
}

float3 normalize(float3 arg_0) {
}

[[kernel]]
void gen_terrain_compute(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]], texture2d<float> global_2 [[texture(2)]], sampler global_3 [[sampler(3)]], Struct_6 global_4 [[buffer(0)]], Struct_10 global_5 [[buffer(2)]], Struct_11 global_6 [[buffer(0)]], Struct_16 global_7 [[buffer(0)]], Struct_17 global_8 [[buffer(0)]], Struct_20 global_9 [[buffer(1)]]) {
    uint local_0 = gid[0];
    float2 local_1 = index_to_p(local_0, global_4.chunk_size, global_4.chunk_corner);
    global_9.data[local_0] = terrain_vertex(local_1, global_4.min_max_height);
    uint local_2 = (gid[0] * 6u);
    if ((local_2 >= ((global_4.chunk_size[0] * global_4.chunk_size[1]) * 6u))) {
        return;
    }
    uint local_3 = (local_0 + (gid[0] / global_4.chunk_size[0]));
    uint local_4 = (local_3 + 1u);
    uint local_5 = ((local_3 + global_4.chunk_size[0]) + 1u);
    uint local_6 = (local_5 + 1u);
    global_5.data[local_2] = local_3;
    global_5.data[(local_2 + 1u)] = local_5;
    global_5.data[(local_2 + 2u)] = local_6;
    global_5.data[(local_2 + 3u)] = local_3;
    global_5.data[(local_2 + 4u)] = local_6;
    global_5.data[(local_2 + 5u)] = local_4;
}

struct gen_terrain_fragment_Output {
    uint vert_component [[user(loc0)]];
    uint index [[user(loc1)]];
};
[[fragment]]
gen_terrain_fragment_Output gen_terrain_fragment(texture2d<float> global_0 [[texture(0)]], sampler global_1 [[sampler(1)]], texture2d<float> global_2 [[texture(2)]], sampler global_3 [[sampler(3)]], Struct_6 global_4 [[buffer(0)]], Struct_10 global_5 [[buffer(2)]], Struct_11 global_6 [[buffer(0)]], Struct_16 global_7 [[buffer(0)]], Struct_17 global_8 [[buffer(0)]], Struct_20 global_9 [[buffer(1)]]) {
    uint local_0 = (uint(((in.uv[0] * float(global_6.texture_size)) + (in.uv[1] * float((global_6.texture_size * global_6.texture_size))))) + global_6.start_index);
    uint local_1 = uint(floor((float(local_0) / 6.0f)));
    uint local_2 = (local_0 % 6u);
    float2 local_3 = index_to_p(local_1, global_6.chunk_size, global_6.chunk_corner);
    Struct_8 local_4 = terrain_vertex(local_3, global_6.min_max_height);
    float local_5 = 0.0f;
    switch (local_2) {
        case 0u: {
            local_5 = local_4.position[0];
            break;
        }
        case 1u: {
            local_5 = local_4.position[1];
            break;
        }
        case 2u: {
            local_5 = local_4.position[2];
            break;
        }
        case 3u: {
            local_5 = local_4.normal[0];
            break;
        }
        case 4u: {
            local_5 = local_4.normal[1];
            break;
        }
        case 5u: {
            local_5 = local_4.normal[2];
            break;
        }
        default: {
            break;
        }
    }
    uint local_6 = (local_1 + (local_1 / global_6.chunk_size[0]));
    uint local_7 = (local_6 + 1u);
    uint local_8 = ((local_6 + global_6.chunk_size[0]) + 1u);
    uint local_9 = (local_8 + 1u);
    uint local_10 = 0u;
    switch (local_2) {
        case 0u: {
            local_10 = local_6;
            break;
        }
        case 3u: {
            local_10 = local_6;
            break;
        }
        case 2u: {
            local_10 = local_9;
            break;
        }
        case 4u: {
            local_10 = local_9;
            break;
        }
        case 1u: {
            local_10 = local_8;
            break;
        }
        case 5u: {
            local_10 = local_7;
            break;
        }
        default: {
            break;
        }
    }
    local_10 = in.index;
    uint local_11 = as_type<uint>(local_5);
    return Struct_14(local_11, local_10);
}

float floor(float arg_0) {
}
