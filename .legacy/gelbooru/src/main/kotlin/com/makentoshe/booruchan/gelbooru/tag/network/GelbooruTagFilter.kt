package com.makentoshe.booruchan.gelbooru.tag.network

import com.makentoshe.booruchan.core.tag.TagId
import com.makentoshe.booruchan.core.tag.network.TagFilter

sealed class GelbooruTagFilter: TagFilter {

    data class ById(val id: TagId) : GelbooruTagFilter() {
        override fun toUrl() = id.tagId.toString()
    }

    data class ByName(val name: String): GelbooruTagFilter() {
        override fun toUrl() = name
    }
}
