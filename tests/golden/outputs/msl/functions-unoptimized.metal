#include <metal_stdlib>
using namespace metal;

uint test_packed_integer_dot_product() {
    uint local_0 = 1u;
    uint local_1 = 2u;
    int local_2 = dot4I8Packed(local_0, local_1);
    uint local_3 = 3u;
    uint local_4 = 4u;
    uint local_5 = dot4U8Packed(local_3, local_4);
    int local_6 = dot4I8Packed((5u + local_5), (6u + local_5));
    uint local_7 = dot4U8Packed((7u + local_5), (8u + local_5));
    return local_7;
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

uint dot4U8Packed(uint arg_0, uint arg_1) {
}

int dot4I8Packed(uint arg_0, uint arg_1) {
}

uint dot4U8Packed(uint arg_0, uint arg_1) {
}

[[kernel]]
void main() {
    uint local_0 = test_packed_integer_dot_product();
}
