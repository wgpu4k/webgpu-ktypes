#include <metal_stdlib>
using namespace metal;

void breakIfEmpty() {
    while (true) {
        if (true) {
            break;
        }
    }
}

void breakIfEmptyBody(bool a) {
    while (true) {
        bool local_0 = a;
        bool local_1 = (a != local_0);
        if ((a == local_1)) {
            break;
        }
    }
}

void breakIf(bool a) {
    while (true) {
        bool local_0 = a;
        bool local_1 = (a != local_0);
        if ((a == local_1)) {
            break;
        }
    }
}

void breakIfSeparateVariable() {
    uint local_0 = 0u;
    while (true) {
        local_0 = 1u;
        if ((local_0 == 5u)) {
            break;
        }
    }
}

[[kernel]]
void main() {
    breakIfEmpty();
    breakIfEmptyBody(false);
    breakIf(false);
    breakIfSeparateVariable();
}
