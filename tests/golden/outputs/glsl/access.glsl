#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    uint a;
    uvec3 b;
    int c;
};
struct Struct_4 {
    int value;
};
struct Struct_7 {
    mat3x2 m;
};
struct Struct_10 {
    mat4x2[2] am;
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
    mat4x3 _matrix;
    mat2x2[2] matrix_array;
    int atom;
    int[10] atom_arr;
    uvec2[2] arr;
    Struct_4[] data;
};
struct Struct_22 {
    Struct_13 om_nom_nom;
    uint thing;
};
layout(set = 0, binding = 2) buffer ivec2 global_0;
 Struct_3 global_1 = Struct_3(0u, uvec3(0u, 0u, 0u), 0);
layout(set = 0, binding = 1) uniform Struct_7 global_2;
layout(set = 0, binding = 3) uniform Struct_10 global_3;
layout(set = 0, binding = 0) buffer Struct_21 global_4;

float read_from_private(float foo) {
    return foo;
}

float test_arr_as_arg(float[10][5] a) {
    return a[4][9];
}

void assign_through_ptr_fn(uint p) {
    p = 42u;
}

void assign_array_through_ptr_fn(vec4[2] foo) {
    foo = vec4[2](vec4(1.0f), vec4(2.0f));
}

uint fetch_arg_ptr_array_element(uint[4] p) {
    return p[1];
}

void assign_to_arg_ptr_array_element(uint[4] p) {
    p[1] = 10u;
}

bool index_ptr(bool value) {
    bool[1] a = bool[1](value);
    bool[1] p = a;
    return p[0];
}

void assign_through_ptr() {
    uint val = 33u;
    assign_through_ptr_fn(val);
    vec4[2] arr = vec4[2](vec4(6.0f), vec4(7.0f));
    assign_array_through_ptr_fn(arr);
}

uint fetch_arg_ptr_member(Struct_11 p) {
    return p.x;
}

void assign_to_arg_ptr_member(Struct_11 p) {
    p.x = 10u;
}

int member_ptr() {
    Struct_12 s = Struct_12(42);
    Struct_12 p = s;
    return p[0];
}

void test_matrix_within_struct_accesses() {
    int idx = 1;
    idx = (idx - 1);
    mat3x2 l0 = global_2.m;
    vec2 l1 = global_2.m[0];
    vec2 l2 = global_2.m[idx];
    float l3 = global_2.m[0][1];
    float l4 = global_2.m[0][idx];
    float l5 = global_2.m[idx][1];
    float l6 = global_2.m[idx][idx];
    Struct_7 t = Struct_7(mat3x2(vec2(1.0f), vec2(2.0f), vec2(3.0f)));
    idx = (idx + 1);
    t.m = mat3x2(vec2(6.0f), vec2(5.0f), vec2(4.0f));
    t.m[0] = vec2(9.0f);
    t.m[idx] = vec2(90.0f);
    t.m[0][1] = 10.0f;
    t.m[0][idx] = 20.0f;
    t.m[idx][1] = 30.0f;
    t.m[idx][idx] = 40.0f;
}

void test_matrix_within_array_within_struct_accesses() {
    int idx = 1;
    idx = (idx - 1);
    mat4x2[2] l0 = global_3.am;
    mat4x2 l1 = global_3.am[0];
    vec2 l2 = global_3.am[0][0];
    vec2 l3 = global_3.am[0][idx];
    float l4 = global_3.am[0][0][1];
    float l5 = global_3.am[0][0][idx];
    float l6 = global_3.am[0][idx][1];
    float l7 = global_3.am[0][idx][idx];
    Struct_10 t = Struct_10(mat4x2[2]());
    idx = (idx + 1);
    t.am = mat4x2[2]();
    t.am[0] = mat4x2(vec2(8.0f), vec2(7.0f), vec2(6.0f), vec2(5.0f));
    t.am[0][0] = vec2(9.0f);
    t.am[0][idx] = vec2(90.0f);
    t.am[0][0][1] = 10.0f;
    t.am[0][0][idx] = 20.0f;
    t.am[0][idx][1] = 30.0f;
    t.am[0][idx][idx] = 40.0f;
}

void assign_to_ptr_components() {
    Struct_11 s1;
    assign_to_arg_ptr_member(s1);
    fetch_arg_ptr_member(s1);
    uint[4] a1;
    assign_to_arg_ptr_array_element(a1);
    fetch_arg_ptr_array_element(a1);
}

int let_members_of_members() {
    Struct_22 thing = Struct_22();
    Struct_13 inner = thing.om_nom_nom;
    int delishus = inner.delicious;
    if ((thing.thing != uint(delishus))) {
    }
    return thing.om_nom_nom.delicious;
}

int var_members_of_members() {
    Struct_22 thing = Struct_22();
    Struct_13 inner = thing.om_nom_nom;
    int delishus = inner.delicious;
    if ((thing.thing != uint(delishus))) {
    }
    return thing.om_nom_nom.delicious;
}

vec4 wgsl_foo_frag() {
    global_4._matrix[1][2] = 1.0f;
    global_4._matrix = mat4x3(vec3(0.0f), vec3(1.0f), vec3(2.0f), vec3(3.0f));
    global_4.arr = uvec2[2](uvec2(0u), uvec2(1u));
    global_4.data[1].value = 1;
    global_0 = ivec2(0);
    return vec4(0.0f);
}

vec4 wgsl_foo_vert(uint vi) {
    float foo = 0.0f;
    float baz = foo;
    foo = 1.0f;
    global_1;
    test_matrix_within_struct_accesses();
    test_matrix_within_array_within_struct_accesses();
    mat4x3 _matrix = global_4._matrix;
    uvec2[2] arr = global_4.arr;
    uint index = 3u;
    float b = global_4._matrix[index][0];
    int a = global_4.data[(arrayLength(global_4.data) - 2u)].value;
    ivec2 c = global_0;
    int data_pointer = global_4.data[0].value;
    float foo_value = read_from_private(foo);
    int[5] c2 = int[5](a, int(b), 3, 4, 5);
    c2[(vi + 1u)] = 42;
    int value = c2[vi];
    test_arr_as_arg(float[10][5]());
    return vec4((_matrix * vec4(ivec4(value))), 2.0f);
}

Struct_4[] arrayLength(Struct_4[] arg_0) {
}

void wgsl_foo_compute() {
    assign_through_ptr();
    assign_to_ptr_components();
    index_ptr(true);
    member_ptr();
    let_members_of_members();
    var_members_of_members();
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_foo_frag();
}

out vec4 outValue;
void main() {
    gl_Position = wgsl_foo_vert(vi);
}

void main() {
    wgsl_foo_compute();
}
