#version 450 core
precision highp float;
precision highp int;


void breakIfEmpty() {
    while (true) {
        if (true) {
            break;
        }
    }
}

void breakIfEmptyBody(bool a) {
    while (true) {
        bool b = a;
        bool c = (a != b);
        if ((a == c)) {
            break;
        }
    }
}

void breakIf(bool a) {
    while (true) {
        bool d = a;
        bool e = (a != d);
        if ((a == e)) {
            break;
        }
    }
}

void breakIfSeparateVariable() {
    uint counter = 0u;
    while (true) {
        counter = 1u;
        if ((counter == 5u)) {
            break;
        }
    }
}

void wgsl_main() {
    breakIfEmpty();
    breakIfEmptyBody(false);
    breakIf(false);
    breakIfSeparateVariable();
}

void main() {
    wgsl_main();
}
