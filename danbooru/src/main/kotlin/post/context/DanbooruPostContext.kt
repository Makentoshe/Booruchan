package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import post.deserialize.DanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.deserialize.XmlDanbooruPostDeserializer
import post.network.*
import post.postId

open class DanbooruPostContext(
    private val network: suspend (DanbooruPostRequest) -> Result<String>,
    private val deserialize: (String) -> Result<DanbooruPostDeserialize<*>>
) {

    suspend fun get(request: DanbooruPostRequest): Result<DanbooruPostDeserialize<*>> {
        val networkResult = network(request)
        if (networkResult.isFailure) return Result.failure(
            networkResult.exceptionOrNull() ?: IllegalStateException("Could not define network exception")
        )

        val networkSuccess = networkResult.getOrNull() ?: throw IllegalStateException("Could not define network result")
        val deserializeResult = deserialize(networkSuccess)
        if (deserializeResult.isFailure) return Result.failure(
            deserializeResult.exceptionOrNull() ?: IllegalStateException("Could not define deserialize exception")
        )

        return Result.success(
            deserializeResult.getOrNull() ?: throw IllegalStateException("Could not define deserialize result")
        )
    }
}

open class JsonDanbooruPostContext(
    network: suspend (DanbooruPostRequest) -> Result<String>
) : DanbooruPostContext(network, { json -> JsonDanbooruPostDeserializer().deserializePost(json) })

open class XmlDanbooruPostContext(
    network: suspend (DanbooruPostRequest) -> Result<String>
) : DanbooruPostContext(network, { xml -> XmlDanbooruPostDeserializer().deserializePost(xml) })






fun main() = runBlocking {
    json()
    xml()
}

suspend fun json() {
    val request = JsonDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
    val context = JsonDanbooruPostContext { DanbooruPostNetworkManager(HttpClient()).getPost(it) }
    val result = context.get(request)
    println(result)
}

suspend fun xml() {
    val request = XmlDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
    val context = XmlDanbooruPostContext { DanbooruPostNetworkManager(HttpClient()).getPost(it) }
    val result = context.get(request)
    println(result)
}