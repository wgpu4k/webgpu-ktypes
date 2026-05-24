#include <metal_stdlib>
using namespace metal;
struct Struct_4 {
    uint old_value;
    char _pad0[4];
    bool exchanged;
};
int global_0;
uint global_1;

[[kernel]]
void f() {
    atomicCompareExchangeWeak(&global_1, uint(global_0), 1u);
}

Struct_4 atomicCompareExchangeWeak(/* unknown type */ void arg_0, uint arg_1, uint arg_2) {
}
