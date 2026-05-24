#version 450 core
precision highp float;
precision highp int;

struct Struct_4 {
    uint old_value;
    bool exchanged;
};
 int global_0;
shared uint global_1;

void wgsl_f() {
    atomicCompareExchangeWeak(global_1, uint(global_0), 1u);
}

Struct_4 atomicCompareExchangeWeak(uint arg_0, uint arg_1, uint arg_2) {
}

void main() {
    wgsl_f();
}
