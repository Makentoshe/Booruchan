package comment.deserialize

import comment.Comment

interface CommentDeserialize<out C : Comment> {
    val comment: C
}

