package com.makentoshe.booruchan.gelbooru.comment.network

import com.makentoshe.booruchan.core.comment.network.CommentsRequest
import com.makentoshe.booruchan.gelbooru.network.GelbooruRequest

sealed class GelbooruCommentsRequest: GelbooruRequest(), CommentsRequest {
    protected val internalUrl = "$host/index.php?page=dapi&s=comment&q=index"
}

data class XmlGelbooruCommentsRequest(private val filter: GelbooruCommentsFilter) : GelbooruCommentsRequest() {
    override val url = "$internalUrl${filter.toUrl()}"
}
