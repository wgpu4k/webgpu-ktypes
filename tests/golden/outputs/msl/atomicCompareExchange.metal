#include <metal_stdlib>
using namespace metal;
struct Struct_7 {
    int old_value;
    char _pad0[4];
    bool exchanged;
};
struct Struct_9 {
    uint old_value;
    char _pad0[4];
    bool exchanged;
};
uint global_0 = 128u;

[[kernel]]
void test_atomic_compare_exchange_i32(array<int> global_1 [[buffer(0)]], array<uint> global_2 [[buffer(1)]]) {
    {
        uint local_0 = 0u;
        while (true) {
            if ((local_0 < global_0)) {
                {
                    /* unknown type */ void local_1 = atomicLoad(&global_1[local_0]);
                    bool local_2 = false;
                    while (true) {
                        if (!(local_2)) {
                            int local_3 = as_type<int>((as_type<float>(local_1) + 1.0f));
                            Struct_7 local_4 = atomicCompareExchangeWeak(&global_1[local_0], local_1, local_3);
                            local_1 = local_4.old_value;
                            local_2 = local_4.exchanged;
                        } else {
                            break;
                        }
                    }
                }
                local_0 = (local_0 + 1u);
            } else {
                break;
            }
        }
    }
}

/* unknown type */ void atomicLoad(/* unknown type */ void arg_0) {
}

Struct_7 atomicCompareExchangeWeak(/* unknown type */ void arg_0, /* unknown type */ void arg_1, int arg_2) {
}

[[kernel]]
void test_atomic_compare_exchange_u32(array<int> global_1 [[buffer(0)]], array<uint> global_2 [[buffer(1)]]) {
    {
        uint local_0 = 0u;
        while (true) {
            if ((local_0 < global_0)) {
                {
                    /* unknown type */ void local_1 = atomicLoad(&global_2[local_0]);
                    bool local_2 = false;
                    while (true) {
                        if (!(local_2)) {
                            uint local_3 = as_type<uint>((as_type<float>(local_1) + 1.0f));
                            Struct_9 local_4 = atomicCompareExchangeWeak(&global_2[local_0], local_1, local_3);
                            local_1 = local_4.old_value;
                            local_2 = local_4.exchanged;
                        } else {
                            break;
                        }
                    }
                }
                local_0 = (local_0 + 1u);
            } else {
                break;
            }
        }
    }
}

/* unknown type */ void atomicLoad(/* unknown type */ void arg_0) {
}

Struct_9 atomicCompareExchangeWeak(/* unknown type */ void arg_0, /* unknown type */ void arg_1, uint arg_2) {
}
