#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    vec3 position;
    vec3 color;
};
struct Struct_4 {
    vec4 clip_position;
    vec3 color;
};

Struct_4 wgsl_vs_main(Struct_2 model) {
    Struct_4 out;
    out.color = model.color;
    out.clip_position = vec4(model.position, 1.0f);
    return out;
}

vec4 wgsl_fs_main(Struct_4 in) {
    vec3 color = in.color;
    {
        int i = 0;
        while (true) {
            if ((i < 10)) {
                {
                    float ii = float(i);
                    color[0] = (ii * 0.001f);
                    color[1] = (ii * 0.002f);
                }
                i = 1;
            } else {
                break;
            }
        }
    }
    return vec4(color, 1.0f);
}

layout(location = 0) out vec3 out_color;
void main() {
    Struct_4 res = wgsl_vs_main(model);
    gl_Position = res.clip_position;
    out_color = res.color;
}

layout(location = 0) out vec4 outColor;
void main() {
    outColor = wgsl_fs_main(in);
}
