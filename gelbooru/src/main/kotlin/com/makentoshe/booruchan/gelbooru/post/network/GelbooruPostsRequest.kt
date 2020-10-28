package com.makentoshe.booruchan.gelbooru.post.network

import com.makentoshe.booruchan.core.post.network.PostsRequest
import com.makentoshe.booruchan.gelbooru.network.GelbooruRequest

sealed class GelbooruPostsRequest: GelbooruRequest(), PostsRequest {
    protected val internalUrl = "$host/index.php?page=dapi&s=post&q=index"
}

data class XmlGelbooruPostsRequest(val filter: GelbooruPostsFilter) : GelbooruPostsRequest() {
    override val url = "$internalUrl${filter.toUrl()}"
}

data class JsonGelbooruPostsRequest(val filter: GelbooruPostsFilter) : GelbooruPostsRequest() {
    override val url = "$internalUrl${filter.toUrl()}&json=1"
}

