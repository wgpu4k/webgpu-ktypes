#version 450 core
precision highp float;
precision highp int;

struct Struct_15 {
    uint val_u32;
    int val_i32;
    float val_f32;
    float val_f16;
    vec2 val_f16_2;
    vec3 val_f16_3;
    vec4 val_f16_4;
    float final_value;
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
struct Struct_17 {
    float[2] val_f16_array_2;
};
struct Struct_18 {
    float scalar1;
    float scalar2;
    vec3 v3;
    float tuck_in;
    float scalar4;
    uint larger;
};
 float global_0 = 0.0f;
 float global_1 = f16(15.2f);
layout(set = 0, binding = 0) uniform Struct_15 global_2;
layout(set = 0, binding = 1) buffer Struct_15 global_3;
layout(set = 0, binding = 3) buffer Struct_15 global_4;
layout(set = 0, binding = 2) buffer Struct_17 global_5;
layout(set = 0, binding = 4) buffer Struct_17 global_6;

void f16() {
}

float f16_function(float x) {
    Struct_18 l;
    float val = f16(global_1);
    val = (0.0f - 0.0f);
    val = (val + f16(5.0f));
    val = f16((global_2.val_f32 + float(val)));
    val = vec3(global_2.val_f16)[2];
    global_4.val_i32 = int(0.0f);
    global_4.val_i32 = int(-(0.0f));
    global_4.val_u32 = uint(0.0f);
    global_4.val_u32 = uint(-(0.0f));
    global_4.val_f32 = float(0.0f);
    global_4.val_f32 = float(-(0.0f));
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
    val = f16(1.0f);
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

void f16() {
}

void f16() {
}

void f16() {
}

void abs() {
}

void clamp() {
}

void dot() {
}

void max() {
}

void min() {
}

void sign() {
}

void f16() {
}

void wgsl_main() {
    global_4.final_value = f16_function(0.0f);
}

void main() {
    wgsl_main();
}
