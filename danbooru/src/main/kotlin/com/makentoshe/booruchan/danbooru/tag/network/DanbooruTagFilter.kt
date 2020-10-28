package com.makentoshe.booruchan.danbooru.tag.network

import com.makentoshe.booruchan.core.tag.TagId
import com.makentoshe.booruchan.core.tag.network.TagFilter

sealed class DanbooruTagFilter: TagFilter {

    data class ById(val id: TagId) : DanbooruTagFilter() {
        override fun toUrl() = id.tagId.toString()
    }

    data class ByName(val name: String): DanbooruTagFilter() {
        override fun toUrl() = name
    }
}