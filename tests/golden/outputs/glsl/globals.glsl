#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec3 v3;
    float v1;
};
 bool global_0 = true;
shared float[0] global_1;
shared uint global_2;
layout(set = 0, binding = 2) buffer vec2[] global_3;
layout(set = 0, binding = 3) uniform vec4[20] global_4;
layout(set = 0, binding = 4) uniform vec3 global_5;
layout(set = 0, binding = 5) uniform mat3x2 global_6;
layout(set = 0, binding = 6) uniform mat2x4[2][2] global_7;
layout(set = 0, binding = 7) uniform mat4x2[2][2] global_8;
layout(set = 0, binding = 1) buffer Struct_2 global_9;

void test_msl_packed_vec3_as_arg(vec3 arg) {
}

void test_msl_packed_vec3() {
    global_9.v3 = vec3(1.0f);
    int idx = 1;
    global_9.v3[0] = 1.0f;
    global_9.v3[0] = 2.0f;
    global_9.v3[idx] = 3.0f;
    Struct_2 data = global_9;
    vec3 l0 = data.v3;
    vec2 l1 = data.v3.zx;
    vec3 mvm0 = (data.v3 * mat3x3());
    vec3 mvm1 = (mat3x3() * data.v3);
    vec3 svm0 = (data.v3 * 2.0f);
    vec3 svm1 = (2.0f * data.v3);
}

void wgsl_main() {
    global_1[7] = (global_8[0][0] * global_7[0][0][0])[0];
    global_1[6] = (global_6 * global_5)[0];
    global_1[5] = global_3[1][1];
    global_1[4] = global_4[0][3];
    global_1[3] = global_9.v1;
    global_1[2] = global_9.v3[0];
    global_9.v1 = 4.0f;
    global_1[1] = float(float(global_3));
    float Foo = 1.0f;
    bool at = true;
}

void atomicStore() {
}

void main() {
    wgsl_main();
}
