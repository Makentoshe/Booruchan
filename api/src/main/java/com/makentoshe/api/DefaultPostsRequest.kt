package com.makentoshe.api

import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Tag

data class DefaultPostsRequest(
    override val count: Int, override val tags: Set<Tag>, override val page: Int
) : PostsRequest