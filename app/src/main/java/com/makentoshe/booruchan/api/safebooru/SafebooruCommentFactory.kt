package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.comment.CommentFactory

class SafebooruCommentFactory : CommentFactory {
    override fun build(attributes: Map<String, String>, action: Comment.() -> Comment): Comment {
        return SafebooruComment(attributes).action()
    }
}