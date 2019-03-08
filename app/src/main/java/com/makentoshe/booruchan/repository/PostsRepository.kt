package com.makentoshe.booruchan.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import java.io.Serializable

class PostsRepository(private val booru: Booru) : Repository<Booru.PostRequest, List<Post>>, Serializable {

    override fun get(key: Booru.PostRequest): Posts = booru.getPosts(key)
}