#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    float value;
    float value2;
    vec4 position;
};

vec4 wgsl_fs_main(Struct_2 v_out) {
    return vec4(v_out.value, v_out.value, v_out.value2, v_out.value2);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(v_out);
}
