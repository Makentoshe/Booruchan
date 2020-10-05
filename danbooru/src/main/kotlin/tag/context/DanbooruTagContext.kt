package tag.context

import tag.deserialize.DanbooruTagDeserialize
import tag.deserialize.JsonDanbooruTagDeserializer
import tag.deserialize.XmlDanbooruTagDeserializer
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagRequest
import tag.network.JsonDanbooruTagRequest
import tag.network.XmlDanbooruTagRequest

abstract class DanbooruTagContext<Request : DanbooruTagRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruTagDeserialize<*>>
) : TagContext<Request, DanbooruTagFilter>(network, deserialize)

open class JsonDanbooruTagContext(
    network: suspend (JsonDanbooruTagRequest) -> Result<String>
) : DanbooruTagContext<JsonDanbooruTagRequest>(
    network, { json -> JsonDanbooruTagDeserializer().deserializeTag(json) }
) {
    override fun buildRequest(filter: DanbooruTagFilter) = JsonDanbooruTagRequest(filter)
}

open class XmlDanbooruTagContext(
    network: suspend (XmlDanbooruTagRequest) -> Result<String>
) : DanbooruTagContext<XmlDanbooruTagRequest>(
    network, { xml -> XmlDanbooruTagDeserializer().deserializeTag(xml) }
) {
    override fun buildRequest(filter: DanbooruTagFilter) = XmlDanbooruTagRequest(filter)
}
