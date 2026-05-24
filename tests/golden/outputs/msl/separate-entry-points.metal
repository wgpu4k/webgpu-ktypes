#include <metal_stdlib>
using namespace metal;

void derivatives() {
    float local_0 = dpdx(0.0f);
    float local_1 = dpdy(0.0f);
    float local_2 = fwidth(0.0f);
}

void dpdx() {
}

void dpdy() {
}

void fwidth() {
}

void barriers() {
}

void storageBarrier() {
}

void workgroupBarrier() {
}

void textureBarrier() {
}

[[fragment]]
float4 fragment() {
}

[[kernel]]
void compute() {
}
