#version 450 core
precision highp float;
precision highp int;

struct Struct_2 {
    float a;
    vec3 b;
};

Struct_2 foobar(vec3[12] normals, uint count) {
    {
        uint i = 0u;
        while (true) {
            if ((i < count)) {
                {
                    vec3 n0 = normals[i];
                }
                i = (i + 1u);
            } else {
                break;
            }
        }
    }
    {
        uint j = 0u;
        while (true) {
            if ((j < count)) {
                {
                    vec3 n1 = normals[j];
                }
                j = (j + 1u);
            } else {
                break;
            }
        }
    }
    return Struct_2(0.0f, vec3(0.0f));
}

void wgsl_main() {
    vec3[12] arr;
}

void main() {
    wgsl_main();
}
