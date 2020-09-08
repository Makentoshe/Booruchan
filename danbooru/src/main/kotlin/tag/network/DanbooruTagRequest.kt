package tag.network

import tag.entity.TagId

sealed class DanbooruTagRequest {

    abstract val url: String

    data class Xml(val tagId: TagId) : DanbooruTagRequest() {
        override val url = "https://danbooru.donmai.us/tag/${tagId.tagId}.xml"
    }

    data class Json(val tagId: TagId) : DanbooruTagRequest() {
        override val url = "https://danbooru.donmai.us/tag/${tagId.tagId}.json"
    }
}