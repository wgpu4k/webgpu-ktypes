#include <metal_stdlib>
using namespace metal;
int global_0;
uint global_1;

[[kernel]]
void f() {
    atomicCompareExchangeWeak(&global_1, uint(global_0), 1u);
}

/* unknown type */ void atomicCompareExchangeWeak(/* unknown type */ void arg_0, uint arg_1, uint arg_2) {
}
