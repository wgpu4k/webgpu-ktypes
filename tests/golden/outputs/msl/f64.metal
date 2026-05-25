#include <metal_stdlib>
using namespace metal;
double global_0 = 1.0;
double global_1 = 2.0;

double f(double x) {
    global_0;
    double local_0 = (30.0 + 400.0);
    double local_1 = (local_0 + double(5));
    double local_2 = -(1.0);
    return (((x + local_0) + global_1) + 5.0);
}

[[kernel]]
void main() {
    f(6.0);
}
