#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_fs_main() {
    vec2[2] my_array = vec2[2](vec2(0.0f, 0.0f), vec2(0.0f, 0.0f));
    int index_0 = 0;
    vec2 val_0 = my_array[index_0];
    vec2 val_1 = my_array[index_0];
    return (val_0 * val_1).xxyy;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main();
}
