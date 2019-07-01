package com.makentoshe.api.cache

import android.content.Context
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import java.io.*

/**
 * DiskCache for caching the list of the posts by [PostsRequest].
 * Each post cached by own file with the global index as a filename.
 */
class PostsDiskCache(private val directory: File) : Cache<PostsRequest, List<Post>> {

    /** Returns a list of a posts by key. If there is no posts or something else the null will be returned */
    override fun get(key: PostsRequest): List<Post>? {
        val startPosition = key.page * key.count
        val posts = mutableListOf<Post>()
        for (i in startPosition until startPosition + key.count) {
            val file = File(directory, i.toString())
            // if there is no file in cache
            if (!file.exists()) return null
            // if the selected file is a directory
            if (file.isDirectory) {
                file.delete()
                return null
            }
            try {
                ObjectInputStream(FileInputStream(file)).use {
                    posts.add(it.readObject() as Post)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return posts
    }

    /** Put a list of a posts to a cache */
    override fun add(key: PostsRequest, value: List<Post>) {
        val startPosition = key.page * key.count
        for (i in startPosition until startPosition + key.count) {
            val file = File(directory, i.toString())
            ObjectOutputStream(FileOutputStream(file)).use {
                try {
                    it.writeObject(value[i - startPosition])
                    it.flush()
                } catch (e: IndexOutOfBoundsException) {
                    //its ok - caused when we request 12 items, but receive 8, for example
                }
            }
        }
    }

    /** Clear cache */
    override fun clear() {
        directory.deleteRecursively()
        directory.mkdirs()
    }

    companion object {

        /** Returns an absolute path to a cache directory */
        fun getDir(context: Context): File = context.getDir("Posts", Context.MODE_PRIVATE)

        /** Creates a default cache uses an internal storage */
        fun build(context: Context) =
            PostsDiskCache(getDir(context))
    }
}