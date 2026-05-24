#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    float a;
    float3 b;
};

Struct_2 foobar(/* unknown type */ void normals, uint count) {
    {
        uint local_0 = 0u;
        while (true) {
            if ((local_0 < count)) {
                {
                    float3 local_1 = normals[local_0];
                }
                local_0 = (local_0 + 1u);
            } else {
                break;
            }
        }
    }
    {
        uint local_2 = 0u;
        while (true) {
            if ((local_2 < count)) {
                {
                    float3 local_3 = normals[local_2];
                }
                local_2 = (local_2 + 1u);
            } else {
                break;
            }
        }
    }
    return Struct_2(0.0f, float3(0.0f));
}

[[fragment]]
void main() {
}
