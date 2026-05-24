#version 450 core
precision highp float;
precision highp int;

layout(set = 0, binding = 0) buffer int[2] global_0;

void wgsl_main() {
    int[] tmp;
    {
        int i = 0;
        while (true) {
            if ((i < 2)) {
                {
                    global_0[i] = tmp[i];
                }
                i = (i + 1);
            } else {
                break;
            }
        }
    }
}

void main() {
    wgsl_main();
}
