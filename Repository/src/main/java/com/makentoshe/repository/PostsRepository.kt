package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.repository.cache.Cache
import java.io.Serializable

/**
 * Repository which contains a posts.
 *
 * @param booru a posts source.
 * @param cache all received posts will be cached for the reusing.
 * @param tags the tags that will be associated with posts
 * @param count the number of posts per [get].
 */
class PostsRepository(
    private val booru: Booru,
    private val cache: Cache<Int, Posts>,
    val count: Int,
    val tags: Set<Tag>
) : Repository<Int, Posts>, Serializable {

    /**
     * Returns an posts from the cache by the [key].
     * If the array of posts divide by [count] parts, the [key] is a number of a single part(page).
     *
     * @param key a page number.
     * @return a [Posts] instance which is instance of a List
     */
    override fun get(key: Int): Posts {
        return cache.get(key) { booru.getPosts(count, key, tags) }
    }
}