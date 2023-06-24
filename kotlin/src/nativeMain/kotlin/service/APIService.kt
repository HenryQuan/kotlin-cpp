package service

import io.ktor.client.*
import io.ktor.client.engine.winhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class APIService {
    private val client = HttpClient(WinHttp)

    suspend fun getData(): String {
        return client.get("https://henryquan.github.io/").bodyAsText()
    }
}
