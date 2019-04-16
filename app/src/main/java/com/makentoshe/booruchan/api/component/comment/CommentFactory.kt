package com.makentoshe.booruchan.api.component.comment

import com.makentoshe.booruchan.api.gelbooru.GelbooruComment

interface CommentFactory {
    fun build(attributes: Map<String, String>, action: Comment.() -> Comment = { this }): Comment

    fun build(vararg pairs: Pair<String, String>, action: Comment.() -> Comment = { this }): Comment {
        return GelbooruComment(pairs.toMap()).action()
    }
}