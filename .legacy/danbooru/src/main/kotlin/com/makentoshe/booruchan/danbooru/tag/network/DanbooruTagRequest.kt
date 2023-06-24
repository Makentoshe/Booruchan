package com.makentoshe.booruchan.danbooru.tag.network

import com.makentoshe.booruchan.core.tag.network.TagRequest

import com.makentoshe.booruchan.danbooru.network.DanbooruRequest

sealed class DanbooruTagRequest: DanbooruRequest(), TagRequest

data class XmlDanbooruTagRequest(private val filter: DanbooruTagFilter) : DanbooruTagRequest() {
    override val url = when (filter) {
        is DanbooruTagFilter.ById -> "$host/tags/${filter.id.tagId}.xml"
        is DanbooruTagFilter.ByName -> "$host/tags.xml?search[name]=${filter.name}"
    }
}

data class JsonDanbooruTagRequest(private val filter: DanbooruTagFilter) : DanbooruTagRequest() {
    override val url = when (filter) {
        is DanbooruTagFilter.ById -> "$host/tags/${filter.id.tagId}.json"
        is DanbooruTagFilter.ByName -> "$host/tags.json?search[name]=${filter.name}"
    }
}
