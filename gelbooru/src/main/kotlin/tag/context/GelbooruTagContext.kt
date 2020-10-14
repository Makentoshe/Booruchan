package tag.context

import tag.deserialize.GelbooruTagDeserialize
import tag.deserialize.JsonGelbooruTagDeserializer
import tag.deserialize.XmlGelbooruTagDeserializer
import tag.network.GelbooruTagFilter
import tag.network.GelbooruTagRequest
import tag.network.JsonGelbooruTagRequest
import tag.network.XmlGelbooruTagRequest

abstract class GelbooruTagContext<Request : GelbooruTagRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruTagDeserialize<*>>
) : TagContext<Request, GelbooruTagFilter>(network, deserialize)

open class JsonGelbooruTagContext(
    network: suspend (JsonGelbooruTagRequest) -> Result<String>
) : GelbooruTagContext<JsonGelbooruTagRequest>(
    network, { json -> JsonGelbooruTagDeserializer().deserializeTag(json) }
) {
    override fun buildRequest(filter: GelbooruTagFilter) = JsonGelbooruTagRequest(filter)
}

open class XmlGelbooruTagContext(
    network: suspend (XmlGelbooruTagRequest) -> Result<String>
) : GelbooruTagContext<XmlGelbooruTagRequest>(
    network, { xml -> XmlGelbooruTagDeserializer().deserializeTag(xml) }
) {
    override fun buildRequest(filter: GelbooruTagFilter) = XmlGelbooruTagRequest(filter)
}
