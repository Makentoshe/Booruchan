package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.comment.CommentFactory

class Rule34CommentFactory : CommentFactory {
    override fun build(attributes: Map<String, String>, action: Comment.() -> Comment): Comment {
        return Rule34Comment(attributes).action()
    }
}