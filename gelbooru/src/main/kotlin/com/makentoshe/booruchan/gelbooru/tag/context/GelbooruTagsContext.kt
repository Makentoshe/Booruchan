package com.makentoshe.booruchan.gelbooru.tag.context

import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.gelbooru.tag.GelbooruTagsDeserialize
import com.makentoshe.booruchan.gelbooru.tag.JsonGelbooruTagsDeserializer
import com.makentoshe.booruchan.gelbooru.tag.XmlGelbooruTagsDeserializer
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsFilter
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagsRequest
import com.makentoshe.booruchan.gelbooru.tag.network.JsonGelbooruTagsRequest
import com.makentoshe.booruchan.gelbooru.tag.network.XmlGelbooruTagsRequest

abstract class GelbooruTagsContext<Request : GelbooruTagsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruTagsDeserialize<*>>
) : TagsContext<Request, GelbooruTagsFilter>(network, deserialize) {

    override fun filterBuilder() = GelbooruTagsFilter.Builder()
    
}

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
