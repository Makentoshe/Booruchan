package post.context

import post.deserialize.DanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.deserialize.XmlDanbooruPostDeserializer
import post.network.DanbooruPostRequest
import post.network.JsonDanbooruPostRequest
import post.network.XmlDanbooruPostRequest

open class DanbooruPostContext<in Request: DanbooruPostRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruPostDeserialize<*>>
) : PostContext<Request>(network, deserialize)

open class JsonDanbooruPostContext(
    network: suspend (JsonDanbooruPostRequest) -> Result<String>
) : DanbooruPostContext<JsonDanbooruPostRequest>(
    network, { json -> JsonDanbooruPostDeserializer().deserializePost(json) }
)

open class XmlDanbooruPostContext(
    network: suspend (XmlDanbooruPostRequest) -> Result<String>
) : DanbooruPostContext<XmlDanbooruPostRequest>(
    network, { xml -> XmlDanbooruPostDeserializer().deserializePost(xml) }
)
