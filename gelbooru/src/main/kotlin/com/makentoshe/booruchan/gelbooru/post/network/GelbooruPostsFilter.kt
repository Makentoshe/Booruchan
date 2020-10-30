package com.makentoshe.booruchan.gelbooru.post.network

import com.makentoshe.booruchan.core.post.Tags
import com.makentoshe.booruchan.core.post.network.PostsFilter

class GelbooruPostsFilter(params: Map<String, Any>) : PostsFilter(params) {

    override val firstChar: String = "&"

    class Builder : PostsFilter.Builder() {
        override val countKey: String = "limit"
        override val pageKey: String = "pid"
        override val tagsKey: String = "tags"

        override fun build(count: Int?, page: Int?, tags: Tags): GelbooruPostsFilter {
            return GelbooruPostsFilter(params(count, page, tags))
        }
    }
}
