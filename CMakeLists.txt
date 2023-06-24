cmake_minimum_required(VERSION 3.16)
project(kotlin-c)

set(KOTLIN_DIR "${CMAKE_CURRENT_SOURCE_DIR}/kotlin/build/bin/native/releaseStatic")
# add_library(kotlin-mathop-lib SHARED IMPORTED)
# set_target_properties(kotlin-mathop-lib PROPERTIES IMPORTED_IMPLIB "${KOTLIN_DIR}/libmathop.a")


add_executable(main main.c)
target_link_libraries(main "${KOTLIN_DIR}/libmathop.a")
target_include_directories(main PUBLIC "${KOTLIN_DIR}")