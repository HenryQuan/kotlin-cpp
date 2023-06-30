#include <stdio.h>
#include <mathop_api.h>
#include <fstream>
void callback_block(const char*);

int main(int argc, char* argv[]) {
    printf("Hello, World!\n");
    int result = math_op(1, 2);
    printf("Result from Kotlin is %d\n", result);

    auto http_string = "123"; // get_blog_string();
    printf("async call\n");
    printf("Result from Kotlin is %s\n", http_string);

    // read from sample.json
    std::ifstream ifs("sample.json");
    // convert content to char* 
    std::string content( (std::istreambuf_iterator<char>(ifs) ),
                       (std::istreambuf_iterator<char>()    ) );
    auto decoded = decode_json_string(content.c_str());
    auto lib = mathop_symbols();
    auto loc_string = lib->kotlin.root.model.SearchResult.get_locations_string(decoded);
    printf("Result from Kotlin is %s\n---\n", loc_string);
    auto service = lib->kotlin.root.service.APIService.APIService();

    lib->kotlin.root.service.APIService.getData(service, (void*)callback_block);
    getchar();
    return 0;
}

void callback_block(const char* result) {
    printf("callback is %s\n", result);
}