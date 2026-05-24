#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    uint a;
    uint3 b;
    int c;
};
struct Struct_4 {
    int value;
};
struct Struct_7 {
    float3x2 m;
};
struct Struct_10 {
    /* unknown type */ void am;
};
struct Struct_11 {
    uint x;
};
struct Struct_12 {
    int m;
};
struct Struct_13 {
    int delicious;
};
struct Struct_21 {
    float4x3 _matrix;
    /* unknown type */ void matrix_array;
    int atom;
    /* unknown type */ void atom_arr;
    /* unknown type */ void arr;
    /* unknown type */ void data;
};
struct Struct_22 {
    Struct_13 om_nom_nom;
    uint thing;
};
Struct_3 global_1 = Struct_3(0u, uint3(0u, 0u, 0u), 0);

float read_from_private(thread float* foo) {
    return foo;
}

float test_arr_as_arg(/* unknown type */ void a) {
    return a[4][9];
}

void assign_through_ptr_fn(thread uint* p) {
    p = 0u;
}

void assign_array_through_ptr_fn(thread /* unknown type */ void* foo) {
    foo = /* unknown type */ void(float4(1.0f), float4(2.0f));
}

uint fetch_arg_ptr_array_element(thread /* unknown type */ void* p) {
    return p[1];
}

void assign_to_arg_ptr_array_element(thread /* unknown type */ void* p) {
    p[1] = 0u;
}

bool index_ptr(bool value) {
    /* unknown type */ void local_0 = /* unknown type */ void(value);
    /* unknown type */ void local_1 = &local_0;
    return local_1[0];
}

void assign_through_ptr() {
    uint local_0 = 0u;
    /* unknown type */ void local_1 = /* unknown type */ void(float4(6.0f), float4(7.0f));
}

uint fetch_arg_ptr_member(thread Struct_11* p) {
    return p.x;
}

void assign_to_arg_ptr_member(thread Struct_11* p) {
    p.x = 0u;
}

int member_ptr() {
    Struct_12 local_0 = Struct_12(42);
    /* unknown type */ void local_1 = &local_0;
    return local_1[0];
}

void test_matrix_within_struct_accesses() {
    int local_0 = 1;
    local_0 = (local_0 - 1);
    float3x2 local_1 = global_2.m;
    float2 local_2 = global_2.m[0];
    float2 local_3 = global_2.m[local_0];
    float local_4 = global_2.m[0][1];
    float local_5 = global_2.m[0][local_0];
    float local_6 = global_2.m[local_0][1];
    float local_7 = global_2.m[local_0][local_0];
    Struct_7 local_8 = Struct_7(float3x2(float2(1.0f), float2(2.0f), float2(3.0f)));
    local_0 = (local_0 + 1);
    local_8.m = float3x2(float2(6.0f), float2(5.0f), float2(4.0f));
    local_8.m[0] = float2(9.0f);
    local_8.m[local_0] = float2(90.0f);
    local_8.m[0][1] = 10.0f;
    local_8.m[0][local_0] = 20.0f;
    local_8.m[local_0][1] = 30.0f;
    local_8.m[local_0][local_0] = 40.0f;
}

void test_matrix_within_array_within_struct_accesses() {
    int local_0 = 1;
    local_0 = (local_0 - 1);
    /* unknown type */ void local_1 = global_3.am;
    float4x2 local_2 = global_3.am[0];
    float2 local_3 = global_3.am[0][0];
    float2 local_4 = global_3.am[0][local_0];
    float local_5 = global_3.am[0][0][1];
    float local_6 = global_3.am[0][0][local_0];
    float local_7 = global_3.am[0][local_0][1];
    float local_8 = global_3.am[0][local_0][local_0];
    Struct_10 local_9 = Struct_10(/* unknown type */ void());
    local_0 = (local_0 + 1);
    local_9.am = /* unknown type */ void();
    local_9.am[0] = float4x2(float2(8.0f), float2(7.0f), float2(6.0f), float2(5.0f));
    local_9.am[0][0] = float2(9.0f);
    local_9.am[0][local_0] = float2(90.0f);
    local_9.am[0][0][1] = 10.0f;
    local_9.am[0][0][local_0] = 20.0f;
    local_9.am[0][local_0][1] = 30.0f;
    local_9.am[0][local_0][local_0] = 40.0f;
}

void assign_to_ptr_components() {
    Struct_11 local_0;
    /* unknown type */ void local_1;
}

int let_members_of_members() {
    Struct_22 local_0 = Struct_22();
    Struct_13 local_1 = local_0.om_nom_nom;
    int local_2 = local_1.delicious;
    if ((local_0.thing != uint(local_2))) {
    }
    return local_0.om_nom_nom.delicious;
}

int var_members_of_members() {
    Struct_22 local_0 = Struct_22();
    Struct_13 local_1 = local_0.om_nom_nom;
    int local_2 = local_1.delicious;
    if ((local_0.thing != uint(local_2))) {
    }
    return local_0.om_nom_nom.delicious;
}

[[fragment]]
float4 foo_frag(int2 global_0 [[buffer(2)]], Struct_7 global_2 [[buffer(1)]], Struct_10 global_3 [[buffer(3)]], Struct_21 global_4 [[buffer(0)]]) {
}

struct foo_vert_Output {
    float4 position [[position]];
};
[[vertex]]
foo_vert_Output foo_vert(int2 global_0 [[buffer(2)]], Struct_7 global_2 [[buffer(1)]], Struct_10 global_3 [[buffer(3)]], Struct_21 global_4 [[buffer(0)]]) {
}

[[kernel]]
void foo_compute(int2 global_0 [[buffer(2)]], Struct_7 global_2 [[buffer(1)]], Struct_10 global_3 [[buffer(3)]], Struct_21 global_4 [[buffer(0)]]) {
}
