#include <stdio.h>
#include <mathop_api.h>
#include <fstream>

int main(int argc, char* argv[]) {
    printf("Hello, World!\n");
    int result = math_op(1, 2);
    printf("Result from Kotlin is %d\n", result);

    auto http_string = get_blog_string();
    printf("Result from Kotlin is %s\n", http_string);

    // read from sample.json
    std::ifstream ifs("sample.json");
    // convert content to char* 
    std::string content( (std::istreambuf_iterator<char>(ifs) ),
                       (std::istreambuf_iterator<char>()    ) );
    auto decoded = decode_json_string(content.c_str());
    return 0;
}
