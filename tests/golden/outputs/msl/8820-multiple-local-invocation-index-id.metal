#include <metal_stdlib>
using namespace metal;
struct Struct_2 {
    uint3 local_invocation_id;
    uint local_invocation_index;
};
uint global_0;

[[kernel]]
void compute1() {
    global_0 = (input.local_invocation_index * 2);
    global_0 = input.local_invocation_id[0];
}
