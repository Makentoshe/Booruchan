package tag.context

import tag.deserialize.GelbooruTagsDeserialize
import tag.deserialize.JsonGelbooruTagsDeserializer
import tag.deserialize.XmlGelbooruTagsDeserializer
import tag.network.GelbooruTagsFilter
import tag.network.GelbooruTagsRequest
import tag.network.JsonGelbooruTagsRequest
import tag.network.XmlGelbooruTagsRequest

abstract class GelbooruTagsContext<Request : GelbooruTagsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruTagsDeserialize<*>>
) : TagsContext<Request, GelbooruTagsFilter>(network, deserialize)

open class JsonGelbooruTagsContext(
    network: suspend (JsonGelbooruTagsRequest) -> Result<String>
) : GelbooruTagsContext<JsonGelbooruTagsRequest>(
    network, { json -> JsonGelbooruTagsDeserializer().deserializeTags(json) }
) {
    override fun buildRequest(filter: GelbooruTagsFilter) = JsonGelbooruTagsRequest(filter)
}

open class XmlGelbooruTagsContext(
    network: suspend (GelbooruTagsRequest) -> Result<String>
) : GelbooruTagsContext<XmlGelbooruTagsRequest>(
    network, { xml -> XmlGelbooruTagsDeserializer().deserializeTags(xml) }
) {
    override fun buildRequest(filter: GelbooruTagsFilter) = XmlGelbooruTagsRequest(filter)
}
