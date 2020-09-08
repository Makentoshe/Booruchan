package tag.network

sealed class DanbooruTagsRequest {

    protected val host = "https://danbooru.donmai.us"

    abstract val url: String

    data class Xml(private val filter: DanbooruTagsFilter) : DanbooruTagsRequest() {
        override val url = "$host/tags.xml$filter"
    }

    data class Json(private val filter: DanbooruTagsFilter) : DanbooruTagsRequest() {
        override val url = "$host/tags.json$filter"
    }
}