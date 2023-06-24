package com.makentoshe.booruchan.danbooru.tag.context

import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.danbooru.tag.deserialize.DanbooruTagsDeserialize
import com.makentoshe.booruchan.danbooru.tag.deserialize.JsonDanbooruTagsDeserializer
import com.makentoshe.booruchan.danbooru.tag.deserialize.XmlDanbooruTagsDeserializer
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagsFilter
import com.makentoshe.booruchan.danbooru.tag.network.DanbooruTagsRequest
import com.makentoshe.booruchan.danbooru.tag.network.JsonDanbooruTagsRequest
import com.makentoshe.booruchan.danbooru.tag.network.XmlDanbooruTagsRequest

abstract class DanbooruTagsContext<Request : DanbooruTagsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruTagsDeserialize<*>>
) : TagsContext<Request, DanbooruTagsFilter>(network, deserialize) {
    override fun filterBuilder() = DanbooruTagsFilter.Builder()
}

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
