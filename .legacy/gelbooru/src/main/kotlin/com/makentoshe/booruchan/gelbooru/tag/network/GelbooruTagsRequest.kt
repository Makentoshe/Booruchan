package com.makentoshe.booruchan.gelbooru.tag.network

import com.makentoshe.booruchan.core.tag.network.TagsRequest
import com.makentoshe.booruchan.gelbooru.network.GelbooruRequest

sealed class GelbooruTagsRequest : GelbooruRequest(), TagsRequest {
    protected val internalUrl = "$host/index.php?page=dapi&s=tag&q=index"
}

data class XmlGelbooruTagsRequest(private val filter: GelbooruTagsFilter) : GelbooruTagsRequest() {
    override val url = "$internalUrl${filter.toUrl()}"
}

data class JsonGelbooruTagsRequest(private val filter: GelbooruTagsFilter) : GelbooruTagsRequest() {
    override val url = "$internalUrl&json=1${filter.toUrl()}"
}
