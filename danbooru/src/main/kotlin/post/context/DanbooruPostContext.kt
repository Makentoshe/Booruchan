package post.context

import post.deserialize.DanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.deserialize.XmlDanbooruPostDeserializer
import post.network.DanbooruPostRequest

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
