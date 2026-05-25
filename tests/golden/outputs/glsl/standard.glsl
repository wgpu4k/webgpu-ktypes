#version 450 core
precision highp float;
precision highp int;


bool test_any_and_all_for_bool() {
    bool a = any(true);
    return all(a);
}

bool all(bool arg_0) {
}

vec4 wgsl_derivatives(vec4 foo) {
    vec4 x = dpdxCoarse(foo);
    vec4 y = dpdyCoarse(foo);
    vec4 z = fwidthCoarse(foo);
    x = dpdxFine(foo);
    y = dpdyFine(foo);
    z = fwidthFine(foo);
    x = dpdx(foo);
    y = dpdy(foo);
    z = fwidth(foo);
    bool a = test_any_and_all_for_bool();
    return ((x + y) * z);
}

vec4 dpdxCoarse(vec4 arg_0) {
}

vec4 dpdyCoarse(vec4 arg_0) {
}

vec4 fwidthCoarse(vec4 arg_0) {
}

vec4 dpdxFine(vec4 arg_0) {
}

vec4 dpdyFine(vec4 arg_0) {
}

vec4 fwidthFine(vec4 arg_0) {
}

vec4 dpdx(vec4 arg_0) {
}

vec4 dpdy(vec4 arg_0) {
}

vec4 fwidth(vec4 arg_0) {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_derivatives(foo);
}
