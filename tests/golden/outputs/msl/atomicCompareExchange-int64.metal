#include <metal_stdlib>
using namespace metal;
struct Struct_7 {
    long old_value;
    char _pad0[4];
    bool exchanged;
};
struct Struct_9 {
    ulong old_value;
    char _pad0[4];
    bool exchanged;
};
uint global_0 = 128u;

[[kernel]]
void test_atomic_compare_exchange_i64(array<long> global_1 [[buffer(0)]], array<ulong> global_2 [[buffer(1)]]) {
    {
        uint local_0 = 0u;
        while (true) {
            if ((local_0 < global_0)) {
                {
                    long local_1 = atomicLoad(&global_1[local_0]);
                    bool local_2 = false;
                    while (true) {
                        if (!(local_2)) {
                            long local_3 = as_type<long>((local_1 + 10));
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

Struct_7 atomicCompareExchangeWeak(/* unknown type */ void arg_0, long arg_1, long arg_2) {
}

[[kernel]]
void test_atomic_compare_exchange_u64(array<long> global_1 [[buffer(0)]], array<ulong> global_2 [[buffer(1)]]) {
    {
        uint local_0 = 0u;
        while (true) {
            if ((local_0 < global_0)) {
                {
                    ulong local_1 = atomicLoad(&global_2[local_0]);
                    bool local_2 = false;
                    while (true) {
                        if (!(local_2)) {
                            ulong local_3 = as_type<ulong>((local_1 + 10u));
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

Struct_9 atomicCompareExchangeWeak(/* unknown type */ void arg_0, ulong arg_1, ulong arg_2) {
}
