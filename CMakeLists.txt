cmake_minimum_required(VERSION 3.16)
project(kotlin-c)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Threads REQUIRED)
find_library(BCRYPT_LIB bcrypt)

set(KOTLIN_DIR "${CMAKE_CURRENT_SOURCE_DIR}/kotlin/build/bin/native")
set(KOTLIN_SHARED_DIR "${KOTLIN_DIR}/releaseShared")
set(KOTLIN_STATIC_DIR "${KOTLIN_DIR}/releaseStatic")

add_library(static-mathop-lib STATIC IMPORTED)
set_target_properties(static-mathop-lib PROPERTIES 
    IMPORTED_IMPLIB "${KOTLIN_STATIC_DIR}/libmathop.a"
    IMPORTED_LOCATION "${KOTLIN_STATIC_DIR}/libmathop.a"
    INTERFACE_INCLUDE_DIRECTORIES "${KOTLIN_STATIC_DIR}"
)
# For the static library, we need to link pthreads and bcrypt manually
target_link_libraries(static-mathop-lib INTERFACE Threads::Threads bcrypt)

add_library(shared-mathop-lib SHARED IMPORTED)
set_target_properties(shared-mathop-lib PROPERTIES
    IMPORTED_IMPLIB "${KOTLIN_SHARED_DIR}/mathop.dll"
    IMPORTED_LOCATION "${KOTLIN_SHARED_DIR}/mathop.dll"
    INTERFACE_INCLUDE_DIRECTORIES "${KOTLIN_SHARED_DIR}"
)

# .c won't work, must be .cpp somehow probably due to missing functions in C
set(SRC main.cpp)
add_executable(main ${SRC})
target_link_libraries(main PRIVATE shared-mathop-lib)
