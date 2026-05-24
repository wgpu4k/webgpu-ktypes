#include <metal_stdlib>
using namespace metal;

void thing() {
}

void with_diagnostic() {
}

[[kernel]]
void main() {
    thing();
    with_diagnostic();
}
