#version 450 core
precision highp float;
precision highp int;

 float global_0 = -(65504.0f);
 float global_1 = 65504.0f;
 float global_2 = -(3.4028235E38f);
 float global_3 = 3.4028235E38f;
 float global_4 = -(Infinity.0f);
 float global_5 = Infinity.0f;
 float global_6 = -(Infinity.0f);
 float global_7 = Infinity.0f;

int test_f16_to_i32(float16_t f) {
    return int(f);
}

uint test_f16_to_u32(float16_t f) {
    return uint(f);
}

int test_f16_to_i64(float16_t f) {
    return int(f);
}

uint test_f16_to_u64(float16_t f) {
    return uint(f);
}

int test_f32_to_i32(float f) {
    return int(f);
}

uint test_f32_to_u32(float f) {
    return uint(f);
}

int test_f32_to_i64(float f) {
    return int(f);
}

uint test_f32_to_u64(float f) {
    return uint(f);
}

int test_f64_to_i32(double f) {
    return int(f);
}

uint test_f64_to_u32(double f) {
    return uint(f);
}

int test_f64_to_i64(double f) {
    return int(f);
}

uint test_f64_to_u64(double f) {
    return uint(f);
}

ivec2 test_f16_to_i32_vec(vec2 f) {
    return ivec2(f);
}

uvec2 test_f16_to_u32_vec(vec2 f) {
    return uvec2(f);
}

ivec2 test_f16_to_i64_vec(vec2 f) {
    return ivec2(f);
}

uvec2 test_f16_to_u64_vec(vec2 f) {
    return uvec2(f);
}

ivec2 test_f32_to_i32_vec(vec2 f) {
    return ivec2(f);
}

uvec2 test_f32_to_u32_vec(vec2 f) {
    return uvec2(f);
}

ivec2 test_f32_to_i64_vec(vec2 f) {
    return ivec2(f);
}

uvec2 test_f32_to_u64_vec(vec2 f) {
    return uvec2(f);
}

ivec2 test_f64_to_i32_vec(dvec2 f) {
    return ivec2(f);
}

uvec2 test_f64_to_u32_vec(dvec2 f) {
    return uvec2(f);
}

ivec2 test_f64_to_i64_vec(dvec2 f) {
    return ivec2(f);
}

uvec2 test_f64_to_u64_vec(dvec2 f) {
    return uvec2(f);
}

void test_const_eval() {
    int min_f16_to_i32 = int(global_0);
    int max_f16_to_i32 = int(global_1);
    uint min_f16_to_u32 = uint(global_0);
    uint max_f16_to_u32 = uint(global_1);
    int min_f16_to_i64 = int(global_0);
    int max_f16_to_i64 = int(global_1);
    uint min_f16_to_u64 = uint(global_0);
    uint max_f16_to_u64 = uint(global_1);
    int min_f32_to_i32 = int(global_2);
    int max_f32_to_i32 = int(global_3);
    uint min_f32_to_u32 = uint(global_2);
    uint max_f32_to_u32 = uint(global_3);
    int min_f32_to_i64 = int(global_2);
    int max_f32_to_i64 = int(global_3);
    uint min_f32_to_u64 = uint(global_2);
    uint max_f32_to_u64 = uint(global_3);
    int min_f64_to_i64 = int(global_4);
    int max_f64_to_i64 = int(global_5);
    uint min_f64_to_u64 = uint(global_4);
    uint max_f64_to_u64 = uint(global_5);
    int min_abstract_float_to_i32 = int(global_6);
    int max_abstract_float_to_i32 = int(global_7);
    uint min_abstract_float_to_u32 = uint(global_6);
    uint max_abstract_float_to_u32 = uint(global_7);
    int min_abstract_float_to_i64 = int(global_6);
    int max_abstract_float_to_i64 = int(global_7);
    uint min_abstract_float_to_u64 = uint(global_6);
    uint max_abstract_float_to_u64 = uint(global_7);
}

void wgsl_main() {
    test_const_eval();
    test_f16_to_i32(1.0f);
    test_f16_to_u32(1.0f);
    test_f16_to_i64(1.0f);
    test_f16_to_u64(1.0f);
    test_f32_to_i32(1.0f);
    test_f32_to_u32(1.0f);
    test_f32_to_i64(1.0f);
    test_f32_to_u64(1.0f);
    test_f64_to_i32(1.0f);
    test_f64_to_u32(1.0f);
    test_f64_to_i64(1.0f);
    test_f64_to_u64(1.0f);
    test_f16_to_i32_vec(vec2(1.0f, 2.0f));
    test_f16_to_u32_vec(vec2(1.0f, 2.0f));
    test_f16_to_i64_vec(vec2(1.0f, 2.0f));
    test_f16_to_u64_vec(vec2(1.0f, 2.0f));
    test_f32_to_i32_vec(vec2(1.0f, 2.0f));
    test_f32_to_u32_vec(vec2(1.0f, 2.0f));
    test_f32_to_i64_vec(vec2(1.0f, 2.0f));
    test_f32_to_u64_vec(vec2(1.0f, 2.0f));
    test_f64_to_i32_vec(vec2(1.0f, 2.0f));
    test_f64_to_u32_vec(vec2(1.0f, 2.0f));
    test_f64_to_i64_vec(vec2(1.0f, 2.0f));
    test_f64_to_u64_vec(vec2(1.0f, 2.0f));
}

void main() {
    wgsl_main();
}
