package tag.network

import tag.TagId

sealed class GelbooruTagFilter {

    data class ById(val id: TagId) : GelbooruTagFilter()

    data class ByName(val name: String): GelbooruTagFilter()
}