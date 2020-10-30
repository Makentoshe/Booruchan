package com.makentoshe.booruchan.gelbooru.post.context

import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.gelbooru.post.deserialize.GelbooruPostsDeserialize
import com.makentoshe.booruchan.gelbooru.post.deserialize.JsonGelbooruPostsDeserializer
import com.makentoshe.booruchan.gelbooru.post.deserialize.XmlGelbooruPostsDeserializer
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsFilter
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsRequest
import com.makentoshe.booruchan.gelbooru.post.network.JsonGelbooruPostsRequest
import com.makentoshe.booruchan.gelbooru.post.network.XmlGelbooruPostsRequest

abstract class GelbooruPostsContext<Request : GelbooruPostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruPostsDeserialize<*>>
) : PostsContext<Request, GelbooruPostsFilter>(network, deserialize) {
    override fun filterBuilder() = GelbooruPostsFilter.Builder()
}

open class JsonGelbooruPostsContext(
    network: suspend (JsonGelbooruPostsRequest) -> Result<String>
) : GelbooruPostsContext<JsonGelbooruPostsRequest>(
    network, { json -> JsonGelbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: GelbooruPostsFilter) = JsonGelbooruPostsRequest(filter)
}

open class XmlGelbooruPostsContext(
    network: suspend (XmlGelbooruPostsRequest) -> Result<String>
) : GelbooruPostsContext<XmlGelbooruPostsRequest>(
    network, { json -> XmlGelbooruPostsDeserializer().deserializePosts(json) }
) {
    override fun buildRequest(filter: GelbooruPostsFilter) = XmlGelbooruPostsRequest(filter)
}
