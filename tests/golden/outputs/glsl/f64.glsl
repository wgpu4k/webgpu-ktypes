#version 450 core
precision highp float;
precision highp int;

 double global_0 = 1.0;
 double global_1 = 2.0;

double f(double x) {
    global_0;
    double y = (30.0 + 400.0);
    double z = (y + double(5));
    double w = -(1.0);
    return (((x + y) + global_1) + 5.0);
}

void wgsl_main() {
    f(6.0);
}

void main() {
    wgsl_main();
}
