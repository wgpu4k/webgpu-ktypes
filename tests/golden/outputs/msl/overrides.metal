#include <metal_stdlib>
using namespace metal;
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

[[kernel]]
void main() {
    float local_0 = (global_9 * 5);
    bool local_1 = !(global_0);
    bool local_2 = local_1;
    float local_3 = (global_8 * 10.0f);
    global_7 = global_2;
    global_1;
    global_3;
    global_5;
    global_6;
}
