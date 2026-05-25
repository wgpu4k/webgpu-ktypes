#include <metal_stdlib>
using namespace metal;
uint global_0 = 2u;
int global_1 = 3;
bool global_2 = true;
bool global_3 = false;
int global_4 = 4;
float global_5 = 3.141f;
float4 global_6 = float4((4.0f / 9.0f), 0.0f, 0.0f, 0.0f);
int global_7 = 0;
int global_8 = 1;
int global_9 = 2;
float2 global_10 = (float2(1.0f) + float2(3.0f, 4.0f));
bool2 global_11 = (float2(3.0f) == float2(3.0f, 4.0f));
array<int, 9> global_12 = array<int, 9>(1, 2, 3, 4, 5, 6, 7, 8, 9);
float4 global_13 = float4(1, 2, 3, 4);
int global_14 = global_4;
int global_15 = (global_4 + global_4);
float global_16 = (global_5 * 2.0f);
int global_17 = (global_14 + global_14);

void swizzle_of_compose() {
    float4 local_0 = float4(float2(1, 2), float2(3, 4)).wzyx;
}

void index_of_compose() {
    float local_0 = float4(float2(1, 2), float2(3, 4))[1];
}

void compose_three_deep() {
    float local_0 = float4(float3(float2(6, 7), 8), 9)[0];
}

void non_constant_initializers() {
    int local_0 = (10 + 20);
    int local_1 = local_0;
    int local_2 = local_1;
    int local_3 = (30 + 40);
    float4 local_4 = float4(local_0, local_1, local_2, local_3);
}

void compose_of_splat() {
    float4 local_0 = float4(float3(1.0f), 2.0f).wzyx;
}

void compose_vector_zero_val_binop() {
    float3 local_0 = (float3(int2(), 0) + float3(1));
    float3 local_1 = (float3(int2(), 0) + float3(0, 1, 2));
    float3 local_2 = (float3(int2(), 2) + float3(1, int2()));
}

void test_local_const() {
    int local_0 = 2;
    array<float> local_1;
}

void packed_dot_product() {
    int local_0 = dot4I8Packed(global_0, global_0);
    uint local_1 = dot4U8Packed(global_0, global_0);
    int local_2 = dot4I8Packed((global_0 + 1u), (global_0 + 2u));
    uint local_3 = dot4U8Packed((global_0 + 1u), (global_0 + 2u));
    int local_4 = dot4I8Packed(16909060u, 84281096u);
    uint local_5 = dot4U8Packed(16909060u, 84281096u);
    int local_6 = dot4I8Packed(4278385924u, 84343288u);
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
    bool local_0 = any(false);
    bool local_1 = any(true);
    bool local_2 = all(false);
    bool local_3 = all(true);
    bool4 local_4 = any(bool4());
    float4 local_5 = any(float4(bool(), true, float2(global_3)));
    float4 local_6 = all(float4(float3(bool2(), global_2), false));
    float4 local_7 = all(float4(true));
}

bool any(bool arg_0) {
}

bool any(bool arg_0) {
}

bool all(bool arg_0) {
}

bool all(bool arg_0) {
}

bool4 any(bool4 arg_0) {
}

float4 any(float4 arg_0) {
}

float4 all(float4 arg_0) {
}

float4 all(float4 arg_0) {
}

void splat_of_constant() {
    float4 local_0 = -(float4(global_4));
}

void compose_of_constant() {
    float4 local_0 = -(float4(global_4, global_4, global_4, global_4));
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
    float local_0 = global_12[0];
    uint local_1 = global_13[0];
    int local_2 = global_12[i];
    int local_3 = global_13[i];
}

[[kernel]]
void main() {
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
