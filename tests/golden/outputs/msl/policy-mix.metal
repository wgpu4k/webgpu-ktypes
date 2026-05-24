#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    /* unknown type */ void a;
};
struct Struct_5 {
    /* unknown type */ void a;
};
/* unknown type */ void global_1;
/* unknown type */ void global_2;

float4 mock_function(int2 c, int i, int l) {
    /* unknown type */ void local_0 = /* unknown type */ void(float4(0.707f, 0.0f, 0.0f, 1.0f), float4(0.0f, 0.707f, 0.0f, 1.0f));
    return (((((global_3.a[i] + global_4.a[i]) + textureLoad(global_0, c, i, l)) + global_1[i]) + global_2[i]) + local_0[i]);
}

void textureLoad() {
}

[[kernel]]
void main(texture2d_array<float> global_0 [[texture(2)]], Struct_3 global_3 [[buffer(0)]], Struct_5 global_4 [[buffer(1)]]) {
}
