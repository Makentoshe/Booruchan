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
            try {
                posts.add(getPost(i.toString())?: return null)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return posts
    }

    private fun getPost(key: String): Post? {
        val file = File(directory, key)
        // if there is no file in cache
        if (!file.exists()) return null
        // if the selected file is a directory
        if (file.isDirectory) return file.delete().let { null }

        ObjectInputStream(FileInputStream(file)).use {
            return it.readObject() as Post
        }
    }

    /** Put a list of a posts to a cache */
    override fun add(key: PostsRequest, value: List<Post>) {
        val startPosition = key.page * key.count
        for (i in startPosition until startPosition + key.count) {
            try {
                addPost(i.toString(), value[i - startPosition])
            } catch (e: IndexOutOfBoundsException) {
                //its ok - caused when we make request for 12 items, but receive only 8, for example
                return
            }
        }
    }

    /** Put a post to a cache */
    private fun addPost(key: String, value: Post) {
        val file = File(directory, key)
        ObjectOutputStream(FileOutputStream(file)).use {
            it.writeObject(value)
            it.flush()
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

