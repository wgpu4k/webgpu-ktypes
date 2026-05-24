#version 450 core
precision highp float;
precision highp int;

struct Struct_7 {
    int old_value;
    bool exchanged;
};
struct Struct_9 {
    uint old_value;
    bool exchanged;
};
 uint global_0 = 128u;
layout(set = 0, binding = 0) buffer int[] global_1;
layout(set = 0, binding = 1) buffer uint[] global_2;

void wgsl_test_atomic_compare_exchange_i32() {
    {
        uint i = 0u;
        while (true) {
            if ((i < global_0)) {
                {
                    int old = atomicLoad(global_1[i]);
                    bool exchanged = false;
                    while (true) {
                        if (!(exchanged)) {
                            int new_ = floatBitsToInt((/* unsupported bitcast */ old + 1.0f));
                            Struct_7 result = atomicCompareExchangeWeak(global_1[i], old, new_);
                            old = result.old_value;
                            exchanged = result.exchanged;
                        } else {
                            break;
                        }
                    }
                }
                i = (i + 1u);
            } else {
                break;
            }
        }
    }
}

int atomicLoad(int arg_0) {
}

Struct_7 atomicCompareExchangeWeak(int arg_0, int arg_1, int arg_2) {
}

void wgsl_test_atomic_compare_exchange_u32() {
    {
        uint i = 0u;
        while (true) {
            if ((i < global_0)) {
                {
                    uint old = atomicLoad(global_2[i]);
                    bool exchanged = false;
                    while (true) {
                        if (!(exchanged)) {
                            uint new_ = floatBitsToUint((/* unsupported bitcast */ old + 1.0f));
                            Struct_9 result = atomicCompareExchangeWeak(global_2[i], old, new_);
                            old = result.old_value;
                            exchanged = result.exchanged;
                        } else {
                            break;
                        }
                    }
                }
                i = (i + 1u);
            } else {
                break;
            }
        }
    }
}

uint atomicLoad(uint arg_0) {
}

Struct_9 atomicCompareExchangeWeak(uint arg_0, uint arg_1, uint arg_2) {
}

void main() {
    wgsl_test_atomic_compare_exchange_i32();
}

void main() {
    wgsl_test_atomic_compare_exchange_u32();
}
