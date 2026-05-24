#version 450 core
precision highp float;
precision highp int;


void break_from_loop() {
    {
        int i = 0;
        while (true) {
            if ((i < 4)) {
                {
                    break;
                }
                i = 1;
            } else {
                break;
            }
        }
    }
}

void wgsl_main() {
    break_from_loop();
}

void main() {
    wgsl_main();
}
