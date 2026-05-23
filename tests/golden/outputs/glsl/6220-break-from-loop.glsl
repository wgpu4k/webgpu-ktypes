#version 450 core
precision highp float;
precision highp int;


void break_from_loop() {
    {
        float i = 0;
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

void main() {
}

void main() {
    main();
}
