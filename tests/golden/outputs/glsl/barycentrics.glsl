#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_fs_main(vec3 bary) {
    return vec4(bary, 1.0f);
}

vec4 wgsl_fs_main_no_perspective(vec3 bary) {
    return vec4(bary, 1.0f);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(bary);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main_no_perspective(bary);
}
