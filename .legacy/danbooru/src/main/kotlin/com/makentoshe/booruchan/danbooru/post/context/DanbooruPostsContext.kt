package com.makentoshe.booruchan.danbooru.post.context

import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.danbooru.post.deserialize.DanbooruPostsDeserialize
import com.makentoshe.booruchan.danbooru.post.deserialize.JsonDanbooruPostsDeserializer
import com.makentoshe.booruchan.danbooru.post.deserialize.XmlDanbooruPostsDeserializer
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsFilter
import com.makentoshe.booruchan.danbooru.post.network.DanbooruPostsRequest
import com.makentoshe.booruchan.danbooru.post.network.JsonDanbooruPostsRequest
import com.makentoshe.booruchan.danbooru.post.network.XmlDanbooruPostsRequest

abstract class DanbooruPostsContext<Request : DanbooruPostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruPostsDeserialize<*>>
) : PostsContext<Request, DanbooruPostsFilter>(network, deserialize) {
    override fun filterBuilder() = DanbooruPostsFilter.Builder()
}

open class JsonDanbooruPostsContext(
    network: suspend (JsonDanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<JsonDanbooruPostsRequest>(
    network, { json -> JsonDanbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: DanbooruPostsFilter) = JsonDanbooruPostsRequest(filter)
}

open class XmlDanbooruPostsContext(
    network: suspend (XmlDanbooruPostsRequest) -> Result<String>
) : DanbooruPostsContext<XmlDanbooruPostsRequest>(
    network, { json -> XmlDanbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: DanbooruPostsFilter) = XmlDanbooruPostsRequest(filter)
}
