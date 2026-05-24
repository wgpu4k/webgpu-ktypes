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
    array<Struct_2> particles;
};
uint global_0 = 1500u;

[[kernel]]
void main(Struct_3 global_1 [[buffer(0)]], Struct_5 global_2 [[buffer(1)]], Struct_5 global_3 [[buffer(2)]]) {
    uint local_0 = global_invocation_id[0];
    if ((local_0 >= global_0)) {
        return;
    }
    float2 local_1 = global_2.particles[local_0].pos;
    float2 local_2 = global_2.particles[local_0].vel;
    float2 local_3 = float2(0.0f, 0.0f);
    float2 local_4 = float2(0.0f, 0.0f);
    float2 local_5 = float2(0.0f, 0.0f);
    int local_6 = 0;
    int local_7 = 0;
    float2 local_8;
    float2 local_9;
    uint local_10 = 0u;
    while (true) {
        if ((local_10 >= global_0)) {
            break;
        }
        if ((local_10 == local_0)) {
            continue;
        }
        local_8 = global_2.particles[local_10].pos;
        local_9 = global_2.particles[local_10].vel;
        if ((distance(local_8, local_1) < global_1.rule1Distance)) {
            local_3 = (local_3 + local_8);
            local_6 = (local_6 + 1);
        }
        if ((distance(local_8, local_1) < global_1.rule2Distance)) {
            local_5 = (local_5 - (local_8 - local_1));
        }
        if ((distance(local_8, local_1) < global_1.rule3Distance)) {
            local_4 = (local_4 + local_9);
            local_7 = (local_7 + 1);
        }
        local_10 = (local_10 + 1u);
    }
    if ((local_6 > 0)) {
        local_3 = ((local_3 / float(local_6)) - local_1);
    }
    if ((local_7 > 0)) {
        local_4 = (local_4 / float(local_7));
    }
    local_2 = (((local_2 + (local_3 * global_1.rule1Scale)) + (local_5 * global_1.rule2Scale)) + (local_4 * global_1.rule3Scale));
    local_2 = (normalize(local_2) * clamp(length(local_2), 0.0f, 0.1f));
    local_1 = (local_1 + (local_2 * global_1.deltaT));
    if ((local_1[0] < -(1.0f))) {
        local_1[0] = 1.0f;
    }
    if ((local_1[0] > 1.0f)) {
        local_1[0] = -(1.0f);
    }
    if ((local_1[1] < -(1.0f))) {
        local_1[1] = 1.0f;
    }
    if ((local_1[1] > 1.0f)) {
        local_1[1] = -(1.0f);
    }
    global_3.particles[local_0].pos = local_1;
    global_3.particles[local_0].vel = local_2;
}

float2 distance(float2 arg_0, float2 arg_1) {
}

float2 distance(float2 arg_0, float2 arg_1) {
}

float2 distance(float2 arg_0, float2 arg_1) {
}

float2 normalize(float2 arg_0) {
}

float2 length(float2 arg_0) {
}

float2 clamp(float2 arg_0, float arg_1, float arg_2) {
}
