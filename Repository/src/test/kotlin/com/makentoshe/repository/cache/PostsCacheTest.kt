package com.makentoshe.repository.cache

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostsCacheTest {

    private lateinit var cache: PostsCache

    @Before
    fun init() {
        cache = PostsCache(2)
    }

    @Test
    fun `should return a posts if they are present`() {
        cache.get(Booru.PostRequest(2, 0, setOf())) {
            Posts(listOf(Post(id = 1), Post(id = 2)))
        }

        var posts = cache.getIfPresent(Booru.PostRequest(1, 0, setOf()))
        assertNotNull(posts)
        assertEquals(1, posts!![0].id)

        posts = cache.getIfPresent(Booru.PostRequest(1, 1, setOf()))
        assertNotNull(posts)
        assertEquals(2, posts!![0].id)

        posts = cache.getIfPresent(Booru.PostRequest(1, 2, setOf()))
        assertNull(posts)

        posts = cache.getIfPresent(Booru.PostRequest(3, 0, setOf()))
        assertNull(posts)
    }

    @Test
    fun `should remove value`() {
        cache.get(Booru.PostRequest(2, 0, setOf())) {
            Posts(listOf(Post(id = 1), Post(id = 2)))
        }
        cache.remove(Booru.PostRequest(1, 0, setOf()))

        assertNull(cache.getIfPresent(Booru.PostRequest(1, 0, setOf())))
        assertNotNull(Booru.PostRequest(1, 1, setOf()))
        assertNull(cache.getIfPresent(Booru.PostRequest(2, 0, setOf())))
    }

    @Test
    fun `should return all values`() {
        cache.get(Booru.PostRequest(2, 0, setOf())) {
            Posts(listOf(Post(id = 1), Post(id = 2)))
        }
        cache.get(Booru.PostRequest(3, 2, setOf())) {
            Posts(listOf(Post(id = 3), Post(id = 4), Post(id = 5)))
        }

        assertEquals(1, cache.getAll().size)
        assertEquals(5, cache.getAll().iterator().next().size)
    }

    @Test
    fun `should replace old value by new`() {
        cache.get(Booru.PostRequest(3, 0, setOf())) {
            Posts(listOf(Post(id = 1), Post(id = 2), Post(id = 3)))
        }
        cache.get(Booru.PostRequest(2, 1, setOf())) {
            Posts(listOf(Post(id = 4), Post(id = 5)))
        }

        val posts = cache.getIfPresent(Booru.PostRequest(4, 0, setOf()))
        assertNotNull(posts)
        assertEquals(1, posts!![0].id)
        assertEquals(2, posts[1].id)
        assertEquals(4, posts[2].id)
        assertEquals(5, posts[3].id)

        println(cache)

    }
}