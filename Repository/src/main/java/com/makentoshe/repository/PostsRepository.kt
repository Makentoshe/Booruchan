package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.repository.cache.Cache
import com.makentoshe.repository.cache.ClearableCache
import java.io.Serializable


class PostsRepository(private val booru: Booru) : Repository<Booru.PostRequest, Posts>, Serializable {

    override fun get(key: Booru.PostRequest): Posts = booru.getPosts(key)
}