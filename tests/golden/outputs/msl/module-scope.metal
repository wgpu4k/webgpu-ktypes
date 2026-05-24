#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    int x;
};
int global_0 = 1;

void statement() {
}

Struct_1 returns() {
    return Struct_1(global_0);
}

void call() {
    statement();
    Struct_1 local_0 = returns();
    float local_1 = float(global_0);
    texture2d<float> local_2 = textureSample(global_1, global_2, float2(local_1));
}

texture2d<float> textureSample(texture2d<float> arg_0, sampler arg_1, float2 arg_2) {
}

[[fragment]]
void main(texture2d<float> global_1 [[texture(0)]], sampler global_2 [[sampler(1)]]) {
    call();
    statement();
    returns();
}
