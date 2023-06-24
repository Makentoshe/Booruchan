package com.makentoshe.booruchan.gelbooru.comment.deserialize

import com.makentoshe.booruchan.core.comment.deserialize.CommentDeserialize
import com.makentoshe.booruchan.gelbooru.comment.GelbooruComment
import com.makentoshe.booruchan.gelbooru.comment.XmlGelbooruComment

/** Json does not supported. 15.10.20 */
//typealias JsonGelbooruCommentDeserialize = GelbooruCommentDeserialize<JsonGelbooruComment>

typealias XmlGelbooruCommentDeserialize = GelbooruCommentDeserialize<XmlGelbooruComment>

data class GelbooruCommentDeserialize<out Comment: GelbooruComment>(
    override val comment: Comment
): CommentDeserialize<Comment>
