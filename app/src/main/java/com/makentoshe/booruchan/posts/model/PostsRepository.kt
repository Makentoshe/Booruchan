package com.makentoshe.booruchan.posts.model

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.repository.Repository
import com.makentoshe.repository.cache.Cache
import java.io.Serializable

class PostsRepository(
    private val booru: Booru,
    private val cache: Cache<Int, Posts>,
    private val count: Int,
    private val tags: Set<Tag>
): Repository<Int, Posts>, Serializable {

    override fun get(key: Int): Posts {
        return cache.get(key) { booru.getPosts(count, key, tags) }
    }
}
