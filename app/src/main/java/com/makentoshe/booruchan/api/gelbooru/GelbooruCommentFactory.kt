package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.comment.CommentFactory

class GelbooruCommentFactory() : CommentFactory {
    override fun build(attributes: Map<String, String>, action: Comment.() -> Comment): Comment {
        return GelbooruComment(attributes).action()
    }
}