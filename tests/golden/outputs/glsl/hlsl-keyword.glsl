#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_fs_main() {
    vec4 Pass = vec4(1.0f, 1.0f, 1.0f, 1.0f);
    return Pass;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main();
}
