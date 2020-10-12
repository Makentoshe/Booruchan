package post.context

import post.deserialize.GelbooruPostsDeserialize
import post.deserialize.JsonGelbooruPostsDeserializer
import post.deserialize.XmlGelbooruPostsDeserializer
import post.network.GelbooruPostsFilter
import post.network.GelbooruPostsRequest
import post.network.JsonGelbooruPostsRequest
import post.network.XmlGelbooruPostsRequest

abstract class GelbooruPostsContext<Request : GelbooruPostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruPostsDeserialize<*>>
) : PostsContext<Request, GelbooruPostsFilter>(network, deserialize)

open class JsonGelbooruPostsContext(
    network: suspend (JsonGelbooruPostsRequest) -> Result<String>
) : GelbooruPostsContext<JsonGelbooruPostsRequest>(
    network, { json -> JsonGelbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: GelbooruPostsFilter) = JsonGelbooruPostsRequest(filter)
}

open class XmlGelbooruPostsContext(
    network: suspend (XmlGelbooruPostsRequest) -> Result<String>
) : GelbooruPostsContext<XmlGelbooruPostsRequest>(
    network, { json -> XmlGelbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: GelbooruPostsFilter) = XmlGelbooruPostsRequest(filter)
}
