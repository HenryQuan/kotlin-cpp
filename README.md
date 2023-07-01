# Kotlin C

Experiment with Kotlin/Native to export functions from Kotlin to C++ on Windows.

## Findings

- `build.gradle.kts` can be challenging to setup, but much better than CMake
- Both static and dynamic libraries can be generated with `staticLib` or `sharedLib`
- `pthread` and `bcrypt` are required to be linked with the static library
- Only the dynamic library works in the Windows Sandbox (a fresh environment)
  - The static library build will fail to run due to missing DLLs

## The generated header

The generated header contains everything from Kotlin. First, you can create a reference to all the symbols, mathop is the name of the library.

```c++
const auto lib = mathop_symbols();
```

Then, you can call the function by using its kotlin package name, it can be a bit long.

```c++
auto service = lib->kotlin.root.model.service.APIService.APIService();
lib->kotlin.root.model.service.APIService.getData(service, (void*)callback_block);
```

In C/C++, you can define some macros to make it shorter.

```c++
#define KN lib->kotlin.root
#define KN_MODEL KN.model
#define KN_SERVICE KN_MODEL.service
#define KN_API_SERVICE KN_SERVICE.APIService
```

Now, the code above can be written as:

```c++
auto service = KN_API_SERVICE.APIService();
KN_API_SERVICE.getData(service, (void*)callback_block);
```

### Callbacks

In Kotlin, we have `suspend` functions to make asynchronous calls. In C/C++, we can use callbacks to achieve similar results. However, it can be challenging to setup both sides. First, we need to define a callback function in C/C++.

```c++
void callback_block(const char*);
void location_block(mathop_kref_model_Location);
```

It is nice that kotlin exports many symbols for us to use so primitive types can be used directly, but for classes from kotlin, we need to use `xxx_kref_xxx` symbols as a pointer. It isn't possible for C/C++ to know how to handle a Kotlin class, but Kotlin can. In Kotlin, we need to define callbacks we need to use in C/C++.

```kotlin
// This maps to `void callback_block(const char*);`
typealias StringCallback = CPointer<CFunction<(CValues<ByteVar>) -> Unit>>
// This maps to `void location_block(mathop_kref_model_Location);`
typealias LocationCallback = CPointer<CFunction<(COpaquePointer) -> Unit>>
```

Note that I used `COpaquePointer`. I guess it is similar to `void*` in C/C++. `mathop_kref_model_Location` must be used to prevent any crashes from C/C++. They must match exactly. Now, we can use the callbacks in Kotlin.

```kotlin
fun getData(callback: StringCallback) {
    CoroutineScope(Dispatchers.Default).launch {
        val data = getData()                        // Get the data suspend
        callback.invoke(data.cstr)                  // Call `callback_block` in C/C++
    }
}

fun traverse_locations(locationCallback: LocationCallback) {
    for (loc in locations) {
        val stableRef = StableRef.create(loc)       // Create a stable reference to `loc` must match with C/C++
        val locPointer = stableRef.asCPointer()     // Create a pointer to `loc`
        locationCallback(locPointer)                // Call `location_block` in C/C++
        stableRef.dispose()                         // Free the memory
    }
}
```

It is always easier to callback a primitive type. Now, we have the `Location` object in C/C++. We can call our lib to get the data from it.

```c++
void callback_block(const char* result) {
    printf("callback is %s\n", result);
}

void location_block(mathop_kref_model_Location loc) {
    printf("location is %s\n", KN_MODEL.Location.get_name(loc));
}
```

Note that we only need to do this because it is native. For Android development, all kotlin codes are native to JVM. For iOS development, it has much better support for Swift and Objective-C. `suspend` functions are converted to `@escaping` closures. You can also access everything within your kotlin class.
