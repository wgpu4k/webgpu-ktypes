#include <metal_stdlib>
using namespace metal;

void derivatives() {
    float local_0 = dpdx(0.0f);
    float local_1 = dpdy(0.0f);
    float local_2 = fwidth(0.0f);
}

float dpdx(float arg_0) {
}

float dpdy(float arg_0) {
}

float fwidth(float arg_0) {
}

void barriers() {
    storageBarrier();
    workgroupBarrier();
    textureBarrier();
}

void storageBarrier() {
}

void workgroupBarrier() {
}

void textureBarrier() {
}

[[fragment]]
float4 fragment() {
    derivatives();
    return float4();
}

[[kernel]]
void compute() {
    barriers();
}
