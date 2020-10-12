package post.context

import post.deserialize.DanbooruPostsDeserialize
import post.deserialize.JsonDanbooruPostsDeserializer
import post.deserialize.XmlDanbooruPostsDeserializer
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsRequest
import post.network.JsonDanbooruPostsRequest
import post.network.XmlDanbooruPostsRequest

abstract class DanbooruPostsContext<Request : DanbooruPostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruPostsDeserialize<*>>
) : PostsContext<Request, DanbooruPostsFilter>(network, deserialize)

open class JsonDanbooruPostsContext(
    network: suspend (JsonDanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<JsonDanbooruPostsRequest>(
    network, { json -> JsonDanbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: DanbooruPostsFilter) = JsonDanbooruPostsRequest(filter)
}

open class XmlDanbooruPostsContext(
    network: suspend (XmlDanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<XmlDanbooruPostsRequest>(
    network, { json -> XmlDanbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: DanbooruPostsFilter) = XmlDanbooruPostsRequest(filter)
}
