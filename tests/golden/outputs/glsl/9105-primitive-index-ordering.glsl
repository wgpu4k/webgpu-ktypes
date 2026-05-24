#version 450 core
precision highp float;
precision highp int;


vec4 wgsl_func(float input_location, vec4 arbitrary_position, uint index) {
    return vec4(arbitrary_position.xy, input_location, float(index));
}

layout(location = 0) in float input_location;
layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_func(input_location, arbitrary_position, index);
}
