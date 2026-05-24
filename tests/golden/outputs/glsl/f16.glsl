#version 450 core
precision highp float;
precision highp int;

struct Struct_16 {
    uint val_u32;
    int val_i32;
    float val_f32;
    float16_t val_f16;
    vec2 val_f16_2;
    vec3 val_f16_3;
    vec4 val_f16_4;
    float16_t final_value;
    mat2x2 val_mat2x2;
    mat2x3 val_mat2x3;
    mat2x4 val_mat2x4;
    mat3x2 val_mat3x2;
    mat3x3 val_mat3x3;
    mat3x4 val_mat3x4;
    mat4x2 val_mat4x2;
    mat4x3 val_mat4x3;
    mat4x4 val_mat4x4;
};
struct Struct_18 {
    float16_t[2] val_f16_array_2;
};
struct Struct_19 {
    float16_t scalar1;
    float16_t scalar2;
    vec3 v3;
    float16_t tuck_in;
    float16_t scalar4;
    uint larger;
};
 float16_t global_0 = 1.0f;
 float16_t global_1 = float16_t(15.2f);
layout(set = 0, binding = 0) uniform Struct_16 global_2;
layout(set = 0, binding = 1) buffer Struct_16 global_3;
layout(set = 0, binding = 3) buffer Struct_16 global_4;
layout(set = 0, binding = 2) buffer Struct_18 global_5;
layout(set = 0, binding = 4) buffer Struct_18 global_6;

float16_t f16_function(float16_t x) {
    global_0;
    Struct_19 l;
    float16_t val = float16_t(global_1);
    val = (1.0f - 33333.0f);
    val = (val + float16_t(5.0f));
    val = float16_t((global_2.val_f32 + float(val)));
    val = vec3(global_2.val_f16)[2];
    global_4.val_i32 = int(65504.0f);
    global_4.val_i32 = int(-(65504.0f));
    global_4.val_u32 = uint(65504.0f);
    global_4.val_u32 = uint(-(65504.0f));
    global_4.val_f32 = float(65504.0f);
    global_4.val_f32 = float(-(65504.0f));
    global_4.val_f16 = (global_2.val_f16 + global_3.val_f16);
    global_4.val_f16_2 = (global_2.val_f16_2 + global_3.val_f16_2);
    global_4.val_f16_3 = (global_2.val_f16_3 + global_3.val_f16_3);
    global_4.val_f16_4 = (global_2.val_f16_4 + global_3.val_f16_4);
    global_4.val_mat2x2 = (global_2.val_mat2x2 + global_3.val_mat2x2);
    global_4.val_mat2x3 = (global_2.val_mat2x3 + global_3.val_mat2x3);
    global_4.val_mat2x4 = (global_2.val_mat2x4 + global_3.val_mat2x4);
    global_4.val_mat3x2 = (global_2.val_mat3x2 + global_3.val_mat3x2);
    global_4.val_mat3x3 = (global_2.val_mat3x3 + global_3.val_mat3x3);
    global_4.val_mat3x4 = (global_2.val_mat3x4 + global_3.val_mat3x4);
    global_4.val_mat4x2 = (global_2.val_mat4x2 + global_3.val_mat4x2);
    global_4.val_mat4x3 = (global_2.val_mat4x3 + global_3.val_mat4x3);
    global_4.val_mat4x4 = (global_2.val_mat4x4 + global_3.val_mat4x4);
    global_6.val_f16_array_2 = global_5.val_f16_array_2;
    val = abs(val);
    val = clamp(val, val, val);
    val = dot(vec2(val), vec2(val));
    val = max(val, val);
    val = min(val, val);
    val = sign(val);
    val = float16_t(1.0f);
    vec2 float_vec2 = vec2(global_2.val_f16_2);
    global_4.val_f16_2 = vec2(float_vec2);
    vec3 float_vec3 = vec3(global_2.val_f16_3);
    global_4.val_f16_3 = vec3(float_vec3);
    vec4 float_vec4 = vec4(global_2.val_f16_4);
    global_4.val_f16_4 = vec4(float_vec4);
    global_4.val_mat2x2 = mat2x2(mat2x2(global_2.val_mat2x2));
    global_4.val_mat2x3 = mat2x3(mat2x3(global_2.val_mat2x3));
    global_4.val_mat2x4 = mat2x4(mat2x4(global_2.val_mat2x4));
    global_4.val_mat3x2 = mat3x2(mat3x2(global_2.val_mat3x2));
    global_4.val_mat3x3 = mat3x3(mat3x3(global_2.val_mat3x3));
    global_4.val_mat3x4 = mat3x4(mat3x4(global_2.val_mat3x4));
    global_4.val_mat4x2 = mat4x2(mat4x2(global_2.val_mat4x2));
    global_4.val_mat4x3 = mat4x3(mat4x3(global_2.val_mat4x3));
    global_4.val_mat4x4 = mat4x4(mat4x4(global_2.val_mat4x4));
    return val;
}

float16_t abs(float16_t arg_0) {
}

float16_t clamp(float16_t arg_0, float16_t arg_1, float16_t arg_2) {
}

vec2 dot(vec2 arg_0, vec2 arg_1) {
}

float16_t max(float16_t arg_0, float16_t arg_1) {
}

float16_t min(float16_t arg_0, float16_t arg_1) {
}

float16_t sign(float16_t arg_0) {
}

void wgsl_main() {
    global_4.final_value = f16_function(2.0f);
}

void main() {
    wgsl_main();
}
