#include <metal_stdlib>
using namespace metal;

void break_from_loop() {
    {
        int local_0 = 0;
        while (true) {
            if ((local_0 < 4)) {
                {
                    break;
                }
                local_0 = 1;
            } else {
                break;
            }
        }
    }
}

[[kernel]]
void main() {
    break_from_loop();
}
