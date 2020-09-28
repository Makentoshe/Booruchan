package post.context

import post.deserialize.DanbooruPostsDeserialize
import post.deserialize.JsonDanbooruPostsDeserializer
import post.deserialize.XmlDanbooruPostsDeserializer
import post.network.DanbooruPostsRequest
import post.network.JsonDanbooruPostsRequest
import post.network.XmlDanbooruPostsRequest

open class DanbooruPostsContext<in Request : DanbooruPostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruPostsDeserialize<*>>
) : PostsContext<Request>(network, deserialize)

open class JsonDanbooruPostsContext(
    network: suspend (JsonDanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<JsonDanbooruPostsRequest>(
    network, { json -> JsonDanbooruPostsDeserializer().deserializePosts(json) }
)

open class XmlDanbooruPostsContext(
    network: suspend (DanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<XmlDanbooruPostsRequest>(
    network, { json -> XmlDanbooruPostsDeserializer().deserializePosts(json) }
)
