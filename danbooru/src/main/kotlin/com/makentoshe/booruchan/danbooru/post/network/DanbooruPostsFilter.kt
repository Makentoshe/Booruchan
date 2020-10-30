package com.makentoshe.booruchan.danbooru.post.network

import com.makentoshe.booruchan.core.post.Tags
import com.makentoshe.booruchan.core.post.network.PostsFilter

class DanbooruPostsFilter(params: Map<String, Any>) : PostsFilter(params) {

    override val firstChar: String = "?"

    class Builder : PostsFilter.Builder() {
        override val countKey: String = "limit"
        override val pageKey: String = "page"
        override val tagsKey: String = "tags"

        override fun build(count: Int?, page: Int?, tags: Tags): DanbooruPostsFilter {
            return DanbooruPostsFilter(params(count, page, tags))
        }
    }
}