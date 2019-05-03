package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.post.Post
import java.io.Serializable

class CommentsRepository(private val booru: Booru) : Repository<Post, List<Comment>>,
                                                     Serializable {
    override fun get(key: Post): List<Comment>? {
        return booru.getComments().request(key.id)
    }
}