#version 450 core
precision highp float;
precision highp int;

struct Struct_8 {
    float16_t scalar_f16;
    float scalar_f32;
    vec2 vec2_f16;
    vec2 vec2_f32;
    vec3 vec3_f16;
    vec3 vec3_f32;
    vec4 vec4_f16;
    vec4 vec4_f32;
};

Struct_8 wgsl_test_direct(float16_t scalar_f16, float scalar_f32, vec2 vec2_f16, vec2 vec2_f32, vec3 vec3_f16, vec3 vec3_f32, vec4 vec4_f16, vec4 vec4_f32) {
    Struct_8 output;
    output.scalar_f16 = (scalar_f16 + 1.0h);
    output.scalar_f32 = (scalar_f32 + 1.0f);
    output.vec2_f16 = (vec2_f16 + vec2(1.0h));
    output.vec2_f32 = (vec2_f32 + vec2(1.0f));
    output.vec3_f16 = (vec3_f16 + vec3(1.0h));
    output.vec3_f32 = (vec3_f32 + vec3(1.0f));
    output.vec4_f16 = (vec4_f16 + vec4(1.0h));
    output.vec4_f32 = (vec4_f32 + vec4(1.0f));
    return output;
}

Struct_8 wgsl_test_struct(Struct_8 input) {
    Struct_8 output;
    output.scalar_f16 = (input.scalar_f16 + 1.0h);
    output.scalar_f32 = (input.scalar_f32 + 1.0f);
    output.vec2_f16 = (input.vec2_f16 + vec2(1.0h));
    output.vec2_f32 = (input.vec2_f32 + vec2(1.0f));
    output.vec3_f16 = (input.vec3_f16 + vec3(1.0h));
    output.vec3_f32 = (input.vec3_f32 + vec3(1.0f));
    output.vec4_f16 = (input.vec4_f16 + vec4(1.0h));
    output.vec4_f32 = (input.vec4_f32 + vec4(1.0f));
    return output;
}

Struct_8 wgsl_test_copy_input(Struct_8 input_original) {
    Struct_8 input = input_original;
    Struct_8 output;
    output.scalar_f16 = (input.scalar_f16 + 1.0h);
    output.scalar_f32 = (input.scalar_f32 + 1.0f);
    output.vec2_f16 = (input.vec2_f16 + vec2(1.0h));
    output.vec2_f32 = (input.vec2_f32 + vec2(1.0f));
    output.vec3_f16 = (input.vec3_f16 + vec3(1.0h));
    output.vec3_f32 = (input.vec3_f32 + vec3(1.0f));
    output.vec4_f16 = (input.vec4_f16 + vec4(1.0h));
    output.vec4_f32 = (input.vec4_f32 + vec4(1.0f));
    return output;
}

float16_t wgsl_test_return_partial(Struct_8 input_original) {
    Struct_8 input = input_original;
    input.scalar_f16 = 0.0h;
    return input.scalar_f16;
}

Struct_8 wgsl_test_component_access(Struct_8 input) {
    Struct_8 output;
    output.vec2_f16[0] = input.vec2_f16[1];
    output.vec2_f16[1] = input.vec2_f16[0];
    return output;
}

layout(location = 0) in float16_t scalar_f16;
layout(location = 1) in float scalar_f32;
layout(location = 2) in vec2 vec2_f16;
layout(location = 3) in vec2 vec2_f32;
layout(location = 4) in vec3 vec3_f16;
layout(location = 5) in vec3 vec3_f32;
layout(location = 6) in vec4 vec4_f16;
layout(location = 7) in vec4 vec4_f32;
layout(location = 0) out Struct_8 outColor;
void main() {
    outColor = wgsl_test_direct(scalar_f16, scalar_f32, vec2_f16, vec2_f32, vec3_f16, vec3_f32, vec4_f16, vec4_f32);
}

layout(location = 0) out Struct_8 outColor;
void main() {
    outColor = wgsl_test_struct(input);
}

layout(location = 0) out Struct_8 outColor;
void main() {
    outColor = wgsl_test_copy_input(input_original);
}

layout(location = 0) out float16_t outColor;
void main() {
    outColor = wgsl_test_return_partial(input_original);
}

layout(location = 0) out Struct_8 outColor;
void main() {
    outColor = wgsl_test_component_access(input);
}
