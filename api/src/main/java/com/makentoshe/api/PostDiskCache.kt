package com.makentoshe.api

import android.content.Context
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import java.io.*

/**
 * DiskCache decorator for caching the list of the posts by [PostsRequest].
 */
class PostDiskCache(private val diskCache: DiskCache) : Cache<PostsRequest, List<Post>> {

    override fun get(key: PostsRequest): List<Post>? {
        val first = key.page * key.count
        val list = ArrayList<Post>()
        (first until first + key.count).forEach {
            val bytes = diskCache.get(it.toString()) ?: return@forEach
            ByteArrayInputStream(bytes).use { byteStream ->
                list.add(ObjectInputStream(byteStream).readObject() as Post)
            }
        }
        return if (list.isEmpty()) null else list
    }

    override fun add(key: PostsRequest, value: List<Post>) {
        val first = key.page * key.count
        (first until first + key.count).forEachIndexed { localindex, index ->
            val post = value[localindex]
            ByteArrayOutputStream().use {
                ObjectOutputStream(it).apply { writeObject(post); flush() }
                val bytes = it.toByteArray()
                diskCache.add(index.toString(), bytes)
            }
        }
    }

    override fun clear() = diskCache.clear()

    companion object {
        fun getDir(context: Context): File = context.getDir("Posts", Context.MODE_PRIVATE)
    }
}