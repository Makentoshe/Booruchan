package com.makentoshe.booruchan.gelbooru.post.network

import com.makentoshe.booruchan.core.post.Tags
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.string

class GelbooruPostsFilter(params: Map<String, Any>) : PostsFilter(params) {

    /** Most preferred type-safety constructor */
    constructor(count: Int? = null, page: Int? = null, tags: Tags = Tags.EMPTY) : this(buildMap(count, page, tags))

    override val firstChar: String = "&"

    companion object {
        private const val COUNT = "limit"
        private const val PAGE = "pid"
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
