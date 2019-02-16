package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.repository.cache.PostsCache

class PostsCachedRepository(
    private val cache: PostsCache,
    private val repository: Repository<Booru.PostRequest, Posts>
) : ClearableRepository<Booru.PostRequest, Posts> {

    override fun get(key: Booru.PostRequest): Posts? {
        return cache.get(key) { repository.get(key) }
    }

    override fun clear() = cache.clear()
}