#include <metal_stdlib>
using namespace metal;
struct Struct_3 {
    array<float4, 10> a;
};
struct Struct_5 {
    array<float4, 20> a;
};
array<float, 30> global_1;
array<float, 40> global_2;

float4 mock_function(int2 c, int i, int l) {
    array<float4, 2> local_0 = array<float4, 2>(float4(0.707f, 0.0f, 0.0f, 1.0f), float4(0.0f, 0.707f, 0.0f, 1.0f));
    return (((((global_3.a[i] + global_4.a[i]) + textureLoad(global_0, c, i, l)) + global_1[i]) + global_2[i]) + local_0[i]);
}

texture2d_array<float> textureLoad(texture2d_array<float> arg_0, int2 arg_1, int arg_2, int arg_3) {
}

[[kernel]]
void main(texture2d_array<float> global_0 [[texture(2)]], Struct_3 global_3 [[buffer(0)]], Struct_5 global_4 [[buffer(1)]]) {
    mock_function(float2(1, 2), 3, 4);
}
