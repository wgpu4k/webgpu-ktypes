#version 450 core
precision highp float;
precision highp int;


float wgsl_main(vec4 pos) {
    return (pos[2] - 0.1f);
}

layout(location = 0) out float outColor;
void main() {
    outColor = wgsl_main(pos);
}
