#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint dummy;
};
struct Struct_4 {
    float4 position;
};
struct Struct_6 {
    uint2 indices;
};
struct Struct_9 {
    array<Struct_4, 2> vertices;
    array<Struct_6, 1> primitives;
    uint vertex_count;
    uint primitive_count;
};
Struct_1 global_0;
Struct_9 global_1;

uint3 ts_main() {
    return float3(1, 1, 1);
}

void ms_main() {
}
