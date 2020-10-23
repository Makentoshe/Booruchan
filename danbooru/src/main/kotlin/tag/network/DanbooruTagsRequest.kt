package tag.network

import network.DanbooruRequest

sealed class DanbooruTagsRequest: DanbooruRequest(), TagsRequest

data class XmlDanbooruTagsRequest(private val filter: DanbooruTagsFilter) : DanbooruTagsRequest() {
    override val url = "$host/tags.xml${filter.toUrl()}"
}

data class JsonDanbooruTagsRequest(private val filter: DanbooruTagsFilter) : DanbooruTagsRequest() {
    override val url = "$host/tags.json${filter.toUrl()}"
}
