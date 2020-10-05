package tag.network

sealed class DanbooruTagsRequest: TagsRequest {
    protected val host = "https://danbooru.donmai.us"
}

data class XmlDanbooruTagsRequest(private val filter: DanbooruTagsFilter) : DanbooruTagsRequest() {
    override val url = "$host/tags.xml${filter.toUrl()}"
}

data class JsonDanbooruTagsRequest(private val filter: DanbooruTagsFilter) : DanbooruTagsRequest() {
    override val url = "$host/tags.json${filter.toUrl()}"
}
