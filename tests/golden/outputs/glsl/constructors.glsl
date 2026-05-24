#version 450 core
precision highp float;
precision highp int;

struct Struct_3 {
    vec4 a;
    int b;
};
 vec3 global_0 = vec3(0.0f);
 vec3 global_1 = vec3(0.0f, 1.0f, 2.0f);
 mat2x2 global_2 = mat2x2(0.0f, 1.0f, 2.0f, 3.0f);
 mat2x2[1] global_3 = mat2x2[1](mat2x2(0.0f, 1.0f, 2.0f, 3.0f));
 bool global_4 = false;
 int global_5 = 0;
 uint global_6 = 0u;
 float global_7 = 0.0f;
 uvec2 global_8 = uvec2(0u);
 mat2x2 global_9 = mat2x2(vec2(0.0f), vec2(0.0f));
 vec2 global_10 = vec2(0u);
 mat2x2 global_11 = mat2x2(vec2(0.0f), vec2(0.0f));
 int[4] global_12 = int[4](0, 1, 2, 3);
 Struct_3[3] global_13 = Struct_3[3]();
 Struct_3 global_14 = Struct_3();

void wgsl_main() {
    Struct_3 foo;
    foo = Struct_3(vec4(1.0f), 1);
    mat2x2 m0 = mat2x2(1.0f, 0.0f, 0.0f, 1.0f);
    mat4x4 m1 = mat4x4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    bool zvc0 = false;
    int zvc1 = 0;
    uint zvc2 = 0u;
    float zvc3 = 0.0f;
    uvec2 zvc4 = uvec2(0u);
    mat2x2 zvc5 = mat2x2(vec2(0.0f), vec2(0.0f));
    Struct_3[3] zvc6 = Struct_3[3]();
    Struct_3 zvc7 = Struct_3();
    uvec2 zvc8 = vec2(0.0f);
    vec2 zvc9 = vec2(0.0f);
    vec2 cit0 = vec2(0u);
    mat2x2 cit1 = mat2x2(vec2(0.0f), vec2(0.0f));
    int[4] cit2 = int[4](0, 1, 2, 3);
    bool ic0 = bool(false);
    int ic1 = int(0);
    uint ic2 = uint(0u);
    float ic3 = float(0.0f);
    uvec2 ic4 = uvec2(uvec2(0u));
    mat2x3 ic5 = mat2x3(mat2x3(vec3(0.0f), vec3(0.0f)));
    vec2 ic6 = vec2(uvec2(0u));
    mat2x3 ic7 = mat2x3(mat2x3(vec3(0.0f), vec3(0.0f)));
    int cc00 = int(1u);
    int cc01 = int(1.0f);
    int cc02 = int(1);
    int cc03 = int(1.0f);
    int cc04 = int(true);
    uint cc05 = uint(1);
    uint cc06 = uint(1.0f);
    uint cc07 = uint(1);
    uint cc08 = uint(1.0f);
    uint cc09 = uint(true);
    float cc10 = float(1);
    float cc11 = float(1u);
    float cc12 = float(1);
    float cc13 = float(1.0f);
    float cc14 = float(true);
    bool cc15 = bool(1);
    bool cc16 = bool(1u);
    bool cc17 = bool(1.0f);
    bool cc18 = bool(1);
    bool cc19 = bool(1.0f);
}

void main() {
    wgsl_main();
}
