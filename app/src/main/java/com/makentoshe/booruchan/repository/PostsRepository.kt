package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.Posts

import java.io.Serializable

class PostsRepository(private val booru: Booru) : Repository<Posts.Request, List<Post>>, Serializable {

    override fun get(key: Posts.Request): List<Post> = booru.getPosts().request(key)
}