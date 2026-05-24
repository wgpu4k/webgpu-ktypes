#include <metal_stdlib>
using namespace metal;

int use_me() {
    return 10;
}

int use_return() {
    return use_me();
}

int use_assign_var() {
    int local_0 = use_me();
    return local_0;
}

int use_assign_let() {
    int local_0 = use_me();
    return local_0;
}

void use_phony_assign() {
    use_me();
}

[[kernel]]
void main() {
    use_return();
    use_assign_var();
    use_assign_let();
    use_phony_assign();
}
