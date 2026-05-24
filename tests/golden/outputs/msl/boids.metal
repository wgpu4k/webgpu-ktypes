#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float2 pos;
    float2 vel;
};
struct Struct_3 {
    float deltaT;
    float rule1Distance;
    float rule2Distance;
    float rule3Distance;
    float rule1Scale;
    float rule2Scale;
    float rule3Scale;
};
struct Struct_5 {
    /* unknown type */ void particles;
};
uint global_0 = 0u;

[[kernel]]
void main(Struct_3 global_1 [[buffer(0)]], Struct_5 global_2 [[buffer(1)]], Struct_5 global_3 [[buffer(2)]]) {
}

void distance() {
}

void distance() {
}

void distance() {
}

void normalize() {
}

void clamp() {
}

void length() {
}
