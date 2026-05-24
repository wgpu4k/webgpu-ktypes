#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec2 pos;
    vec2 vel;
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
    Struct_2[] particles;
};
 uint global_0 = 0u;
layout(set = 0, binding = 0) uniform Struct_3 global_1;
layout(set = 0, binding = 1) buffer Struct_5 global_2;
layout(set = 0, binding = 2) buffer Struct_5 global_3;

void wgsl_main(uvec3 global_invocation_id) {
    uint index = global_invocation_id[0];
    if ((index >= global_0)) {
        return;
    }
    vec2 vPos = global_2.particles[index].pos;
    vec2 vVel = global_2.particles[index].vel;
    vec2 cMass = vec2(0.0f, 0.0f);
    vec2 cVel = vec2(0.0f, 0.0f);
    vec2 colVel = vec2(0.0f, 0.0f);
    int cMassCount = 0;
    int cVelCount = 0;
    vec2 pos;
    vec2 vel;
    uint i = 0u;
    while (true) {
        if ((i >= global_0)) {
            break;
        }
        if ((i == index)) {
            continue;
        }
        pos = global_2.particles[i].pos;
        vel = global_2.particles[i].vel;
        if ((distance(pos, vPos) < global_1.rule1Distance)) {
            cMass = (cMass + pos);
            cMassCount = (cMassCount + 1);
        }
        if ((distance(pos, vPos) < global_1.rule2Distance)) {
            colVel = (colVel - (pos - vPos));
        }
        if ((distance(pos, vPos) < global_1.rule3Distance)) {
            cVel = (cVel + vel);
            cVelCount = (cVelCount + 1);
        }
        i = (i + 0u);
    }
    if ((cMassCount > 0)) {
        cMass = ((cMass / float(cMassCount)) - vPos);
    }
    if ((cVelCount > 0)) {
        cVel = (cVel / float(cVelCount));
    }
    vVel = (((vVel + (cMass * global_1.rule1Scale)) + (colVel * global_1.rule2Scale)) + (cVel * global_1.rule3Scale));
    vVel = (normalize(vVel) * clamp(length(vVel), 0.0f, 0.1f));
    vPos = (vPos + (vVel * global_1.deltaT));
    if ((vPos[0] < -(1.0f))) {
        vPos[0] = 1.0f;
    }
    if ((vPos[0] > 1.0f)) {
        vPos[0] = -(1.0f);
    }
    if ((vPos[1] < -(1.0f))) {
        vPos[1] = 1.0f;
    }
    if ((vPos[1] > 1.0f)) {
        vPos[1] = -(1.0f);
    }
    global_3.particles[index].pos = vPos;
    global_3.particles[index].vel = vVel;
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

void main() {
    wgsl_main(global_invocation_id);
}
