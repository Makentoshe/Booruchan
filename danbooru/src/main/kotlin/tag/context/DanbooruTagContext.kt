package tag.context

import tag.deserialize.DanbooruTagDeserialize
import tag.deserialize.JsonDanbooruTagDeserializer
import tag.deserialize.XmlDanbooruTagDeserializer
import tag.network.DanbooruTagRequest
import tag.network.JsonDanbooruTagRequest
import tag.network.XmlDanbooruTagRequest

open class DanbooruTagContext<Request: DanbooruTagRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruTagDeserialize<*>>
) : TagContext<Request>(network, deserialize)

open class JsonDanbooruTagContext(
    network: suspend (JsonDanbooruTagRequest) -> Result<String>
) : DanbooruTagContext<JsonDanbooruTagRequest>(
    network, { json -> JsonDanbooruTagDeserializer().deserializeTag(json) }
)

open class XmlDanbooruTagContext(
    network: suspend (XmlDanbooruTagRequest) -> Result<String>
) : DanbooruTagContext<XmlDanbooruTagRequest>(
    network, { xml -> XmlDanbooruTagDeserializer().deserializeTag(xml) }
)
