package com.makentoshe.booruchan.danbooru.post.network

import com.makentoshe.booruchan.core.post.Tags
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.string

class DanbooruPostsFilter private constructor(params: Map<String, Any>) : PostsFilter(params) {

    constructor(count: Int? = null, page: Int? = null, tags: Tags = Tags.EMPTY) : this(buildMap(count, page, tags))

    companion object {
        private const val COUNT = "limit"
        private const val PAGE = "page"
        private const val TAGS = "tags"

        private fun buildMap(count: Int?, page: Int?, tags: Tags): Map<String, Any> {
            val params = HashMap<String, Any>()
            if (count != null) params[COUNT] = count
            if (page != null) params[PAGE] = page
            if (tags.tags.isNotEmpty()) params[TAGS] = tags.string
            return params
        }
    }
}