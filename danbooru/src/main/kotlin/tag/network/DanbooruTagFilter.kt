package tag.network

import tag.TagId

sealed class DanbooruTagFilter {

    data class ById(val id: TagId) : DanbooruTagFilter()

    data class ByName(val name: String): DanbooruTagFilter()
}