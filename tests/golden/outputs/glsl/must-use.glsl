#version 450 core
precision highp float;
precision highp int;


int use_me() {
    return 10;
}

int use_return() {
    return use_me();
}

int use_assign_var() {
    int q = use_me();
    return q;
}

int use_assign_let() {
    int q = use_me();
    return q;
}

void use_phony_assign() {
}

void wgsl_main() {
}

void main() {
    wgsl_main();
}
