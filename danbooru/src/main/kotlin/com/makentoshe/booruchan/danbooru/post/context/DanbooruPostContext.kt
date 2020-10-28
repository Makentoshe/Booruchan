package com.makentoshe.booruchan.danbooru.post.context

import com.makentoshe.booruchan.core.post.context.PostContext
import com.makentoshe.booruchan.danbooru.post.deserialize.DanbooruPostDeserialize
import com.makentoshe.booruchan.danbooru.post.deserialize.JsonDanbooruPostDeserializer
import com.makentoshe.booruchan.danbooru.post.deserialize.XmlDanbooruPostDeserializer
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostFilter
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostRequest
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostRequest
import com.makentoshe.booruchan.danbooru.post.network.XmlDanbooruPostRequest

abstract class DanbooruPostContext<Request : DanbooruPostRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruPostDeserialize<*>>
) : PostContext<Request, DanbooruPostFilter>(network, deserialize)

open class JsonDanbooruPostContext(
    network: suspend (JsonDanbooruPostRequest) -> Result<String>
) : DanbooruPostContext<JsonDanbooruPostRequest>(
    network, { json -> JsonDanbooruPostDeserializer().deserializePost(json) }
) {
    override fun buildRequest(filter: DanbooruPostFilter) = JsonDanbooruPostRequest(filter)
}

open class XmlDanbooruPostContext(
    network: suspend (XmlDanbooruPostRequest) -> Result<String>
) : DanbooruPostContext<XmlDanbooruPostRequest>(
    network, { xml -> XmlDanbooruPostDeserializer().deserializePost(xml) }
) {
    override fun buildRequest(filter: DanbooruPostFilter) = XmlDanbooruPostRequest(filter)
}
