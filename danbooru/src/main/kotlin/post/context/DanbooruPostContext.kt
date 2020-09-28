package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import post.deserialize.DanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.deserialize.XmlDanbooruPostDeserializer
import post.network.*
import post.postId

open class DanbooruPostContext(
    network: suspend (DanbooruPostRequest) -> Result<String>,
    deserialize: (String) -> Result<DanbooruPostDeserialize<*>>
) : PostContext<DanbooruPostRequest>(network, deserialize)

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