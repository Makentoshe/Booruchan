package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.component.comment.Comment

interface Comments {

    fun request(postId: Long): List<Comment>
}