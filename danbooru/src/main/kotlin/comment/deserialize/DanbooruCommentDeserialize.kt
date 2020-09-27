package comment.deserialize

import comment.DanbooruComment

data class DanbooruCommentDeserialize<out Comment: DanbooruComment>(val comment: Comment)
