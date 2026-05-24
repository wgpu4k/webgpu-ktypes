#version 450 core
precision highp float;
precision highp int;


bool test_any_and_all_for_bool() {
    bool a = any(true);
    return all(a);
}

void any() {
}

void all() {
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

void dpdxCoarse() {
}

void dpdyCoarse() {
}

void fwidthCoarse() {
}

void dpdxFine() {
}

void dpdyFine() {
}

void fwidthFine() {
}

void dpdx() {
}

void dpdy() {
}

void fwidth() {
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_derivatives(foo);
}
