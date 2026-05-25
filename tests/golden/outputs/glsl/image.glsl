#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) uniform texture_2d<u32> global_0;
layout(set = 0, binding = 3) uniform texture_multisampled_2d<u32> global_1;
layout(set = 0, binding = 4) uniform texture2D global_2;
layout(set = 0, binding = 1) uniform texture_storage_2d<rgba8uint, read> global_3;
layout(set = 0, binding = 5) uniform texture_2d_array<u32> global_4;
layout(set = 0, binding = 6) uniform texture_storage_1d<r32uint, read> global_5;
layout(set = 0, binding = 7) uniform texture_1d<u32> global_6;
layout(set = 0, binding = 2) uniform texture_storage_1d<r32uint, write> global_7;
layout(set = 0, binding = 0) uniform texture1D global_8;
layout(set = 0, binding = 1) uniform texture2D global_9;
layout(set = 0, binding = 2) uniform texture_2d<u32> global_10;
layout(set = 0, binding = 3) uniform texture_2d<i32> global_11;
layout(set = 0, binding = 4) uniform texture2DArray global_12;
layout(set = 0, binding = 5) uniform textureCube global_13;
layout(set = 0, binding = 6) uniform textureCubeArray global_14;
layout(set = 0, binding = 7) uniform texture3D global_15;
layout(set = 0, binding = 8) uniform texture2DMS global_16;
layout(set = 1, binding = 0) uniform sampler global_17;
layout(set = 1, binding = 1) uniform sampler_comparison global_18;
layout(set = 1, binding = 2) uniform texture2D global_19;
layout(set = 1, binding = 3) uniform texture2D global_20;
layout(set = 1, binding = 4) uniform texture2D global_21;

void wgsl_main(uvec3 local_id) {
    uvec2 dim = textureDimensions(global_3);
    ivec2 itc = mod(ivec2((dim * local_id.xy)), ivec2(10, 20));
    texture_2d<u32> value1 = textureLoad(global_0, itc, int(local_id[2]));
    texture_2d<u32> value1_2 = textureLoad(global_0, itc, uint(local_id[2]));
    texture_multisampled_2d<u32> value2 = textureLoad(global_1, itc, int(local_id[2]));
    texture_multisampled_2d<u32> value3 = textureLoad(global_1, itc, uint(local_id[2]));
    texture_storage_2d<rgba8uint, read> value4 = textureLoad(global_3, itc);
    texture_2d_array<u32> value5 = textureLoad(global_4, itc, local_id[2], (int(local_id[2]) + 1));
    texture_2d_array<u32> value6 = textureLoad(global_4, itc, int(local_id[2]), (int(local_id[2]) + 1));
    texture_1d<u32> value7 = textureLoad(global_6, int(local_id[0]), int(local_id[2]));
    texture_storage_1d<r32uint, read> value8 = textureLoad(global_5, int(local_id[0]));
    texture_2d<u32> value1u = textureLoad(global_0, uvec2(itc), int(local_id[2]));
    texture_multisampled_2d<u32> value2u = textureLoad(global_1, uvec2(itc), int(local_id[2]));
    texture_multisampled_2d<u32> value3u = textureLoad(global_1, uvec2(itc), uint(local_id[2]));
    texture_storage_2d<rgba8uint, read> value4u = textureLoad(global_3, uvec2(itc));
    texture_2d_array<u32> value5u = textureLoad(global_4, uvec2(itc), local_id[2], (int(local_id[2]) + 1));
    texture_2d_array<u32> value6u = textureLoad(global_4, uvec2(itc), int(local_id[2]), (int(local_id[2]) + 1));
    texture_1d<u32> value7u = textureLoad(global_6, uint(local_id[0]), int(local_id[2]));
    textureStore(global_7, itc[0], ((((value1 + value2) + value4) + value5) + value6));
    textureStore(global_7, uint(itc[0]), ((((value1u + value2u) + value4u) + value5u) + value6u));
}

uvec2 textureDimensions(texture_storage_2d<rgba8uint, read> arg_0) {
}

texture_2d<u32> textureLoad(texture_2d<u32> arg_0, ivec2 arg_1, int arg_2) {
}

texture_2d<u32> textureLoad(texture_2d<u32> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_multisampled_2d<u32> textureLoad(texture_multisampled_2d<u32> arg_0, ivec2 arg_1, int arg_2) {
}

texture_multisampled_2d<u32> textureLoad(texture_multisampled_2d<u32> arg_0, ivec2 arg_1, uint arg_2) {
}

texture_storage_2d<rgba8uint, read> textureLoad(texture_storage_2d<rgba8uint, read> arg_0, ivec2 arg_1) {
}

texture_2d_array<u32> textureLoad(texture_2d_array<u32> arg_0, ivec2 arg_1, uint arg_2, int arg_3) {
}

texture_2d_array<u32> textureLoad(texture_2d_array<u32> arg_0, ivec2 arg_1, int arg_2, int arg_3) {
}

texture_1d<u32> textureLoad(texture_1d<u32> arg_0, int arg_1, int arg_2) {
}

texture_storage_1d<r32uint, read> textureLoad(texture_storage_1d<r32uint, read> arg_0, int arg_1) {
}

texture_2d<u32> textureLoad(texture_2d<u32> arg_0, uvec2 arg_1, int arg_2) {
}

texture_multisampled_2d<u32> textureLoad(texture_multisampled_2d<u32> arg_0, uvec2 arg_1, int arg_2) {
}

texture_multisampled_2d<u32> textureLoad(texture_multisampled_2d<u32> arg_0, uvec2 arg_1, uint arg_2) {
}

texture_storage_2d<rgba8uint, read> textureLoad(texture_storage_2d<rgba8uint, read> arg_0, uvec2 arg_1) {
}

texture_2d_array<u32> textureLoad(texture_2d_array<u32> arg_0, uvec2 arg_1, uint arg_2, int arg_3) {
}

texture_2d_array<u32> textureLoad(texture_2d_array<u32> arg_0, uvec2 arg_1, int arg_2, int arg_3) {
}

texture_1d<u32> textureLoad(texture_1d<u32> arg_0, uint arg_1, int arg_2) {
}

texture_storage_1d<r32uint, write> textureStore(texture_storage_1d<r32uint, write> arg_0, int arg_1, texture_2d<u32> arg_2) {
}

texture_storage_1d<r32uint, write> textureStore(texture_storage_1d<r32uint, write> arg_0, uint arg_1, texture_2d<u32> arg_2) {
}

void wgsl_depth_load(uvec3 local_id) {
    uvec2 dim = textureDimensions(global_3);
    ivec2 itc = mod(ivec2((dim * local_id.xy)), ivec2(10, 20));
    float val = textureLoad(global_2, itc, int(local_id[2]));
    textureStore(global_7, itc[0], uvec4(uint(val)));
    return;
}

uvec2 textureDimensions(texture_storage_2d<rgba8uint, read> arg_0) {
}

texture2D textureLoad(texture2D arg_0, ivec2 arg_1, int arg_2) {
}

texture_storage_1d<r32uint, write> textureStore(texture_storage_1d<r32uint, write> arg_0, int arg_1, uvec4 arg_2) {
}

vec4 wgsl_queries() {
    uint dim_1d = textureDimensions(global_8);
    uint dim_1d_lod = textureDimensions(global_8, int(dim_1d));
    uvec2 dim_2d = textureDimensions(global_9);
    uvec2 dim_2d_lod = textureDimensions(global_9, 1);
    uvec2 dim_2d_array = textureDimensions(global_12);
    uvec2 dim_2d_array_lod = textureDimensions(global_12, 1);
    uvec2 dim_cube = textureDimensions(global_13);
    uvec2 dim_cube_lod = textureDimensions(global_13, 1);
    uvec2 dim_cube_array = textureDimensions(global_14);
    uvec2 dim_cube_array_lod = textureDimensions(global_14, 1);
    uvec3 dim_3d = textureDimensions(global_15);
    uvec3 dim_3d_lod = textureDimensions(global_15, 1);
    uvec2 dim_2s_ms = textureDimensions(global_16);
    uint sum = ((((((((((dim_1d + dim_2d[1]) + dim_2d_lod[1]) + dim_2d_array[1]) + dim_2d_array_lod[1]) + dim_cube[1]) + dim_cube_lod[1]) + dim_cube_array[1]) + dim_cube_array_lod[1]) + dim_3d[2]) + dim_3d_lod[2]);
    return vec4(float(sum));
}

uint textureDimensions(texture1D arg_0) {
}

uint textureDimensions(texture1D arg_0, int arg_1) {
}

uvec2 textureDimensions(texture2D arg_0) {
}

uvec2 textureDimensions(texture2D arg_0, int arg_1) {
}

uvec2 textureDimensions(texture2DArray arg_0) {
}

uvec2 textureDimensions(texture2DArray arg_0, int arg_1) {
}

uvec2 textureDimensions(textureCube arg_0) {
}

uvec2 textureDimensions(textureCube arg_0, int arg_1) {
}

uvec2 textureDimensions(textureCubeArray arg_0) {
}

uvec2 textureDimensions(textureCubeArray arg_0, int arg_1) {
}

uvec3 textureDimensions(texture3D arg_0) {
}

uvec3 textureDimensions(texture3D arg_0, int arg_1) {
}

uvec2 textureDimensions(texture2DMS arg_0) {
}

vec4 wgsl_levels_queries() {
    uint num_levels_2d = textureNumLevels(global_9);
    uint num_layers_2d = textureNumLayers(global_12);
    uint num_levels_2d_array = textureNumLevels(global_12);
    uint num_layers_2d_array = textureNumLayers(global_12);
    uint num_levels_cube = textureNumLevels(global_13);
    uint num_levels_cube_array = textureNumLevels(global_14);
    uint num_layers_cube = textureNumLayers(global_14);
    uint num_levels_3d = textureNumLevels(global_15);
    uint num_samples_aa = textureNumSamples(global_16);
    uint sum = (((((((num_layers_2d + num_layers_cube) + num_samples_aa) + num_levels_2d) + num_levels_2d_array) + num_levels_3d) + num_levels_cube) + num_levels_cube_array);
    return vec4(float(sum));
}

uint textureNumLevels(texture2D arg_0) {
}

uint textureNumLayers(texture2DArray arg_0) {
}

uint textureNumLevels(texture2DArray arg_0) {
}

uint textureNumLayers(texture2DArray arg_0) {
}

uint textureNumLevels(textureCube arg_0) {
}

uint textureNumLevels(textureCubeArray arg_0) {
}

uint textureNumLayers(textureCubeArray arg_0) {
}

uint textureNumLevels(texture3D arg_0) {
}

uint textureNumSamples(texture2DMS arg_0) {
}

vec4 wgsl_texture_sample() {
    vec2 tc = vec2(0.5f);
    vec3 tc3 = vec3(0.5f);
    ivec2 offset = ivec2(3, 1);
    float level = 2.3f;
    vec4 a;
    a = textureSample(global_8, global_17, tc[0]);
    a = textureSample(global_9, global_17, tc);
    a = textureSample(global_9, global_17, tc, ivec2(3, 1));
    a = textureSampleLevel(global_9, global_17, tc, level);
    a = textureSampleLevel(global_9, global_17, tc, level, offset);
    a = textureSampleBias(global_9, global_17, tc, 2.0f, offset);
    a = textureSampleBaseClampToEdge(global_9, global_17, tc);
    a = textureSample(global_12, global_17, tc, 0u);
    a = textureSample(global_12, global_17, tc, 0u, offset);
    a = textureSampleLevel(global_12, global_17, tc, 0u, level);
    a = textureSampleLevel(global_12, global_17, tc, 0u, level, offset);
    a = textureSampleBias(global_12, global_17, tc, 0u, 2.0f, offset);
    a = textureSample(global_12, global_17, tc, 0);
    a = textureSample(global_12, global_17, tc, 0, offset);
    a = textureSampleLevel(global_12, global_17, tc, 0, level);
    a = textureSampleLevel(global_12, global_17, tc, 0, level, offset);
    a = textureSampleBias(global_12, global_17, tc, 0, 2.0f, offset);
    a = textureSample(global_14, global_17, tc3, 0u);
    a = textureSampleLevel(global_14, global_17, tc3, 0u, level);
    a = textureSampleBias(global_14, global_17, tc3, 0u, 2.0f);
    a = textureSample(global_14, global_17, tc3, 0);
    a = textureSampleLevel(global_14, global_17, tc3, 0, level);
    a = textureSampleBias(global_14, global_17, tc3, 0, 2.0f);
    return a;
}

vec4 textureSample(texture1D arg_0, sampler arg_1, float arg_2) {
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2, ivec2 arg_3) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3, ivec2 arg_4) {
}

vec4 textureSampleBias(texture2D arg_0, sampler arg_1, vec2 arg_2, float arg_3, ivec2 arg_4) {
}

vec4 textureSampleBaseClampToEdge(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSample(texture2DArray arg_0, sampler arg_1, vec2 arg_2, uint arg_3) {
}

vec4 textureSample(texture2DArray arg_0, sampler arg_1, vec2 arg_2, uint arg_3, ivec2 arg_4) {
}

vec4 textureSampleLevel(texture2DArray arg_0, sampler arg_1, vec2 arg_2, uint arg_3, float arg_4) {
}

vec4 textureSampleLevel(texture2DArray arg_0, sampler arg_1, vec2 arg_2, uint arg_3, float arg_4, ivec2 arg_5) {
}

vec4 textureSampleBias(texture2DArray arg_0, sampler arg_1, vec2 arg_2, uint arg_3, float arg_4, ivec2 arg_5) {
}

vec4 textureSample(texture2DArray arg_0, sampler arg_1, vec2 arg_2, int arg_3) {
}

vec4 textureSample(texture2DArray arg_0, sampler arg_1, vec2 arg_2, int arg_3, ivec2 arg_4) {
}

vec4 textureSampleLevel(texture2DArray arg_0, sampler arg_1, vec2 arg_2, int arg_3, float arg_4) {
}

vec4 textureSampleLevel(texture2DArray arg_0, sampler arg_1, vec2 arg_2, int arg_3, float arg_4, ivec2 arg_5) {
}

vec4 textureSampleBias(texture2DArray arg_0, sampler arg_1, vec2 arg_2, int arg_3, float arg_4, ivec2 arg_5) {
}

vec4 textureSample(textureCubeArray arg_0, sampler arg_1, vec3 arg_2, uint arg_3) {
}

vec4 textureSampleLevel(textureCubeArray arg_0, sampler arg_1, vec3 arg_2, uint arg_3, float arg_4) {
}

vec4 textureSampleBias(textureCubeArray arg_0, sampler arg_1, vec3 arg_2, uint arg_3, float arg_4) {
}

vec4 textureSample(textureCubeArray arg_0, sampler arg_1, vec3 arg_2, int arg_3) {
}

vec4 textureSampleLevel(textureCubeArray arg_0, sampler arg_1, vec3 arg_2, int arg_3, float arg_4) {
}

vec4 textureSampleBias(textureCubeArray arg_0, sampler arg_1, vec3 arg_2, int arg_3, float arg_4) {
}

vec4 wgsl_gather() {
    vec2 tc = vec2(0.5f);
    float dref = 0.5f;
    int s2d = textureGather(1, global_9, global_17, tc);
    int s2d_offset = textureGather(3, global_9, global_17, tc, ivec2(3, 1));
    texture2D s2d_depth = textureGatherCompare(global_19, global_18, tc, dref);
    texture2D s2d_depth_offset = textureGatherCompare(global_19, global_18, tc, dref, ivec2(3, 1));
    int u = textureGather(0, global_10, global_17, tc);
    int i = textureGather(0, global_11, global_17, tc);
    vec4 f = (vec4(u) + vec4(i));
    return ((((s2d + s2d_offset) + s2d_depth) + s2d_depth_offset) + f);
}

int textureGather(int arg_0, texture2D arg_1, sampler arg_2, vec2 arg_3) {
}

int textureGather(int arg_0, texture2D arg_1, sampler arg_2, vec2 arg_3, ivec2 arg_4) {
}

texture2D textureGatherCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

texture2D textureGatherCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3, ivec2 arg_4) {
}

int textureGather(int arg_0, texture_2d<u32> arg_1, sampler arg_2, vec2 arg_3) {
}

int textureGather(int arg_0, texture_2d<i32> arg_1, sampler arg_2, vec2 arg_3) {
}

vec4 wgsl_depth_no_comparison() {
    vec2 tc = vec2(0.5f);
    int level = 1;
    vec4 s2d = textureSample(global_19, global_17, tc);
    texture2D s2d_gather = textureGather(global_19, global_17, tc);
    vec4 s2d_level = textureSampleLevel(global_19, global_17, tc, level);
    return ((s2d + s2d_gather) + s2d_level);
}

vec4 textureSample(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

texture2D textureGather(texture2D arg_0, sampler arg_1, vec2 arg_2) {
}

vec4 textureSampleLevel(texture2D arg_0, sampler arg_1, vec2 arg_2, int arg_3) {
}

float wgsl_texture_sample_comparison() {
    vec2 tc = vec2(0.5f);
    vec3 tc3 = vec3(0.5f);
    float dref = 0.5f;
    float a;
    a = textureSampleCompare(global_19, global_18, tc, dref);
    a = textureSampleCompare(global_20, global_18, tc, 0u, dref);
    a = textureSampleCompare(global_20, global_18, tc, 0, dref);
    a = textureSampleCompare(global_21, global_18, tc3, dref);
    a = textureSampleCompareLevel(global_19, global_18, tc, dref);
    a = textureSampleCompareLevel(global_20, global_18, tc, 0u, dref);
    a = textureSampleCompareLevel(global_20, global_18, tc, 0, dref);
    a = textureSampleCompareLevel(global_21, global_18, tc3, dref);
    return a;
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, uint arg_3, float arg_4) {
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, int arg_3, float arg_4) {
}

float textureSampleCompare(texture2D arg_0, sampler_comparison arg_1, vec3 arg_2, float arg_3) {
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, float arg_3) {
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, uint arg_3, float arg_4) {
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec2 arg_2, int arg_3, float arg_4) {
}

float textureSampleCompareLevel(texture2D arg_0, sampler_comparison arg_1, vec3 arg_2, float arg_3) {
}

void main() {
    wgsl_main(local_id);
}

void main() {
    wgsl_depth_load(local_id);
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_queries();
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_levels_queries();
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_texture_sample();
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_gather();
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_depth_no_comparison();
}

layout(location = 0) out float outColor;
void main() {
    outColor = wgsl_texture_sample_comparison();
}
