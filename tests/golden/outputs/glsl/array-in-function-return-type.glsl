#version 450 core
precision highp float;
precision highp int;


float[2] ret_array() {
    return float[2](1.0f, 2.0f);
}

float[2][3] ret_array_array() {
    return float[2][3](ret_array(), ret_array(), ret_array());
}

vec4 wgsl_main() {
    float[2][3] a = ret_array_array();
    return vec4(a[0][0], a[0][1], 0.0f, 1.0f);
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_main();
}
