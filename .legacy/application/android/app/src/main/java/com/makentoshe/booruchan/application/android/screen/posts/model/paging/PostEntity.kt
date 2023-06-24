package com.makentoshe.booruchan.application.android.screen.posts.model.paging

import com.makentoshe.booruchan.core.post.Post

sealed class PostEntity {

    data class Success(val post: Post) : PostEntity()

    data class Failure(val exception: Throwable) : PostEntity()
}