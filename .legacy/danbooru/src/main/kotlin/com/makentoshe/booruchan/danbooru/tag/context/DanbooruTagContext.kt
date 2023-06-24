package com.makentoshe.booruchan.danbooru.tag.context

import com.makentoshe.booruchan.core.tag.context.TagContext
import com.makentoshe.booruchan.danbooru.tag.deserialize.DanbooruTagDeserialize
import com.makentoshe.booruchan.danbooru.tag.deserialize.JsonDanbooruTagDeserializer
import com.makentoshe.booruchan.danbooru.tag.deserialize.XmlDanbooruTagDeserializer
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagFilter
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagRequest
import com.makentoshe.booruchan.danbooru.tag.network.JsonDanbooruTagRequest
import com.makentoshe.booruchan.danbooru.tag.network.XmlDanbooruTagRequest

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
