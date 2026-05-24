#version 450 core
precision highp float;
precision highp int;


void blockLexicalScope(bool a) {
    {
        int a = 2;
        {
            float a = 2.0f;
        }
        int test = a;
    }
    bool test = a;
}

void ifLexicalScope(bool a) {
    if (a) {
        float a = 2.0f;
    }
    bool test = a;
}

void loopLexicalScope(bool a) {
    while (true) {
        float a = 2.0f;
    }
    bool test = a;
}

void forLexicalScope(float a) {
    {
        int a = 0;
        while (true) {
            if ((a < 1)) {
                {
                    bool a = true;
                }
                a = (a + 1);
            } else {
                break;
            }
        }
    }
    float test = a;
}

void whileLexicalScope(int a) {
    while (true) {
        if ((a > 2)) {
            bool a = false;
        } else {
            break;
        }
    }
    int test = a;
}

void switchLexicalScope(int a) {
    switch (a) {
        case 0: {
            bool a = false;
            break;
        }
        case 1: {
            float a = 2.0f;
            break;
        }
        default: {
            bool a = true;
            break;
        }
    }
    bool test = (a == 2);
}

void wgsl_main() {
}

void main() {
    wgsl_main();
}
