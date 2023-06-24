import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import lib.math_op
import model.DATA
import model.SearchResult
import service.APIService

fun main() {
    val result = math_op(5, 6)
    println("Hello, Kotlin/Native!, $result")

    runBlocking {
        val service = APIService()
        val blogString = service.getData()
        println(blogString)
    }

    // decode json
    val decoded = Json.decodeFromString<SearchResult>(DATA.jsonString)
    println(decoded.locations.first().name)
}
