package tag.context

import tag.deserialize.DanbooruTagsDeserialize
import tag.deserialize.JsonDanbooruTagsDeserializer
import tag.deserialize.XmlDanbooruTagsDeserializer
import tag.network.DanbooruTagsFilter
import tag.network.DanbooruTagsRequest
import tag.network.JsonDanbooruTagsRequest
import tag.network.XmlDanbooruTagsRequest

abstract class DanbooruTagsContext<Request : DanbooruTagsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruTagsDeserialize<*>>
) : TagsContext<Request, DanbooruTagsFilter>(network, deserialize)

open class JsonDanbooruTagsContext(
    network: suspend (JsonDanbooruTagsRequest) -> Result<String>
) : DanbooruTagsContext<JsonDanbooruTagsRequest>(
    network, { json -> JsonDanbooruTagsDeserializer().deserializeTags(json) }
) {
    override fun buildRequest(filter: DanbooruTagsFilter) = JsonDanbooruTagsRequest(filter)
}

open class XmlDanbooruTagsContext(
    network: suspend (DanbooruTagsRequest) -> Result<String>
) : DanbooruTagsContext<XmlDanbooruTagsRequest>(
    network, { xml -> XmlDanbooruTagsDeserializer().deserializeTags(xml) }
) {
    override fun buildRequest(filter: DanbooruTagsFilter) = XmlDanbooruTagsRequest(filter)
}
