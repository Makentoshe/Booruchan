package tag.network

import tag.TagId

sealed class DanbooruTagFilter: TagFilter {

    data class ById(val id: TagId) : DanbooruTagFilter() {
        override fun toUrl() = id.tagId.toString()
    }

    data class ByName(val name: String): DanbooruTagFilter() {
        override fun toUrl() = name
    }
}