package tag.network

sealed class DanbooruTagRequest {

    protected val host = "https://danbooru.donmai.us"

    abstract val url: String

    data class Xml(private val filter: DanbooruTagFilter) : DanbooruTagRequest() {
        override val url = when (filter) {
            is DanbooruTagFilter.ById -> "$host/tags/${filter.id.tagId}.xml"
            is DanbooruTagFilter.ByName -> "$host/tags.xml?search[name]=${filter.name}"
        }
    }

    data class Json(private val filter: DanbooruTagFilter) : DanbooruTagRequest() {
        override val url = when (filter) {
            is DanbooruTagFilter.ById -> "$host/tags/${filter.id.tagId}.json"
            is DanbooruTagFilter.ByName -> "$host/tags.json?search[name]=${filter.name}"
        }
    }
}