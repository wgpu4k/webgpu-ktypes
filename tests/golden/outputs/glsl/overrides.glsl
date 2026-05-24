#version 450 core
precision highp float;
precision highp int;

 bool global_0 = true;
 float global_1 = 2.3f;
 float global_2;
 float global_3 = 0.0f;
 float global_4;
 float global_5 = 2.718f;
 uint global_6 = 0;
 float global_7;
 float global_8 = (global_2 * 10.0f);
 float global_9 = (2 * global_4);

void wgsl_main() {
    float t = (global_9 * 5);
    bool a = !(global_0);
    bool x = a;
    float gain_x_100 = (global_8 * 10.0f);
    global_7 = global_2;
    global_1;
    global_3;
    global_5;
    global_6;
}

void main() {
    wgsl_main();
}
