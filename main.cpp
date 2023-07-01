#include <stdio.h>
#include <mathop_api.h>
#include <fstream>

#define KN kotlin.root
#define KN_MODEL KN.model

void callback_block(const char*);
void location_block(mathop_kref_model_Location);

const auto lib = mathop_symbols();

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
    auto loc_string = lib->KN_MODEL.SearchResult.get_locations_string(decoded);
    auto loc_list = lib->KN_MODEL.SearchResult.get_locations(decoded);
    lib->KN_MODEL.SearchResult.traverse_locations(decoded, (void*)location_block);

    printf("Result from Kotlin is %s\n---\n", loc_string);
    auto service = lib->KN.service.APIService.APIService();

    get_data_callback(service, (void*)callback_block);
    getchar(); // wait for async call
    return 0;
}

void location_block(mathop_kref_model_Location loc) {
    printf("location is %s\n", lib->KN_MODEL.Location.get_name(loc));
}

void callback_block(const char* result) {
    printf("callback is %s\n", result);
}