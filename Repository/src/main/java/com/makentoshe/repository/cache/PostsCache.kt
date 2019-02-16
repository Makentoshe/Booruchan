package com.makentoshe.repository.cache

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import java.util.HashMap
import java.util.concurrent.ArrayBlockingQueue

/**
 * @param size is a posts count in the storage.
 */
class PostsCache(private val size: Int) :
    ClearableCache<Booru.PostRequest, Posts> {

    private val storage = HashMap<Int, Post>()
    private val keysQueue = ArrayBlockingQueue<Int>(size)

    override fun get(key: Booru.PostRequest, loader: () -> Posts): Posts {
        return getIfPresent(key) ?: loader().apply {
            addToStorage(key, this)
        }
    }

    private fun addToStorage(key: Booru.PostRequest, value: Posts) {
        for (i in 0 until key.count) {
            val position = key.count * key.page + i
            addElementToStorage(position, value[i])
        }
    }

    private fun addElementToStorage(position: Int, post: Post) {
        try {
            keysQueue.add(position)
            storage[position] = post
        } catch (e: IllegalStateException) {
            keysQueue.poll()
            addElementToStorage(position, post)
        }
    }

    override fun getIfPresent(key: Booru.PostRequest): Posts? {
        val startPosition = key.count * key.page
        val posts = mutableListOf<Post>()
        for (i in startPosition until startPosition + key.count) {
            val post = storage[i] ?: return null
            posts.add(post)
        }
        return Posts(posts)
    }

    override fun remove(key: Booru.PostRequest): Posts? {
        val startPosition = key.count * key.page
        val posts = mutableListOf<Post>()
        for (i in startPosition until startPosition + key.count) {
            val post = storage.remove(i) ?: continue
            posts.add(post)
        }
        return Posts(posts)
    }

    override fun getAll(): Collection<Posts> = listOf(Posts(storage.values.toList()))

    override fun clear() {
        storage.clear()
        keysQueue.clear()
    }
}