#version 450 core
precision highp float;
precision highp int;

struct Struct_6 {
    float fract;
    float whole;
};
struct Struct_7 {
    vec2 fract;
    vec2 whole;
};
struct Struct_8 {
    vec4 fract;
    vec4 whole;
};
struct Struct_9 {
    float fract;
    int exp;
};
struct Struct_11 {
    vec4 fract;
    ivec4 exp;
};

void wgsl_main() {
    float f = 1.0f;
    vec4 v = vec4(0.0f);
    float a = degrees(f);
    float b = radians(f);
    vec4 c = degrees(v);
    vec4 d = radians(v);
    vec4 e = saturate(v);
    vec4 g = refract(v, v, f);
    int sign_a = sign(-(1));
    vec4 sign_b = sign(vec4(-(1)));
    float sign_c = sign(-(1.0f));
    vec4 sign_d = sign(vec4(-(1.0f)));
    vec4 sign_e = sign(vec4(0.0f));
    ivec2 const_dot = dot(ivec2(0), ivec2(0));
    uint first_leading_bit_abs = firstLeadingBit(abs(0u));
    int flb_a = firstLeadingBit(-(1));
    vec2 flb_b = firstLeadingBit(vec2(-(1)));
    vec2 flb_c = firstLeadingBit(vec2(1u));
    int ftb_a = firstTrailingBit(-(1));
    uint ftb_b = firstTrailingBit(1u);
    vec2 ftb_c = firstTrailingBit(vec2(-(1)));
    vec2 ftb_d = firstTrailingBit(vec2(1u));
    uint ctz_a = countTrailingZeros(0u);
    int ctz_b = countTrailingZeros(0);
    int ctz_c = countTrailingZeros(0);
    int ctz_d = countTrailingZeros(-(1));
    vec2 ctz_e = countTrailingZeros(vec2(0u));
    vec2 ctz_f = countTrailingZeros(vec2(0));
    vec2 ctz_g = countTrailingZeros(vec2(1u));
    vec2 ctz_h = countTrailingZeros(vec2(1));
    int clz_a = countLeadingZeros(-(1));
    uint clz_b = countLeadingZeros(1u);
    vec2 clz_c = countLeadingZeros(vec2(-(1)));
    vec2 clz_d = countLeadingZeros(vec2(1u));
    float lde_a = ldexp(1.0f, 2);
    vec2 lde_b = ldexp(vec2(1.0f, 2.0f), vec2(3, 4));
    Struct_6 modf_a = modf(1.5f);
    float modf_b = modf(1.5f)[0];
    float modf_c = modf(1.5f)[1];
    Struct_7 modf_d = modf(vec2(1.5f, 1.5f));
    float modf_e = modf(vec4(1.5f, 1.5f, 1.5f, 1.5f))[1][0];
    float modf_f = modf(vec2(1.5f, 1.5f))[0][1];
    Struct_9 frexp_a = frexp(1.5f);
    float frexp_b = frexp(1.5f)[0];
    int frexp_c = frexp(1.5f)[1];
    int frexp_d = frexp(vec4(1.5f, 1.5f, 1.5f, 1.5f))[1][0];
    float quantizeToF16_a = quantizeToF16(1.0f);
    vec2 quantizeToF16_b = quantizeToF16(vec2(1.0f, 1.0f));
    vec3 quantizeToF16_c = quantizeToF16(vec3(1.0f, 1.0f, 1.0f));
    vec4 quantizeToF16_d = quantizeToF16(vec4(1.0f, 1.0f, 1.0f, 1.0f));
}

float degrees(float arg_0) {
}

float radians(float arg_0) {
}

vec4 degrees(vec4 arg_0) {
}

vec4 radians(vec4 arg_0) {
}

vec4 saturate(vec4 arg_0) {
}

vec4 refract(vec4 arg_0, vec4 arg_1, float arg_2) {
}

int sign(int arg_0) {
}

vec4 sign(vec4 arg_0) {
}

float sign(float arg_0) {
}

vec4 sign(vec4 arg_0) {
}

vec4 sign(vec4 arg_0) {
}

ivec2 dot(ivec2 arg_0, ivec2 arg_1) {
}

uint abs(uint arg_0) {
}

uint firstLeadingBit(uint arg_0) {
}

int firstLeadingBit(int arg_0) {
}

vec2 firstLeadingBit(vec2 arg_0) {
}

vec2 firstLeadingBit(vec2 arg_0) {
}

int firstTrailingBit(int arg_0) {
}

uint firstTrailingBit(uint arg_0) {
}

vec2 firstTrailingBit(vec2 arg_0) {
}

vec2 firstTrailingBit(vec2 arg_0) {
}

uint countTrailingZeros(uint arg_0) {
}

int countTrailingZeros(int arg_0) {
}

int countTrailingZeros(int arg_0) {
}

int countTrailingZeros(int arg_0) {
}

vec2 countTrailingZeros(vec2 arg_0) {
}

vec2 countTrailingZeros(vec2 arg_0) {
}

vec2 countTrailingZeros(vec2 arg_0) {
}

vec2 countTrailingZeros(vec2 arg_0) {
}

int countLeadingZeros(int arg_0) {
}

uint countLeadingZeros(uint arg_0) {
}

vec2 countLeadingZeros(vec2 arg_0) {
}

vec2 countLeadingZeros(vec2 arg_0) {
}

float ldexp(float arg_0, int arg_1) {
}

vec2 ldexp(vec2 arg_0, vec2 arg_1) {
}

Struct_6 modf(float arg_0) {
}

Struct_6 modf(float arg_0) {
}

Struct_6 modf(float arg_0) {
}

Struct_7 modf(vec2 arg_0) {
}

Struct_8 modf(vec4 arg_0) {
}

Struct_7 modf(vec2 arg_0) {
}

Struct_9 frexp(float arg_0) {
}

Struct_9 frexp(float arg_0) {
}

Struct_9 frexp(float arg_0) {
}

Struct_11 frexp(vec4 arg_0) {
}

float quantizeToF16(float arg_0) {
}

vec2 quantizeToF16(vec2 arg_0) {
}

vec3 quantizeToF16(vec3 arg_0) {
}

vec4 quantizeToF16(vec4 arg_0) {
}

void main() {
    wgsl_main();
}
