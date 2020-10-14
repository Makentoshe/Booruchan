package tag.network

import tag.TagId

sealed class GelbooruTagFilter: TagFilter {

    data class ById(val id: TagId) : GelbooruTagFilter() {
        override fun toUrl() = id.tagId.toString()
    }

    data class ByName(val name: String): GelbooruTagFilter() {
        override fun toUrl() = name
    }
}
