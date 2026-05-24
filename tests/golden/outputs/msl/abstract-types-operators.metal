#include <metal_stdlib>
using namespace metal;
float global_0 = (1.0f + 2.0f);
float global_1 = (1.0f + 2);
float global_2 = (1.0f + 2.0f);
float global_3 = (1 + 2.0f);
float global_4 = (1 + 2);
float global_5 = (1 + 2.0f);
float global_6 = (1.0f + 2.0f);
float global_7 = (1.0f + 2);
float global_8 = (1.0f + 2.0f);
int global_9 = (1 + 2);
int global_10 = (1 + 2);
int global_11 = (1 + 2);
int global_12 = (1 + 2);
uint global_13 = (1 + 2);
uint global_14 = (1 + 2u);
uint global_15 = (1u + 2);
uint global_16 = (1u + 2u);
uint global_17 = ~(0u);
uint global_18 = (~(0) & (0 - 1));
int global_19 = -(-2147483648);
float global_20 = -(3.4028235E38f);
int global_21 = (1 << 2);
int global_22 = (1 << 2u);
uint global_23 = (1 << 2);
uint global_24 = (1 << 2u);
int global_25 = (1 << 2);
int global_26 = (1 << 2u);
int global_27 = (1 >> 2);
int global_28 = (1 >> 2u);
uint global_29 = (1 >> 2);
uint global_30 = (1 >> 2u);
int global_31 = (1 >> 2);
int global_32 = (1 >> 2u);
int global_33 = int(-(0));
int global_34 = -(-2147483648);
array<uint, 64> global_35;

void runtime_values() {
    float local_0 = 42;
    int local_1 = 43;
    uint local_2 = 44;
    float local_3 = (1.0f + 2.0f);
    float local_4 = (1.0f + 2);
    float local_5 = (1.0f + local_0);
    float local_6 = (1 + 2.0f);
    float local_7 = (1 + 2);
    float local_8 = (1 + local_0);
    float local_9 = (local_0 + 2.0f);
    float local_10 = (local_0 + 2);
    float local_11 = (local_0 + local_0);
    int local_12 = (1 + 2);
    int local_13 = (1 + local_1);
    int local_14 = (local_1 + 2);
    int local_15 = (local_1 + local_1);
    uint local_16 = (1 + 2);
    uint local_17 = (1 + local_2);
    uint local_18 = (local_2 + 2);
    uint local_19 = (local_2 + local_2);
    int local_20 = (1 << local_2);
    int local_21 = (1 << local_2);
}

void wgpu_4445() {
    float local_0 = (((3.0f * 2.0f) - 1.0f) * 1.0f);
    float local_1 = (((3.0f * 2.0f) + 1.0f) * 1.0f);
    float local_2 = (((3.0f * 2.0f) - 1.0f) * 1.0f);
}

void wgpu_4435() {
    int local_0 = 1;
    uint local_1 = global_35[(local_0 - 1)];
}

[[kernel]]
void main() {
    runtime_values();
    wgpu_4445();
    wgpu_4435();
}
