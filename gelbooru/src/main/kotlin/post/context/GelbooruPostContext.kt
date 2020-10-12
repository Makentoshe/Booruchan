package post.context

import post.deserialize.GelbooruPostDeserialize
import post.deserialize.JsonGelbooruPostDeserializer
import post.deserialize.XmlGelbooruPostDeserializer
import post.network.GelbooruPostFilter
import post.network.GelbooruPostRequest
import post.network.JsonGelbooruPostRequest
import post.network.XmlGelbooruPostRequest

abstract class GelbooruPostContext<Request : GelbooruPostRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruPostDeserialize<*>>
) : PostContext<Request, GelbooruPostFilter>(network, deserialize)

open class JsonGelbooruPostContext(
    network: suspend (JsonGelbooruPostRequest) -> Result<String>
) : GelbooruPostContext<JsonGelbooruPostRequest>(
    network, { json -> JsonGelbooruPostDeserializer().deserializePost(json) }
) {
    override fun buildRequest(filter: GelbooruPostFilter) = JsonGelbooruPostRequest(filter)
}

open class XmlGelbooruPostContext(
    network: suspend (XmlGelbooruPostRequest) -> Result<String>
) : GelbooruPostContext<XmlGelbooruPostRequest>(
    network, { xml -> XmlGelbooruPostDeserializer().deserializePost(xml) }
) {
    override fun buildRequest(filter: GelbooruPostFilter) = XmlGelbooruPostRequest(filter)
}
