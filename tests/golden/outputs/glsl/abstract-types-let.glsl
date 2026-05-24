#version 450 core
precision highp float;
precision highp int;


void all_constant_arguments() {
    ivec2 xvipaiai = vec2(42, 43);
    uvec2 xvupaiai = vec2(44, 45);
    vec2 xvfpaiai = vec2(46, 47);
    vec2 xvfpafaf = vec2(48.0f, 49.0f);
    vec2 xvfpaiaf = vec2(48, 49.0f);
    uvec2 xvupuai = vec2(0u, 43);
    uvec2 xvupaiu = vec2(42, 0u);
    uvec2 xvuuai = uvec2(0u, 43);
    uvec2 xvuaiu = uvec2(42, 0u);
    ivec2 xvip____ = vec2();
    uvec2 xvup____ = vec2();
    vec2 xvfp____ = vec2();
    mat2x2 xmfp____ = mat2x2(vec2(), vec2());
    mat2x2 xmfpaiaiaiai = mat2x2(1, 2, 3, 4);
    mat2x2 xmfpafaiaiai = mat2x2(1.0f, 2, 3, 4);
    mat2x2 xmfpaiafaiai = mat2x2(1, 2.0f, 3, 4);
    mat2x2 xmfpaiaiafai = mat2x2(1, 2, 3.0f, 4);
    mat2x2 xmfpaiaiaiaf = mat2x2(1, 2, 3, 4.0f);
    mat2x2 xmfp_faiaiai = mat2x2(1.0f, 2, 3, 4);
    mat2x2 xmfpai_faiai = mat2x2(1, 2.0f, 3, 4);
    mat2x2 xmfpaiai_fai = mat2x2(1, 2, 3.0f, 4);
    mat2x2 xmfpaiaiai_f = mat2x2(1, 2, 3, 4.0f);
    ivec2 xvispai = vec2(1);
    vec2 xvfspaf = vec2(1.0f);
    ivec2 xvis_ai = ivec2(1);
    uvec2 xvus_ai = uvec2(1);
    vec2 xvfs_ai = vec2(1);
    vec2 xvfs_af = vec2(1.0f);
    float[2] xafafaf = float[2](1.0f, 2.0f);
    float[2] xaf_faf = float[2](1.0f, 2.0f);
    float[2] xafaf_f = float[2](1.0f, 2.0f);
    float[2] xafaiai = float[2](1, 2);
    int[2] xai_iai = int[2](0, 2);
    int[2] xaiai_i = int[2](1, 0);
    int[2] xaipaiai = int[2](1, 2);
    float[2] xafpaiai = int[2](1, 2);
    float[2] xafpaiaf = int[2](1, 2.0f);
    float[2] xafpafai = float[2](1.0f, 2);
    float[2] xafpafaf = float[2](1.0f, 2.0f);
    ivec3[1] xavipai = vec3[1](vec3(1));
    vec3[1] xavfpai = vec3[1](vec3(1));
    vec3[1] xavfpaf = vec3[1](vec3(1.0f));
    ivec2 xvisai = vec2(1);
    uvec2 xvusai = vec2(1);
    vec2 xvfsai = vec2(1);
    vec2 xvfsaf = vec2(1.0f);
    int[2] iaipaiai = int[2](1, 2);
    int[2] iafpaiaf = int[2](1, 2.0f);
    float[2] iafpafai = float[2](1.0f, 2);
    float[2] iafpafaf = float[2](1.0f, 2.0f);
}

void mixed_constant_and_runtime_arguments() {
    uint u;
    int i;
    float f;
    uvec2 xvupuai = vec2(u, 43);
    uvec2 xvupaiu = vec2(42, u);
    vec2 xvfpfai = vec2(f, 47);
    vec2 xvfpfaf = vec2(f, 49.0f);
    uvec2 xvuuai = uvec2(u, 43);
    uvec2 xvuaiu = uvec2(42, u);
    mat2x2 xmfp_faiaiai = mat2x2(f, 2, 3, 4);
    mat2x2 xmfpai_faiai = mat2x2(1, f, 3, 4);
    mat2x2 xmfpaiai_fai = mat2x2(1, 2, f, 4);
    mat2x2 xmfpaiaiai_f = mat2x2(1, 2, 3, f);
    float[2] xaf_faf = float[2](f, 2.0f);
    float[2] xafaf_f = float[2](1.0f, f);
    float[2] xaf_fai = float[2](f, 2);
    float[2] xafai_f = float[2](1, f);
    int[2] xai_iai = int[2](i, 2);
    int[2] xaiai_i = int[2](1, i);
    float[2] xafp_faf = float[2](f, 2.0f);
    float[2] xafpaf_f = float[2](1.0f, f);
    float[2] xafp_fai = float[2](f, 2);
    float[2] xafpai_f = int[2](1, f);
    int[2] xaip_iai = int[2](i, 2);
    int[2] xaipai_i = int[2](1, i);
    ivec2 xvisi = vec2(i);
    uvec2 xvusu = vec2(u);
    vec2 xvfsf = vec2(f);
}

void wgsl_main() {
}

void main() {
    wgsl_main();
}
