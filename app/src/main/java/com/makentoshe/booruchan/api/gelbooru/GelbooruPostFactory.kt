package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.component.post.PostFactory
import com.makentoshe.booruchan.api.component.post.PostTagsParser

class GelbooruPostFactory(
    private val postTagsParser: PostTagsParser
) : PostFactory<GelbooruPost> {

    override fun build(attributes: Map<String, String>, action: GelbooruPost.() -> Unit): GelbooruPost {
        return GelbooruPost(postTagsParser, attributes).apply(action)
    }
}