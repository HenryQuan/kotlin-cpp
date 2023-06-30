package service

import io.ktor.client.*
import io.ktor.client.engine.winhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.cinterop.*
import kotlinx.cinterop.internal.CCall
import kotlinx.cinterop.internal.CStruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

typealias StringCallback = CPointer<CFunction<(CValues<ByteVar>) -> Unit>>

class APIService {
    private val client = HttpClient(WinHttp)

    suspend fun getData(): String {
        return client.get("https://henryquan.github.io/").bodyAsText()
    }

    @CName("get_data_callback")
    fun getData(callback: StringCallback) {
        CoroutineScope(Dispatchers.Default).launch {
            val data = getData()
            callback.invoke(data.cstr)
        }
    }
}
