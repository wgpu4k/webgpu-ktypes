#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec4 position;
    float value;
    vec4 unused_value2;
    float unused_value;
    float value2;
};

Struct_2 wgsl_vs_main() {
    return Struct_2(vec4(1.0f), 1.0f, vec4(2.0f), 1.0f, 0.5f);
}

layout(location = 1) out float out_value;
layout(location = 2) out vec4 out_unused_value2;
layout(location = 0) out float out_unused_value;
layout(location = 3) out float out_value2;
void main() {
    Struct_2 res = wgsl_vs_main();
    gl_Position = res.position;
    out_value = res.value;
    out_unused_value2 = res.unused_value2;
    out_unused_value = res.unused_value;
    out_value2 = res.value2;
}
