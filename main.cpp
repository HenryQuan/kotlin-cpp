#include <stdio.h>
#include <mathop_api.h>

int main(int argc, char* argv[]) {
    printf("Hello, World!\n");
    int result = math_op(1, 2);
    printf("Result from Kotlin is %d\n", result);
    return 0;
}
