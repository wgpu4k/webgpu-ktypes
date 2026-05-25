#include <metal_stdlib>
using namespace metal;
struct Struct_1 {
    uint dummy;
};
struct Struct_4 {
    float4 position;
};
struct Struct_5 {
    uint indices;
};
struct Struct_8 {
    array<Struct_4, 1> vertices;
    array<Struct_5, 1> primitives;
    uint vertex_count;
    uint primitive_count;
};
Struct_1 global_0;
Struct_8 global_1;

uint3 ts_main() {
    return float3(1, 1, 1);
}

void ms_main() {
}
