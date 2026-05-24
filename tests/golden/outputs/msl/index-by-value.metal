#include <metal_stdlib>
using namespace metal;

int index_arg_array(array<int, 5> a, int i) {
    return a[i];
}

int index_let_array(int i, int j) {
    array<array<int, 2>, 2> local_0 = array<array<int, 2>, 2>(array<int, 2>(1, 2), array<int, 2>(3, 4));
    return local_0[i][j];
}

float index_let_matrix(int i, int j) {
    float2x2 local_0 = float2x2(1, 2, 3, 4);
    return local_0[i][j];
}

float4 index_let_array_1d(uint vi) {
    array<int, 5> local_0 = array<int, 5>(1, 2, 3, 4, 5);
    int local_1 = local_0[vi];
    return float4(int4(local_1));
}

struct main_Output {
    float4 position [[position]];
};
[[vertex]]
main_Output main() {
    index_arg_array(array<int, 5>(1, 2, 3, 4, 5), 6);
    index_let_array(1, 2);
    index_let_matrix(1, 2);
    return index_let_array_1d(vi);
}
