package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.post.PostFactory
import com.makentoshe.booruchan.api.component.post.PostTagsParser

class Rule34PostFactory(
    private val postTagsParser: PostTagsParser
) : PostFactory<Rule34Post> {

    override fun build(attributes: Map<String, String>, action: Rule34Post.() -> Unit): Rule34Post {
        return Rule34Post(postTagsParser, attributes).apply(action)
    }
}