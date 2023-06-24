package com.makentoshe.booruchan.danbooru.comment.deserialize

import com.makentoshe.booruchan.core.comment.deserialize.CommentDeserialize
import com.makentoshe.booruchan.danbooru.comment.DanbooruComment
import com.makentoshe.booruchan.danbooru.comment.JsonDanbooruComment
import com.makentoshe.booruchan.danbooru.comment.XmlDanbooruComment

typealias JsonDanbooruCommentDeserialize = DanbooruCommentDeserialize<JsonDanbooruComment>
typealias XmlDanbooruCommentDeserialize = DanbooruCommentDeserialize<XmlDanbooruComment>

data class DanbooruCommentDeserialize<out Comment: DanbooruComment>(
    override val comment: Comment
): CommentDeserialize<Comment>
