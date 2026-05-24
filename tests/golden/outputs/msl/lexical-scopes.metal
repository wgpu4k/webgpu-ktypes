#include <metal_stdlib>
using namespace metal;

void blockLexicalScope(bool a) {
    {
        int local_0 = 2;
        {
            float local_1 = 2.0f;
        }
        int local_2 = local_1;
    }
    bool local_3 = local_1;
}

void ifLexicalScope(bool a) {
    if (a) {
        float local_0 = 2.0f;
    }
    bool local_1 = local_0;
}

void loopLexicalScope(bool a) {
    while (true) {
        float local_0 = 2.0f;
    }
    bool local_1 = local_0;
}

void forLexicalScope(float a) {
    {
        int local_0 = 0;
        while (true) {
            if ((local_1 < 1)) {
                {
                    bool local_1 = true;
                }
                local_1 = (local_1 + 1);
            } else {
                break;
            }
        }
    }
    float local_2 = local_1;
}

void whileLexicalScope(int a) {
    while (true) {
        if ((a > 2)) {
            bool local_0 = false;
        } else {
            break;
        }
    }
    int local_1 = local_0;
}

void switchLexicalScope(int a) {
    switch (a) {
        case 0: {
            bool local_0 = false;
            break;
        }
        case 1: {
            float local_1 = 2.0f;
            break;
        }
        default: {
            bool local_2 = true;
            break;
        }
    }
    bool local_3 = (local_2 == 2);
}

[[kernel]]
void main() {
    blockLexicalScope(false);
    ifLexicalScope(true);
    loopLexicalScope(false);
    forLexicalScope(1.0f);
    whileLexicalScope(1);
    switchLexicalScope(1);
}
