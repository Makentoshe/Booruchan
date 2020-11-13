package com.makentoshe.booruchan.gelbooru.tag.context

import com.makentoshe.booruchan.core.tag.context.TagContext
import com.makentoshe.booruchan.gelbooru.tag.GelbooruTagDeserialize
import com.makentoshe.booruchan.gelbooru.tag.JsonGelbooruTagDeserializer
import com.makentoshe.booruchan.gelbooru.tag.XmlGelbooruTagDeserializer
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagFilter
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagRequest
import com.makentoshe.booruchan.gelbooru.tag.network.JsonGelbooruTagRequest
import com.makentoshe.booruchan.gelbooru.tag.network.XmlGelbooruTagRequest

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
