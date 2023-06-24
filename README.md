# Kotlin C
Experiment with Kotlin/Native to export functions from Kotlin to C++ on Windows.

## Findings
- `build.gradle.kts` can be challenging to setup, but much better than CMake
- Both static and dynamic libraries can be generated with `staticLib` or `sharedLib`
- `pthread` and `bcrypt` are required to be linked with the static library
- Only the dynamic library works in the Windows Sandbox (a fresh environment)
    - The static library build will fail to run due to missing DLLs
