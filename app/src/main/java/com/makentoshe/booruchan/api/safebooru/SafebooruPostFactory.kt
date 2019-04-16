package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.component.post.PostFactory
import com.makentoshe.booruchan.api.component.post.PostTagsParser

class SafebooruPostFactory(
    private val postTagsParser: PostTagsParser
) : PostFactory<SafebooruPost> {

    override fun build(attributes: Map<String, String>, action: SafebooruPost.() -> Unit): SafebooruPost {
        return SafebooruPost(postTagsParser, attributes).apply(action)
    }
}