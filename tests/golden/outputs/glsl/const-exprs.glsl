#version 450 core
precision highp float;
precision highp int;

 uint global_0 = 2u;
 int global_1 = 3;
 bool global_2 = true;
 bool global_3 = false;
 int global_4 = 4;
 float global_5 = 3.141f;
 vec4 global_6 = vec4((4.0f / 9.0f), 0.0f, 0.0f, 0.0f);
 int global_7 = 0;
 int global_8 = 1;
 int global_9 = 2;
 vec2 global_10 = (vec2(1.0f) + vec2(3.0f, 4.0f));
 bvec2 global_11 = (vec2(3.0f) == vec2(3.0f, 4.0f));
 int[9] global_12 = int[9](1, 2, 3, 4, 5, 6, 7, 8, 9);
 vec4 global_13 = vec4(1, 2, 3, 4);
 int global_14 = global_4;
 int global_15 = (global_4 + global_4);
 float global_16 = (global_5 * 2.0f);
 int global_17 = (global_14 + global_14);

void swizzle_of_compose() {
    vec4 out = vec4(vec2(1, 2), vec2(3, 4)).wzyx;
}

void index_of_compose() {
    float out = vec4(vec2(1, 2), vec2(3, 4))[1];
}

void compose_three_deep() {
    float out = vec4(vec3(vec2(6, 7), 8), 9)[0];
}

void non_constant_initializers() {
    int w = (10 + 20);
    int x = w;
    int y = x;
    int z = (30 + 40);
    vec4 out = vec4(w, x, y, z);
}

void compose_of_splat() {
    vec4 x = vec4(vec3(1.0f), 2.0f).wzyx;
}

void compose_vector_zero_val_binop() {
    vec3 a = (vec3(ivec2(0), 0) + vec3(1));
    vec3 b = (vec3(ivec2(0), 0) + vec3(0, 1, 2));
    vec3 c = (vec3(ivec2(0), 2) + vec3(1, ivec2(0)));
}

void test_local_const() {
    int local_const = 2;
    float[] arr;
}

void packed_dot_product() {
    int signed_four = dot4I8Packed(global_0, global_0);
    uint unsigned_four = dot4U8Packed(global_0, global_0);
    int signed_twelve = dot4I8Packed((global_0 + 1u), (global_0 + 2u));
    uint unsigned_twelve = dot4U8Packed((global_0 + 1u), (global_0 + 2u));
    int signed_seventy = dot4I8Packed(16909060u, 84281096u);
    uint unsigned_seventy = dot4U8Packed(16909060u, 84281096u);
    int minus_four = dot4I8Packed(4278385924u, 84343288u);
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

uint dot4U8Packed(uint arg_0, uint arg_1) {
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

uint dot4U8Packed(uint arg_0, uint arg_1) {
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

uint dot4U8Packed(uint arg_0, uint arg_1) {
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

void relational() {
    bool scalar_any_false = any(false);
    bool scalar_any_true = any(true);
    bool scalar_all_false = all(false);
    bool scalar_all_true = all(true);
    bvec4 vec_any_false = any(bvec4(false));
    vec4 vec_any_true = any(vec4(false, true, vec2(global_3)));
    vec4 vec_all_false = all(vec4(vec3(bvec2(false), global_2), false));
    vec4 vec_all_true = all(vec4(true));
}

bool any(bool arg_0) {
}

bool any(bool arg_0) {
}

bool all(bool arg_0) {
}

bool all(bool arg_0) {
}

bvec4 any(bvec4 arg_0) {
}

vec4 any(vec4 arg_0) {
}

vec4 all(vec4 arg_0) {
}

vec4 all(vec4 arg_0) {
}

void splat_of_constant() {
    vec4 out = -(vec4(global_4));
}

void compose_of_constant() {
    vec4 out = -(vec4(global_4, global_4, global_4, global_4));
}

uint map_texture_kind(int texture_kind) {
    switch (texture_kind) {
        case 0: {
            return 10u;
            break;
        }
        case 1: {
            return 20u;
            break;
        }
        case 2: {
            return 30u;
            break;
        }
        default: {
            return 0u;
            break;
        }
    }
}

void abstract_access(uint i) {
    float a = global_12[0];
    uint b = global_13[0];
    int c = global_12[i];
    int d = global_13[i];
}

void wgsl_main() {
    swizzle_of_compose();
    index_of_compose();
    compose_three_deep();
    non_constant_initializers();
    splat_of_constant();
    compose_of_constant();
    map_texture_kind(1);
    compose_of_splat();
    test_local_const();
    compose_vector_zero_val_binop();
    relational();
    packed_dot_product();
    test_local_const();
    abstract_access(1);
}

void main() {
    wgsl_main();
}
