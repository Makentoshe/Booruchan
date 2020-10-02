package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import post.deserialize.DanbooruPostsDeserialize
import post.deserialize.JsonDanbooruPostsDeserializer
import post.deserialize.XmlDanbooruPostsDeserializer
import post.network.*

abstract class DanbooruPostsContext<Request : DanbooruPostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruPostsDeserialize<*>>
) : PostsContext<Request>(network, deserialize) {
    abstract fun buildRequest(filter: DanbooruPostsFilter): Request
}

open class JsonDanbooruPostsContext(
    network: suspend (JsonDanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<JsonDanbooruPostsRequest>(
    network, { json -> JsonDanbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: DanbooruPostsFilter) = JsonDanbooruPostsRequest(filter)
}

open class XmlDanbooruPostsContext(
    network: suspend (DanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<XmlDanbooruPostsRequest>(
    network, { json -> XmlDanbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: DanbooruPostsFilter) = XmlDanbooruPostsRequest(filter)
}

fun main() = runBlocking {
    val postContext = JsonDanbooruPostsContext { DanbooruPostsNetworkManager(HttpClient()).getPosts(it) }
    val filter = DanbooruPostsFilter(count = 13)
    val request = postContext.buildRequest(filter)
    val result = postContext.get(request)
    println(result)
}
