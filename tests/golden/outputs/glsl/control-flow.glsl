#version 450 core
precision highp float;
precision highp int;

 int global_0 = 1;

void control_flow() {
    storageBarrier();
    workgroupBarrier();
    textureBarrier();
    int pos;
    switch (1) {
        default: {
            pos = 1;
            break;
        }
    }
    switch (pos) {
        case 1: {
            pos = 0;
            break;
            break;
        }
        case 2: {
            pos = 1;
            break;
        }
        case 3: {
            pos = 2;
            break;
        }
        case 4: {
            pos = 2;
            break;
        }
        case 5: {
            pos = 3;
            break;
        }
        case 6: {
            pos = 4;
            break;
        }
    }
    switch (0u) {
        case 0u: {
            break;
        }
        default: {
            break;
        }
    }
    switch (pos) {
        case 1: {
            pos = 0;
            break;
            break;
        }
        case 2: {
            pos = 1;
            break;
        }
        case 3: {
            pos = 2;
            break;
        }
        case 4: {
            break;
        }
        default: {
            pos = 3;
            break;
        }
    }
    switch (pos) {
        case 1: {
            pos = 0;
            break;
        }
        case 2: {
            pos = 1;
            break;
        }
        case 3: {
            pos = 2;
            break;
        }
        case 4: {
            pos = 2;
            break;
        }
        case 5: {
            pos = 3;
            break;
        }
        case 6: {
            pos = 3;
            break;
        }
        default: {
            pos = 4;
            break;
        }
    }
}

void storageBarrier() {
}

void workgroupBarrier() {
}

void textureBarrier() {
}

void switch_default_break(int i) {
    switch (i) {
        default: {
            break;
            break;
        }
    }
}

void switch_case_break() {
    switch (0) {
        case 0: {
            break;
            break;
        }
        default: {
            break;
        }
    }
    return;
}

void switch_selector_type_conversion() {
    switch (0u) {
        case 0: {
            break;
        }
        default: {
            break;
        }
    }
    switch (0) {
        case 0u: {
            break;
        }
        default: {
            break;
        }
    }
}

void loop_switch_continue(int x) {
    while (true) {
        switch (x) {
            case 1: {
                continue;
                break;
            }
            default: {
                break;
            }
        }
    }
}

void loop_switch_continue_nesting(int x, int y, int z) {
    while (true) {
        switch (x) {
            case 1: {
                continue;
                break;
            }
            case 2: {
                switch (y) {
                    case 1: {
                        continue;
                        break;
                    }
                    default: {
                        while (true) {
                            switch (z) {
                                case 1: {
                                    continue;
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
        switch (y) {
            default: {
                continue;
                break;
            }
        }
    }
    while (true) {
        switch (y) {
            case 1: {
                switch (z) {
                    default: {
                        continue;
                        break;
                    }
                }
                break;
            }
        }
    }
}

void loop_switch_omit_continue_variable_checks(int x, int y, int z, int w) {
    int pos = 0;
    while (true) {
        switch (x) {
            case 1: {
                pos = 1;
                break;
            }
            default: {
                break;
            }
        }
    }
    while (true) {
        switch (x) {
            case 1: {
                break;
            }
            case 2: {
                switch (y) {
                    case 1: {
                        continue;
                        break;
                    }
                    default: {
                        switch (z) {
                            case 1: {
                                pos = 2;
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
    }
}

void switch_const_expr_case_selectors() {
    int TWO = 2;
    switch (0) {
        case 0: {
            break;
        }
        case 1: {
            break;
        }
        case 2: {
            break;
        }
        case 3: {
            break;
        }
        case 4: {
            break;
        }
        default: {
            break;
        }
    }
}

void wgsl_main() {
    control_flow();
    switch_default_break(1);
    switch_case_break();
    switch_selector_type_conversion();
    switch_const_expr_case_selectors();
    loop_switch_continue(1);
    loop_switch_continue_nesting(1, 2, 3);
    loop_switch_omit_continue_variable_checks(1, 2, 3, 4);
}

void main() {
    wgsl_main();
}
