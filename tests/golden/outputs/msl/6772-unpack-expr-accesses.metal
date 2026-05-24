#include <metal_stdlib>
using namespace metal;

[[kernel]]
void main() {
    int local_0 = 2;
    unpack4xI8(12u)[local_0];
    unpack4xU8(12u)[1];
}

uint unpack4xI8(uint arg_0) {
}

uint unpack4xU8(uint arg_0) {
}
