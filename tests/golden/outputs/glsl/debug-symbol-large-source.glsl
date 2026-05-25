#version 450 core
precision highp float;
precision highp int;

struct Struct_6 {
    uvec2 chunk_size;
    ivec2 chunk_corner;
    vec2 min_max_height;
};
struct Struct_8 {
    vec3 position;
    vec3 normal;
};
struct Struct_10 {
    uint[] data;
};
struct Struct_11 {
    uvec2 chunk_size;
    ivec2 chunk_corner;
    vec2 min_max_height;
    uint texture_size;
    uint start_index;
};
struct Struct_13 {
    uint index;
    vec4 position;
    vec2 uv;
};
struct Struct_14 {
    uint vert_component;
    uint index;
};
struct Struct_16 {
    vec4 view_pos;
    mat4x4 view_proj;
};
struct Struct_17 {
    vec3 position;
    vec3 color;
};
struct Struct_18 {
    vec4 clip_position;
    vec3 normal;
    vec3 world_pos;
};
struct Struct_20 {
    Struct_8[] data;
};
layout(set = 2, binding = 0) uniform texture2D global_0;
layout(set = 2, binding = 1) uniform sampler global_1;
layout(set = 2, binding = 2) uniform texture2D global_2;
layout(set = 2, binding = 3) uniform sampler global_3;
layout(set = 0, binding = 0) uniform Struct_6 global_4;
layout(set = 0, binding = 2) buffer Struct_10 global_5;
layout(set = 0, binding = 0) uniform Struct_11 global_6;
layout(set = 0, binding = 0) uniform Struct_16 global_7;
layout(set = 1, binding = 0) uniform Struct_17 global_8;
layout(set = 0, binding = 1) buffer Struct_20 global_9;

vec3 permute3(vec3 x) {
    return mod((((x * 34.0f) + 1.0f) * x), vec3(289.0f));
}

vec2 index_to_p(uint vert_index, uvec2 chunk_size, ivec2 chunk_corner) {
    return (vec2(mod(float(vert_index), float((chunk_size[0] + 1u))), float((vert_index / (chunk_size[0] + 1u)))) + vec2(chunk_corner));
}

float snoise2(vec2 v) {
    vec4 C = vec4(0.21132487f, 0.36602542f, -(0.57735026f), 0.024390243f);
    vec2 i = floor((v + dot(v, C.yy)));
    vec2 x0 = ((v - i) + dot(i, C.xx));
    vec2 i1 = select(vec2(1.0f, 0.0f), vec2(0.0f, 1.0f), (x0[0] < x0[1]));
    vec4 x12 = ((x0.xyxy + C.xxzz) - vec4(i1, 0.0f, 0.0f));
    i = mod(i, vec2(289.0f));
    vec3 p = permute3(((permute3((i[1] + vec3(0.0f, i1[1], 1.0f))) + i[0]) + vec3(0.0f, i1[0], 1.0f)));
    vec3 m = max((0.5f - vec3(dot(x0, x0), dot(x12.xy, x12.xy), dot(x12.zw, x12.zw))), vec3(0.0f));
    m = (m * m);
    m = (m * m);
    vec3 x = ((2.0f * fract((p * C.www))) - 1.0f);
    vec3 h = (abs(x) - 0.5f);
    vec3 ox = floor((x + 0.5f));
    vec3 a0 = (x - ox);
    m = (m * (1.7928429f - (0.85373473f * ((a0 * a0) + (h * h)))));
    vec3 g = vec3(((a0[0] * x0[0]) + (h[0] * x0[1])), ((a0.yz * x12.xz) + (h.yz * x12.yw)));
    return (130.0f * dot(m, g));
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

vec2 floor(vec2 arg_0) {
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

vec2 select(vec2 arg_0, vec2 arg_1, bool arg_2) {
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

vec3 max(vec3 arg_0, vec3 arg_1) {
}

vec3 fract(vec3 arg_0) {
}

vec3 abs(vec3 arg_0) {
}

vec3 floor(vec3 arg_0) {
}

vec3 dot(vec3 arg_0, vec3 arg_1) {
}

float fbm(vec2 p) {
    uint NUM_OCTAVES = 5u;
    vec2 x = (p * 0.01f);
    float v = 0.0f;
    float a = 0.5f;
    vec2 shift = vec2(100.0f);
    vec2 cs = vec2(cos(0.5f), sin(0.5f));
    mat2x2 rot = mat2x2(cs[0], cs[1], -(cs[1]), cs[0]);
    {
        uint i = 0u;
        while (true) {
            if ((i < NUM_OCTAVES)) {
                {
                    v = (v + (a * snoise2(x)));
                    x = (((rot * x) * 2.0f) + shift);
                    a = (a * 0.5f);
                }
                i = (i + 1u);
            } else {
                break;
            }
        }
    }
    return v;
}

float cos(float arg_0) {
}

float sin(float arg_0) {
}

vec3 color23(vec2 p) {
    return vec3(((snoise2(p) * 0.5f) + 0.5f), ((snoise2((p + vec2(23.0f, 32.0f))) * 0.5f) + 0.5f), ((snoise2((p + vec2(-(43.0f), 3.0f))) * 0.5f) + 0.5f));
}

Struct_13 wgsl_gen_terrain_vertex(uint vindex) {
    float u = float(mod(((vindex + 2u) / 3u), 2u));
    float v = float(mod(((vindex + 1u) / 3u), 2u));
    vec2 uv = vec2(u, v);
    vec4 position = vec4((-(1.0f) + (uv * 2.0f)), 0.0f, 1.0f);
    uint index = (uint(((uv[0] * float(global_6.texture_size)) + (uv[1] * float(global_6.texture_size)))) + global_6.start_index);
    return Struct_13(index, position, uv);
}

Struct_18 wgsl_vs_main(Struct_8 vertex) {
    vec4 clip_position = (global_7.view_proj * vec4(vertex.position, 1.0f));
    vec3 normal = vertex.normal;
    return Struct_18(clip_position, normal, vertex.position);
}

vec3 terrain_point(vec2 p, vec2 min_max_height) {
    return vec3(p[0], mix(min_max_height[0], min_max_height[1], fbm(p)), p[1]);
}

float mix(float arg_0, float arg_1, float arg_2) {
}

vec4 wgsl_fs_main(Struct_18 in) {
    global_0;
    global_1;
    global_2;
    global_3;
    color23(vec2(1, 2));
    vec3 color = smoothstep(vec3(0.0f), vec3(0.1f), fract(in.world_pos));
    color = mix(vec3(0.5f, 0.1f, 0.7f), vec3(0.2f, 0.2f, 0.2f), vec3(((color[0] * color[1]) * color[2])));
    float ambient_strength = 0.1f;
    vec3 ambient_color = (global_8.color * ambient_strength);
    vec3 light_dir = normalize((global_8.position - in.world_pos));
    vec3 view_dir = normalize((global_7.view_pos.xyz - in.world_pos));
    vec3 half_dir = normalize((view_dir + light_dir));
    vec3 diffuse_strength = max(dot(in.normal, light_dir), 0.0f);
    vec3 diffuse_color = (diffuse_strength * global_8.color);
    vec3 specular_strength = pow(max(dot(in.normal, half_dir), 0.0f), 32.0f);
    vec3 specular_color = (specular_strength * global_8.color);
    vec3 result = (((ambient_color + diffuse_color) + specular_color) * color);
    return vec4(result, 1.0f);
}

vec3 fract(vec3 arg_0) {
}

vec3 smoothstep(vec3 arg_0, vec3 arg_1, vec3 arg_2) {
}

vec3 mix(vec3 arg_0, vec3 arg_1, vec3 arg_2) {
}

vec3 normalize(vec3 arg_0) {
}

vec3 normalize(vec3 arg_0) {
}

vec3 normalize(vec3 arg_0) {
}

vec3 dot(vec3 arg_0, vec3 arg_1) {
}

vec3 max(vec3 arg_0, float arg_1) {
}

vec3 dot(vec3 arg_0, vec3 arg_1) {
}

vec3 max(vec3 arg_0, float arg_1) {
}

vec3 pow(vec3 arg_0, float arg_1) {
}

Struct_8 terrain_vertex(vec2 p, vec2 min_max_height) {
    vec3 v = terrain_point(p, min_max_height);
    vec3 tpx = (terrain_point((p + vec2(0.1f, 0.0f)), min_max_height) - v);
    vec3 tpz = (terrain_point((p + vec2(0.0f, 0.1f)), min_max_height) - v);
    vec3 tnx = (terrain_point((p + vec2(-(0.1f), 0.0f)), min_max_height) - v);
    vec3 tnz = (terrain_point((p + vec2(0.0f, -(0.1f))), min_max_height) - v);
    vec3 pn = normalize(cross(tpz, tpx));
    vec3 nn = normalize(cross(tnz, tnx));
    vec3 n = ((pn + nn) * 0.5f);
    return Struct_8(v, n);
}

vec3 normalize(vec3 arg_0) {
}

vec3 normalize(vec3 arg_0) {
}

void wgsl_gen_terrain_compute(uvec3 gid) {
    uint vert_index = gid[0];
    vec2 p = index_to_p(vert_index, global_4.chunk_size, global_4.chunk_corner);
    global_9.data[vert_index] = terrain_vertex(p, global_4.min_max_height);
    uint start_index = (gid[0] * 6u);
    if ((start_index >= ((global_4.chunk_size[0] * global_4.chunk_size[1]) * 6u))) {
        return;
    }
    uint v00 = (vert_index + (gid[0] / global_4.chunk_size[0]));
    uint v10 = (v00 + 1u);
    uint v01 = ((v00 + global_4.chunk_size[0]) + 1u);
    uint v11 = (v01 + 1u);
    global_5.data[start_index] = v00;
    global_5.data[(start_index + 1u)] = v01;
    global_5.data[(start_index + 2u)] = v11;
    global_5.data[(start_index + 3u)] = v00;
    global_5.data[(start_index + 4u)] = v11;
    global_5.data[(start_index + 5u)] = v10;
}

Struct_14 wgsl_gen_terrain_fragment(Struct_13 in) {
    uint i = (uint(((in.uv[0] * float(global_6.texture_size)) + (in.uv[1] * float((global_6.texture_size * global_6.texture_size))))) + global_6.start_index);
    uint vert_index = uint(floor((float(i) / 6.0f)));
    uint comp_index = mod(i, 6u);
    vec2 p = index_to_p(vert_index, global_6.chunk_size, global_6.chunk_corner);
    Struct_8 v = terrain_vertex(p, global_6.min_max_height);
    float vert_component = 0.0f;
    switch (comp_index) {
        case 0u: {
            vert_component = v.position[0];
            break;
        }
        case 1u: {
            vert_component = v.position[1];
            break;
        }
        case 2u: {
            vert_component = v.position[2];
            break;
        }
        case 3u: {
            vert_component = v.normal[0];
            break;
        }
        case 4u: {
            vert_component = v.normal[1];
            break;
        }
        case 5u: {
            vert_component = v.normal[2];
            break;
        }
        default: {
            break;
        }
    }
    uint v00 = (vert_index + (vert_index / global_6.chunk_size[0]));
    uint v10 = (v00 + 1u);
    uint v01 = ((v00 + global_6.chunk_size[0]) + 1u);
    uint v11 = (v01 + 1u);
    uint index = 0u;
    switch (comp_index) {
        case 0u: {
            index = v00;
            break;
        }
        case 3u: {
            index = v00;
            break;
        }
        case 2u: {
            index = v11;
            break;
        }
        case 4u: {
            index = v11;
            break;
        }
        case 1u: {
            index = v01;
            break;
        }
        case 5u: {
            index = v10;
            break;
        }
        default: {
            break;
        }
    }
    index = in.index;
    uint ivert_component = floatBitsToUint(vert_component);
    return Struct_14(ivert_component, index);
}

float floor(float arg_0) {
}

layout(location = 0) out uint out_index;
layout(location = 1) out vec2 out_uv;
void main() {
    Struct_13 res = wgsl_gen_terrain_vertex(vindex);
    out_index = res.index;
    gl_Position = res.position;
    out_uv = res.uv;
}

layout(location = 0) out vec3 out_normal;
layout(location = 1) out vec3 out_world_pos;
void main() {
    Struct_18 res = wgsl_vs_main(vertex);
    gl_Position = res.clip_position;
    out_normal = res.normal;
    out_world_pos = res.world_pos;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(in);
}

void main() {
    wgsl_gen_terrain_compute(gid);
}

layout(location = 0) out Struct_14 outColor;
void main() {
    outColor = wgsl_gen_terrain_fragment(in);
}
