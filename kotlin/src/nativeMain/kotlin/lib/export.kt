package lib

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.DATA
import model.SearchResult
import service.APIService

@CName("math_op")
fun mathOp(a: Int, b: Int): Int {
    return a + b
}

@CName("get_blog_string")
suspend fun getBlogString(): String {
    val service = APIService()
    return service.getData()
}

@CName("decode_json_string")
fun decodeJsonString(json: String): SearchResult {
    return Json.decodeFromString<SearchResult>(json)
}
