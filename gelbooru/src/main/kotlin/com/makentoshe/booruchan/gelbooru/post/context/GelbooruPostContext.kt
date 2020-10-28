package com.makentoshe.booruchan.gelbooru.post.context

import com.makentoshe.booruchan.core.post.context.PostContext
import com.makentoshe.booruchan.gelbooru.post.deserialize.GelbooruPostDeserialize
import com.makentoshe.booruchan.gelbooru.post.deserialize.JsonGelbooruPostDeserializer
import com.makentoshe.booruchan.gelbooru.post.deserialize.XmlGelbooruPostDeserializer
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostFilter
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostRequest
import com.makentoshe.booruchan.gelbooru.post.network.JsonGelbooruPostRequest
import com.makentoshe.booruchan.gelbooru.post.network.XmlGelbooruPostRequest

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
