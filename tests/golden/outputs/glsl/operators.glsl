#version 450 core
precision highp float;
precision highp int;

 vec4 global_0 = vec4(1.0f, 1.0f, 1.0f, 1.0f);
 vec4 global_1 = vec4(0.0f, 0.0f, 0.0f, 0.0f);
 vec4 global_2 = vec4(0.5f, 0.5f, 0.5f, 0.5f);
 ivec4 global_3 = ivec4(1, 1, 1, 1);
 bool global_4 = false;
 bool global_5 = true;
 bool global_6 = (false && (sqrt(-(1)) != 0));
 bool global_7 = (false && ((0u + 1.0f) > 0));
 vec3 global_8 = !((global_4 || any(vec3(false, false, false))));
 bool global_9 = (global_4 || global_5);

int sqrt(int arg_0) {
}

vec4 splat(float m, int n) {
    vec2 a = (((2.0f + vec2(m)) - 4.0f) / 8.0f);
    ivec4 b = mod(ivec4(n), 2);
    return (a.xyxy + vec4(b));
}

vec2 splat_assignment() {
    vec2 a = vec2(2.0f);
    a = 1.0f;
    a = 3.0f;
    a = 4.0f;
    return a;
}

vec3 bool_cast(vec3 x) {
    bvec3 y = bvec3(x);
    return vec3(y);
}

bool p() {
    return true;
}

bool q() {
    return false;
}

bool r() {
    return true;
}

bool s() {
    return false;
}

void arithmetic() {
    int one_i = 1;
    uint one_u = 1u;
    float one_f = 1.0f;
    int two_i = 2;
    uint two_u = 2u;
    float two_f = 2.0f;
    float neg0 = -(one_f);
    vec2 neg1 = -(vec2(one_i));
    vec2 neg2 = -(vec2(one_f));
    int add0 = (two_i + one_i);
    uint add1 = (two_u + one_u);
    float add2 = (two_f + one_f);
    vec2 add3 = (vec2(two_i) + vec2(one_i));
    vec3 add4 = (vec3(two_u) + vec3(one_u));
    vec4 add5 = (vec4(two_f) + vec4(one_f));
    int sub0 = (two_i - one_i);
    uint sub1 = (two_u - one_u);
    float sub2 = (two_f - one_f);
    vec2 sub3 = (vec2(two_i) - vec2(one_i));
    vec3 sub4 = (vec3(two_u) - vec3(one_u));
    vec4 sub5 = (vec4(two_f) - vec4(one_f));
    int mul0 = (two_i * one_i);
    uint mul1 = (two_u * one_u);
    float mul2 = (two_f * one_f);
    vec2 mul3 = (vec2(two_i) * vec2(one_i));
    vec3 mul4 = (vec3(two_u) * vec3(one_u));
    vec4 mul5 = (vec4(two_f) * vec4(one_f));
    int div0 = (two_i / one_i);
    uint div1 = (two_u / one_u);
    float div2 = (two_f / one_f);
    vec2 div3 = (vec2(two_i) / vec2(one_i));
    vec3 div4 = (vec3(two_u) / vec3(one_u));
    vec4 div5 = (vec4(two_f) / vec4(one_f));
    int rem0 = mod(two_i, one_i);
    uint rem1 = mod(two_u, one_u);
    float rem2 = mod(two_f, one_f);
    vec2 rem3 = mod(vec2(two_i), vec2(one_i));
    vec3 rem4 = mod(vec3(two_u), vec3(one_u));
    vec4 rem5 = mod(vec4(two_f), vec4(one_f));
    {
        vec2 add0 = (vec2(two_i) + one_i);
        vec2 add1 = (two_i + vec2(one_i));
        vec2 add2 = (vec2(two_u) + one_u);
        vec2 add3 = (two_u + vec2(one_u));
        vec2 add4 = (vec2(two_f) + one_f);
        vec2 add5 = (two_f + vec2(one_f));
        vec2 sub0 = (vec2(two_i) - one_i);
        vec2 sub1 = (two_i - vec2(one_i));
        vec2 sub2 = (vec2(two_u) - one_u);
        vec2 sub3 = (two_u - vec2(one_u));
        vec2 sub4 = (vec2(two_f) - one_f);
        vec2 sub5 = (two_f - vec2(one_f));
        vec2 mul0 = (vec2(two_i) * one_i);
        vec2 mul1 = (two_i * vec2(one_i));
        vec2 mul2 = (vec2(two_u) * one_u);
        vec2 mul3 = (two_u * vec2(one_u));
        vec2 mul4 = (vec2(two_f) * one_f);
        vec2 mul5 = (two_f * vec2(one_f));
        vec2 div0 = (vec2(two_i) / one_i);
        vec2 div1 = (two_i / vec2(one_i));
        vec2 div2 = (vec2(two_u) / one_u);
        vec2 div3 = (two_u / vec2(one_u));
        vec2 div4 = (vec2(two_f) / one_f);
        vec2 div5 = (two_f / vec2(one_f));
        vec2 rem0 = mod(vec2(two_i), one_i);
        vec2 rem1 = mod(two_i, vec2(one_i));
        vec2 rem2 = mod(vec2(two_u), one_u);
        vec2 rem3 = mod(two_u, vec2(one_u));
        vec2 rem4 = mod(vec2(two_f), one_f);
        vec2 rem5 = mod(two_f, vec2(one_f));
    }
    mat3x3 add = (mat3x3(vec3(0.0f), vec3(0.0f), vec3(0.0f)) + mat3x3(vec3(0.0f), vec3(0.0f), vec3(0.0f)));
    mat3x3 sub = (mat3x3(vec3(0.0f), vec3(0.0f), vec3(0.0f)) - mat3x3(vec3(0.0f), vec3(0.0f), vec3(0.0f)));
    mat3x3 mul_scalar0 = (mat3x3(vec3(0.0f), vec3(0.0f), vec3(0.0f)) * one_f);
    mat3x3 mul_scalar1 = (two_f * mat3x3(vec3(0.0f), vec3(0.0f), vec3(0.0f)));
    vec4 mul_vector0 = (mat4x3(vec3(0.0f), vec3(0.0f), vec3(0.0f), vec3(0.0f)) * vec4(one_f));
    vec3 mul_vector1 = (vec3(two_f) * mat4x3(vec3(0.0f), vec3(0.0f), vec3(0.0f), vec3(0.0f)));
    mat4x3 mul = (mat4x3(vec3(0.0f), vec3(0.0f), vec3(0.0f), vec3(0.0f)) * mat3x4(vec4(0.0f), vec4(0.0f), vec4(0.0f)));
    int prevent_const_eval;
    int wgpu_7437 = (prevent_const_eval + -(-2147483648));
}

void bit() {
    int one_i = 1;
    uint one_u = 1u;
    int two_i = 2;
    uint two_u = 2u;
    int flip0 = ~(one_i);
    uint flip1 = ~(one_u);
    vec2 flip2 = ~(vec2(one_i));
    vec3 flip3 = ~(vec3(one_u));
    int or0 = (two_i | one_i);
    uint or1 = (two_u | one_u);
    vec2 or2 = (vec2(two_i) | vec2(one_i));
    vec3 or3 = (vec3(two_u) | vec3(one_u));
    int and0 = (two_i & one_i);
    uint and1 = (two_u & one_u);
    vec2 and2 = (vec2(two_i) & vec2(one_i));
    vec3 and3 = (vec3(two_u) & vec3(one_u));
    int xor0 = (two_i ^ one_i);
    uint xor1 = (two_u ^ one_u);
    vec2 xor2 = (vec2(two_i) ^ vec2(one_i));
    vec3 xor3 = (vec3(two_u) ^ vec3(one_u));
    int shl0 = (two_i << one_u);
    uint shl1 = (two_u << one_u);
    vec2 shl2 = (vec2(two_i) << vec2(one_u));
    vec3 shl3 = (vec3(two_u) << vec3(one_u));
    int shr0 = (two_i >> one_u);
    uint shr1 = (two_u >> one_u);
    vec2 shr2 = (vec2(two_i) >> vec2(one_u));
    vec3 shr3 = (vec3(two_u) >> vec3(one_u));
}

void comparison() {
    int one_i = 1;
    uint one_u = 1u;
    float one_f = 1.0f;
    int two_i = 2;
    uint two_u = 2u;
    float two_f = 2.0f;
    bool eq0 = (two_i == one_i);
    bool eq1 = (two_u == one_u);
    bool eq2 = (two_f == one_f);
    bvec2 eq3 = (vec2(two_i) == vec2(one_i));
    bvec3 eq4 = (vec3(two_u) == vec3(one_u));
    bvec4 eq5 = (vec4(two_f) == vec4(one_f));
    bool neq0 = (two_i != one_i);
    bool neq1 = (two_u != one_u);
    bool neq2 = (two_f != one_f);
    bvec2 neq3 = (vec2(two_i) != vec2(one_i));
    bvec3 neq4 = (vec3(two_u) != vec3(one_u));
    bvec4 neq5 = (vec4(two_f) != vec4(one_f));
    bool lt0 = (two_i < one_i);
    bool lt1 = (two_u < one_u);
    bool lt2 = (two_f < one_f);
    bvec2 lt3 = (vec2(two_i) < vec2(one_i));
    bvec3 lt4 = (vec3(two_u) < vec3(one_u));
    bvec4 lt5 = (vec4(two_f) < vec4(one_f));
    bool lte0 = (two_i <= one_i);
    bool lte1 = (two_u <= one_u);
    bool lte2 = (two_f <= one_f);
    bvec2 lte3 = (vec2(two_i) <= vec2(one_i));
    bvec3 lte4 = (vec3(two_u) <= vec3(one_u));
    bvec4 lte5 = (vec4(two_f) <= vec4(one_f));
    bool gt0 = (two_i > one_i);
    bool gt1 = (two_u > one_u);
    bool gt2 = (two_f > one_f);
    bvec2 gt3 = (vec2(two_i) > vec2(one_i));
    bvec3 gt4 = (vec3(two_u) > vec3(one_u));
    bvec4 gt5 = (vec4(two_f) > vec4(one_f));
    bool gte0 = (two_i >= one_i);
    bool gte1 = (two_u >= one_u);
    bool gte2 = (two_f >= one_f);
    bvec2 gte3 = (vec2(two_i) >= vec2(one_i));
    bvec3 gte4 = (vec3(two_u) >= vec3(one_u));
    bvec4 gte5 = (vec4(two_f) >= vec4(one_f));
}

void assignment() {
    int zero_i = 0;
    int one_i = 1;
    uint one_u = 1u;
    uint two_u = 2u;
    int a = one_i;
    a = one_i;
    a = one_i;
    a = a;
    a = a;
    a = one_i;
    a = zero_i;
    a = zero_i;
    a = zero_i;
    a = two_u;
    a = one_u;
    a = (a + 1);
    a = (a - 1);
    ivec3 vec0 = ivec3(0);
    vec0[one_i] = (vec0[one_i] + 1);
    vec0[one_i] = (vec0[one_i] - 1);
}

void negation_avoids_prefix_decrement() {
    int i = 1;
    int i0 = -(i);
    int i1 = -(-(i));
    int i2 = -(-(i));
    int i3 = -(-(i));
    int i4 = -(-(-(i)));
    int i5 = -(-(-(-(i))));
    int i6 = -(-(-(-(-(i)))));
    int i7 = -(-(-(-(-(i)))));
    float f = 1.0f;
    float f0 = -(f);
    float f1 = -(-(f));
    float f2 = -(-(f));
    float f3 = -(-(f));
    float f4 = -(-(-(f)));
    float f5 = -(-(-(-(f))));
    float f6 = -(-(-(-(-(f)))));
    float f7 = -(-(-(-(-(f)))));
}

vec4 builtins() {
    bool condition = true;
    int s1 = select(0, 1, condition);
    vec4 s2 = select(global_1, global_0, condition);
    vec4 s3 = select(global_0, global_1, bvec4(false, false, false, false));
    vec4 m1 = mix(global_1, global_0, global_2);
    vec4 m2 = mix(global_1, global_0, 0.1f);
    float b1 = intBitsToFloat(global_3[0]);
    vec4 b2 = intBitsToFloat(global_3);
    ivec4 v_i32_zero = ivec4(global_1);
    return (((((vec4((ivec4(s1) + v_i32_zero)) + s2) + m1) + m2) + b1) + b2);
}

int select(int arg_0, int arg_1, bool arg_2) {
}

vec4 select(vec4 arg_0, vec4 arg_1, bool arg_2) {
}

vec4 select(vec4 arg_0, vec4 arg_1, bvec4 arg_2) {
}

vec4 mix(vec4 arg_0, vec4 arg_1, vec4 arg_2) {
}

vec4 mix(vec4 arg_0, vec4 arg_1, float arg_2) {
}

void logical() {
    bool t = true;
    bool f = false;
    bool neg0 = !(t);
    vec2 neg1 = !(vec2(t));
    bool or = (t || f);
    bool and = (t && f);
    bool bitwise_or0 = (t | f);
    vec3 bitwise_or1 = (vec3(t) | vec3(f));
    bool bitwise_and0 = (t & f);
    vec4 bitwise_and1 = (vec4(t) & vec4(f));
    bool short_circuit_1_invalid = (false && (sqrt(-(1)) != 0));
    bool short_circuit_2_invalid_rhs = (false && ((0u + 1.0f) > 0));
    bool short_circuit_3 = (global_4 || global_5);
    vec3 short_circuit_4 = !((global_4 || any(vec3(false, false, false))));
    vec3 short_circuit_5 = !((f || any(vec3(false, false, false))));
    bool short_circuit_6 = ((p() || q()) && (r() || s()));
    bool short_circuit_7 = (true || q());
}

int sqrt(int arg_0) {
}

void wgsl_main(uvec3 id) {
    builtins();
    splat(float(id[0]), int(id[1]));
    splat_assignment();
    bool_cast(global_0.xyz);
    logical();
    arithmetic();
    bit();
    comparison();
    assignment();
    negation_avoids_prefix_decrement();
}

void main() {
    wgsl_main(id);
}
